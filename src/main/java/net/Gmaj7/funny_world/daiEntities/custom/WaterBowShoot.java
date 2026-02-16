package net.Gmaj7.funny_world.daiEntities.custom;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public abstract class WaterBowShoot extends AbstractArrow {
    public ItemStack waterBow;

    protected WaterBowShoot(EntityType<? extends WaterBowShoot> entityType, Level level) {
        super(entityType, level);
    }

    public void setWaterBow(ItemStack waterBow) {
        this.waterBow = waterBow;
    }

    public ItemStack getWaterBow() {
        return waterBow;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.GENERIC_SPLASH;
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(Items.ARROW);
    }
}
