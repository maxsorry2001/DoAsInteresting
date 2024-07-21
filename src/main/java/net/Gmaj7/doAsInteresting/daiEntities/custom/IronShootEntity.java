package net.Gmaj7.doAsInteresting.daiEntities.custom;

import net.Gmaj7.doAsInteresting.daiEntities.daiEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class IronShootEntity extends AbstractArrow {

    private  ItemStack shootItem;
    private float charge;
    public IronShootEntity(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public IronShootEntity(Level pLevel) {
        super(daiEntities.IRON_SHOOT_ENTITY.get(), pLevel);
    }

    public IronShootEntity(Level pLevel,LivingEntity pOwner ,ItemStack pItemStack){
        super(daiEntities.IRON_SHOOT_ENTITY.get(), pOwner, pLevel, pItemStack);
        this.setShootItem(pItemStack);
    }
    public IronShootEntity(LivingEntity pOwner, Level pLevel) {
        super(daiEntities.IRON_SHOOT_ENTITY.get(), pOwner, pLevel, new ItemStack(Items.IRON_INGOT));
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity target = pResult.getEntity();
        if(target instanceof LivingEntity && !this.level().isClientSide()){
            target.hurt(new DamageSource(target.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.SONIC_BOOM), this, this.getOwner()), (float) (this.getBaseDamage() * 75 * this.charge));
            ItemStack itemStack = this.getDefaultPickupItem();
            ItemEntity itemEntity = new ItemEntity(target.level(), target.getX(), target.getY(), target.getZ(), itemStack);
            this.level().addFreshEntity(itemEntity);
            this.remove(RemovalReason.KILLED);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        if(!this.level().isClientSide()){
            BlockPos blockPos = pResult.getBlockPos();
            this.level().explode(this, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 3F * this.charge, false, Level.ExplosionInteraction.TNT);
            ItemEntity itemEntity = new ItemEntity(this.level(), blockPos.getX(), blockPos.getY(), blockPos.getZ(), shootItem);
            this.level().addFreshEntity(itemEntity);
            this.remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return this.shootItem;
    }

    public void setShootItem(ItemStack shootItem) {
        this.shootItem = shootItem;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putFloat("charge", charge);
        pCompound.put("item", this.shootItem.save(this.registryAccess()));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.charge = pCompound.getFloat("charge");
        this.setShootItem(ItemStack.parse(this.registryAccess(), pCompound.getCompound("item")).get());
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.ANVIL_LAND;
    }

    public void setCharge(float charge) {
        this.charge = charge;
    }
}
