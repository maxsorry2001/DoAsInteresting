package net.Gmaj7.funny_world.daiEntities.custom;

import net.Gmaj7.funny_world.daiEffects.daiMobEffects;
import net.Gmaj7.funny_world.daiEntities.daiEntities;
import net.Gmaj7.funny_world.daiItems.daiItems;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
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

import java.util.List;

public class ThrownCarbonMonoxideEntity extends ThrowableItemProjectile {
    public ThrownCarbonMonoxideEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ThrownCarbonMonoxideEntity(double pX, double pY, double pZ, Level pLevel) {
        super(daiEntities.THROWN_CARBON_MONOXIDE_ENTITY.get(), pX, pY, pZ, pLevel);
    }

    public ThrownCarbonMonoxideEntity(Level pLevel, LivingEntity pShooter) {
        super(daiEntities.THROWN_CARBON_MONOXIDE_ENTITY.get(), pShooter, pLevel);
    }

    @Override
    protected Item getDefaultItem() {
        return daiItems.CARBON_MONOXIDE.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();
        if(!this.level().isClientSide()){
            if(entity.isOnFire()){
                this.level().explode(this, this.getX(), this.getY(), this.getZ(), 4.0F, true, Level.ExplosionInteraction.BLOCK);
            }
            else{
                List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(4));
                for (LivingEntity target : list)
                    target.addEffect(new MobEffectInstance(daiMobEffects.CARBON_MONOXIDE_POISONING, 300));
            }
        }
        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        BlockPos target = pResult.getBlockPos().relative(pResult.getDirection());
        BlockState blockState = this.level().getBlockState(target);
        if(!this.level().isClientSide()){
            if((blockState.is(BlockTags.FIRE)|| blockState.is(Blocks.LAVA) || blockState.is(Blocks.LAVA_CAULDRON))){
                this.level().explode(this, this.getX(), this.getY(), this.getZ(), 4.0F, true, Level.ExplosionInteraction.BLOCK);
            }
            else {
                List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, new AABB(target).inflate(4));
                for (LivingEntity targetEntity : list)
                    targetEntity.addEffect(new MobEffectInstance(daiMobEffects.CARBON_MONOXIDE_POISONING, 300));
            }
        }
        this.discard();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isOnFire() && !this.level().isClientSide()){
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 4.0F,true, Level.ExplosionInteraction.BLOCK);
            this.discard();
        }
    }
}
