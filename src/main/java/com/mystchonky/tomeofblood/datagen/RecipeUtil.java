package com.mystchonky.tomeofblood.datagen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

import java.util.List;

public class RecipeUtil {

    public static void bloodmagicRecipe(Recipe<?> recipe, RecipeOutput consumer, ResourceLocation id) {
        RecipeUtil.modCompatRecipe("neovitae", recipe, consumer, id);
    }

    /**
     * Register a recipe with a load condition. Useful for mod compat recipes, as those recipes
     * would otherwise crash if the other mod is not installed
     *
     * @param condition     The condition for this recipe
     * @param recipe        The recipe
     * @param consumer      The recipe output
     * @param id            The recipe id
     */
    protected static void conditionalRecipe(ICondition condition, Recipe<?> recipe, RecipeOutput consumer, ResourceLocation id) {
        consumer.accept(id, recipe, null, condition);
    }

    protected static void modCompatRecipe(String modid, Recipe<?> recipe, RecipeOutput consumer, ResourceLocation id) {
        conditionalRecipe(new ModLoadedCondition(modid), recipe, consumer, id);
    }

    public static JsonObject modCompatGlyphRecipe(String modid, JsonElement recipe) {
        JsonObject json = new JsonObject();
        ICondition.writeConditions(JsonOps.INSTANCE, json, List.of(new ModLoadedCondition(modid)));
        recipe.getAsJsonObject().entrySet().stream().forEach((entry) -> json.add(entry.getKey(), entry.getValue()));
        return json;
    }
}
