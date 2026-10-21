package mod.tomeofblood.data.recipes;

import com.breakinblocks.neovitae.common.item.NVItems;
import com.hollingsworth.arsnouveau.common.crafting.recipes.EnchantingApparatusRecipe;
import com.hollingsworth.arsnouveau.common.datagen.ApparatusRecipeBuilder;
import com.hollingsworth.arsnouveau.common.datagen.ApparatusRecipeProvider;
import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry;
import mod.tomeofblood.TomeOfBlood;
import mod.tomeofblood.init.ItemRegistrar;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;

import java.nio.file.Path;

public class EnchantingAppRecipeProvider extends ApparatusRecipeProvider {
    public EnchantingAppRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public void collectJsons(CachedOutput pOutput) {
        recipes.add(builder()
                .withReagent(NVItems.SENTIENT_HELMET.get())
                .withPedestalItem(4, ItemsRegistry.MAGE_FIBER)
                .withResult(ItemRegistrar.LIVING_MAGE_HOOD)
                .keepNbtOfReagent(true)
                .build()
        );
        recipes.add(builder()
                .withReagent(NVItems.SENTIENT_PLATE.get())
                .withPedestalItem(4, ItemsRegistry.MAGE_FIBER)
                .withResult(ItemRegistrar.LIVING_MAGE_ROBES)
                .keepNbtOfReagent(true)
                .build()
        );
        recipes.add(builder()
                .withReagent(NVItems.SENTIENT_LEGGINGS.get())
                .withPedestalItem(4, ItemsRegistry.MAGE_FIBER)
                .withResult(ItemRegistrar.LIVING_MAGE_LEGGINGS)
                .keepNbtOfReagent(true)
                .build()
        );
        recipes.add(builder()
                .withReagent(NVItems.SENTIENT_BOOTS.get())
                .withPedestalItem(4, ItemsRegistry.MAGE_FIBER)
                .withResult(ItemRegistrar.LIVING_MAGE_BOOTS)
                .keepNbtOfReagent(true)
                .build()
        );
        for (ApparatusRecipeBuilder.RecipeWrapper<? extends EnchantingApparatusRecipe> g : recipes) {
            if (g != null) {
                Path path = getRecipePath(output, g.id().getPath());
                saveStable(pOutput, g.serialize(), path);
            }
        }

    }

    protected static Path getRecipePath(Path pathIn, String str) {
        return pathIn.resolve("data/" + TomeOfBlood.MODID + "/recipe/" + str + ".json");
    }

    @Override
    public String getName() {
        return "ToB Apparatus";
    }
}
