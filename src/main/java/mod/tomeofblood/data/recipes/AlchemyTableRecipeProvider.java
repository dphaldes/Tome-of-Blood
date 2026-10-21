package mod.tomeofblood.data.recipes;

import com.breakinblocks.neovitae.common.recipe.tabulavitae.TabulaVitaeRecipe;
import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry;
import mod.tomeofblood.TomeOfBlood;
import mod.tomeofblood.init.ItemRegistrar;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;

import java.util.List;

public class AlchemyTableRecipeProvider {

    String basePath = "alchemytable/";

    public void buildRecipes(RecipeOutput consumer) {
        Ingredient water = DataComponentIngredient.of(false, DataComponents.POTION_CONTENTS, new PotionContents(Potions.WATER), Items.POTION);

        RecipeUtil.bloodmagicRecipe(new TabulaVitaeRecipe(List.of(Ingredient.of(Items.SUGAR), Ingredient.of(Items.SEAGRASS), Ingredient.of(Items.SEAGRASS), water), new ItemStack(ItemRegistrar.MINT_TEA.get()), 100, 100, 1), consumer, ResourceLocation.fromNamespaceAndPath(TomeOfBlood.MODID, basePath + "mint_tea"));

        RecipeUtil.bloodmagicRecipe(new TabulaVitaeRecipe(List.of(Ingredient.of(ItemRegistrar.NOVICE_TOME), Ingredient.of(Items.OBSIDIAN), Ingredient.of(Items.DIAMOND), Ingredient.of(Items.QUARTZ_BLOCK), Ingredient.of(Items.BLAZE_ROD)), new ItemStack(ItemRegistrar.APPRENTICE_TOME.get()), 5000, 100, 2), consumer, ResourceLocation.fromNamespaceAndPath(TomeOfBlood.MODID, basePath + "apprentice_tome"));
        RecipeUtil.bloodmagicRecipe(new TabulaVitaeRecipe(List.of(Ingredient.of(ItemRegistrar.APPRENTICE_TOME), Ingredient.of(Items.NETHER_STAR), Ingredient.of(Items.EMERALD), Ingredient.of(Items.ENDER_PEARL), Ingredient.of(Items.TOTEM_OF_UNDYING), Ingredient.of(ItemsRegistry.WILDEN_TRIBUTE)), new ItemStack(ItemRegistrar.ARCHMAGE_TOME.get()), 15000, 100, 3), consumer, ResourceLocation.fromNamespaceAndPath(TomeOfBlood.MODID, basePath + "archmage_tome"));
    }
}
