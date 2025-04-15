package net.Gmaj7.funny_world.daiItems.custom;

import net.Gmaj7.funny_world.daiEntities.custom.EntitiesArrowEntity;
import net.Gmaj7.funny_world.daiEntities.custom.MomentumArrowEntity;
import net.Gmaj7.funny_world.daiInit.daiFunctions;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class EntitiesArrowItem extends ArrowItem {
    public EntitiesArrowItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public AbstractArrow createArrow(Level pLevel, ItemStack pAmmo, LivingEntity pShooter, @Nullable ItemStack pWeapon) {
        EntitiesArrowEntity entitiesArrowEntity = new EntitiesArrowEntity(pLevel, pShooter);
        LivingEntity livingEntity = pShooter.level().getNearestEntity(LivingEntity.class, TargetingConditions.forNonCombat().range(6), pShooter, pShooter.getX(), pShooter.getY(), pShooter.getZ(), pShooter.getBoundingBox().inflate(6));
        if(pWeapon != null)
            entitiesArrowEntity.setEntityHealth(livingEntity.getHealth());
        return entitiesArrowEntity;
    }

    @Override
    public Projectile asProjectile(Level pLevel, Position pPos, ItemStack pStack, Direction pDirection) {
        EntitiesArrowEntity entitiesArrowEntity = new EntitiesArrowEntity(pLevel, pPos.x(), pPos.y(), pPos.z());
        entitiesArrowEntity.pickup = AbstractArrow.Pickup.ALLOWED;
        return entitiesArrowEntity;
    }
}
