package net.Gmaj7.funny_world.daiEntities.custom;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
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
}
