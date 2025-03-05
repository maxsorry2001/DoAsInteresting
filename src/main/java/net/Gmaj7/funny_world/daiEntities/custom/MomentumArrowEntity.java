package net.Gmaj7.funny_world.daiEntities.custom;

import net.Gmaj7.funny_world.daiEntities.daiEntities;
import net.Gmaj7.funny_world.daiInit.daiFunctions;
import net.Gmaj7.funny_world.daiItems.daiItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
public class MomentumArrowEntity extends AbstractArrow {
    private final double m = 1.5;
    private double power = 1;
    protected MomentumArrowEntity(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public MomentumArrowEntity(Level pLevel, double pX, double pY, double pZ) {
        super(daiEntities.MOMENTUM_ARROW_ENTITY.get(), pLevel);
        this.setPos(pX, pY, pZ);
    }

    public MomentumArrowEntity(Level pLevel, LivingEntity pOwner, ItemStack itemStack) {
        super(daiEntities.MOMENTUM_ARROW_ENTITY.get(), pLevel);
        this.setOwner(pOwner);
        this.setPos(pOwner.getX(), pOwner.getEyeY() - 0.1, pOwner.getZ());
        this.setPickupItemStack(itemStack.copy());
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        Entity entity = pResult.getEntity();
        if(entity instanceof LivingEntity target){
            Vec3 vec3 = this.getDeltaMovement();
            Vec3 vec31 = target.getDeltaMovement();
            AABB aabb = target.getBoundingBox();
            double mT = aabb.getSize() * 3;
            Vec3 vec32 = new Vec3((m * vec3.x() * power + mT * vec31.x()) / (m + mT),
                    (m * vec3.y() * power + mT * vec31.y()) / (m + mT),
                    (m * vec3.z() * power + mT * vec31.z()) / (m + mT));
            target.setDeltaMovement(vec32);
            double f = (Math.abs(vec32.length() * (m + mT) - vec31.length() * mT - vec3.length() * m) / 0.02);
            target.hurt(new DamageSource(daiFunctions.getHolder(this.level(), Registries.DAMAGE_TYPE, DamageTypes.GENERIC)), (float) f);
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        Vec3 vec3 = this.getDeltaMovement();
        double momentum = vec3.length() * power;
        this.level().explode(getOwner(), this.getX(), this.getY(), this.getZ(), (float) momentum / 2, false, Level.ExplosionInteraction.TNT);
        this.discard();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putDouble("m_power", power);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.power = pCompound.getDouble("m_power");
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(daiItems.MOMENTUM_ARROW.get());
    }

    public void setPower(double power) {
        this.power = power + 1;
    }
}
