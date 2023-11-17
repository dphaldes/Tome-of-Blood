package com.mystchonky.tomeofblood.datagen.recipes;

import com.hollingsworth.arsnouveau.common.crafting.recipes.GlyphRecipe;
import com.hollingsworth.arsnouveau.common.datagen.GlyphRecipeProvider;
import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry;
import com.mystchonky.tomeofblood.TomeOfBlood;
import com.mystchonky.tomeofblood.common.glyphs.EffectSentientHarm;
import com.mystchonky.tomeofblood.common.glyphs.EffectSentientWrath;
import com.mystchonky.tomeofblood.datagen.RecipeUtil;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import wayoftime.bloodmagic.common.item.BloodMagicItems;

import java.nio.file.Path;

import static com.hollingsworth.arsnouveau.setup.registry.RegistryHelper.getRegistryName;

public class ToBGlyphRecipeProvider extends GlyphRecipeProvider {
    public ToBGlyphRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public void collectJsons(CachedOutput pOutput) {

        recipes.add(get(EffectSentientHarm.INSTANCE)
                .withItem(BloodMagicItems.SENTIENT_SWORD));

        recipes.add(get(EffectSentientWrath.INSTANCE)
                .withItem(BloodMagicItems.SENTIENT_SCYTHE)
                .withItem(BloodMagicItems.THROWING_DAGGER)
                .withItem(ItemsRegistry.CONJURATION_ESSENCE));

        for (GlyphRecipe recipe : recipes) {
            Path path = getScribeGlyphPath(output, recipe.output.getItem());
            saveStable(pOutput, RecipeUtil.modCompatGlyphRecipe("bloodmagic", recipe.asRecipe()), path);
        }

    }

    protected static Path getScribeGlyphPath(Path pathIn, Item glyph) {
        return pathIn.resolve("data/" + TomeOfBlood.MODID + "/recipes/" + getRegistryName(glyph).getPath() + ".json");
    }

    @Override
    public String getName() {
        return "Example Glyph Recipes";
    }
}
