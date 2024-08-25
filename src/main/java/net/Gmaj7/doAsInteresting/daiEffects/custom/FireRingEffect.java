package net.Gmaj7.doAsInteresting.daiEffects.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public class FireRingEffect extends MobEffect {
    public FireRingEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        List<LivingEntity> list = pLivingEntity.level().getEntitiesOfClass(LivingEntity.class,  pLivingEntity.getBoundingBox().inflate(3,3,3));
        for (LivingEntity target : list){
            if(target == pLivingEntity) continue;
            target.setRemainingFireTicks(20);
        }
        if(pAmplifier % 10 == 0){
            for (int j = 1; j < 45; j++){
                double r = j * 2 *Math.PI / 45;
                pLivingEntity.level().addParticle(ParticleTypes.LANDING_LAVA, pLivingEntity.getX() + 3 * Math.sin(r), pLivingEntity.getY() + 0.1, pLivingEntity.getZ() + 3 * Math.cos(r), 0, 0, 0);
            }
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        int i = 20 >> pAmplifier;
        return i > 0 ? pAmplifier % i == 0 : true;
    }
}
