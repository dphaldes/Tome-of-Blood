package com.mystchonky.tomeofblood.datagen;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.mystchonky.tomeofblood.TomeOfBlood;
import com.mystchonky.tomeofblood.common.registry.IntegrationRegistry;
import com.mystchonky.tomeofblood.common.registry.ItemRegistry;
import com.mystchonky.tomeofblood.common.registry.LangRegistry;
import com.mystchonky.tomeofblood.common.registry.MobEffectRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.HashMap;
import java.util.Map;

public class LangProvider extends LanguageProvider {

    public LangProvider(PackOutput output) {
        super(output, TomeOfBlood.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(ItemRegistry.NOVICE_TOME.get(), "Novice Tome of Blood");
        add(ItemRegistry.APPRENTICE_TOME.get(), "Apprentice Tome of Blood");
        add(ItemRegistry.ARCHMAGE_TOME.get(), "Archmage Tome of Blood");
        add(ItemRegistry.MINT_TEA.get(), "Mint Tea");
        add(ItemRegistry.LIVING_MAGE_HOOD.get(), "Heretic's Hood");
        add(ItemRegistry.LIVING_MAGE_ROBES.get(), "Heretic's Robes");
        add(ItemRegistry.LIVING_MAGE_LEGGINGS.get(), "Heretic's Leggings");
        add(ItemRegistry.LIVING_MAGE_BOOTS.get(), "Heretic's Boots");

        add("itemGroup.tomeofblood.tome_of_blood", "Tome Of Blood");

        add(MobEffectRegistry.SERENE.get(), "Serene");
        add(MobEffectRegistry.VULNERABLE.get(), "Vulnerable");

        add(LangRegistry.SCHOOL_BLOODMAGIC, "Blood Magic");
        add(LangRegistry.LOW_LP, "Your soul feels weak..");
        add(LangRegistry.MANA_BONUS_UPGRADE, "Mana Attunement");

        IntegrationRegistry.registeredSpells.forEach(spell -> {
            add(TomeOfBlood.MODID + ".glyph_name." + spell.getRegistryName().getPath(), spell.getName());
            add(TomeOfBlood.MODID + ".glyph_desc." + spell.getRegistryName().getPath(), spell.getBookDescription());

            Map<AbstractAugment, String> augmentDescriptions = new HashMap<>();
            spell.addAugmentDescriptions(augmentDescriptions);
            augmentDescriptions.forEach((augment, description) -> add(spell.getAugmentLangKey(augment), description));
        });
    }

    private void add(Component component, String value) {
        if (component.getContents() instanceof TranslatableContents contents) {
            add(contents.getKey(), value);
        }
    }
}
