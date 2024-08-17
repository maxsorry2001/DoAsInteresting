package net.Gmaj7.doAsInteresting.daiEntities.custom;

import net.Gmaj7.doAsInteresting.daiEffects.daiMobEffects;
import net.Gmaj7.doAsInteresting.daiEntities.daiEntities;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.Random;

public class NetherBrickEntity extends ThrowableItemProjectile {
    private float hit_damage = 15F;
    private int piercing = 0;
    private float punch = 0F;
    private ItemStack itemStack = new ItemStack(Items.NETHER_BRICK);
    private boolean can_get = true;
    public NetherBrickEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public NetherBrickEntity(Level plevel, double pX, double pY, double pZ, ItemStack pPickupItemStack){
        super(daiEntities.BRICK_ENTITY.get(), pX, pY, pZ, plevel);
        this.setItemStack(pPickupItemStack);
    }

    public NetherBrickEntity(Level pLevel) {
        super(daiEntities.NETHER_BRICK_ENTITY.get(), pLevel);
    }

    public NetherBrickEntity(LivingEntity pShooter, Level pLevel, ItemStack itemStack) {
        super(daiEntities.NETHER_BRICK_ENTITY.get(), pShooter, pLevel);
        this.setItemStack(itemStack);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();
        if(!this.level().isClientSide){
            if(entity instanceof LivingEntity target){
                int i = new Random().nextInt(3) + 1;
                target.setRemainingFireTicks(i * 150);
                target.hurt(new DamageSource(target.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.PLAYER_ATTACK), this.getOwner()), this.getHitDamage() * i);
                target.addEffect(new MobEffectInstance(daiMobEffects.INTERNAL_INJURY, 120 * i));
                switch (i){
                    case 1 -> this.level().playSound(this, target.getOnPos(), SoundEvents.ITEM_BREAK, SoundSource.NEUTRAL, i, i);
                    case 2 -> this.level().playSound(this, target.getOnPos(), SoundEvents.IRON_GOLEM_ATTACK, SoundSource.NEUTRAL, i, i);
                    case 3 -> this.level().playSound(this, target.getOnPos(), SoundEvents.WARDEN_SONIC_BOOM, SoundSource.NEUTRAL, i, i);
                }
                target.knockback(punch, this.getOwner().getX() - target.getX(), this.getOwner().getZ() - target.getZ());
            }
            if(this.piercing > 1) this.piercing -= 1;
            else {
                if(this.can_get){
                    ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), this.itemStack);
                    this.level().addFreshEntity(itemEntity);
                    this.discard();
                }
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        if(!this.level().isClientSide && this.can_get){
            ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), this.itemStack);
            this.level().addFreshEntity(itemEntity);
            this.discard();
        }
    }

    @Override
    protected Item getDefaultItem() {
        return Items.NETHER_BRICK;
    }

    public void setHitDamage(float damage) {
        this.hit_damage = this.hit_damage * damage;
    }

    public float getHitDamage() {
        return this.hit_damage;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putFloat("hit_damage", this.hit_damage);
        pCompound.putBoolean("can_get", this.can_get);
        pCompound.put("item", this.itemStack.save(this.registryAccess()));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.hit_damage = pCompound.getFloat("hit_damage");
        this.can_get = pCompound.getBoolean("can_get");
        this.setItemStack(ItemStack.parse(this.registryAccess(), pCompound.getCompound("item")).get());
    }
    public void setCan_get(boolean can_get) {
        this.can_get = can_get;
    }

    public void setPiercing(int piercing) {
        this.piercing = piercing;
    }

    public void setPunch(float punch) {
        this.punch = punch;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}
