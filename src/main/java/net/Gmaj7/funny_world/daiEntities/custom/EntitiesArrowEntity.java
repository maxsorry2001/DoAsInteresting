package net.Gmaj7.funny_world.daiEntities.custom;

import net.Gmaj7.funny_world.daiEntities.daiEntities;
import net.Gmaj7.funny_world.daiInit.daiFunctions;
import net.Gmaj7.funny_world.daiItems.daiItems;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.joml.Vector3f;

import java.util.List;

public class EntitiesArrowEntity extends AbstractArrow {
    private float entityHealth;
    public EntitiesArrowEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public EntitiesArrowEntity(Level level, double x, double y, double z) {
        super(daiEntities.ENTITIES_ARROW_ENTITY.get(), level);
        this.setPos(x, y, z);
    }

    public EntitiesArrowEntity(Level level, LivingEntity pOwner) {
        super(daiEntities.ENTITIES_ARROW_ENTITY.get(), level);
        this.setOwner(pOwner);
        this.setPos(pOwner.getX(), pOwner.getEyeY() - 0.1, pOwner.getZ());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putFloat("entity_health", entityHealth);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.entityHealth = compound.getFloat("entity_health");
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        makeDamage();
        makeParticleAndSound();
        this.discard();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if(result.getEntity() instanceof LivingEntity){
            makeDamage();
            makeParticleAndSound();
            this.discard();
        }
    }

    public void setEntityHealth(float entityHealth) {
        this.entityHealth = entityHealth;
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(daiItems.ENTITIES_ARROW.get());
    }

    protected void makeDamage(){
        List<LivingEntity> list = level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(5));
        if(this.entityHealth > 0 && !list.isEmpty()) {
            for (LivingEntity target : list)
                if(target != getOwner())
                    target.hurt(new DamageSource(daiFunctions.getHolder(level(), Registries.DAMAGE_TYPE, DamageTypes.SONIC_BOOM), getOwner()), entityHealth);
        }
    }

    protected void makeParticleAndSound(){
       if(level() instanceof ServerLevel serverLevel){
           serverLevel.sendParticles(new DustParticleOptions(new Vector3f(1f, 1f,1f), 2), getX(), getY(), getZ(), 800, 5, 5, 5, 0.5);
           serverLevel.sendParticles(new DustParticleOptions(new Vector3f(1f, 0, 0), 2), getX(), getY(), getZ(), 800, 5, 5, 5, 0.5);
           serverLevel.sendParticles(new DustParticleOptions(new Vector3f(0, 0, 1f), 2), getX(), getY(), getZ(), 800, 5, 5, 5, 0.5);
           serverLevel.playSound(this, this.getOnPos(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.NEUTRAL, 5, 5);
       }
    }
}
