package net.Gmaj7.doAsInteresting.daiEntities.custom;

import net.Gmaj7.doAsInteresting.daiBlocks.custom.SculkTNT;
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

import java.util.List;

public class ThrownOxygenEntity extends ThrowableItemProjectile {
    public ThrownOxygenEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ThrownOxygenEntity(double pX, double pY, double pZ, Level pLevel) {
        super(daiEntities.THROWN_HYDROGEN_ENTITY.get(), pX, pY, pZ, pLevel);
    }

    public ThrownOxygenEntity(Level pLevel, LivingEntity pShooter) {
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
        if(entity.isOnFire()) {
            for (int dx = this.blockPosition().getX() - 4; dx <= this.blockPosition().getX() + 4; dx ++){
                for (int dy = this.blockPosition().getY() + 4; dy >= this.blockPosition().getY() - 4; dy --){
                    for (int dz = this.blockPosition().getZ() - 4; dz <= this.blockPosition().getZ() + 4; dz++){
                        BlockPos blockPos = new BlockPos(dx, dy, dz);
                        if(this.level().getBlockState(blockPos).is(Blocks.AIR)) {
                            if(this.level().getBlockState(blockPos.below()).is(BlockTags.SOUL_FIRE_BASE_BLOCKS))
                                this.level().setBlockAndUpdate(blockPos, Blocks.SOUL_FIRE.defaultBlockState());
                            else this.level().setBlockAndUpdate(blockPos, Blocks.FIRE.defaultBlockState());
                        }
                    }
                }
            }
        }
        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        BlockPos target = pResult.getBlockPos().relative(pResult.getDirection());
        BlockState blockState = this.level().getBlockState(target);
        if(blockState.is(BlockTags.FIRE)){
            for (int dx = this.blockPosition().getX() - 4; dx <= this.blockPosition().getX() + 4; dx ++){
                for (int dy = this.blockPosition().getY() + 4; dy >= this.blockPosition().getY() - 4; dy --){
                    for (int dz = this.blockPosition().getZ() - 4; dz <= this.blockPosition().getZ() + 4; dz++){
                        BlockPos blockPos = new BlockPos(dx, dy, dz);
                        if(this.level().getBlockState(blockPos).is(Blocks.AIR)) {
                            if(this.level().getBlockState(blockPos.below()).is(BlockTags.SOUL_FIRE_BASE_BLOCKS))
                                this.level().setBlockAndUpdate(blockPos, Blocks.SOUL_FIRE.defaultBlockState());
                            else this.level().setBlockAndUpdate(blockPos, Blocks.FIRE.defaultBlockState());
                        }
                    }
                }
            }
        }
        this.discard();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isOnFire()){
            for (int dx = this.blockPosition().getX() - 4; dx <= this.blockPosition().getX() + 4; dx ++){
                for (int dy = this.blockPosition().getY() + 4; dy >= this.blockPosition().getY() - 4; dy --){
                    for (int dz = this.blockPosition().getZ() - 4; dz <= this.blockPosition().getZ() + 4; dz++){
                        BlockPos blockPos = new BlockPos(dx, dy, dz);
                        if(this.level().getBlockState(blockPos).is(Blocks.AIR)) {
                            if(this.level().getBlockState(blockPos.below()).is(BlockTags.SOUL_FIRE_BASE_BLOCKS))
                                this.level().setBlockAndUpdate(blockPos, Blocks.SOUL_FIRE.defaultBlockState());
                            else this.level().setBlockAndUpdate(blockPos, Blocks.FIRE.defaultBlockState());
                        }
                    }
                }
            }
        }
    }
}
