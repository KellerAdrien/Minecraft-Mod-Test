package farlgerann.immersivepunching.entity.navi;

import farlgerann.immersivepunching.entity.Navi.NaviEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class NaviEntityModel extends SinglePartEntityModel<NaviEntity> {
        private final ModelPart root;
        private final ModelPart body;
        private final ModelPart rightWing;
        private final ModelPart leftWing;

        public NaviEntityModel(ModelPart root) {
                super(RenderLayer::getEntityTranslucent);
                this.root = root.getChild(EntityModelPartNames.ROOT);
                this.body = this.root.getChild(EntityModelPartNames.BODY);
                this.rightWing = this.body.getChild(EntityModelPartNames.RIGHT_WING);
                this.leftWing = this.body.getChild(EntityModelPartNames.LEFT_WING);
        }

        @Override
        public ModelPart getPart() {
                return this.root;
        }

        public static TexturedModelData getTexturedModelData() {
                ModelData modelData = new ModelData();
                ModelPartData modelPartData = modelData.getRoot();
                ModelPartData modelPartData2 = modelPartData.addChild(EntityModelPartNames.ROOT,
                                ModelPartBuilder.create(),
                                ModelTransform.pivot(0.0F, 23.5F, 0.0F));
                modelPartData2.addChild(
                                EntityModelPartNames.HEAD,
                                ModelPartBuilder.create().uv(0, 0).cuboid(-2.5F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F,
                                                new Dilation(0.0F)),
                                ModelTransform.pivot(0.0F, -3.99F, 0.0F));
                ModelPartData modelPartData3 = modelPartData2.addChild(
                                EntityModelPartNames.BODY,
                                ModelPartBuilder.create()
                                                .uv(0, 10)
                                                .cuboid(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 2.0F, new Dilation(0.0F))
                                                .uv(0, 16)
                                                .cuboid(-1.5F, 0.0F, -1.0F, 3.0F, 5.0F, 2.0F, new Dilation(-0.2F)),
                                ModelTransform.pivot(0.0F, -4.0F, 0.0F));
                modelPartData3.addChild(
                                EntityModelPartNames.RIGHT_WING,
                                ModelPartBuilder.create().uv(16, 14).cuboid(0.0F, 1.0F, 0.0F, 0.0F, 5.0F, 8.0F,
                                                new Dilation(0.0F)),
                                ModelTransform.pivot(-0.5F, 0.0F, 0.6F));
                modelPartData3.addChild(
                                EntityModelPartNames.LEFT_WING,
                                ModelPartBuilder.create().uv(16, 14).cuboid(0.0F, 1.0F, 0.0F, 0.0F, 5.0F, 8.0F,
                                                new Dilation(0.0F)),
                                ModelTransform.pivot(0.5F, 0.0F, 0.6F));
                return TexturedModelData.of(modelData, 32, 32);
        }

        public void setAngles(NaviEntity NaviEntity, float f, float g, float h, float i, float j) {
                this.getPart().traverse().forEach(ModelPart::resetTransform);
                float k = h * 20.0F * (float) (Math.PI / 180.0) + f;
                float l = MathHelper.cos(k) * (float) Math.PI * 0.15F + g;
                float n = h * 9.0F * (float) (Math.PI / 180.0);
                float o = Math.min(g / 0.3F, 1.0F);
                float p = 1.0F - o;

                this.rightWing.pitch = 0.43633232F * (1.0F - o);
                this.rightWing.yaw = (float) (-Math.PI / 4) + l;
                this.leftWing.pitch = 0.43633232F * (1.0F - o);
                this.leftWing.yaw = (float) (Math.PI / 4) - l;
                this.body.pitch = o * (float) (Math.PI / 4);
                this.root.pivotY = this.root.pivotY + (float) Math.cos((double) n) * 0.25F * p;


        }
}
