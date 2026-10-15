package com.mystchonky.tomeofblood.common.glyphs;

import com.breakinblocks.neovitae.api.NeoVitaeAPI;
import com.breakinblocks.neovitae.common.datacomponent.SpiritusType;
import com.breakinblocks.neovitae.common.effect.NVMobEffects;
import com.breakinblocks.neovitae.common.item.soul.SentientToolHelper;
import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.IDamageEffect;
import com.hollingsworth.arsnouveau.api.spell.IPotionEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDampen;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDurationDown;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentExtendTime;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentFortune;
import com.mystchonky.tomeofblood.TomeOfBlood;
import com.mystchonky.tomeofblood.common.registry.IntegrationRegistry;
import com.mystchonky.tomeofblood.common.registry.MobEffectRegistry;
import com.mystchonky.tomeofblood.common.util.DemonWillUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.ForgeConfigSpec;
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

    public MobEffect getEffectType(SpiritusType type) {
        return switch (type) {
            case RAW -> MobEffects.POISON;
            case RUINA -> MobEffects.WITHER;
            case VINDICTA -> MobEffects.WEAKNESS;
            case INVICTUS -> MobEffects.MOVEMENT_SLOWDOWN;
            case NIHILUM -> MobEffectRegistry.VULNERABLE.get();
        };
    }

    @Override
    public void buildConfig(ForgeConfigSpec.Builder builder) {
        super.buildConfig(builder);
        addDamageConfig(builder, 4.0);
        addAmpConfig(builder, 2.0);
        addPotionConfig(builder, 5);
        addExtendTimeConfig(builder, 5);
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
        return "Consume Demon Will and attack your target (damage scales with will in inventory). Target drops Raw will on kill. $(br2)Extend Time applies potion effect based on Will type. $(br2)" +
                "Raw - Poison $(br)Corrosive - Withering $(br)Vengeful - Weakness $(br)Steadfast - Slowness $(br)Destructive - Vulnerable ";
    }

    public Set<SpellSchool> getSchools() {
        return setOf(IntegrationRegistry.BLOODMAGIC);
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

