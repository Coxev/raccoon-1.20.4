package net.coxev.raccoon.entity.feature;

import net.coxev.raccoon.entity.client.RaccoonModel;
import net.coxev.raccoon.entity.custom.RaccoonEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;


public class RaccoonHeldItemFeatureRenderer extends FeatureRenderer<RaccoonEntity, RaccoonModel<RaccoonEntity>> {
    private final HeldItemRenderer heldItemRenderer;

    public RaccoonHeldItemFeatureRenderer(FeatureRendererContext<RaccoonEntity, RaccoonModel<RaccoonEntity>> context, HeldItemRenderer heldItemRenderer) {
        super(context);
        this.heldItemRenderer = heldItemRenderer;
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, RaccoonEntity entity, float limbAngle, float limbDistance, float age, float tickDelta, float headYaw, float headPitch) {
        float m;
        if(!entity.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()){
            ItemStack heldItem = entity.getEquippedStack(EquipmentSlot.MAINHAND);
            matrixStack.push();
            matrixStack.translate(-0.05f, 1.03f, 0.35f);
            matrixStack.translate(this.getContextModel().head.pivotX / 16.0f, this.getContextModel().head.pivotY / 16.0f, this.getContextModel().head.pivotZ / 16.0f);
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.clamp(headYaw, -20.0f, 20.0f) * 1.1f));
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(MathHelper.clamp(headPitch, -10.0f, 10.0f) * 1.1f));
            matrixStack.translate(0.06f, 0.27f, -0.5f);
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0f));
            this.heldItemRenderer.renderItem(entity, heldItem, ModelTransformationMode.GROUND, false, matrixStack, vertexConsumerProvider, light);
            matrixStack.pop();
        }
    }
}
