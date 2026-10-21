package mod.tomeofblood.common.glyphs;

import com.breakinblocks.neovitae.api.NeoVitaeAPI;
import com.breakinblocks.neovitae.common.datacomponent.SpiritusType;
import com.breakinblocks.neovitae.common.effect.NVMobEffects;
import com.breakinblocks.neovitae.common.item.soul.SentientToolHelper;
import com.hollingsworth.arsnouveau.api.spell.*;
import com.hollingsworth.arsnouveau.common.spell.augment.*;
import mod.tomeofblood.TomeOfBlood;
import mod.tomeofblood.common.util.DemonWillUtil;
import mod.tomeofblood.init.IntegrationRegistrar;
import mod.tomeofblood.init.MobEffectRegistrar;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;

public class EffectSentientHarm extends AbstractEffect implements IDamageEffect, IPotionEffect {

    public static EffectSentientHarm INSTANCE = new EffectSentientHarm();

    public EffectSentientHarm() {
        super(TomeOfBlood.prefix(GlyphLibrary.EffectSentientHarm), "Sentient Harm");
    }

    @Override
    public void onResolveEntity(EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver) {
        if (shooter instanceof Player player && rayTraceResult.getEntity() instanceof LivingEntity target) {
            SpiritusType type = DemonWillUtil.getActiveTypeFromPlayer(spellContext);
            int souls = (int) NeoVitaeAPI.getInstance().getPlayerSpiritusHandler().getTotalSpiritus(type, player);
            int bracket = DemonWillUtil.getBracket(type, souls);
            int time = (int) spellStats.getDurationMultiplier();
            float damage = (float) (DAMAGE.get() + DemonWillUtil.getExtraDamage(type, bracket) + (AMP_VALUE.get() * spellStats.getAmpMultiplier()));

            target.addEffect(new MobEffectInstance(NVMobEffects.SPIRITUS_SNARE, 300, 0, false, false));
            applyConfigPotion(target, NVMobEffects.SPIRITUS_SNARE, spellStats, false);

            if (time > 0) {
                applyConfigPotion(target, getEffectType(type), spellStats);
            } else {
                // TODO: Change to bloodmagic damage source later
                boolean damaged = attemptDamage(world, shooter, spellStats, spellContext, resolver, target, buildDamageSource(world, shooter), damage);
                if (damaged && bracket >= 0) {
                    NeoVitaeAPI.getInstance().getPlayerSpiritusHandler().consumeSpiritus(type, player, SentientToolHelper.SOUL_DRAIN_PER_SWING[bracket]);
                }
            }
        }

    }

    public Holder<MobEffect> getEffectType(SpiritusType type) {
        return switch (type) {
            case RAW -> MobEffects.POISON;
            case RUINA -> MobEffects.WITHER;
            case VINDICTA -> MobEffects.WEAKNESS;
            case INVICTUS -> MobEffects.MOVEMENT_SLOWDOWN;
            case NIHILUM -> MobEffectRegistrar.VULNERABLE;
        };
    }

    @Override
    public void buildConfig(ModConfigSpec.Builder builder) {
        super.buildConfig(builder);
        addDamageConfig(builder, 4.0);
        addAmpConfig(builder, 2.0);
        addPotionConfig(builder, 5);
        addExtendTimeConfig(builder, 5);
    }

    @Override
    public void addAugmentDescriptions(Map<AbstractAugment, String> map) {
        super.addAugmentDescriptions(map);
        map.put(AugmentExtendTime.INSTANCE, "Applies an effect based on the Spiritus type instead of dealing damage, increases the duration.");
        map.put(AugmentDurationDown.INSTANCE, "Applies an effect based on the Spiritus type instead of dealing damage, decreases the duration.");
        map.put(AugmentAmplify.INSTANCE, "Increases damage dealt, or the level of the effect applied.");
    }

    @Override
    protected void addDefaultAugmentLimits(Map<ResourceLocation, Integer> defaults) {
        defaults.put(AugmentAmplify.INSTANCE.getRegistryName(), 2);
    }

    @Override
    public int getDefaultManaCost() {
        return 25;
    }

    @Override
    public @NotNull Set<AbstractAugment> getCompatibleAugments() {
        return augmentSetOf(AugmentAmplify.INSTANCE, AugmentDampen.INSTANCE, AugmentExtendTime.INSTANCE, AugmentDurationDown.INSTANCE, AugmentFortune.INSTANCE);
    }

    @Override
    public String getBookDescription() {
        return "Consumes Spiritus to attack your target. Damage scales with the Spiritus you carry, and the target drops Spiritus when killed. " +
                "With Extend Time, it applies an effect based on your Spiritus type instead: Raw causes Poison, Ruina causes Wither, Vindicta causes Weakness, Invictus causes Slowness and Nihilum causes Vulnerable.";
    }

    public Set<SpellSchool> getSchools() {
        return setOf(IntegrationRegistrar.BLOODMAGIC);
    }

    @Override
    public int getBaseDuration() {
        return POTION_TIME == null ? 30 : POTION_TIME.get();
    }

    @Override
    public int getExtendTimeDuration() {
        return EXTEND_TIME == null ? 8 : EXTEND_TIME.get();
    }
}

