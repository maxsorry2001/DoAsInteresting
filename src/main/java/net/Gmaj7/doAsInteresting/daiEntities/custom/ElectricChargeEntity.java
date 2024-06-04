package net.Gmaj7.doAsInteresting.daiEntities.custom;

import net.Gmaj7.doAsInteresting.daiEffects.daiMobEffects;
import net.Gmaj7.doAsInteresting.daiEntities.daiEntities;
import net.Gmaj7.doAsInteresting.daiItems.daiItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class ElectricChargeEntity extends ThrowableItemProjectile {
    public ElectricChargeEntity(EntityType<? extends ElectricChargeEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ElectricChargeEntity(Level pLevel) {
        super(daiEntities.ELECTRIC_CHARGE_ENTITY.get(), pLevel);
    }

    public ElectricChargeEntity(Level pLevel, LivingEntity pShooter) {
        super(daiEntities.ELECTRIC_CHARGE_ENTITY.get(), pShooter, pLevel);
    }

    @Override
    protected Item getDefaultItem() {
        return daiItems.ELECTRIC_CHARGE.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity target = pResult.getEntity();
        if(target instanceof LivingEntity){
            ((LivingEntity) target).addEffect(new MobEffectInstance(daiMobEffects.ELECTRIC_CHARGE, 300));
            this.remove(RemovalReason.KILLED);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        this.remove(RemovalReason.KILLED);
        ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), new ItemStack(this.getDefaultItem()));
        this.level().addFreshEntity(itemEntity);
    }
}
