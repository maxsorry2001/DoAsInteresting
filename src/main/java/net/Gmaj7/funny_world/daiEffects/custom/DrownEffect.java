package net.Gmaj7.funny_world.daiEffects.custom;

import net.Gmaj7.funny_world.daiInit.daiFunctions;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class DrownEffect extends MobEffect {
    public DrownEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        livingEntity.hurt(new DamageSource(daiFunctions.getHolder(livingEntity.level(), Registries.DAMAGE_TYPE, DamageTypes.DROWN)), 2 * amplifier);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration % 40 == 0;
    }
}
