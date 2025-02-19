package net.Gmaj7.funny_world.daiEntities.custom;

import net.Gmaj7.funny_world.daiEntities.daiEntities;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class ThunderBallEntity extends AbstractHurtingProjectile {
    private int time;
    public ThunderBallEntity(EntityType<? extends AbstractHurtingProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ThunderBallEntity(double pX, double pY, double pZ, Level pLevel) {
        super(daiEntities.THUNDER_BALL_ENTITY.get(), pX, pY, pZ, pLevel);
    }

    public ThunderBallEntity(double pX, double pY, double pZ, Vec3 pMovement, Level pLevel) {
        super(daiEntities.THUNDER_BALL_ENTITY.get(), pX, pY, pZ, pMovement, pLevel);
    }

    public ThunderBallEntity(LivingEntity pOwner, Vec3 pMovement, Level pLevel) {
        super(daiEntities.THUNDER_BALL_ENTITY.get(), pOwner, pMovement, pLevel);
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        if (!this.level().isClientSide()){
            this.discard();
            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, this.level());
            lightningBolt.teleportTo(pResult.getBlockPos().getX(), pResult.getBlockPos().getY(), pResult.getBlockPos().getZ());
            this.level().addFreshEntity(lightningBolt);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if(!this.level().isClientSide()){
            this.discard();
            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, this.level());
            lightningBolt.teleportTo(pResult.getEntity().getX(), pResult.getEntity().getY(), pResult.getEntity().getZ());
            this.level().addFreshEntity(lightningBolt);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("time", time);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setTime(pCompound.getInt("time"));
    }

    @Override
    public void tick() {
        super.tick();
        if(this.time <= 0 && !this.level().isClientSide()){
            this.discard();
            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, this.level());
            lightningBolt.teleportTo(this.getX(), this.getY(), this.getZ());
            this.level().addFreshEntity(lightningBolt);
        }
        else setTime(getTime() - 1);
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    @Nullable
    @Override
    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.ELECTRIC_SPARK;
    }
}
