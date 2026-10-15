package com.mystchonky.tomeofblood.common.registry;

import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.mystchonky.tomeofblood.TomeOfBlood;
import com.mystchonky.tomeofblood.common.items.LivingMageArmorItem;
import com.mystchonky.tomeofblood.common.items.MintTeaItem;
import com.mystchonky.tomeofblood.common.items.TomeOfBloodItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import wayoftime.bloodmagic.common.registries.BloodMagicCreativeTabs;
import wayoftime.bloodmagic.core.living.LivingStats;

public class ItemRegistry {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TomeOfBlood.MODID);
    private static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TomeOfBlood.MODID);

    public static final DeferredItem<TomeOfBloodItem> NOVICE_TOME = ITEMS.register("novice_tome_of_blood", () -> new TomeOfBloodItem(new Item.Properties().stacksTo(1), SpellTier.ONE));
    public static final DeferredItem<TomeOfBloodItem> APPRENTICE_TOME = ITEMS.register("apprentice_tome_of_blood", () -> new TomeOfBloodItem(new Item.Properties().stacksTo(1), SpellTier.TWO));
    public static final DeferredItem<TomeOfBloodItem> ARCHMAGE_TOME = ITEMS.register("archmage_tome_of_blood", () -> new TomeOfBloodItem(new Item.Properties().stacksTo(1), SpellTier.THREE));

    public static final DeferredItem<MintTeaItem> MINT_TEA = ITEMS.register("mint_tea", () -> new MintTeaItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<LivingMageArmorItem> LIVING_MAGE_HOOD = ITEMS.register("living_mage_hood", () -> new LivingMageArmorItem(ArmorItem.Type.HELMET));
    public static final DeferredItem<LivingMageArmorItem> LIVING_MAGE_ROBES = ITEMS.register("living_mage_robes", () -> new LivingMageArmorItem(ArmorItem.Type.CHESTPLATE));
    public static final DeferredItem<LivingMageArmorItem> LIVING_MAGE_LEGGINGS = ITEMS.register("living_mage_leggings", () -> new LivingMageArmorItem(ArmorItem.Type.LEGGINGS));
    public static final DeferredItem<LivingMageArmorItem> LIVING_MAGE_BOOTS = ITEMS.register("living_mage_boots", () -> new LivingMageArmorItem(ArmorItem.Type.BOOTS));

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATIVE_TAB = CREATIVE_TABS.register("tome_of_blood", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.tomeofblood.tome_of_blood"))
            .icon(() -> ItemRegistry.ARCHMAGE_TOME.get().getDefaultInstance())
            .withTabsBefore(BloodMagicCreativeTabs.BLOODMAGIC_UPGRADES.getKey())
            .displayItems((params, output) -> {
                output.accept(NOVICE_TOME.get());
                output.accept(APPRENTICE_TOME.get());
                output.accept(ARCHMAGE_TOME.get());
                output.accept(MINT_TEA.get());
                output.accept(LIVING_MAGE_HOOD.get());
                var armor = LIVING_MAGE_ROBES.get();
                ItemStack stack = armor.getDefaultInstance();
                armor.updateLivingStats(stack, new LivingStats());
                output.accept(stack);
                output.accept(LIVING_MAGE_LEGGINGS.get());
                output.accept(LIVING_MAGE_BOOTS.get());
            })
            .build());

    public static void register(IEventBus modBus) {
        ITEMS.register(modBus);
        CREATIVE_TABS.register(modBus);
    }
}
