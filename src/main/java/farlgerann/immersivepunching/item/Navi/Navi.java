package farlgerann.immersivepunching.item.Navi;

import farlgerann.immersivepunching.ImmersivePunching;
// import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Random;

public class Navi extends Item {
    public Navi(Settings settings) {
        super(settings);
    }

    Random rand = new Random();
    float minPitch = 0.75F;
    float maxPitch = 1.35F;

    // register
    public static final SoundEvent NAVI_SPAWN = registerSoundEvent("navi_hey");

    public static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(ImmersivePunching.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    // On right click
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        // Ensure we don't spawn the lightning only on the client.
        // This is to prevent desync.
        if (world.isClient) {
            float n = minPitch + (maxPitch - minPitch) * rand.nextFloat();
            world.playSound(user.getX(), user.getY(), user.getZ(), NAVI_SPAWN, SoundCategory.NEUTRAL, 10000.0F, n,
                    false);

            // BlockPos frontOfPlayer =
            // user.getBlockPos().offset(user.getHorizontalFacing(), 10);

            return TypedActionResult.pass(user.getStackInHand(hand));
        }

        // Spawn the lightning bolt.
        // LightningEntity lightningBolt = new Entity(EntityType.LIGHTNING_BOLT, world);
        // lightningBolt.setPosition(frontOfPlayer.toCenterPos());
        // world.spawnEntity(lightningBolt);

        // Nothing has changed to the item stack,
        // so we just return it how it was.
        return TypedActionResult.fail(user.getStackInHand(hand));
    }
}
