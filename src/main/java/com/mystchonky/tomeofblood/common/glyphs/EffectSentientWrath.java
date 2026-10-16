package com.mystchonky.tomeofblood.common.glyphs;

import com.breakinblocks.neovitae.api.NeoVitaeAPI;
import com.breakinblocks.neovitae.common.datacomponent.SpiritusType;
import com.breakinblocks.neovitae.common.item.soul.SentientToolHelper;
import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.IDamageEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAOE;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDampen;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDurationDown;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentExtendTime;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentFortune;
import com.hollingsworth.arsnouveau.setup.registry.ModPotions;
import com.mystchonky.tomeofblood.TomeOfBlood;
import com.mystchonky.tomeofblood.client.registry.ParticleRegistry;
import com.mystchonky.tomeofblood.common.registry.IntegrationRegistry;
import com.mystchonky.tomeofblood.common.registry.MobEffectRegistry;
import com.mystchonky.tomeofblood.common.util.DemonWillUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EffectSentientWrath extends AbstractEffect implements IDamageEffect {

    public static EffectSentientWrath INSTANCE = new EffectSentientWrath();

    public EffectSentientWrath() {
        super(TomeOfBlood.prefix(GlyphLibrary.EffectSentientWrath), "Sentient Wrath");
    }

    @Override
    public void onResolveEntity(EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver) {
        if (!(rayTraceResult.getEntity() instanceof LivingEntity target && world instanceof ServerLevel level))
            return;
        if (!(shooter instanceof Player player))
            return;

        SpiritusType type = DemonWillUtil.getActiveTypeFromPlayer(spellContext);
        int souls = (int) NeoVitaeAPI.getInstance().getPlayerSpiritusHandler().getTotalSpiritus(type, player);
        int bracket = DemonWillUtil.getBracket(type, souls);
        Vec3 vec = safelyGetHitPos(rayTraceResult);
        float baseDamage = (float) (DAMAGE.get() + AMP_VALUE.get() * spellStats.getAmpMultiplier());
        double range = 3 + spellStats.getAoeMultiplier();
        int duration = (int) (POTION_TIME.get() + EXTEND_TIME.get() * spellStats.getDurationMultiplier());

        List<LivingEntity> targets = new ArrayList<>();

        if (!target.hasEffect(MobEffectRegistry.MARKED)) {
            target.addEffect(new MobEffectInstance(MobEffectRegistry.MARKED, 20 * duration, 1));
            attemptDamage(level, shooter, spellStats, spellContext, resolver, target, buildDamageSource(level, shooter), baseDamage);
            targets.add(target);
        } else {
            // heavy attack all targets
            float heavyDamage = baseDamage + 2.0f + DemonWillUtil.getExtraDamage(type, bracket);
            heavyAttack(vec, level, shooter, target, spellStats, spellContext, resolver, duration, heavyDamage);
            targets.add(target);

            for (LivingEntity mob : world.getEntitiesOfClass(LivingEntity.class, new AABB(target.position().add(range, range, range), target.position().subtract(range, range, range)))) {
                if (mob.equals(target) || mob.equals(shooter))
                    continue;
                vec = mob.position();
                heavyAttack(vec, level, shooter, mob, spellStats, spellContext, resolver, duration, heavyDamage);
                targets.add(mob);
            }
        }

        // Consume will
        double willCost = SentientToolHelper.SOUL_DRAIN_PER_SWING[Math.max(bracket, 0)] * targets.size() * (targets.size() > 1 ? 0.75 : 1);
        NeoVitaeAPI.getInstance().getPlayerSpiritusHandler().consumeSpiritus(type, player, willCost);

        //trigger on hit
        targets.stream().filter(it -> !it.isAlive()).findAny().ifPresent(it -> applyEffectOnKill(player, type, duration));


    }

    public void heavyAttack(Vec3 vec, ServerLevel level, LivingEntity shooter, LivingEntity target, SpellStats stats, SpellContext context, SpellResolver resolver, int duration, float damage) {
        // calculate heavy damage
        if (attemptDamage(level, shooter, stats, context, resolver, target, buildDamageSource(level, shooter), damage)) {
            level.sendParticles(ParticleRegistry.WRATH_SLASH.get(), vec.x, vec.y + 0.5, vec.z, 1,
                    ParticleUtil.inRange(-0.1, 0.1), ParticleUtil.inRange(-0.1, 0.1), ParticleUtil.inRange(-0.1, 0.1), 0.3);
            target.addEffect(new MobEffectInstance(MobEffectRegistry.MARKED, 20 * duration));
        }

    }

    public void applyEffectOnKill(Player player, SpiritusType type, int duration) {
        if (type == SpiritusType.RAW)
            return;
        MobEffectInstance instance = switch (type) {
            case RUINA -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, duration * 20);
            case VINDICTA -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, duration * 20);
            case INVICTUS -> new MobEffectInstance(MobEffects.ABSORPTION, duration * 20, 127);
            case NIHILUM -> new MobEffectInstance(ModPotions.SPELL_DAMAGE_EFFECT, duration * 20);
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };

        player.addEffect(instance);
        if (type == SpiritusType.INVICTUS) {
            float extra = player.getAbsorptionAmount();
            player.setAbsorptionAmount(Math.min(extra + player.getMaxHealth() * 0.2f, 10));
        }
    }

    @Override
    public void buildConfig(ModConfigSpec.Builder builder) {
        super.buildConfig(builder);
        addDamageConfig(builder, 3.0);
        addAmpConfig(builder, 2.0);
        addPotionConfig(builder, 20);
        addExtendTimeConfig(builder, 5);
    }

    @Override
    public void addAugmentDescriptions(Map<AbstractAugment, String> map) {
        super.addAugmentDescriptions(map);
        map.put(AugmentAOE.INSTANCE, "Increases the radius of the heavy attack.");
        map.put(AugmentExtendTime.INSTANCE, "Increases the duration of Marked and of the bonus granted on a kill.");
        map.put(AugmentDurationDown.INSTANCE, "Decreases the duration of Marked and of the bonus granted on a kill.");
    }

    @Override
    protected void addDefaultAugmentLimits(Map<ResourceLocation, Integer> defaults) {
        defaults.put(AugmentAmplify.INSTANCE.getRegistryName(), 2);
    }

    @Override
    protected int getDefaultManaCost() {
        return 40;
    }

    @Override
    protected @NotNull Set<AbstractAugment> getCompatibleAugments() {
        return augmentSetOf(
                AugmentAmplify.INSTANCE, AugmentDampen.INSTANCE,
                AugmentExtendTime.INSTANCE, AugmentDurationDown.INSTANCE,
                AugmentAOE.INSTANCE,
                AugmentFortune.INSTANCE
        );
    }

    @Override
    public String getBookDescription() {
        return "Marks a target on first hit. Attacking a marked target will do a scaling heavy attack in a radius and Marks all targets. Consumes Will. $(br2)Grants player bonuses on kill when not using Raw will.$(br2)" +
                "Corrosive - Resistance$(br)Vengeful - Speed$(br)Steadfast - Absorption$(br)Destructive - Spell Power";
    }

    @Override
    public SpellTier defaultTier() {
        return SpellTier.TWO;
    }

    @Override
    protected @NotNull Set<SpellSchool> getSchools() {
        return setOf(IntegrationRegistry.BLOODMAGIC);
    }
}
