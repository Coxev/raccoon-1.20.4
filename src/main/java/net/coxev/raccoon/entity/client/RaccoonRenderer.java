package net.coxev.raccoon.entity.client;

import net.coxev.raccoon.Raccoon;
import net.coxev.raccoon.entity.custom.RaccoonEntity;
import net.coxev.raccoon.entity.feature.RaccoonHeldItemFeatureRenderer;
import net.coxev.raccoon.entity.feature.RaccoonMexicanFeatureRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.FoxHeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RaccoonRenderer extends MobEntityRenderer<RaccoonEntity, RaccoonModel<RaccoonEntity>> {
    public static final Identifier TEXTURE = new Identifier(Raccoon.MOD_ID, "textures/entity/raccoon.png");
    public static final Identifier TEXTURE_MEXICAN = new Identifier(Raccoon.MOD_ID, "textures/entity/raccoon_mexican.png");
    public static final Identifier TEXTURE_TAMED = new Identifier(Raccoon.MOD_ID, "textures/entity/raccoon_tamed.png");

    public RaccoonRenderer(EntityRendererFactory.Context context) {
        super(context, new RaccoonModel<>(context.getPart(ModModelLayers.RACCOON)), 0.6f);
        this.addFeature(new RaccoonHeldItemFeatureRenderer(this, context.getHeldItemRenderer()));
    }

    @Override
    public Identifier getTexture(RaccoonEntity entity) {
        if(entity.isTamed()){
            return TEXTURE_TAMED;
        } else if(entity.isMexican()){
            return TEXTURE_MEXICAN;
        } else {
            return TEXTURE;
        }
    }

    @Override
    public void render(RaccoonEntity raccoon, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        if(raccoon.isBaby()) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(1f, 1f, 1f);
        }
        super.render(raccoon, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
