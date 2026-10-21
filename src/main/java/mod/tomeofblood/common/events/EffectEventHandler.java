package mod.tomeofblood.common.events;

import mod.tomeofblood.TomeOfBlood;
import mod.tomeofblood.init.MobEffectRegistrar;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = TomeOfBlood.MODID)
public class EffectEventHandler {

    @SubscribeEvent
    public static void vulnerableEntity(LivingIncomingDamageEvent event) {
        if (event.getEntity().hasEffect(MobEffectRegistrar.VULNERABLE)) {
            event.setAmount((float) (event.getAmount() * 1.1));
        }
    }
}
