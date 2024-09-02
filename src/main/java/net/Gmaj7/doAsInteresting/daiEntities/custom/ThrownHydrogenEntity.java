package net.Gmaj7.doAsInteresting.daiEntities.custom;

import net.Gmaj7.doAsInteresting.daiEntities.daiEntities;
import net.Gmaj7.doAsInteresting.daiItems.daiItems;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class ThrownHydrogenEntity extends ThrowableItemProjectile {
    public ThrownHydrogenEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ThrownHydrogenEntity(double pX, double pY, double pZ, Level pLevel) {
        super(daiEntities.THROWN_HYDROGEN_ENTITY.get(), pX, pY, pZ, pLevel);
    }

    public ThrownHydrogenEntity(Level pLevel, LivingEntity pShooter) {
        super(daiEntities.THROWN_HYDROGEN_ENTITY.get(), pShooter, pLevel);
    }

    @Override
    protected Item getDefaultItem() {
        return daiItems.HYDROGEN.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();
        if(entity.isOnFire() && !this.level().isClientSide()){
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 4.0F, true, Level.ExplosionInteraction.BLOCK);
            this.level().setBlockAndUpdate(this.blockPosition(), Blocks.WATER.defaultBlockState());
        }
        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        BlockPos target = pResult.getBlockPos().relative(pResult.getDirection());
        BlockState blockState = this.level().getBlockState(target);
        if((blockState.is(BlockTags.FIRE)|| blockState.is(Blocks.LAVA) || blockState.is(Blocks.LAVA_CAULDRON)) && !this.level().isClientSide()){
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 4.0F, true, Level.ExplosionInteraction.BLOCK);
            this.level().setBlockAndUpdate(target, Blocks.WATER.defaultBlockState());
        }
        this.discard();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isOnFire() && !this.level().isClientSide()){
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 4.0F,true, Level.ExplosionInteraction.BLOCK);
            this.level().setBlockAndUpdate(this.blockPosition(), Blocks.WATER.defaultBlockState());
            this.discard();
        }
    }
}
