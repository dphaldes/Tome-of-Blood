package mod.tomeofblood.init;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

public class Registrar {

    public static void init(IEventBus modbus) {
        ItemRegistrar.register(modbus);
        MobEffectRegistrar.register(modbus);
        ParticleRegistrar.register(modbus);
        IntegrationRegistrar.init();
        LangRegistrar.init();

        modbus.addListener(Registrar::commonSetup);
        modbus.addListener(Registrar::clientSetup);
        modbus.addListener(ParticleRegistrar::registerParticleProviders);
    }

    private static void commonSetup(FMLCommonSetupEvent event) {
        IntegrationRegistrar.postInit();
    }

    private static void clientSetup(FMLClientSetupEvent event) {

    }
}
