package mod.tomeofblood.client.events;

import mod.tomeofblood.TomeOfBlood;
import mod.tomeofblood.init.ItemRegistrar;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = TomeOfBlood.MODID)
public class ClientEventHandler {

    @SubscribeEvent
    public static void initItemColors(final RegisterColorHandlersEvent.Item event) {
        event.register((stack, color) -> color > 0 ? -1 : colorFromArmor(stack), ItemRegistrar.LIVING_MAGE_HOOD);
        event.register((stack, color) -> color > 0 ? -1 : colorFromArmor(stack), ItemRegistrar.LIVING_MAGE_ROBES);
        event.register((stack, color) -> color > 0 ? -1 : colorFromArmor(stack), ItemRegistrar.LIVING_MAGE_LEGGINGS);
        event.register((stack, color) -> color > 0 ? -1 : colorFromArmor(stack), ItemRegistrar.LIVING_MAGE_BOOTS);
    }


    public static int colorFromArmor(ItemStack stack) {
        return FastColor.ARGB32.opaque(stack.getOrDefault(DataComponents.BASE_COLOR, DyeColor.RED).getTextColor());
    }

}
