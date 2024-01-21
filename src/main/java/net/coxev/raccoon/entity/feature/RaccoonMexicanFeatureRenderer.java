package net.coxev.raccoon.entity.feature;

import net.coxev.raccoon.Raccoon;
import net.coxev.raccoon.entity.client.RaccoonModel;
import net.coxev.raccoon.entity.custom.RaccoonEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class RaccoonMexicanFeatureRenderer<T extends RaccoonEntity, M extends RaccoonModel<T>> extends FeatureRenderer<T, M> {

    private final FeatureRendererContext<T, M> renderLayerParent;
    private static final Identifier SKIN = new Identifier(Raccoon.MOD_ID, "textures/entity/raccoon_collar.png");

    public RaccoonMexicanFeatureRenderer(FeatureRendererContext<T, M> renderer) {
        super(renderer);
        this.renderLayerParent = renderer;
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider renderTypeBuffer, int light, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        matrixStack.push();
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(netHeadYaw));
        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(headPitch));
        RaccoonMexicanFeatureRenderer.renderModel(this.getContextModel(), SKIN, matrixStack, renderTypeBuffer, light, livingEntity, 50, 50, 50);
        matrixStack.pop();
    }
}