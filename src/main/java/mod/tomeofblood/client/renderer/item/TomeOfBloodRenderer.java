package mod.tomeofblood.client.renderer.item;

import com.hollingsworth.arsnouveau.client.renderer.item.SpellBookRenderer;
import com.hollingsworth.arsnouveau.common.items.SpellBook;
import mod.tomeofblood.TomeOfBlood;
import net.minecraft.resources.ResourceLocation;

public class TomeOfBloodRenderer extends SpellBookRenderer {

    public TomeOfBloodRenderer() {
        super();
    }

    @Override
    public ResourceLocation getTextureLocation(SpellBook o) {
        String base = "textures/item/spellbook_black";
        return TomeOfBlood.prefix(base + ".png");
    }
}
