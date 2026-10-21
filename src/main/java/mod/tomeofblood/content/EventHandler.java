package mod.tomeofblood.content;

import com.breakinblocks.neovitae.api.NeoVitaeAPI;
import com.breakinblocks.neovitae.api.sentient.ISentientArmorManager;
import com.breakinblocks.neovitae.common.event.NeoVitaeCraftedEvent;
import com.hollingsworth.arsnouveau.api.event.SpellCastEvent;
import com.hollingsworth.arsnouveau.api.event.SpellCostCalcEvent;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.util.ManaUtil;
import com.hollingsworth.arsnouveau.common.items.SpellBook;
import mod.tomeofblood.TomeOfBlood;
import mod.tomeofblood.content.spellbook.TomeOfBloodItem;
import mod.tomeofblood.init.LivingUpgradeRegistrar;
import mod.tomeofblood.init.MobEffectRegistrar;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.Arrays;

@EventBusSubscriber(modid = TomeOfBlood.MODID)
public class EventHandler {

    @SubscribeEvent
    public static void spellDiscount(SpellCostCalcEvent event) {
        if (event.context.getUnwrappedCaster() instanceof Player player) {
            ISentientArmorManager manager = NeoVitaeAPI.getInstance().getSentientArmorManager();
            if (manager.hasFullSet(player)) {
                int level = manager.getUpgradeLevel(player, LivingUpgradeRegistrar.MANA_UPGRADE);
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
                manager.grantUpgradeExperience(player, LivingUpgradeRegistrar.MANA_UPGRADE, xpAward);
            }
        }
    }

    @SubscribeEvent
    public static void vulnerableEntity(LivingIncomingDamageEvent event) {
        if (event.getEntity().hasEffect(MobEffectRegistrar.VULNERABLE)) {
            event.setAmount((float) (event.getAmount() * 1.1)); //TODO: Make it configurable
        }
    }

    @SubscribeEvent
    public static void altarSpellbookRecipe(NeoVitaeCraftedEvent.Altar event) {
        if (event.getOutput().getItem() instanceof TomeOfBloodItem) {
            if (event.getInputs()[0].getItem() instanceof SpellBook) {
                ItemStack tome = event.getOutput().copy();
                tome.applyComponents(event.getInputs()[0].getComponentsPatch());
                event.setOutput(tome);
            }
        }
    }

    @SubscribeEvent
    public static void alchemyTableSpellbookRecipe(NeoVitaeCraftedEvent.AlchemyTable event) {
        if (event.getOutput().getItem() instanceof TomeOfBloodItem) {
            Arrays.stream(event.getInputs())
                    .filter(itemStack -> itemStack.getItem() instanceof TomeOfBloodItem)
                    .findFirst().ifPresent((input) -> {
                                ItemStack tome = event.getOutput().copy();
                                tome.applyComponents(input.getComponentsPatch());
                                event.setOutput(tome);
                            }
                    );
        }
    }

}
