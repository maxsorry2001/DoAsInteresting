package net.Gmaj7.funny_world.daiEffects.custom;

import net.Gmaj7.funny_world.daiInit.daiDamageTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class CarbonMonoxidePosisoningEffect extends MobEffect {
    public CarbonMonoxidePosisoningEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if(!(pLivingEntity instanceof Player && ((Player) pLivingEntity).isCreative()))
            pLivingEntity.hurt(new DamageSource(pLivingEntity.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(daiDamageTypes.CARBON_MONOXIDE_POISONING)), 1.5F);
        pLivingEntity.setAirSupply(0);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        int i = 20 >> pAmplifier;
        return i > 0 ? pAmplifier % i == 0 : true;
    }

    @Override
    public void onEffectStarted(LivingEntity pLivingEntity, int pAmplifier) {
        pLivingEntity.setAirSupply(0);
        super.onEffectStarted(pLivingEntity, pAmplifier);
    }
}
