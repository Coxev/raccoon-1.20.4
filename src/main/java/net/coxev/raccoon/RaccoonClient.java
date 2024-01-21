package net.coxev.raccoon;

import net.coxev.raccoon.block.ModBlocks;
import net.coxev.raccoon.entity.ModEntities;
import net.coxev.raccoon.entity.client.ModModelLayers;
import net.coxev.raccoon.entity.client.RaccoonModel;
import net.coxev.raccoon.entity.client.RaccoonRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.block.entity.BedBlockEntityRenderer;

public class RaccoonClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.RACCOON, RaccoonRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.RACCOON, RaccoonModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.RACCOONMANINOV, BedBlockEntityRenderer::getHeadTexturedModelData);
        ModBlocks.registerClient();
    }
}
