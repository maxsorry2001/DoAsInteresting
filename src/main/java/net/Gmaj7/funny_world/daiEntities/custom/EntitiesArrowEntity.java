package net.Gmaj7.funny_world.daiEntities.custom;

import net.Gmaj7.funny_world.daiEntities.daiEntities;
import net.Gmaj7.funny_world.daiInit.daiFunctions;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

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
        super.onHitBlock(result);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        if(entity instanceof LivingEntity target){
            if(this.entityHealth > 0)
                target.hurt(new DamageSource(daiFunctions.getHolder(level(), Registries.DAMAGE_TYPE, DamageTypes.SONIC_BOOM), getOwner()), entityHealth);
            this.discard();
        }
    }

    public void setEntityHealth(float entityHealth) {
        this.entityHealth = entityHealth;
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(Items.ARROW);
    }
}
