package mod.tomeofblood.common.items;

import com.breakinblocks.neovitae.common.datacomponent.NVDataComponents;
import com.breakinblocks.neovitae.common.item.NVItems;
import com.breakinblocks.neovitae.common.item.NVMaterialsAndTiers;
import com.breakinblocks.neovitae.common.item.UpgradeHolderBase;
import com.breakinblocks.neovitae.common.tag.NVTags;
import com.hollingsworth.arsnouveau.client.renderer.tile.GenericModel;
import com.hollingsworth.arsnouveau.common.armor.AnimatedMagicArmor;
import com.hollingsworth.arsnouveau.common.items.data.ArmorPerkHolder;
import com.hollingsworth.arsnouveau.setup.registry.DataComponentRegistry;
import mod.tomeofblood.TomeOfBlood;
import mod.tomeofblood.client.renderer.ToBGenericModel;
import mod.tomeofblood.client.renderer.item.LivingMageArmorRenderer;
import mod.tomeofblood.init.ItemRegistrar;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import java.util.function.Consumer;

public class LivingMageArmorItem extends AnimatedMagicArmor implements UpgradeHolderBase {
    public LivingMageArmorItem(Type slot) {
        super(NVMaterialsAndTiers.SENTIENT_ARMOUR_MATERIAL, slot, properties(slot), new ToBGenericModel<LivingMageArmorItem>("living_mage_armor", "item/living_mage_armor").withEmptyAnim());
    }

    private static Properties properties(Type slot) {
        Properties properties = new Properties().stacksTo(1).durability(slot.getDurability(33))
                .component(DataComponentRegistry.ARMOR_PERKS, new ArmorPerkHolder())
                .component(DataComponents.BASE_COLOR, DyeColor.RED);
        if (slot == Type.CHESTPLATE) {
            properties.component(NVDataComponents.REQUIRED_SET, NVTags.Items.SENTIENT_SET)
                    .component(NVDataComponents.CURRENT_UPGRADE_POINTS, 0);
        }
        return properties;
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == NVItems.REAGENT_BINDING.get() || super.isValidRepairItem(toRepair, repair);
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, @Nullable T entity, Consumer<Item> onBroken) {
        if (this != ItemRegistrar.LIVING_MAGE_ROBES.get()) {
            return super.damageItem(stack, amount, entity, onBroken);
        }
        int durRemaining = (stack.getMaxDamage() - 1 - stack.getDamageValue());
        return Math.max(Math.min(durRemaining, amount), 0);
    }

    @Override
    public String getColor(ItemStack object) {
        return object.getOrDefault(DataComponents.BASE_COLOR, DyeColor.RED).getName();
    }

    @Override
    public @Nullable ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        GenericModel<AnimatedMagicArmor> genericModel = (GenericModel<AnimatedMagicArmor>) model;
        return TomeOfBlood.prefix("textures/" + genericModel.textPathRoot + "/" + genericModel.name + "_" + this.getColor(stack) + ".png");
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public <T extends LivingEntity> HumanoidModel<?> getGeoArmorRenderer(@Nullable T livingEntity, ItemStack itemStack,
                                                                                 @Nullable EquipmentSlot equipmentSlot, @Nullable HumanoidModel<T> original) {
                if (renderer == null) {
                    renderer = new LivingMageArmorRenderer(getArmorModel());
                }
                return this.renderer;
            }
        });
    }
}
