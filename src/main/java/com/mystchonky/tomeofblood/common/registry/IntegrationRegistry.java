package com.mystchonky.tomeofblood.common.registry;

import com.hollingsworth.arsnouveau.api.familiar.AbstractFamiliarHolder;
import com.hollingsworth.arsnouveau.api.perk.PerkSlot;
import com.hollingsworth.arsnouveau.api.registry.FamiliarRegistry;
import com.hollingsworth.arsnouveau.api.registry.GlyphRegistry;
import com.hollingsworth.arsnouveau.api.registry.PerkRegistry;
import com.hollingsworth.arsnouveau.api.registry.SpellCasterRegistry;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.SpellCaster;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.setup.registry.DataComponentRegistry;
import com.mystchonky.tomeofblood.common.glyphs.EffectSentientHarm;
import com.mystchonky.tomeofblood.common.glyphs.EffectSentientWrath;
import com.mystchonky.tomeofblood.common.items.TomeOfBloodItem;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class IntegrationRegistry {

    public static SpellSchool BLOODMAGIC = new SpellSchool("bloodmagic");

    public static List<AbstractSpellPart> registeredSpells = new ArrayList<>();
    public static List<AbstractFamiliarHolder> registeredFamiliars = new ArrayList<>();

    public static void init() {
        registerGlyphs();
        registerFamiliars();
    }

    public static void postInit() {
        registerSounds();
        registerPerkProviders();
        registerSpellCasters();
    }

    public static void registerGlyphs() {
        registerSpellPart(EffectSentientHarm.INSTANCE);
        registerSpellPart(EffectSentientWrath.INSTANCE);
    }

    public static void registerFamiliars() {
    }

    public static void registerSounds() {
    }

    public static void registerSpellPart(AbstractSpellPart spellPart) {
        GlyphRegistry.registerSpell(spellPart);
        registeredSpells.add(spellPart);
    }

    public static void registerFamiliarHolder(AbstractFamiliarHolder familiarHolder) {
        FamiliarRegistry.registerFamiliar(familiarHolder);
        registeredFamiliars.add(familiarHolder);
    }

    public static void registerSpellCasters() {
        SpellCasterRegistry.register(ItemRegistry.NOVICE_TOME.get(), IntegrationRegistry::bloodCaster);
        SpellCasterRegistry.register(ItemRegistry.APPRENTICE_TOME.get(), IntegrationRegistry::bloodCaster);
        SpellCasterRegistry.register(ItemRegistry.ARCHMAGE_TOME.get(), IntegrationRegistry::bloodCaster);
    }

    private static TomeOfBloodItem.BloodBookCaster bloodCaster(ItemStack stack) {
        SpellCaster caster = stack.get(DataComponentRegistry.SPELL_CASTER);
        return caster == null ? null : new TomeOfBloodItem.BloodBookCaster(caster);
    }

    public static void registerPerkProviders() {
        PerkRegistry.registerPerkProvider(ItemRegistry.LIVING_MAGE_HOOD.get(), List.of(
                List.of(PerkSlot.ONE),
                List.of(PerkSlot.ONE, PerkSlot.TWO),
                List.of(PerkSlot.ONE, PerkSlot.THREE)
        ));
        PerkRegistry.registerPerkProvider(ItemRegistry.LIVING_MAGE_ROBES.get(), List.of(
                List.of(),
                List.of(),
                List.of()
        ));
        PerkRegistry.registerPerkProvider(ItemRegistry.LIVING_MAGE_LEGGINGS.get(), List.of(
                List.of(PerkSlot.ONE),
                List.of(PerkSlot.ONE, PerkSlot.TWO),
                List.of(PerkSlot.ONE, PerkSlot.ONE, PerkSlot.THREE)
        ));
        PerkRegistry.registerPerkProvider(ItemRegistry.LIVING_MAGE_BOOTS.get(), List.of(
                List.of(PerkSlot.ONE),
                List.of(PerkSlot.ONE, PerkSlot.ONE),
                List.of(PerkSlot.ONE, PerkSlot.ONE, PerkSlot.TWO)
        ));
    }

}
