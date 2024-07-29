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
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)

public class NaviEntityModel<T extends NaviEntity> extends SinglePartEntityModel<NaviEntity> {
        private final ModelPart head;
        private final ModelPart body;
        private final ModelPart leftWing;
        private final ModelPart rightWing;
        private ModelPart root;

        public NaviEntityModel(ModelPart root) {
                this.root = root.getChild(EntityModelPartNames.ROOT);
                this.head = this.root.getChild(EntityModelPartNames.HEAD);
                this.body = this.root.getChild(EntityModelPartNames.BODY);
                this.rightWing = this.body.getChild(EntityModelPartNames.RIGHT_WING);
                this.leftWing = this.body.getChild(EntityModelPartNames.LEFT_WING);
        }

        public static TexturedModelData getTexturedModelData() {
                ModelData modelData = new ModelData();
                ModelPartData modelPartData = modelData.getRoot();
                ModelPartData modelPartData2 = modelPartData.addChild(EntityModelPartNames.ROOT,
                                ModelPartBuilder.create(),
                                ModelTransform.pivot(0.0F, 23.5F, 0.0F));
                modelPartData2.addChild(
                                EntityModelPartNames.HEAD,
                                ModelPartBuilder.create(),
                                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
                ModelPartData modelPartData3 = modelPartData2.addChild("body", ModelPartBuilder.create(),
                                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

                ModelPartData navi = modelPartData3.addChild("navi", ModelPartBuilder.create().uv(0, 13)
                                .cuboid(-3.25F, -4.75F, -5.5F, 5.0F, 5.0F, 5.0F, new Dilation(0.0F))
                                .uv(0, 25).cuboid(-2.75F, -4.25F, -5.75F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F))
                                .uv(10, 25).cuboid(-2.75F, -4.25F, -1.25F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F))
                                .uv(0, 32).cuboid(-2.75F, -5.0F, -5.0F, 4.0F, 1.0F, 4.0F, new Dilation(0.0F))
                                .uv(0, 39).cuboid(1.0F, -4.25F, -5.0F, 1.0F, 4.0F, 4.0F, new Dilation(0.0F))
                                .uv(18, 32).cuboid(-2.75F, -0.5F, -5.0F, 4.0F, 1.0F, 4.0F, new Dilation(0.0F))
                                .uv(13, 39).cuboid(-3.5F, -4.25F, -5.0F, 1.0F, 4.0F, 4.0F, new Dilation(0.0F)),
                                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

                ModelPartData left_wing = modelPartData3.addChild("left_wing",
                                ModelPartBuilder.create().uv(0, -12).cuboid(1.25F, -7.5F,
                                                0.75F, 0.0F, 12.0F, 12.0F, new Dilation(0.0F)),
                                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

                ModelPartData right_wing = modelPartData3
                                .addChild("right_wing",
                                                ModelPartBuilder.create().uv(0, -12).cuboid(0.75F, -7.75F, 0.5F, 0.0F,
                                                                12.0F,
                                                                12.0F, new Dilation(0.0F)),
                                                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
                return TexturedModelData.of(modelData, 64, 64);
        }

        @Override
        public ModelPart getPart() {
                return this.root;
        }

        @Override
        public void setAngles(NaviEntity naviEntity, float f, float g, float h, float i, float j) {
                this.getPart().traverse().forEach(ModelPart::resetTransform);
                float k = h * 20.0F * (float) (Math.PI / 180.0) + f;
                float l = MathHelper.cos(k) * (float) Math.PI * 0.15F + g;
                float m = h - (float) naviEntity.age;
                float n = h * 9.0F * (float) (Math.PI / 180.0);
                float o = Math.min(g / 0.3F, 1.0F);
                float p = 1.0F - o;
                float q = naviEntity.method_43397(m);
                if (naviEntity.isDancing()) {
                        float r = h * 8.0F * (float) (Math.PI / 180.0) + g;
                        float s = MathHelper.cos(r) * 16.0F * (float) (Math.PI / 180.0);
                        float t = naviEntity.method_44368(m);
                        float u = MathHelper.cos(r) * 14.0F * (float) (Math.PI / 180.0);
                        float v = MathHelper.cos(r) * 30.0F * (float) (Math.PI / 180.0);
                        this.root.yaw = naviEntity.isSpinning() ? (float) (Math.PI * 4) * t : this.root.yaw;
                        this.root.roll = s * (1.0F - t);
                        this.head.yaw = v * (1.0F - t);
                        this.head.roll = u * (1.0F - t);
                } else {
                        this.head.pitch = j * (float) (Math.PI / 180.0);
                        this.head.yaw = i * (float) (Math.PI / 180.0);
                }

                this.rightWing.pitch = 0.43633232F * (1.0F - o);
                this.rightWing.yaw = (float) (-Math.PI / 4) + l;
                this.leftWing.pitch = 0.43633232F * (1.0F - o);
                this.leftWing.yaw = (float) (Math.PI / 4) - l;
                this.body.pitch = o * (float) (Math.PI / 4);
                this.root.pivotY = this.root.pivotY + (float) Math.cos((double) n) * 0.25F * p;

        }
}
