package net.Gmaj7.funny_world.daiEntities.custom;

import net.Gmaj7.funny_world.daiEntities.daiEntities;
import net.Gmaj7.funny_world.daiInit.daiFunctions;
import net.Gmaj7.funny_world.daiItems.daiItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class WaterBomb extends WaterBowShoot{
    private static final float directDamage = 8.0F;
    private static final float wideDamage = 4.0F;

    public WaterBomb(EntityType<? extends WaterBowShoot> entityType, Level level) {
        super(entityType, level);
        this.pickup = Pickup.DISALLOWED;
    }

    public WaterBomb(Level pLevel) {
        super(daiEntities.WATER_BOMB.get(), pLevel);
        waterBow = new ItemStack(daiItems.WATER_BOW.get());
    }

    public WaterBomb(Level pLevel, LivingEntity pOwner, ItemStack itemStack){
        super(daiEntities.WATER_BOMB.get(), pLevel);
        this.setOwner(pOwner);
        this.setPos(pOwner.getX(), pOwner.getEyeY() - 0.1, pOwner.getZ());
        this.waterBow = itemStack.copy();
        this.pickup = Pickup.DISALLOWED;
    }
    public WaterBomb(LivingEntity pOwner, Level pLevel) {
        super(daiEntities.WATER_BOMB.get(), pLevel);
        this.waterBow = new ItemStack(daiItems.WATER_BOW.get());
        this.setOwner(pOwner);
        this.pickup = Pickup.DISALLOWED;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity target = result.getEntity();
        if(!level().isClientSide() && target instanceof LivingEntity){
            target.hurt(new DamageSource(daiFunctions.getHolder(level(), Registries.DAMAGE_TYPE, DamageTypes.MAGIC), getOwner()),directDamage);
            List<LivingEntity> list = level().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(3));
            if(getOwner() != null && list.equals(getOwner())) list.remove(getOwner());
            for (LivingEntity livingEntity : list) {
                livingEntity.hurt(new DamageSource(daiFunctions.getHolder(level(), Registries.DAMAGE_TYPE, DamageTypes.MAGIC), getOwner()), wideDamage);
            }
        }
        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        if(!level().isClientSide()){
            List<LivingEntity> list = level().getEntitiesOfClass(LivingEntity.class, new AABB(result.getBlockPos()).inflate(3));
            if(getOwner() != null && list.equals(getOwner())) list.remove(getOwner());
            for (LivingEntity livingEntity : list) {
                livingEntity.hurt(new DamageSource(daiFunctions.getHolder(level(), Registries.DAMAGE_TYPE, DamageTypes.MAGIC), getOwner()), wideDamage);
            }
        }
        this.discard();
    }



}
