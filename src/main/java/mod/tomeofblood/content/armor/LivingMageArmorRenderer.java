package mod.tomeofblood.content.armor;

import com.hollingsworth.arsnouveau.client.renderer.item.ArmorRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.GenericModel;
import com.hollingsworth.arsnouveau.common.armor.AnimatedMagicArmor;
import mod.tomeofblood.TomeOfBlood;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import software.bernie.geckolib.model.GeoModel;

public class LivingMageArmorRenderer extends ArmorRenderer {
    public LivingMageArmorRenderer(GeoModel<AnimatedMagicArmor> modelProvider) {
        super(modelProvider);
    }

    @Override
    public ResourceLocation getTextureLocation(AnimatedMagicArmor instance) {
        if (model instanceof GenericModel<AnimatedMagicArmor> genericModel) {
            var color = getCurrentStack().getOrDefault(DataComponents.BASE_COLOR, DyeColor.RED).getName();
            return TomeOfBlood.prefix("textures/" + genericModel.textPathRoot + "/" + genericModel.name + "_" + color + ".png");
        }

        return super.getTextureLocation(instance);
    }
}
