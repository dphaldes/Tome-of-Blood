package com.mystchonky.tomeofblood.datagen.models;

import com.mystchonky.tomeofblood.TomeOfBlood;
import com.mystchonky.tomeofblood.common.registry.IntegrationRegistry;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class GlyphItemModelProvider extends ItemModelProvider {
    public GlyphItemModelProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, TomeOfBlood.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        IntegrationRegistry.registeredSpells.forEach(spell -> basicItem(spell.getRegistryName()));
    }
}
