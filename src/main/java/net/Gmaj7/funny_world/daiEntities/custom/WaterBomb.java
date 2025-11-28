package net.Gmaj7.funny_world.daiEntities.custom;

import net.Gmaj7.funny_world.daiEntities.daiEntities;
import net.Gmaj7.funny_world.daiInit.daiFunctions;
import net.Gmaj7.funny_world.daiItems.daiItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.joml.Vector3f;

import java.util.List;

public class WaterBomb extends WaterBowShoot{
    private static final float directDamage = 8.0F;
    private static final float wideDamage = 4.0F;
    public int animationTime = 1;
    public final AnimationState animationState = new AnimationState();

    public WaterBomb(EntityType<? extends WaterBowShoot> entityType, Level level) {
        super(entityType, level);
        this.pickup = Pickup.DISALLOWED;
    }

    public WaterBomb(Level pLevel) {
        super(daiEntities.WATER_BOMB.get(), pLevel);
        waterBow = new ItemStack(daiItems.WATER_BOW.get());
    }

    public WaterBomb(Level pLevel, LivingEntity pOwner, ItemStack itemStack){
        super(daiEntities.WATER_BOMB.get(), pLevel);
        this.setOwner(pOwner);
        this.setPos(pOwner.getX(), pOwner.getEyeY() - 0.1, pOwner.getZ());
        this.waterBow = itemStack.copy();
        this.pickup = Pickup.DISALLOWED;
    }
    public WaterBomb(LivingEntity pOwner, Level pLevel) {
        super(daiEntities.WATER_BOMB.get(), pLevel);
        this.waterBow = new ItemStack(daiItems.WATER_BOW.get());
        this.setOwner(pOwner);
        this.pickup = Pickup.DISALLOWED;
    }

    private void setAnimationStates(){
        if(this.animationTime <= 1){
            this.animationTime = 20;
            this.animationState.start(this.tickCount);
        }
        else this.animationTime--;
    }

    @Override
    public void tick() {
        super.tick();
        if(this.level().isClientSide()) this.setAnimationStates();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity target = result.getEntity();
        if(!level().isClientSide() && target instanceof LivingEntity){
            target.hurt(new DamageSource(daiFunctions.getHolder(level(), Registries.DAMAGE_TYPE, DamageTypes.MAGIC), getOwner()),directDamage);
            List<LivingEntity> list = level().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(3));
            list.remove(target);
            if(getOwner() != null && list.equals(getOwner())) list.remove(getOwner());
            for (LivingEntity livingEntity : list) {
                livingEntity.hurt(new DamageSource(daiFunctions.getHolder(level(), Registries.DAMAGE_TYPE, DamageTypes.MAGIC), getOwner()), wideDamage);
            }
            ((ServerLevel)level()).sendParticles(new DustParticleOptions(new Vector3f((float) 0xAF / 0xFF, (float) 0xEE / 0xFF, (float) 0xEE / 0xFF), 1.0F), result.getLocation().x() + level().random.nextDouble(), (result.getLocation().y() + 1), result.getLocation().z() + level().random.nextDouble(),
                    30, 3 * (level().random.nextFloat() - 0.5), 3 * (level().random.nextFloat() - 0.5), 3 * (level().random.nextFloat() - 0.5), 1.0);
        }
        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        if(!level().isClientSide()){
            List<LivingEntity> list = level().getEntitiesOfClass(LivingEntity.class, new AABB(result.getBlockPos()).inflate(3));
            if(getOwner() != null && list.equals(getOwner())) list.remove(getOwner());
            for (LivingEntity livingEntity : list) {
                livingEntity.hurt(new DamageSource(daiFunctions.getHolder(level(), Registries.DAMAGE_TYPE, DamageTypes.MAGIC), getOwner()), wideDamage);
            }
            ((ServerLevel)level()).sendParticles(new DustParticleOptions(new Vector3f((float) 0xAF / 0xFF, (float) 0xEE / 0xFF, (float) 0xEE / 0xFF), 1.0F), result.getLocation().x() + level().random.nextDouble(), (result.getLocation().y() + 1), result.getLocation().z() + level().random.nextDouble(),
                    30, 3 * (level().random.nextFloat() - 0.5), 3 * (level().random.nextFloat() - 0.5), 3 * (level().random.nextFloat() - 0.5), 1.0);
        }
        this.discard();
    }
}
