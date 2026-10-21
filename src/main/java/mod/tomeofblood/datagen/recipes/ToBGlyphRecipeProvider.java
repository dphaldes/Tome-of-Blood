package mod.tomeofblood.datagen.recipes;

import com.breakinblocks.neovitae.common.item.NVItems;
import com.hollingsworth.arsnouveau.common.crafting.recipes.GlyphRecipe;
import com.hollingsworth.arsnouveau.common.datagen.GlyphRecipeProvider;
import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry;
import com.mojang.serialization.JsonOps;
import mod.tomeofblood.TomeOfBlood;
import mod.tomeofblood.common.glyphs.EffectSentientHarm;
import mod.tomeofblood.common.glyphs.EffectSentientWrath;
import mod.tomeofblood.datagen.RecipeUtil;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;

import java.nio.file.Path;

import static com.hollingsworth.arsnouveau.setup.registry.RegistryHelper.getRegistryName;

public class ToBGlyphRecipeProvider extends GlyphRecipeProvider {
    public ToBGlyphRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public void collectJsons(CachedOutput pOutput) {

        recipes.add(get(EffectSentientHarm.INSTANCE)
                .withItem(NVItems.SENTIENT_SWORD.get()));

        recipes.add(get(EffectSentientWrath.INSTANCE)
                .withItem(NVItems.SENTIENT_SCYTHE.get())
                .withItem(NVItems.THROWING_DAGGER.get())
                .withItem(ItemsRegistry.CONJURATION_ESSENCE));

        for (GlyphRecipe recipe : recipes) {
            Path path = getScribeGlyphPath(output, recipe.output.getItem());
            saveStable(pOutput, RecipeUtil.modCompatGlyphRecipe("neovitae", GlyphRecipe.CODEC.encodeStart(JsonOps.INSTANCE, recipe).getOrThrow()), path);
        }

    }

    protected static Path getScribeGlyphPath(Path pathIn, Item glyph) {
        return pathIn.resolve("data/" + TomeOfBlood.MODID + "/recipe/" + getRegistryName(glyph).getPath() + ".json");
    }

    @Override
    public String getName() {
        return "Example Glyph Recipes";
    }
}
