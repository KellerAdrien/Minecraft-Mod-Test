package farlgerann.immersivepunching.entity.Navi;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.BlockPosLookTarget;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.LookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.FleeTask;
import net.minecraft.entity.ai.brain.task.GiveInventoryToLookTargetTask;
import net.minecraft.entity.ai.brain.task.GoTowardsLookTargetTask;
import net.minecraft.entity.ai.brain.task.LookAroundTask;
import net.minecraft.entity.ai.brain.task.LookAtMobWithIntervalTask;
import net.minecraft.entity.ai.brain.task.MoveToTargetTask;
import net.minecraft.entity.ai.brain.task.RandomTask;
import net.minecraft.entity.ai.brain.task.StayAboveWaterTask;
import net.minecraft.entity.ai.brain.task.StrollTask;
import net.minecraft.entity.ai.brain.task.TemptationCooldownTask;
import net.minecraft.entity.ai.brain.task.WaitTask;
import net.minecraft.entity.ai.brain.task.WalkToNearestVisibleWantedItemTask;
import net.minecraft.entity.ai.brain.task.WalkTowardsLookTargetTask;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class NaviBrain {

    protected static Brain<?> create(Brain<NaviEntity> brain) {
        addCoreActivities(brain);
        addIdleActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreActivities(Brain<NaviEntity> brain) {
        brain.setTaskList(
                Activity.CORE,
                0,
                ImmutableList.of(
                        new StayAboveWaterTask(0.8F),
                        new FleeTask<>(2.5F),
                        new LookAroundTask(45, 90),
                        new MoveToTargetTask(),
                        new TemptationCooldownTask(MemoryModuleType.LIKED_NOTEBLOCK_COOLDOWN_TICKS),
                        new TemptationCooldownTask(MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS)));
    }

    private static void addIdleActivities(Brain<NaviEntity> brain) {
        brain.setTaskList(
                Activity.IDLE,
                ImmutableList.of(
                        Pair.of(0, WalkToNearestVisibleWantedItemTask.create(navi -> true, 1.75F, true, 32)),
                        Pair.of(1, new GiveInventoryToLookTargetTask<>(NaviBrain::getLookTarget, 2.25F, 20)),
                        Pair.of(2,
                                WalkTowardsLookTargetTask.create(NaviBrain::getLookTarget,
                                        Predicate.not(NaviBrain::hasNearestVisibleWantedItem), 4, 16, 2.25F)),
                        Pair.of(3, LookAtMobWithIntervalTask.follow(6.0F, UniformIntProvider.create(30, 60))),
                        Pair.of(
                                4,
                                new RandomTask<>(
                                        ImmutableList.of(Pair.of(StrollTask.createSolidTargeting(1.0F), 2),
                                                Pair.of(GoTowardsLookTargetTask.create(1.0F, 3), 2),
                                                Pair.of(new WaitTask(30, 60), 1))))),
                ImmutableSet.of());
    }

    public static void updateActivities(NaviEntity navi) {
        navi.getBrain().resetPossibleActivities(ImmutableList.of(Activity.IDLE));
    }

    public static void rememberNoteBlock(LivingEntity navi, BlockPos pos) {
        Brain<?> brain = navi.getBrain();
        GlobalPos globalPos = GlobalPos.create(navi.getWorld().getRegistryKey(), pos);
        Optional<GlobalPos> optional = brain.getOptionalRegisteredMemory(MemoryModuleType.LIKED_NOTEBLOCK);
        if (optional.isEmpty()) {
            brain.remember(MemoryModuleType.LIKED_NOTEBLOCK, globalPos);
            brain.remember(MemoryModuleType.LIKED_NOTEBLOCK_COOLDOWN_TICKS, 600);
        } else if (((GlobalPos) optional.get()).equals(globalPos)) {
            brain.remember(MemoryModuleType.LIKED_NOTEBLOCK_COOLDOWN_TICKS, 600);
        }
    }

    private static Optional<LookTarget> getLookTarget(LivingEntity navi) {
        Brain<?> brain = navi.getBrain();
        Optional<GlobalPos> optional = brain.getOptionalRegisteredMemory(MemoryModuleType.LIKED_NOTEBLOCK);
        if (optional.isPresent()) {
            GlobalPos globalPos = (GlobalPos) optional.get();
            if (shouldGoTowardsNoteBlock(navi, brain, globalPos)) {
                return Optional.of(new BlockPosLookTarget(globalPos.pos().up()));
            }

            brain.forget(MemoryModuleType.LIKED_NOTEBLOCK);
        }

        return getLikedLookTarget(navi);
    }

    private static boolean hasNearestVisibleWantedItem(LivingEntity entity) {
        Brain<?> brain = entity.getBrain();
        return brain.hasMemoryModule(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
    }

    private static boolean shouldGoTowardsNoteBlock(LivingEntity navi, Brain<?> brain, GlobalPos pos) {
        Optional<Integer> optional = brain.getOptionalRegisteredMemory(MemoryModuleType.LIKED_NOTEBLOCK_COOLDOWN_TICKS);
        World world = navi.getWorld();
        return world.getRegistryKey() == pos.dimension() && world.getBlockState(pos.pos()).isOf(Blocks.NOTE_BLOCK)
                && optional.isPresent();
    }

    private static Optional<LookTarget> getLikedLookTarget(LivingEntity navi) {
        return getLikedPlayer(navi).map(player -> new EntityLookTarget(player, true));
    }

    public static Optional<ServerPlayerEntity> getLikedPlayer(LivingEntity navi) {
        World world = navi.getWorld();
        if (!world.isClient() && world instanceof ServerWorld serverWorld) {
            Optional<UUID> optional = navi.getBrain().getOptionalRegisteredMemory(MemoryModuleType.LIKED_PLAYER);
            if (optional.isPresent()) {
                if (serverWorld.getEntity((UUID) optional.get()) instanceof ServerPlayerEntity serverPlayerEntity
                        && (serverPlayerEntity.interactionManager.isSurvivalLike()
                                || serverPlayerEntity.interactionManager.isCreative())
                        && serverPlayerEntity.isInRange(navi, 64.0)) {
                    return Optional.of(serverPlayerEntity);
                }

                return Optional.empty();
            }
        }

        return Optional.empty();
    }
}
