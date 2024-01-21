package net.coxev.raccoon.datagen;

import net.coxev.raccoon.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.RACCOON_SPAWN_EGG,
                new Model(Optional.of(new Identifier("item/template_spawn_egg")), Optional.empty()));
        itemModelGenerator.register(ModItems.MALACHITE_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.MALACHITE_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.MALACHITE_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.MALACHITE_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.MALACHITE_HOE, Models.HANDHELD);

        itemModelGenerator.registerArmor(((ArmorItem) ModItems.MALACHITE_HELMET));
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.MALACHITE_CHESTPLATE));
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.MALACHITE_LEGGINGS));
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.MALACHITE_BOOTS));

    }
}
