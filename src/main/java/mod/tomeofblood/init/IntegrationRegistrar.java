package mod.tomeofblood.init;

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
import mod.tomeofblood.content.spell.EffectSentientHarm;
import mod.tomeofblood.content.spell.EffectSentientWrath;
import mod.tomeofblood.content.spellbook.TomeOfBloodItem;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class IntegrationRegistrar {

    public static SpellSchool BLOODMAGIC = new SpellSchool("bloodmagic");

    public static List<AbstractSpellPart> registeredSpells = new ArrayList<>();
    public static List<AbstractFamiliarHolder> registeredFamiliars = new ArrayList<>();

    public static void init() {
        registerGlyphs();
    }

    public static void postInit() {
        registerPerkProviders();
        registerSpellCasters();
    }

    public static void registerGlyphs() {
        registerSpellPart(EffectSentientHarm.INSTANCE);
        registerSpellPart(EffectSentientWrath.INSTANCE);
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
        SpellCasterRegistry.register(ItemRegistrar.NOVICE_TOME.get(), IntegrationRegistrar::bloodCaster);
        SpellCasterRegistry.register(ItemRegistrar.APPRENTICE_TOME.get(), IntegrationRegistrar::bloodCaster);
        SpellCasterRegistry.register(ItemRegistrar.ARCHMAGE_TOME.get(), IntegrationRegistrar::bloodCaster);
    }

    private static TomeOfBloodItem.BloodBookCaster bloodCaster(ItemStack stack) {
        SpellCaster caster = stack.get(DataComponentRegistry.SPELL_CASTER);
        return caster == null ? null : new TomeOfBloodItem.BloodBookCaster(caster);
    }

    public static void registerPerkProviders() {
        PerkRegistry.registerPerkProvider(ItemRegistrar.LIVING_MAGE_HOOD.get(), List.of(
                List.of(PerkSlot.ONE),
                List.of(PerkSlot.ONE, PerkSlot.TWO),
                List.of(PerkSlot.ONE, PerkSlot.THREE)
        ));
        PerkRegistry.registerPerkProvider(ItemRegistrar.LIVING_MAGE_ROBES.get(), List.of(
                List.of(),
                List.of(),
                List.of()
        ));
        PerkRegistry.registerPerkProvider(ItemRegistrar.LIVING_MAGE_LEGGINGS.get(), List.of(
                List.of(PerkSlot.ONE),
                List.of(PerkSlot.ONE, PerkSlot.TWO),
                List.of(PerkSlot.ONE, PerkSlot.ONE, PerkSlot.THREE)
        ));
        PerkRegistry.registerPerkProvider(ItemRegistrar.LIVING_MAGE_BOOTS.get(), List.of(
                List.of(PerkSlot.ONE),
                List.of(PerkSlot.ONE, PerkSlot.ONE),
                List.of(PerkSlot.ONE, PerkSlot.ONE, PerkSlot.TWO)
        ));
    }

}
