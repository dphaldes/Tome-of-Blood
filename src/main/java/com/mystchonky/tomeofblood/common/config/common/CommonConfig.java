package com.mystchonky.tomeofblood.common.config.common;

import net.neoforged.neoforge.common.ModConfigSpec;

public class CommonConfig {

    public final ModConfigSpec.ConfigValue<Integer> CONVERSION_RATE;
    public final ModConfigSpec.ConfigValue<Double> LIVING_MAGE_SCALE;

    public CommonConfig(ModConfigSpec.Builder builder) {
        CONVERSION_RATE = builder.comment("Conversion rate of LP into player mana").define("conversionRate", 10);
        LIVING_MAGE_SCALE = builder.comment("Scale at which Living Mage Armor gains experience compared to normal Living Armor").define("livingExpScale", 0.8);
    }
}
