package net.Gmaj7.funny_world.daiItems.custom;

import net.Gmaj7.funny_world.daiEntities.custom.MomentumArrowEntity;
import net.Gmaj7.funny_world.daiInit.daiFunctions;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class MomentumArrowItem extends ArrowItem {
    public MomentumArrowItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public AbstractArrow createArrow(Level pLevel, ItemStack pAmmo, LivingEntity pShooter, @Nullable ItemStack pWeapon) {
        MomentumArrowEntity momentumArrowEntity = new MomentumArrowEntity(pLevel, pShooter, pAmmo.copyWithCount(1));
        if(pWeapon != null)
            momentumArrowEntity.setPower(EnchantmentHelper.getEnchantmentLevel(daiFunctions.getEnchantmentHolder(pLevel, Enchantments.POWER), pShooter));
        return momentumArrowEntity;
    }

    @Override
    public Projectile asProjectile(Level pLevel, Position pPos, ItemStack pStack, Direction pDirection) {
        MomentumArrowEntity momentumArrowEntity = new MomentumArrowEntity(pLevel, pPos.x(), pPos.y(), pPos.z());
        momentumArrowEntity.pickup = AbstractArrow.Pickup.ALLOWED;
        return momentumArrowEntity;
    }
}
