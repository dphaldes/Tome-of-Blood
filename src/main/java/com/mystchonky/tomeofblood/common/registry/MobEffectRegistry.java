package com.mystchonky.tomeofblood.common.registry;

import com.hollingsworth.arsnouveau.common.potions.PublicEffect;
import com.mystchonky.tomeofblood.TomeOfBlood;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MobEffectRegistry {

    private static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, TomeOfBlood.MODID);

    //TODO: figure out a way to datagen lang automatically
    public static final DeferredHolder<MobEffect, MobEffect> SERENE = MOB_EFFECTS.register("serene", () -> new PublicEffect(MobEffectCategory.BENEFICIAL, 16435708));
    public static final DeferredHolder<MobEffect, MobEffect> VULNERABLE = MOB_EFFECTS.register("vulnerable", () -> new PublicEffect(MobEffectCategory.BENEFICIAL, 16435708));
    public static final DeferredHolder<MobEffect, MobEffect> MARKED = MOB_EFFECTS.register("marked", () -> new PublicEffect(MobEffectCategory.HARMFUL, 16435708));

    public static void register(IEventBus modBus) {
        MOB_EFFECTS.register(modBus);
    }

}
