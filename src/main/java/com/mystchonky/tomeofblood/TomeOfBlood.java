package com.mystchonky.tomeofblood;

import com.hollingsworth.arsnouveau.setup.proxy.ClientProxy;
import com.hollingsworth.arsnouveau.setup.proxy.IProxy;
import com.hollingsworth.arsnouveau.setup.proxy.ServerProxy;
import com.mystchonky.tomeofblood.client.registry.ParticleRegistry;
import com.mystchonky.tomeofblood.common.config.BaseConfig;
import com.mystchonky.tomeofblood.common.network.Networking;
import com.mystchonky.tomeofblood.common.registry.IntegrationRegistry;
import com.mystchonky.tomeofblood.common.registry.ItemRegistry;
import com.mystchonky.tomeofblood.common.registry.LivingUpgradeRegistry;
import com.mystchonky.tomeofblood.common.registry.MobEffectRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TomeOfBlood.MODID)
public class TomeOfBlood {
    public static final String MODID = "tomeofblood";
    public static IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new); //TODO: Change to our own

    public static final Logger LOGGER = LogManager.getLogger();

    public TomeOfBlood() {
        // Ensure the config subdirectory is present.
        try {
            Files.createDirectories(FMLPaths.CONFIGDIR.get().resolve(MODID));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Register config files
        var ctx = ModLoadingContext.get();
        ctx.registerConfig(ModConfig.Type.COMMON, BaseConfig.COMMON_SPEC, MODID + "/base-common.toml");
        ctx.registerConfig(ModConfig.Type.CLIENT, BaseConfig.CLIENT_SPEC, MODID + "/base-client.toml");

        IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();

        ItemRegistry.register(modbus);
        LivingUpgradeRegistry.register();
        MobEffectRegistry.register(modbus);
        ParticleRegistry.register(modbus);
        IntegrationRegistry.init();

        modbus.addListener(this::setup);
        modbus.addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
    }

    public static ResourceLocation prefix(String path) {
        return new ResourceLocation(MODID, path);
    }

    private void setup(final FMLCommonSetupEvent event) {
        IntegrationRegistry.postInit();
        Networking.registerMessages();
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
