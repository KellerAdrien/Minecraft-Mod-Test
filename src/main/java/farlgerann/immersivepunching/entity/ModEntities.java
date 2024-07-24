package farlgerann.immersivepunching.entity;

import farlgerann.immersivepunching.ImmersivePunching;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {

    public static final EntityType<NaviEntity> NAVI = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(ImmersivePunching.MOD_ID, "navi_entity"),
            EntityType.Builder.create(NaviEntity::new, SpawnGroup.CREATURE)
                    .dimensions(0.35F, 0.6F)
                    .eyeHeight(0.36F)
                    .vehicleAttachment(0.04F)
                    .maxTrackingRange(8)
                    .trackingTickInterval(2)
                    .build());

    public static void load() {
        FabricDefaultAttributeRegistry.register(NAVI, NaviEntity.createMobAttributes());
    }
}
