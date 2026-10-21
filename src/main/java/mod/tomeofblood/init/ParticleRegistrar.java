package mod.tomeofblood.init;

import mod.tomeofblood.TomeOfBlood;
import mod.tomeofblood.content.spell.WrathSlashParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ParticleRegistrar {

    private static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, TomeOfBlood.MODID);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> WRATH_SLASH = PARTICLE_TYPES.register("wrath_slash", () -> new SimpleParticleType(false));

    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(WRATH_SLASH.get(), WrathSlashParticle.Provider::new);
    }

    public static void register(IEventBus modBus) {
        PARTICLE_TYPES.register(modBus);
    }
}
