package net.Gmaj7.funny_world.daiEntities.custom;

import net.Gmaj7.funny_world.daiEffects.daiMobEffects;
import net.Gmaj7.funny_world.daiEntities.daiEntities;
import net.Gmaj7.funny_world.daiItems.daiItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class NegativeChargeEntity extends ThrowableItemProjectile {
    public NegativeChargeEntity(EntityType<? extends NegativeChargeEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public NegativeChargeEntity(Level pLevel) {
        super(daiEntities.NEGATIVE_CHARGE_ENTITY.get(), pLevel);
    }

    public NegativeChargeEntity(Level pLevel, LivingEntity pShooter) {
        super(daiEntities.NEGATIVE_CHARGE_ENTITY.get(), pShooter, pLevel);
    }

    @Override
    protected Item getDefaultItem() {
        return daiItems.NEGATIVE_CHARGE.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity target = pResult.getEntity();
        if(target instanceof LivingEntity){
            ((LivingEntity) target).addEffect(new MobEffectInstance(daiMobEffects.NEGATIVE_CHARGE, 300));
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        this.discard();
        BlockPos blockPos = pResult.getBlockPos().relative(pResult.getDirection());
        BlockState blockState = this.level().getBlockState(blockPos);
        if(blockState.is(Blocks.WATER) || blockState.is(Blocks.WATER_CAULDRON) && !this.isInWater()){
            ItemStack itemStack = new ItemStack(daiItems.HYDROGEN.get(), 2);
            ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), itemStack);
            this.level().addFreshEntity(itemEntity);
        }
        else if (this.isInWater()){
            this.discard();
        }
        else {
            ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), new ItemStack(this.getDefaultItem()));
            this.level().addFreshEntity(itemEntity);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if(this.isInWater() && !this.level().isClientSide()){
            ItemStack itemStack = new ItemStack(daiItems.HYDROGEN.get(), 2);
            ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), itemStack);
            this.level().addFreshEntity(itemEntity);
            this.discard();
        }
    }
}
