package net.Gmaj7.funny_world.daiEffects.custom;

import net.Gmaj7.funny_world.daiInit.daiFunctions;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Vector3f;

public class DrownEffect extends MobEffect {
    public DrownEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        livingEntity.hurt(new DamageSource(daiFunctions.getHolder(livingEntity.level(), Registries.DAMAGE_TYPE, DamageTypes.DROWN)), 2);
        if(!livingEntity.level().isClientSide()){
            ((ServerLevel)livingEntity.level()).sendParticles(new DustParticleOptions(new Vector3f((float) 0xAF / 0xFF, (float) 0xEE / 0xFF, (float) 0xEE / 0xFF), 1.0F), livingEntity.getX(), livingEntity.getEyeY(), livingEntity.getZ(), 10, livingEntity.level().random.nextFloat() - 0.5, livingEntity.level().random.nextFloat() - 0.5, livingEntity.level().random.nextFloat() - 0.5, 1.0);
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int i = 40 >> amplifier;
        return i > 0 ? duration % i == 0 : true;
    }
}
