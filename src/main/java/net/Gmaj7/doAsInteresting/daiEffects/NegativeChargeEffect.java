package net.Gmaj7.doAsInteresting.daiEffects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class NegativeChargeEffect extends MobEffect {
    protected NegativeChargeEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        List<LivingEntity> list = pLivingEntity.level().getEntitiesOfClass(LivingEntity.class, pLivingEntity.getBoundingBox().inflate(5));
        for (LivingEntity target : list){
            if(target == pLivingEntity) continue;
            int flag;
            if(target.hasEffect(daiMobEffects.NEGATIVE_CHARGE)) flag = 1;
            else if (target.hasEffect(daiMobEffects.ELECTRIC_CHARGE)) flag = -1;
            else continue;
            Vec3 vec3 = new Vec3(target.getX() - pLivingEntity.getX(), target.getY() - pLivingEntity.getY(), target.getZ() - pLivingEntity.getZ());
            Vec3 vec31 = vec3;
            if(vec3.length() > 1) vec31 = vec31.normalize();
            double d = Math.max(vec3.length() * vec3.length(), 1);
            target.move(MoverType.SELF,  new Vec3(vec31.x() * flag / d, vec31.y() * flag / d, vec31.z() * flag / d));
        }
        return super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return true;
    }

    @Override
    public void onEffectStarted(LivingEntity pLivingEntity, int pAmplifier) {
        if(pLivingEntity.hasEffect(daiMobEffects.ELECTRIC_CHARGE)){
            pLivingEntity.removeEffect(daiMobEffects.NEGATIVE_CHARGE);
            pLivingEntity.removeEffect(daiMobEffects.ELECTRIC_CHARGE);
        }
        super.onEffectStarted(pLivingEntity, pAmplifier);
    }
}
