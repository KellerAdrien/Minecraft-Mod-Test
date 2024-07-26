package farlgerann.immersivepunching.entity;

import farlgerann.immersivepunching.ImmersivePunching;
import farlgerann.immersivepunching.entity.Navi.NaviEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModEntities {

        public static final SoundEvent NAVI_YAH = registerSoundEvent("navi_yah");
        public static final SoundEvent NAVI_HELLO = registerSoundEvent("navi_hello");
        public static final SoundEvent NAVI_SPAWN = registerSoundEvent("navi_spawn");
        public static final SoundEvent NAVI_LISTEN = registerSoundEvent("navi_listen");
        public static final SoundEvent NAVI_LEAVE = registerSoundEvent("navi_leave");
        public static final SoundEvent NAVI_BUMP = registerSoundEvent("navi_bump");

        public static SoundEvent registerSoundEvent(String name) {
                Identifier id = Identifier.of(ImmersivePunching.MOD_ID, name);
                return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
        }

        public static final EntityType<NaviEntity> NAVI = Registry.register(Registries.ENTITY_TYPE,
                        Identifier.of(ImmersivePunching.MOD_ID, "navi_entity"),
                        EntityType.Builder.create(NaviEntity::new, SpawnGroup.CREATURE)
                                        .dimensions(0.35F, 0.6F)
                                        .eyeHeight(0.36F)
                                        .vehicleAttachment(0.04F)
                                        .maxTrackingRange(8)
                                        .trackingTickInterval(2)
                                        .build());

        public static void load() {
                FabricDefaultAttributeRegistry.register(NAVI,
                                NaviEntity.createMobAttributes()
                                                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                                                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.1F)
                                                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.1F)
                                                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0)
                                                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0));
    }
}
