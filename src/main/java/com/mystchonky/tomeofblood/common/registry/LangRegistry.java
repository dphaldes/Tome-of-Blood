package com.mystchonky.tomeofblood.common.registry;

import com.mystchonky.tomeofblood.TomeOfBlood;
import net.minecraft.network.chat.Component;

public class LangRegistry {

    public static final Component SCHOOL_BLOODMAGIC = Component.translatable("ars_nouveau.school." + IntegrationRegistry.BLOODMAGIC.getId());
    public static final Component LOW_LP = Component.translatable(TomeOfBlood.prefix("no_lp").toLanguageKey("alert"));
    public static final Component SERENE_EFFECT = Component.translatable(TomeOfBlood.prefix("serene").toLanguageKey("effect"));
    public static final Component VULNERABLE_EFFECT = Component.translatable(TomeOfBlood.prefix("vulnerable").toLanguageKey("effect"));
    public static final Component MANA_BONUS_UPGRADE = Component.translatable(TomeOfBlood.prefix("mana_bonus").toLanguageKey("living_upgrade"));
}
