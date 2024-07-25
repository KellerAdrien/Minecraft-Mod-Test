package farlgerann.immersivepunching;

import farlgerann.immersivepunching.entity.ModEntities;
import farlgerann.immersivepunching.entity.navi.NaviEntityModel;
import farlgerann.immersivepunching.entity.navi.NaviEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ImmersivePunchingClient implements ClientModInitializer {
	public static final EntityModelLayer MODEL_NAVI_LAYER = new EntityModelLayer(
			Identifier.of("immersivepunching", "navi"), "main");
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		EntityRendererRegistry.register(ModEntities.NAVI, (context) -> {
			return new NaviEntityRenderer(context);
		});

		EntityModelLayerRegistry.registerModelLayer(MODEL_NAVI_LAYER, NaviEntityModel::getTexturedModelData);
	}
}