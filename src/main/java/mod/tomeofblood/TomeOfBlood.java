package mod.tomeofblood;

import mod.tomeofblood.init.Registrar;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(TomeOfBlood.MODID)
public class TomeOfBlood {
    public static final String MODID = "tomeofblood";

    public static final Logger LOGGER = LogManager.getLogger();

    public TomeOfBlood(IEventBus modbus, ModContainer container) {
        container.registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC, MODID + "/base-common.toml");

        Registrar.init(modbus);
    }

    public static ResourceLocation prefix(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

}
