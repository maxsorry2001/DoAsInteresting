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
    public NetherBrickEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public NetherBrickEntity(Level pLevel) {
        super(daiEntities.NETHER_BRICK_ENTITY.get(), pLevel);
    }

    public NetherBrickEntity(LivingEntity pShooter, Level pLevel) {
        super(daiEntities.NETHER_BRICK_ENTITY.get(), pShooter, pLevel);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();
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
            ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), new ItemStack(this.getDefaultItem()));
            this.level().addFreshEntity(itemEntity);
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), new ItemStack(this.getDefaultItem()));
        this.level().addFreshEntity(itemEntity);
        this.discard();
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
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.hit_damage = pCompound.getFloat("hit_damage");
    }

    public void setPiercing(int piercing) {
        this.piercing = piercing;
    }

    public void setPunch(float punch) {
        this.punch = punch;
    }
}
