package com.mystchonky.tomeofblood.common.events;

import com.breakinblocks.neovitae.api.NeoVitaeAPI;
import com.breakinblocks.neovitae.api.sentient.ISentientArmorManager;
import com.hollingsworth.arsnouveau.api.event.SpellCastEvent;
import com.hollingsworth.arsnouveau.api.event.SpellCostCalcEvent;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.util.ManaUtil;
import com.mystchonky.tomeofblood.TomeOfBlood;
import com.mystchonky.tomeofblood.client.ClientInfo;
import com.mystchonky.tomeofblood.common.registry.LivingUpgradeRegistry;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid = TomeOfBlood.MODID)
public class EventHandler {
    @SubscribeEvent
    public static void clientTickEnd(ClientTickEvent.Post event) {
        ClientInfo.ticksInGame++;
    }

    @SubscribeEvent
    public static void spellDiscount(SpellCostCalcEvent event) {
        if (event.context.getUnwrappedCaster() instanceof Player player) {
            ISentientArmorManager manager = NeoVitaeAPI.getInstance().getSentientArmorManager();
            if (manager.hasFullSet(player)) {
                int level = manager.getUpgradeLevel(player, LivingUpgradeRegistry.MANA_UPGRADE);
                double discount = level * 0.05;
                event.currentCost = (int) ((1 - discount) * event.currentCost);
            }
        }

    }

    @SubscribeEvent
    public static void awardXPForSpellCast(SpellCastEvent event) {
        if (event.context.getUnwrappedCaster() instanceof Player player) {
            ISentientArmorManager manager = NeoVitaeAPI.getInstance().getSentientArmorManager();
            if (manager.hasFullSet(player)) {
                Spell spell = event.spell;
                SpellContext spellContext = event.context;

                int cost = spellContext.getSpell().getCost() - ManaUtil.getPlayerDiscounts(spellContext.getUnwrappedCaster(), spell, spellContext.getCasterTool());
                cost = Math.max(cost, 0);
                int xpAward = cost / 100;
                manager.grantUpgradeExperience(player, LivingUpgradeRegistry.MANA_UPGRADE, xpAward);
            }
        }
    }

}
