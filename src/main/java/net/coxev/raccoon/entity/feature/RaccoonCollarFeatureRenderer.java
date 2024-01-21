/*package net.coxev.raccoon.entity.feature;

import net.coxev.raccoon.Raccoon;
import net.coxev.raccoon.entity.client.RaccoonModel;
import net.coxev.raccoon.entity.custom.RaccoonEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.WolfCollarFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class RaccoonCollarFeatureRenderer extends FeatureRenderer<RaccoonEntity, RaccoonModel<RaccoonEntity>> {

    private static final Identifier SKIN = new Identifier(Raccoon.MOD_ID, "textures/entity/raccoon_collar.png");

    public RaccoonCollarFeatureRenderer(FeatureRendererContext<RaccoonEntity, RaccoonModel<RaccoonEntity>> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, RaccoonEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (!entity.isTamed() || entity.isInvisible()) {
            return;
        }
        float[] fs = entity.getCollarColor().getColorComponents();
        RaccoonCollarFeatureRenderer.renderModel(this.getContextModel(), SKIN, matrixStack, vertexConsumerProvider, light, entity, fs[0], fs[1], fs[2]);
    }
}
*/