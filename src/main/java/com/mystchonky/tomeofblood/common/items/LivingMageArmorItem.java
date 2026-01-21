package com.mystchonky.tomeofblood.common.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.hollingsworth.arsnouveau.api.perk.ArmorPerkHolder;
import com.hollingsworth.arsnouveau.api.perk.IPerkHolder;
import com.hollingsworth.arsnouveau.api.util.PerkUtil;
import com.hollingsworth.arsnouveau.client.renderer.tile.GenericModel;
import com.hollingsworth.arsnouveau.common.armor.AnimatedMagicArmor;
import com.mystchonky.tomeofblood.TomeOfBlood;
import com.mystchonky.tomeofblood.client.renderer.ToBGenericModel;
import com.mystchonky.tomeofblood.client.renderer.item.LivingMageArmorRenderer;
import com.mystchonky.tomeofblood.common.registry.ItemRegistry;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import wayoftime.bloodmagic.common.item.ArmorMaterialLiving;
import wayoftime.bloodmagic.common.item.ExpandedArmor;
import wayoftime.bloodmagic.core.LivingArmorRegistrar;
import wayoftime.bloodmagic.core.living.ILivingContainer;
import wayoftime.bloodmagic.core.living.LivingStats;
import wayoftime.bloodmagic.core.living.LivingUtil;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class LivingMageArmorItem extends AnimatedMagicArmor implements ILivingContainer, ExpandedArmor {
    public LivingMageArmorItem(Type slot) {
        super(ArmorMaterialLiving.INSTANCE, slot, new ToBGenericModel<LivingMageArmorItem>("living_mage_armor", "item/living_mage_armor").withEmptyAnim());
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == wayoftime.bloodmagic.common.item.BloodMagicItems.REAGENT_BINDING.get() || super.isValidRepairItem(toRepair, repair);
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        if (this != ItemRegistry.LIVING_MAGE_ROBES.get()) {
            return super.damageItem(stack, amount, entity, onBroken);
        }
        int durRemaining = (stack.getMaxDamage() - 1 - stack.getDamageValue());
        return Math.max(Math.min(durRemaining, amount), 0);
    }

    @Override
    public void damageArmor(LivingEntity livingEntity, ItemStack stack, DamageSource source, float damage, EquipmentSlot slot) {
        if (slot == EquipmentSlot.CHEST && damage > getMaxDamage() - stack.getDamageValue()) {
            livingEntity.hurt(livingEntity.damageSources().magic(), 2.0F);
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {

        Multimap<Attribute, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.putAll(super.getAttributeModifiers(slot, stack));
        if (slot == EquipmentSlot.CHEST) {
            LivingStats stats = getLivingStats(stack);
            if (stats != null) {
                stats.getUpgrades().forEach((k, v) -> {
                    if (k.getAttributeProvider() != null)
                        k.getAttributeProvider().handleAttributes(stats, modifiers, UUID.nameUUIDFromBytes(k.getKey().toString().getBytes()), k, k.getLevel(v.intValue()));
                });
            }
        }

        return modifiers;
    }

    @Override
    public boolean canLivingEvolve() {
        return true;
    }

    @Override
    public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
        return hasElytraUpgrade(stack, entity) && stack.getDamageValue() < stack.getMaxDamage() - 1;
    }

    @Override
    public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
        if (!entity.level().isClientSide && (flightTicks + 1) % 40 == 0)
            stack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(net.minecraft.world.entity.EquipmentSlot.CHEST));
        return true;
    }

    public boolean hasElytraUpgrade(ItemStack stack, LivingEntity entity) {
        if (stack.getItem() instanceof LivingMageArmorItem && entity instanceof Player && LivingUtil.hasFullSet((Player) entity))
            return LivingStats.fromPlayer((Player) entity, true).getLevel(LivingArmorRegistrar.UPGRADE_ELYTRA.get().getKey()) > 0;
        else
            return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        ILivingContainer.appendLivingTooltip(stack, getLivingStats(stack), tooltip, true);
    }

    @Override
    public String getColor(ItemStack object) {
        IPerkHolder<ItemStack> perkHolder = PerkUtil.getPerkHolder(object);
        if (!(perkHolder instanceof ArmorPerkHolder data)) {
            return "red";
        }
        return data.getColor() == null || data.getColor().isEmpty() ? "red" : data.getColor();
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        GenericModel<AnimatedMagicArmor> genericModel = (GenericModel<AnimatedMagicArmor>) model;
        return new ResourceLocation(TomeOfBlood.MODID, "textures/" + genericModel.textPathRoot + "/" + genericModel.name + "_" + this.getColor(stack) + ".png").toString();
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack,
                                                                   EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (renderer == null) {
                    renderer = new LivingMageArmorRenderer(getArmorModel());
                }
                renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
    }
}
