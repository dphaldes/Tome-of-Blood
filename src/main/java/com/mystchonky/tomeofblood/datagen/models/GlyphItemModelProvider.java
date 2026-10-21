package com.mystchonky.tomeofblood.datagen.models;

import com.mystchonky.tomeofblood.TomeOfBlood;
import com.mystchonky.tomeofblood.common.registry.IntegrationRegistry;
import com.mystchonky.tomeofblood.common.registry.ItemRegistry;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class GlyphItemModelProvider extends ItemModelProvider {
    public GlyphItemModelProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, TomeOfBlood.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        IntegrationRegistry.registeredSpells.forEach(spell -> basicItem(spell.getRegistryName()));
        basicItem(ItemRegistry.MINT_TEA.get());
    }
}
