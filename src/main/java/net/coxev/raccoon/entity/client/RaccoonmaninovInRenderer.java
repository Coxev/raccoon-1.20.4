/*package net.coxev.raccoon.entity.client;

import net.coxev.raccoon.Raccoon;
import net.coxev.raccoon.block.ModBlocks;
import net.coxev.raccoon.entity.custom.RaccoonEntity;
import net.coxev.raccoon.entity.feature.RaccoonHeldItemFeatureRenderer;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BedBlockEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BedBlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class RaccoonmaninovInRenderer extends BedBlockEntityRenderer {
    private final ModelPart raccoonmaninov;
    private final BlockRenderManager blockRenderManager;

    public RaccoonmaninovInRenderer(BlockEntityRendererFactory.Context context, ModelPart raccoonmaninov, BlockRenderManager blockRenderManager) {
        super(context);
        this.raccoonmaninov = context.getLayerModelPart(ModModelLayers.RACCOONMANINOV);
        this.blockRenderManager = blockRenderManager;
    }

    @Override
    public void render(BedBlockEntity bedBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
        BlockState blockState = ModBlocks.RACCOONMANINOV.getDefaultState();
        this.blockRenderManager.renderBlockAsEntity(blockState, matrixStack, vertexConsumerProvider, i, OverlayTexture.DEFAULT_UV);
        matrixStack.pop();
    }
}*/
