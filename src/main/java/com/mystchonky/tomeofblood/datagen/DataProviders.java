package com.mystchonky.tomeofblood.datagen;

import com.mystchonky.tomeofblood.TomeOfBlood;
import com.mystchonky.tomeofblood.datagen.models.GlyphItemModelProvider;
import com.mystchonky.tomeofblood.datagen.recipes.AlchemyTableRecipeProvider;
import com.mystchonky.tomeofblood.datagen.recipes.BloodAltarRecipeProvider;
import com.mystchonky.tomeofblood.datagen.recipes.DyeRecipeProvider;
import com.mystchonky.tomeofblood.datagen.recipes.EnchantingAppRecipeProvider;
import com.mystchonky.tomeofblood.datagen.recipes.ToBGlyphRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = TomeOfBlood.MODID)
public class DataProviders {

    //use runData configuration to generate stuff, event.includeServer() for data, event.includeClient() for assets
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput output = gen.getPackOutput();

//        gen.addProvider(event.includeServer(), new ArsProviders.ImbuementProvider(gen));
//        gen.addProvider(event.includeServer(), new ArsProviders.GlyphProvider(gen));
        gen.addProvider(event.includeServer(), new EnchantingAppRecipeProvider(gen));
        gen.addProvider(event.includeServer(), new DyeRecipeProvider(gen));

        gen.addProvider(event.includeServer(), new BloodAltarRecipeProvider(output));
        gen.addProvider(event.includeServer(), new AlchemyTableRecipeProvider(output));
        gen.addProvider(event.includeServer(), new ToBGlyphRecipeProvider(gen));

        gen.addProvider(event.includeServer(), new PatchouliProvider(gen));

        gen.addProvider(event.includeClient(), new GlyphItemModelProvider(output, event.getExistingFileHelper()));
        gen.addProvider(event.includeClient(), new LangProvider(output));
    }

}
