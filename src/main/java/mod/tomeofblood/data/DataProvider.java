package mod.tomeofblood.data;

import mod.tomeofblood.TomeOfBlood;
import mod.tomeofblood.data.models.GlyphItemModelProvider;
import mod.tomeofblood.data.recipes.DyeRecipeProvider;
import mod.tomeofblood.data.recipes.EnchantingAppRecipeProvider;
import mod.tomeofblood.data.recipes.ToBGlyphRecipeProvider;
import mod.tomeofblood.data.recipes.ToBRecipeProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = TomeOfBlood.MODID)
public class DataProvider {

    //use runData configuration to generate stuff, event.includeServer() for data, event.includeClient() for assets
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        var gen = event.getGenerator();
        var output = gen.getPackOutput();

        gen.addProvider(event.includeServer(), new EnchantingAppRecipeProvider(gen));
        gen.addProvider(event.includeServer(), new DyeRecipeProvider(gen));

        gen.addProvider(event.includeServer(), new ToBRecipeProvider(output, event.getLookupProvider()));
        gen.addProvider(event.includeServer(), new ToBGlyphRecipeProvider(gen));

        gen.addProvider(event.includeClient(), new GlyphItemModelProvider(output, event.getExistingFileHelper()));
        gen.addProvider(event.includeClient(), new LangProvider(output));
    }

}
