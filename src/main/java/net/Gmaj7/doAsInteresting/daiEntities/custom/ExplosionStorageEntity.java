package net.Gmaj7.doAsInteresting.daiEntities.custom;

import net.Gmaj7.doAsInteresting.daiEntities.daiEntities;
import net.Gmaj7.doAsInteresting.daiItems.daiItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class ExplosionStorageEntity extends ThrowableItemProjectile {

    private  float radius;
    public ExplosionStorageEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ExplosionStorageEntity(Level pLevel) {
        super(daiEntities.EXPLODE_STORAGE_ENTITY.get(), pLevel);
    }

    public ExplosionStorageEntity(Level pLevel, LivingEntity livingEntity) {
        super(daiEntities.EXPLODE_STORAGE_ENTITY.get(), livingEntity, pLevel);
    }

    @Override
    protected Item getDefaultItem() {
        return daiItems.EXPLOSION_STORAGE.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        if(!level().isClientSide){
            level().broadcastEntityEvent(this, (byte) 3);
            level().explode(this, pResult.getEntity().getX(), pResult.getEntity().getY(), pResult.getEntity().getZ(), radius, Level.ExplosionInteraction.TNT);
        }
        this.discard();
        super.onHitEntity(pResult);
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        if(!level().isClientSide){
            level().broadcastEntityEvent(this, (byte) 3);
            level().explode(this, pResult.getBlockPos().getX(), pResult.getBlockPos().getY(), pResult.getBlockPos().getZ(), radius, Level.ExplosionInteraction.TNT);
        }
        this.discard();
        super.onHitBlock(pResult);
    }

    public void setRadius(Float pRadius) {
        this.radius = pRadius;
    }
}
