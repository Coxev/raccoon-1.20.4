package net.coxev.raccoon.item;

import net.coxev.raccoon.Raccoon;
import net.coxev.raccoon.entity.ModEntities;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item RACCOON_SPAWN_EGG = registerItem("raccoon_spawn_egg", new SpawnEggItem(ModEntities.RACCOON, 0x51545B, 0x3E3D3C, new FabricItemSettings()));
    public static final Item MALACHITE = registerItem("malachite", new Item(new Item.Settings()));
    public static final Item MALACHITE_SWORD = registerItem("malachite_sword", new SwordItem(ModToolMaterial.MALACHITE, 3, -2f, new FabricItemSettings()));
    public static final Item MALACHITE_SHOVEL = registerItem("malachite_shovel", new ShovelItem(ModToolMaterial.MALACHITE, 1.5f, -2.8f, new FabricItemSettings()));
    public static final Item MALACHITE_PICKAXE = registerItem("malachite_pickaxe", new PickaxeItem(ModToolMaterial.MALACHITE, 1, -2.5f, new FabricItemSettings()));
    public static final Item MALACHITE_AXE = registerItem("malachite_axe", new AxeItem(ModToolMaterial.MALACHITE, 5, -2.8f, new FabricItemSettings()));
    public static final Item MALACHITE_HOE = registerItem("malachite_hoe", new HoeItem(ModToolMaterial.MALACHITE, -3, 0f, new FabricItemSettings()));
    public static final Item MALACHITE_HELMET = registerItem("malachite_helmet", new ArmorItem(ModArmorMaterials.MALACHITE, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item MALACHITE_CHESTPLATE = registerItem("malachite_chestplate", new ArmorItem(ModArmorMaterials.MALACHITE, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item MALACHITE_LEGGINGS = registerItem("malachite_leggings", new ArmorItem(ModArmorMaterials.MALACHITE, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item MALACHITE_BOOTS = registerItem("malachite_boots", new ArmorItem(ModArmorMaterials.MALACHITE, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries){
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Raccoon.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Raccoon.LOGGER.info("Registering Mod Items for " + Raccoon.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroup);
    }
}
