package mod.tomeofblood.datagen.models;

import mod.tomeofblood.TomeOfBlood;
import mod.tomeofblood.init.IntegrationRegistrar;
import mod.tomeofblood.init.ItemRegistrar;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class GlyphItemModelProvider extends ItemModelProvider {
    public GlyphItemModelProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, TomeOfBlood.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        IntegrationRegistrar.registeredSpells.forEach(spell -> basicItem(spell.getRegistryName()));
        basicItem(ItemRegistrar.MINT_TEA.get());
    }
}
