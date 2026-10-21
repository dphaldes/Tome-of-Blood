package mod.tomeofblood.datagen.recipes;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.concurrent.CompletableFuture;

public class ToBRecipeProvider extends RecipeProvider {

    public ToBRecipeProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput consumer) {
        new BloodAltarRecipeProvider().buildRecipes(consumer);
        new AlchemyTableRecipeProvider().buildRecipes(consumer);
    }
}
