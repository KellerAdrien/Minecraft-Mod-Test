package farlgerann.immersivepunching.entity.navi;

import farlgerann.immersivepunching.entity.Navi.NaviEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class NaviEntityRenderer extends MobEntityRenderer<NaviEntity, NaviEntityModel> {

    private static final Identifier TEXTURE = Identifier.of("immersivepunching", "textures/entity/navi/navitoad.png");

    public NaviEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new NaviEntityModel(context.getPart(EntityModelLayers.ALLAY)), 0.4F);
        this.addFeature(new HeldItemFeatureRenderer(this, context.getHeldItemRenderer()));
    }

    public Identifier getTexture(NaviEntity naviEntity) {
        return TEXTURE;
    }

    protected int getBlockLight(NaviEntity naviEntity, BlockPos blockPos) {
        return 15;
    }
}
