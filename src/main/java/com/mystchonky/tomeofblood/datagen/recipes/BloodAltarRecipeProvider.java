package com.mystchonky.tomeofblood.datagen.recipes;

import com.breakinblocks.neovitae.common.recipe.aravitae.AraVitaeRecipe;
import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry;
import com.mystchonky.tomeofblood.TomeOfBlood;
import com.mystchonky.tomeofblood.common.registry.ItemRegistry;
import com.mystchonky.tomeofblood.datagen.RecipeUtil;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class BloodAltarRecipeProvider {

    String basePath = "altar/";

    public void buildRecipes(RecipeOutput consumer) {
        RecipeUtil.bloodmagicRecipe(new AraVitaeRecipe(Ingredient.of(ItemsRegistry.NOVICE_SPELLBOOK.get()), new ItemStack(ItemRegistry.NOVICE_TOME.get()), 0, 5000, 20, 20), consumer, ResourceLocation.fromNamespaceAndPath(TomeOfBlood.MODID, basePath + "novice_blood_tome"));
        RecipeUtil.bloodmagicRecipe(new AraVitaeRecipe(Ingredient.of(ItemsRegistry.APPRENTICE_SPELLBOOK.get()), new ItemStack(ItemRegistry.APPRENTICE_TOME.get()), 1, 10000, 20, 20), consumer, ResourceLocation.fromNamespaceAndPath(TomeOfBlood.MODID, basePath + "apprentice_blood_tome"));
        RecipeUtil.bloodmagicRecipe(new AraVitaeRecipe(Ingredient.of(ItemsRegistry.ARCHMAGE_SPELLBOOK.get()), new ItemStack(ItemRegistry.ARCHMAGE_TOME.get()), 2, 25000, 20, 20), consumer, ResourceLocation.fromNamespaceAndPath(TomeOfBlood.MODID, basePath + "archmage_blood_tome"));
    }

}
