package net.Gmaj7.funny_world.daiEffects.custom;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;

import java.util.List;

public class CharmEffect extends MobEffect {
    public CharmEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        return super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return super.shouldApplyEffectTickThisTick(duration, amplifier);
    }

    @Override
    public void onEffectStarted(LivingEntity livingEntity, int amplifier) {
        List<Mob> list = livingEntity.level().getEntitiesOfClass(Mob.class, livingEntity.getBoundingBox().inflate(55 * amplifier));
        for (Mob target : list){
            if(!(target instanceof Enemy)) continue;
            target.setTarget(livingEntity);
        }
        super.onEffectStarted(livingEntity, amplifier);
    }
}
