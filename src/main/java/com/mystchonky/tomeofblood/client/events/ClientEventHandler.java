package com.mystchonky.tomeofblood.client.events;

import com.hollingsworth.arsnouveau.api.perk.ArmorPerkHolder;
import com.hollingsworth.arsnouveau.api.perk.IPerkHolder;
import com.mystchonky.tomeofblood.TomeOfBlood;
import com.mystchonky.tomeofblood.common.registry.ItemRegistry;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import static com.hollingsworth.arsnouveau.api.util.PerkUtil.getPerkHolder;

@EventBusSubscriber(value = Dist.CLIENT, modid = TomeOfBlood.MODID)
public class ClientEventHandler {

    @SubscribeEvent
    public static void initItemColors(final RegisterColorHandlersEvent.Item event) {
        event.register((stack, color) -> color > 0 ? -1 : colorFromArmor(stack), ItemRegistry.LIVING_MAGE_HOOD);
        event.register((stack, color) -> color > 0 ? -1 : colorFromArmor(stack), ItemRegistry.LIVING_MAGE_ROBES);
        event.register((stack, color) -> color > 0 ? -1 : colorFromArmor(stack), ItemRegistry.LIVING_MAGE_LEGGINGS);
        event.register((stack, color) -> color > 0 ? -1 : colorFromArmor(stack), ItemRegistry.LIVING_MAGE_BOOTS);
    }


    public static int colorFromArmor(ItemStack stack) {
        IPerkHolder<ItemStack> holder = getPerkHolder(stack);
        if (!(holder instanceof ArmorPerkHolder armorPerkHolder))
            return DyeColor.RED.getTextColor();
        return DyeColor.byName(armorPerkHolder.getColor(), DyeColor.RED).getTextColor();
    }

}
