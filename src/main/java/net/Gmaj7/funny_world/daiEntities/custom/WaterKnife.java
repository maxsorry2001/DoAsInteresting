package net.Gmaj7.funny_world.daiEntities.custom;

import net.Gmaj7.funny_world.daiEntities.daiEntities;
import net.Gmaj7.funny_world.daiInit.daiFunctions;
import net.Gmaj7.funny_world.daiItems.daiItems;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.joml.Vector3f;

public class WaterKnife extends WaterBowShoot{
    public static int maxTime = 20;
    public WaterKnife(EntityType<? extends WaterBowShoot> entityType, Level level) {
        super(entityType, level);
        this.pickup = Pickup.DISALLOWED;
    }

    public WaterKnife(Level pLevel) {
        super(daiEntities.WATER_KNIFE.get(), pLevel);
        waterBow = new ItemStack(daiItems.WATER_BOW.get());
    }

    public WaterKnife(Level pLevel, LivingEntity pOwner, ItemStack itemStack){
        super(daiEntities.WATER_KNIFE.get(), pLevel);
        this.setOwner(pOwner);
        this.setPos(pOwner.getX(), pOwner.getEyeY() - 0.1, pOwner.getZ());
        this.waterBow = itemStack.copy();
        this.pickup = Pickup.DISALLOWED;
    }
    public WaterKnife(LivingEntity pOwner, Level pLevel) {
        super(daiEntities.WATER_KNIFE.get(), pLevel);
        this.waterBow = new ItemStack(daiItems.WATER_BOW.get());
        this.setOwner(pOwner);
        this.pickup = Pickup.DISALLOWED;
    }

    @Override
    public void tick() {
        if(this.tickCount >= maxTime) this.discard();
        super.tick();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        this.playSound(this.getHitGroundSoundEvent(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        Entity entity = result.getEntity();
        if(entity instanceof LivingEntity && !level().isClientSide()) {
            entity.hurt(new DamageSource(daiFunctions.getHolder(level(), Registries.DAMAGE_TYPE, DamageTypes.MAGIC), getOwner()), 10);
            ((ServerLevel)level()).sendParticles(new DustParticleOptions(new Vector3f((float) 0xAF / 0xFF, (float) 0xEE / 0xFF, (float) 0xEE / 0xFF), 1.0F), result.getLocation().x() + level().random.nextDouble(), (result.getLocation().y() + 1), result.getLocation().z() + level().random.nextDouble(),
                    15, level().random.nextFloat() - 0.5, level().random.nextFloat() - 0.5, level().random.nextFloat() - 0.5, 1.0);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        this.playSound(this.getHitGroundSoundEvent(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        if(!level().isClientSide())
            ((ServerLevel)level()).sendParticles(new DustParticleOptions(new Vector3f((float) 0xAF / 0xFF, (float) 0xEE / 0xFF, (float) 0xEE / 0xFF), 1.0F), result.getLocation().x() + level().random.nextDouble(), (result.getLocation().y() + 1), result.getLocation().z() + level().random.nextDouble(),
                    15, level().random.nextFloat() - 0.5, level().random.nextFloat() - 0.5, level().random.nextFloat() - 0.5, 1.0);
        this.remove(RemovalReason.DISCARDED);
    }
}
