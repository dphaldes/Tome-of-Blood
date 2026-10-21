package mod.tomeofblood.content.armor;

import com.hollingsworth.arsnouveau.client.renderer.tile.GenericModel;
import mod.tomeofblood.TomeOfBlood;
import software.bernie.geckolib.animatable.GeoAnimatable;

public class ToBGenericModel<T extends GeoAnimatable> extends GenericModel<T> {
    public ToBGenericModel(String name) {
        super(name);
        this.modelLocation = TomeOfBlood.prefix("geo/" + name + ".geo.json");
        this.textLoc = TomeOfBlood.prefix("textures/" + textPathRoot + "/" + name + ".png");
        this.animationLoc = TomeOfBlood.prefix("animations/" + name + "_animations.json");
        this.name = name;
    }

    public ToBGenericModel(String name, String textPath) {
        this(name);
        this.textPathRoot = textPath;
        this.textLoc = TomeOfBlood.prefix("textures/" + textPathRoot + "/" + name + ".png");
    }
}
