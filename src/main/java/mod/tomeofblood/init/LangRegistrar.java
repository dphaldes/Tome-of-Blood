package mod.tomeofblood.init;

import mod.tomeofblood.TomeOfBlood;
import net.minecraft.network.chat.Component;

import java.util.HashMap;
import java.util.Map;

public class LangRegistrar {
    public static final Map<String, String> entries = new HashMap<>();

    public static void init() {
        key(MobEffectRegistrar.SERENE.get().getDescriptionId(), "Serene");
        key(MobEffectRegistrar.VULNERABLE.get().getDescriptionId(), "Vulnerable");
        key(MobEffectRegistrar.MARKED.get().getDescriptionId(), "Marked");

        typeKey("itemGroup", "tome_of_blood", "Tome Of Blood");
    }

    public static final Entry SCHOOL_BLOODMAGIC = key("ars_nouveau.school." + IntegrationRegistrar.BLOODMAGIC.getId(), "Blood Magic");
    public static final Entry LOW_LP = typeKey("alert", "no_lp", "Your soul feels weak..");
    public static final Entry MANA_BONUS_UPGRADE = typeKey("sentient_upgrade", "mana_bonus", "Mana Attunement");

    private static Entry key(String key, String translation) {
        entries.put(key, translation);
        return new Entry(key, Component.translatable(key));
    }

    private static Entry typeKey(String type, String key, String translation) {
        return key(type + "." + TomeOfBlood.MODID + "." + key, translation);
    }

    public record Entry(String key, Component component) {
    }
}
