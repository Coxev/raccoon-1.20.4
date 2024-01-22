package net.coxev.raccoon.world;

import net.coxev.raccoon.Raccoon;
import net.coxev.raccoon.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;

import java.util.List;

public class ModConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> MALACHITE_ORE_SMALL_KEY = registerKey("malachite_ore_small");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MALACHITE_ORE_MEDIUM_KEY = registerKey("malachite_ore_medium");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MALACHITE_ORE_LARGE_KEY = registerKey("malachite_ore_large");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MALACHITE_ORE_BURIED_KEY = registerKey("malachite_ore_buried");

    public static void boostrap(Registerable<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplacables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplacables = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreFeatureConfig.Target> overworldMalachiteOres =
                List.of(OreFeatureConfig.createTarget(stoneReplacables, ModBlocks.MALACHITE_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplacables, ModBlocks.DEEPSLATE_MALACHITE_ORE.getDefaultState()));

        register(context, MALACHITE_ORE_SMALL_KEY, Feature.ORE, new OreFeatureConfig(overworldMalachiteOres, 4, 0.5f));
        register(context, MALACHITE_ORE_MEDIUM_KEY, Feature.ORE, new OreFeatureConfig(overworldMalachiteOres, 8, 0.5f));
        register(context, MALACHITE_ORE_LARGE_KEY, Feature.ORE, new OreFeatureConfig(overworldMalachiteOres, 12, 0.7f));
        register(context, MALACHITE_ORE_BURIED_KEY, Feature.ORE, new OreFeatureConfig(overworldMalachiteOres, 8, 1.0f));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(Raccoon.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

}
