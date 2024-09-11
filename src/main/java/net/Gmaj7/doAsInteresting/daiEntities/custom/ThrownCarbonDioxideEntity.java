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
import net.neoforged.neoforge.common.Tags;

public class ThrownCarbonDioxideEntity extends ThrowableItemProjectile {
    public ThrownCarbonDioxideEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ThrownCarbonDioxideEntity(double pX, double pY, double pZ, Level pLevel) {
        super(daiEntities.THROWN_CARBON_DIOXIDE_ENTITY.get(), pX, pY, pZ, pLevel);
    }

    public ThrownCarbonDioxideEntity(Level pLevel, LivingEntity pShooter) {
        super(daiEntities.THROWN_CARBON_DIOXIDE_ENTITY.get(), pShooter, pLevel);
    }

    @Override
    protected Item getDefaultItem() {
        return daiItems.CARBON_DIOXIDE.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();
        for (int dx = entity.blockPosition().getX() - 4; dx <= entity.blockPosition().getX() + 4; dx ++){
            for (int dy = entity.blockPosition().getY() + 4; dy >= entity.blockPosition().getY() - 4; dy --){
                for (int dz = entity.blockPosition().getZ() - 4; dz <= entity.blockPosition().getZ() + 4; dz++){
                    BlockPos blockPos = new BlockPos(dx, dy, dz);
                    if(this.level().getBlockState(blockPos).is(BlockTags.FIRE))
                        this.level().setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
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
        for (int dx = target.getX() - 4; dx <= target.getX() + 4; dx ++){
            for (int dy = target.getY() + 4; dy >= target.getY() - 4; dy --){
                for (int dz = target.getZ() - 4; dz <= target.getZ() + 4; dz++){
                    BlockPos blockPos = new BlockPos(dx, dy, dz);
                    if(this.level().getBlockState(blockPos).is(BlockTags.FIRE))
                        this.level().setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
                }
            }
        }
        this.discard();
    }

    @Override
    public void tick() {
        super.tick();
    }
}
