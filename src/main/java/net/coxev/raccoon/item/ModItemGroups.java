package net.coxev.raccoon.item;

import net.coxev.raccoon.Raccoon;
import net.coxev.raccoon.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup RACCOON_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(Raccoon.MOD_ID, "raccoon"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.raccoon"))
                    .icon(() -> new ItemStack(ModItems.RACCOON_SPAWN_EGG)).entries((displayContext, entries) -> {
                        entries.add(ModItems.RACCOON_SPAWN_EGG);

                        entries.add(ModItems.MALACHITE);
                        entries.add(ModItems.MALACHITE_SWORD);
                        entries.add(ModItems.MALACHITE_SHOVEL);
                        entries.add(ModItems.MALACHITE_PICKAXE);
                        entries.add(ModItems.MALACHITE_AXE);
                        entries.add(ModItems.MALACHITE_HOE);

                        entries.add(ModItems.MALACHITE_HELMET);
                        entries.add(ModItems.MALACHITE_CHESTPLATE);
                        entries.add(ModItems.MALACHITE_LEGGINGS);
                        entries.add(ModItems.MALACHITE_BOOTS);

                        entries.add(ModBlocks.TRASH_BIN);
                        entries.add(ModBlocks.RACCOONMANINOV);
                        entries.add(ModBlocks.MROIZO);

                        entries.add(ModBlocks.MALACHITE_ORE);
                        entries.add(ModBlocks.DEEPSLATE_MALACHITE_ORE);
                    }).build());

    public static void registerItemGroups(){
        Raccoon.LOGGER.info("Registering Item Groups for " + Raccoon.MOD_ID);
    }
}
