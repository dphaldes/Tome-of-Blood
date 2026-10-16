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
        add(MobEffectRegistry.MARKED.get(), "Marked");

        add(LangRegistry.SCHOOL_BLOODMAGIC, "Blood Magic");
        add(LangRegistry.LOW_LP, "Your soul feels weak..");
        add(LangRegistry.MANA_BONUS_UPGRADE, "Mana Attunement");

        add("tomeofblood.section.tomeofblood", "Tome of Blood");
        add("tomeofblood.page.tome_of_blood", "Tome of Blood");
        add("tomeofblood.page1.tome_of_blood", "A Tome of Blood is a spell book with a connection to its owner's Anima network. It lets you break through the mana cost limits of your spells.");
        add("tomeofblood.page2.tome_of_blood", "The tome casts spells with your mana first. If you do not have enough mana, it takes the rest of the cost from your Anima network, at 10 EV per point of mana by default.");
        add("tomeofblood.page3.tome_of_blood", "A Tome of Blood is made by placing a spell book in the Ara Vitae. Each tier of spell book becomes the matching tier of tome and keeps its spells. Tomes can be upgraded to the next tier in the Tabula Vitae. Check JEI for the recipes.");
        add("tomeofblood.page1.mint_tea", "Slightly toxic... Drinking it grants Serene, which gives you 10% more EV when you sacrifice your own health. It has a 5% chance to turn toxic.");
        add("tomeofblood.page2.mint_tea", "Mint Tea is brewed in the Tabula Vitae from Sugar, two Seagrass and a Water Bottle.");
        add("tomeofblood.page.heretics_armor", "Heretic's Armor");
        add("tomeofblood.page1.heretics_armor", "Heretic's Armor is an upgrade of Neo Vitae's Sentient Armor. It combines Sentient Armor with Ars Nouveau's magical armor and has access to both perk threads and sentient upgrades.");
        add("tomeofblood.page2.heretics_armor", "The Robes hold sentient upgrade points like a Sentient Plate, and every piece counts toward a full Sentient set. Each piece is made in the Enchanting Apparatus from the matching Sentient piece and keeps its upgrades.");
        add("tomeofblood.page.mana_attunement", "Mana Attunement");
        add("tomeofblood.page1.mana_attunement", "Mana Attunement is a sentient upgrade that lowers the mana cost of your spells by 5% per level, up to 20%. It is trained by casting spells while wearing a full Sentient set. Max level: 4.");
        add("tomeofblood.page2.mana_attunement", "Level 1: 4 upgrade points\nLevel 2: 10 upgrade points\nLevel 3: 22 upgrade points\nLevel 4: 40 upgrade points");

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
