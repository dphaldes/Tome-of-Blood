package com.mystchonky.tomeofblood.common.events;

import com.mystchonky.tomeofblood.TomeOfBlood;
import com.mystchonky.tomeofblood.common.registry.MobEffectRegistry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = TomeOfBlood.MODID)
public class EffectEventHandler {

    @SubscribeEvent
    public static void vulnerableEntity(LivingIncomingDamageEvent event) {
        if (event.getEntity().hasEffect(MobEffectRegistry.VULNERABLE)) {
            event.setAmount((float) (event.getAmount() * 1.1));
        }
    }
}
