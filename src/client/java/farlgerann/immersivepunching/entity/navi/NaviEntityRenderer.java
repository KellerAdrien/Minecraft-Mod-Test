package farlgerann.immersivepunching.entity.navi;

import farlgerann.immersivepunching.ImmersivePunchingClient;
import farlgerann.immersivepunching.entity.Navi.NaviEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class NaviEntityRenderer extends MobEntityRenderer<NaviEntity, NaviEntityModel<NaviEntity>> {

    private static final Identifier TEXTURE = Identifier.of("immersivepunching", "textures/entity/navi/navi.png");

    public NaviEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new NaviEntityModel(context.getPart(ImmersivePunchingClient.MODEL_NAVI_LAYER)), 0.4F);
    }

    public Identifier getTexture(NaviEntity naviEntity) {
        return TEXTURE;
    }

    protected int getBlockLight(NaviEntity naviEntity, BlockPos blockPos) {
        return 15;
    }
}
