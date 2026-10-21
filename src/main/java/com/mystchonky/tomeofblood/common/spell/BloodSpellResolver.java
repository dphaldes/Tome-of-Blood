package com.mystchonky.tomeofblood.common.spell;

import com.breakinblocks.neovitae.api.NeoVitaeAPI;
import com.breakinblocks.neovitae.api.soul.AnimaTicket;
import com.breakinblocks.neovitae.api.soul.IAnima;
import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.mana.IManaCap;
import com.hollingsworth.arsnouveau.api.spell.ISpellValidator;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellValidationError;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.registry.CapabilityRegistry;
import com.mystchonky.tomeofblood.common.config.BaseConfig;
import com.mystchonky.tomeofblood.common.registry.LangRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class BloodSpellResolver extends SpellResolver {

    private final ISpellValidator spellValidator;

    public BloodSpellResolver(SpellContext spellContext) {
        super(spellContext);
        this.spellValidator = ArsNouveauAPI.getInstance().getSpellCastingSpellValidator();
    }

    @Override
    public boolean canCast(LivingEntity entity) {
        // Validate the spell
        List<SpellValidationError> validationErrors = spellValidator.validate(spell.unsafeList());

        if (validationErrors.isEmpty()) {
            // Validation successful. We can check the player's mana now.
            return enoughSpirit(entity);
        } else {
            // Validation failed, explain why if applicable
            if (!silent && !entity.getCommandSenderWorld().isClientSide) {
                // Sending only the first error to avoid spam
                PortUtil.sendMessageNoSpam(entity, validationErrors.get(0).makeTextComponentExisting());
            }
            return false;
        }
    }

    boolean enoughSpirit(LivingEntity entity) {
        int totalCost = getResolveCost();
        IManaCap manaCap = CapabilityRegistry.getMana(entity);
        if (manaCap == null)
            return false;
        double remainder = manaCap.getCurrentMana() - totalCost;
        if (remainder >= 0) {
            // If player has mana to cast, use it
            return true;
        } else {
            // if more mana is required, check stored LP
            remainder *= -1; //flip negatives
            return enoughLP(entity, remainder);
        }
    }

    boolean enoughLP(LivingEntity entity, double manaCost) {
        if (entity instanceof Player player) {
            if (player.isCreative()) return true;
            double totalCost = manaCost * BaseConfig.COMMON.CONVERSION_RATE.get();
            IAnima anima = NeoVitaeAPI.getInstance().getAnima(player.getUUID());
            //LOGGER.debug("Got soulnetwork for " + soulNetwork.getPlayer().getDisplayName().getString());
            int pool = anima == null ? 0 : anima.getCurrentEV();
            if (pool < totalCost) {
                PortUtil.sendMessageCenterScreen(player, Component.translatable(LangRegistry.LOW_LP.getString()));
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public void expendMana() {
        int totalCost = getExpendedCost();
        IManaCap mana = CapabilityRegistry.getMana(spellContext.getUnwrappedCaster());
        if (mana != null) {
            double remainder = mana.getCurrentMana() - totalCost;
            if (remainder >= 0) {
                // if player has more mana than required, use the mana to cast
                mana.removeMana(totalCost);
            } else {
                // if not, use up all player mana and use the remaining from LP
                mana.removeMana(mana.getCurrentMana());
                remainder *= -1; //flip negatives
                if (spellContext.getUnwrappedCaster() instanceof Player player) {
                    if (!player.isCreative()) {
                        int cost = (int) (remainder * BaseConfig.COMMON.CONVERSION_RATE.get());
                        IAnima anima = NeoVitaeAPI.getInstance().getAnima(player.getUUID());
                        if (anima != null) {
                            anima.syphonAndDamage(player, AnimaTicket.create(cost));
                        }
                    }
                }

            }
        }

    }

    @Override
    public SpellResolver getNewResolver(SpellContext context) {
        return new BloodSpellResolver(context);
    }
}
