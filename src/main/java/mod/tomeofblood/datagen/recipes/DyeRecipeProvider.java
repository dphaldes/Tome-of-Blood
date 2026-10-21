package mod.tomeofblood.datagen.recipes;


import com.google.gson.JsonElement;
import com.hollingsworth.arsnouveau.common.crafting.recipes.DyeRecipe;
import com.hollingsworth.arsnouveau.common.datagen.SimpleDataProvider;
import com.mojang.serialization.JsonOps;
import mod.tomeofblood.init.ItemRegistrar;
import net.minecraft.core.NonNullList;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.hollingsworth.arsnouveau.setup.registry.RegistryHelper.getRegistryName;

public class DyeRecipeProvider extends SimpleDataProvider {
    List<FileObj> files = new ArrayList<>();

    public DyeRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public void collectJsons(CachedOutput pOutput) {
        addDyeRecipe(ItemRegistrar.LIVING_MAGE_HOOD);
        addDyeRecipe(ItemRegistrar.LIVING_MAGE_ROBES);
        addDyeRecipe(ItemRegistrar.LIVING_MAGE_LEGGINGS);
        addDyeRecipe(ItemRegistrar.LIVING_MAGE_BOOTS);
        for (FileObj fileObj : files) {
            saveStable(pOutput, fileObj.element, fileObj.path);
        }
    }


    public void add(FileObj fileObj) {
        files.add(fileObj);
    }

    public void addDyeRecipe(ItemLike inputItem) {
        DyeRecipe recipe = new DyeRecipe("", CraftingBookCategory.MISC, inputItem.asItem().getDefaultInstance(), NonNullList.of(Ingredient.EMPTY, Ingredient.of(Tags.Items.DYES), Ingredient.of(inputItem)));
        JsonElement dyeRecipe = DyeRecipe.CODEC.encodeStart(JsonOps.INSTANCE, recipe).getOrThrow();
        add(new FileObj(output.resolve("data/ars_nouveau/recipe/dye_" + getRegistryName(inputItem.asItem()).getPath() + ".json"), dyeRecipe));
    }

    @Override
    public String getName() {
        return "ArsNouveau: Json Datagen";
    }

    public record FileObj(Path path, JsonElement element) {

    }
}
