package mod.tomeofblood.common.events;

import com.breakinblocks.neovitae.common.event.NeoVitaeCraftedEvent;
import com.hollingsworth.arsnouveau.common.items.SpellBook;
import mod.tomeofblood.TomeOfBlood;
import mod.tomeofblood.common.items.TomeOfBloodItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import java.util.Arrays;

@EventBusSubscriber(modid = TomeOfBlood.MODID)
public class RecipeEventHandler {

    @SubscribeEvent
    public static void altarSpellbookRecipe(NeoVitaeCraftedEvent.Altar event) {
        if (event.getOutput().getItem() instanceof TomeOfBloodItem) {
            if (event.getInputs()[0].getItem() instanceof SpellBook) {
                ItemStack tome = event.getOutput().copy();
                tome.applyComponents(event.getInputs()[0].getComponentsPatch());
                event.setOutput(tome);
            }
        }
    }

    @SubscribeEvent
    public static void alchemyTableSpellbookRecipe(NeoVitaeCraftedEvent.AlchemyTable event) {
        if (event.getOutput().getItem() instanceof TomeOfBloodItem) {
            Arrays.stream(event.getInputs())
                    .filter(itemStack -> itemStack.getItem() instanceof TomeOfBloodItem)
                    .findFirst().ifPresent((input) -> {
                                ItemStack tome = event.getOutput().copy();
                                tome.applyComponents(input.getComponentsPatch());
                                event.setOutput(tome);
                            }
                    );
        }
    }

}
