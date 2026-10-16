package com.mystchonky.tomeofblood.common.items;

import com.hollingsworth.arsnouveau.api.spell.SpellCaster;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.common.items.SpellBook;
import com.hollingsworth.arsnouveau.setup.registry.DataComponentRegistry;
import com.mystchonky.tomeofblood.client.renderer.item.TomeOfBloodRenderer;
import com.mystchonky.tomeofblood.common.spell.BloodSpellResolver;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class TomeOfBloodItem extends SpellBook {

    public TomeOfBloodItem(Properties properties, SpellTier tier) {
        super(properties.component(DataComponentRegistry.SPELL_CASTER, new SpellCaster(10)), tier);
        this.tier = tier;
    }

    public static class BloodBookCaster extends SpellCaster {

        public BloodBookCaster(SpellCaster caster) {
            super(caster.getCurrentSlot(), caster.getFlavorText(), caster.isSpellHidden(), caster.getHiddenRecipe(), caster.getMaxSlots(), caster.getSpells());
        }

        @Override
        public SpellResolver getSpellResolver(SpellContext context, Level worldIn, LivingEntity playerIn, InteractionHand handIn) {
            return new BloodSpellResolver(context);
        }
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {
            private final BlockEntityWithoutLevelRenderer renderer = new TomeOfBloodRenderer();

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer;
            }
        });
    }
}
