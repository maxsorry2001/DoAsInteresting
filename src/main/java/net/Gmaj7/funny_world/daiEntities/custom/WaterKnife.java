package net.Gmaj7.funny_world.daiEntities.custom;

import net.Gmaj7.funny_world.daiEntities.daiEntities;
import net.Gmaj7.funny_world.daiItems.daiItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class WaterKnife extends WaterBowShoot{
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
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(Items.ARROW);
    }
}
