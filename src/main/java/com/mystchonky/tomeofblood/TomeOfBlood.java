package com.mystchonky.tomeofblood;

import com.hollingsworth.arsnouveau.setup.proxy.ClientProxy;
import com.hollingsworth.arsnouveau.setup.proxy.IProxy;
import com.hollingsworth.arsnouveau.setup.proxy.ServerProxy;
import com.mystchonky.tomeofblood.client.registry.ParticleRegistry;
import com.mystchonky.tomeofblood.common.config.BaseConfig;
import com.mystchonky.tomeofblood.common.registry.IntegrationRegistry;
import com.mystchonky.tomeofblood.common.registry.ItemRegistry;
import com.mystchonky.tomeofblood.common.registry.MobEffectRegistry;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TomeOfBlood.MODID)
public class TomeOfBlood {
    public static final String MODID = "tomeofblood";
    public static IProxy proxy = FMLEnvironment.dist.isClient() ? new ClientProxy() : new ServerProxy(); //TODO: Change to our own

    public static final Logger LOGGER = LogManager.getLogger();

    public TomeOfBlood(IEventBus modbus, ModContainer container) {
        // Ensure the config subdirectory is present.
        try {
            Files.createDirectories(FMLPaths.CONFIGDIR.get().resolve(MODID));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Register config files
        container.registerConfig(ModConfig.Type.COMMON, BaseConfig.COMMON_SPEC, MODID + "/base-common.toml");
        container.registerConfig(ModConfig.Type.CLIENT, BaseConfig.CLIENT_SPEC, MODID + "/base-client.toml");

        ItemRegistry.register(modbus);
        MobEffectRegistry.register(modbus);
        ParticleRegistry.register(modbus);
        IntegrationRegistry.init();

        modbus.addListener(this::setup);
        modbus.addListener(this::doClientStuff);

        NeoForge.EVENT_BUS.register(this);
    }

    public static ResourceLocation prefix(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    private void setup(final FMLCommonSetupEvent event) {
        IntegrationRegistry.postInit();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

}
