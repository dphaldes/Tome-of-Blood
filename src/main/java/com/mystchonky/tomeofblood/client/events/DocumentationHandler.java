package com.mystchonky.tomeofblood.client.events;

import com.breakinblocks.neovitae.common.item.NVItems;
import com.hollingsworth.arsnouveau.api.documentation.DocCategory;
import com.hollingsworth.arsnouveau.api.documentation.ReloadDocumentationEvent;
import com.hollingsworth.arsnouveau.api.documentation.builder.DocEntryBuilder;
import com.hollingsworth.arsnouveau.api.documentation.entry.PerkDiagramEntry;
import com.hollingsworth.arsnouveau.api.registry.DocumentationRegistry;
import com.hollingsworth.arsnouveau.setup.registry.Documentation;
import com.mystchonky.tomeofblood.TomeOfBlood;
import com.mystchonky.tomeofblood.common.registry.ItemRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(value = Dist.CLIENT, modid = TomeOfBlood.MODID)
public class DocumentationHandler {

    @SubscribeEvent
    public static void addEntries(ReloadDocumentationEvent.AddEntries event) {
        DocCategory category = new DocCategory(TomeOfBlood.prefix("tomeofblood"), ItemRegistry.ARCHMAGE_TOME.toStack(), 1000);
        DocumentationRegistry.registerMainCategory(category);

        Documentation.addPage(new DocEntryBuilder(TomeOfBlood.MODID, category, "tome_of_blood")
                .withIcon(ItemRegistry.ARCHMAGE_TOME)
                .withSortNum(1)
                .withIntroPage()
                .withLocalizedText()
                .withLocalizedText());

        Documentation.addPage(new DocEntryBuilder(TomeOfBlood.MODID, category, ItemRegistry.MINT_TEA)
                .withSortNum(2)
                .withIntroPage()
                .withLocalizedText());

        Documentation.addPage(new DocEntryBuilder(TomeOfBlood.MODID, category, "heretics_armor")
                .withIcon(ItemRegistry.LIVING_MAGE_ROBES)
                .withSortNum(3)
                .withIntroPage()
                .withLocalizedText()
                .withPage(PerkDiagramEntry.create(ItemRegistry.LIVING_MAGE_HOOD, ItemRegistry.LIVING_MAGE_ROBES, ItemRegistry.LIVING_MAGE_LEGGINGS, ItemRegistry.LIVING_MAGE_BOOTS))
                .withCraftingPages(TomeOfBlood.prefix("living_mage_hood"), ItemRegistry.LIVING_MAGE_HOOD)
                .withCraftingPages(TomeOfBlood.prefix("living_mage_robes"), ItemRegistry.LIVING_MAGE_ROBES)
                .withCraftingPages(TomeOfBlood.prefix("living_mage_leggings"), ItemRegistry.LIVING_MAGE_LEGGINGS)
                .withCraftingPages(TomeOfBlood.prefix("living_mage_boots"), ItemRegistry.LIVING_MAGE_BOOTS));

        Documentation.addPage(new DocEntryBuilder(TomeOfBlood.MODID, category, "mana_attunement")
                .withIcon(NVItems.UPGRADE_TOME.get())
                .withSortNum(4)
                .withIntroPage()
                .withLocalizedText());
    }
}
