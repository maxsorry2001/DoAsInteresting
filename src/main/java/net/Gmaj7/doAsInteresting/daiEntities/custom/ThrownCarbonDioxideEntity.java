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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.neoforge.common.Tags;

import java.util.List;

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
        fireSuppression(pResult.getEntity().blockPosition());
        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        fireSuppression(pResult.getBlockPos().relative(pResult.getDirection()));
        this.discard();
    }

    @Override
    public void tick() {
        super.tick();
    }

    private void fireSuppression(BlockPos blockPos){
        for (int dx = blockPos.getX() - 4; dx <= blockPos.getX() + 4; dx ++){
            for (int dy = blockPos.getY() + 4; dy >= blockPos.getY() - 4; dy --){
                for (int dz = blockPos.getZ() - 4; dz <= blockPos.getZ() + 4; dz++){
                    BlockPos firePos = new BlockPos(dx, dy, dz);
                    if(this.level().getBlockState(firePos).is(BlockTags.FIRE))
                        this.level().setBlockAndUpdate(firePos, Blocks.AIR.defaultBlockState());
                }
            }
        }
        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, new AABB(blockPos).inflate(4));
        for (LivingEntity target : list){
            if (target.isOnFire()) target.clearFire();
        }
    }
}
