package mod.tomeofblood.common.util;

import com.breakinblocks.neovitae.common.datacomponent.SpiritusType;
import com.breakinblocks.neovitae.common.item.SpiritusGemItem;
import com.breakinblocks.neovitae.common.item.soul.SentientToolHelper;
import com.breakinblocks.neovitae.spiritus.SpiritusHelper;
import com.hollingsworth.arsnouveau.api.item.inv.InteractType;
import com.hollingsworth.arsnouveau.api.item.inv.InventoryManager;
import com.hollingsworth.arsnouveau.api.item.inv.SlotReference;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import net.minecraft.world.item.ItemStack;

public class DemonWillUtil {

    public static SpiritusType getActiveTypeFromPlayer(SpellContext context) {
        InventoryManager manager = new InventoryManager(context.getCaster());
        SlotReference reference = manager.findItem(it -> it.getItem() instanceof SpiritusGemItem, InteractType.EXTRACT);
        if (!reference.isEmpty()) {
            ItemStack stack = reference.getHandler().getStackInSlot(reference.getSlot());
            return SpiritusHelper.getCurrentType(stack);
        }
        return SpiritusType.RAW;
    }

    public static int getBracket(SpiritusType type, int souls) {
        int bracket = -1;
        for (int i = 0; i < SentientToolHelper.SOUL_BRACKET.length; i++) {
            if (souls >= SentientToolHelper.SOUL_BRACKET[i]) {
                bracket = i;
            }
        }
        return bracket;
    }

    public static float getExtraDamage(SpiritusType type, int bracket) {
        if (bracket < 0) {
            return 0;
        }
        return switch (type) {
            case RUINA, RAW -> (float) SentientToolHelper.WEAPON_DEFAULT_DAMAGE[bracket];
            case NIHILUM -> (float) SentientToolHelper.WEAPON_DESTRUCTIVE_DAMAGE[bracket];
            case VINDICTA -> (float) SentientToolHelper.WEAPON_VENGEFUL_DAMAGE[bracket];
            case INVICTUS -> (float) SentientToolHelper.WEAPON_STEADFAST_DAMAGE[bracket];
        };
    }
}
