package com.mystchonky.tomeofblood.client.registry;

import com.mystchonky.tomeofblood.TomeOfBlood;
import com.mystchonky.tomeofblood.client.particle.WrathSlashParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(modid = TomeOfBlood.MODID, value = Dist.CLIENT)
public class ParticleRegistry {

    private static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, TomeOfBlood.MODID);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> WRATH_SLASH = PARTICLE_TYPES.register("wrath_slash", () -> new SimpleParticleType(false));

    @SubscribeEvent
    public static void registerProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(WRATH_SLASH.get(), WrathSlashParticle.Provider::new);
    }

    public static void register(IEventBus modBus) {
        PARTICLE_TYPES.register(modBus);
    }
}
