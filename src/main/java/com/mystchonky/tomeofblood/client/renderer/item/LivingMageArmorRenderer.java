package com.mystchonky.tomeofblood.client.renderer.item;

import com.hollingsworth.arsnouveau.client.renderer.item.ArmorRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.GenericModel;
import com.hollingsworth.arsnouveau.common.armor.AnimatedMagicArmor;
import com.mystchonky.tomeofblood.TomeOfBlood;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class LivingMageArmorRenderer extends ArmorRenderer {
    public LivingMageArmorRenderer(GeoModel<AnimatedMagicArmor> modelProvider) {
        super(modelProvider);
    }

    @Override
    public ResourceLocation getTextureLocation(AnimatedMagicArmor instance) {
        if (model instanceof GenericModel<AnimatedMagicArmor> genericModel) {
            return new ResourceLocation(TomeOfBlood.MODID, "textures/" + genericModel.textPathRoot + "/" + genericModel.name + "_" + instance.getColor(getCurrentStack()) + ".png");
        }

        return super.getTextureLocation(instance);
    }
}
