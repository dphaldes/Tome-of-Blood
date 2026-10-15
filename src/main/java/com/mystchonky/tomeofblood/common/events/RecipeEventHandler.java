package com.mystchonky.tomeofblood.common.events;

import com.hollingsworth.arsnouveau.common.items.SpellBook;
import com.mystchonky.tomeofblood.TomeOfBlood;
import com.mystchonky.tomeofblood.common.items.TomeOfBloodItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import wayoftime.bloodmagic.api.event.BloodMagicCraftedEvent;

import java.util.Arrays;

@EventBusSubscriber(modid = TomeOfBlood.MODID)
public class RecipeEventHandler {

    @SubscribeEvent
    public static void altarSpellbookRecipe(BloodMagicCraftedEvent.Altar event) {
        if (event.getOutput().getItem() instanceof TomeOfBloodItem) {
            if (event.getInputs()[0].getItem() instanceof SpellBook) {
                ItemStack tome = event.getOutput().copy();
                tome.setTag(event.getInputs()[0].getTag());
                event.setOutput(tome);
            }
        }
    }

    @SubscribeEvent
    public static void alchemyTableSpellbookRecipe(BloodMagicCraftedEvent.AlchemyTable event) {
        if (event.getOutput().getItem() instanceof TomeOfBloodItem) {
            Arrays.stream(event.getInputs())
                    .filter(itemStack -> itemStack.getItem() instanceof TomeOfBloodItem)
                    .findFirst().ifPresent((input) -> {
                                ItemStack tome = event.getOutput().copy();
                                tome.setTag(input.getTag());
                                event.setOutput(tome);
                            }
                    );
        }
    }

}
