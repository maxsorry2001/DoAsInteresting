package net.Gmaj7.funny_world.daiEnchantments.custom;

import com.mojang.serialization.MapCodec;
import net.Gmaj7.funny_world.daiEffects.daiMobEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record CharmEnchantmentEffect() implements EnchantmentEntityEffect {
    public static final MapCodec<CharmEnchantmentEffect> CODEC = MapCodec.unit(CharmEnchantmentEffect::new);
    @Override
    public void apply(ServerLevel serverLevel, int i, EnchantedItemInUse enchantedItemInUse, Entity entity, Vec3 vec3) {
        if(entity instanceof LivingEntity)
            ((LivingEntity) entity).addEffect(new MobEffectInstance(daiMobEffects.CHARM, 500, i));
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
