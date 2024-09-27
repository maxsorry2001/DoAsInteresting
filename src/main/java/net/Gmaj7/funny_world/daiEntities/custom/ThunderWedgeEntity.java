package net.Gmaj7.funny_world.daiEntities.custom;

import net.Gmaj7.funny_world.daiEntities.daiEntities;
import net.Gmaj7.funny_world.daiInit.daiDataComponentTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class ThunderWedgeEntity extends AbstractArrow {
    ItemStack FromItem;
    public ThunderWedgeEntity(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ThunderWedgeEntity(Level pLevel) {
        super(daiEntities.THUNDER_WEDGE_ENTITY.get(), pLevel);
    }
    public ThunderWedgeEntity(Level pLevel, LivingEntity pOwner, ItemStack pPickupItemStack) {
        super(daiEntities.THUNDER_WEDGE_ENTITY.get(), pLevel);
        this.setFromItem(pPickupItemStack);
        this.setOwner(pOwner);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.put("item", this.FromItem.save(this.registryAccess()));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setFromItem(ItemStack.parse(this.registryAccess(), pCompound.getCompound("item")).get());
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        if (!this.level().isClientSide() && this.FromItem != null){
            super.onHitBlock(pResult);
            BlockPos blockPos = pResult.getBlockPos();
            switch (pResult.getDirection()){
                case UP -> blockPos = blockPos.above();
                case DOWN -> blockPos = blockPos.below().below();
                case EAST -> blockPos = blockPos.east();
                case WEST -> blockPos = blockPos.west();
                case NORTH -> blockPos = blockPos.north();
                case SOUTH -> blockPos = blockPos.south();
            }
            this.FromItem.set(daiDataComponentTypes.BLOCKPOS, blockPos);
            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, this.level());
            lightningBolt.moveTo(this.blockPosition().getCenter());
            this.level().addFreshEntity(lightningBolt);
        }
        this.remove(RemovalReason.DISCARDED);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if(!this.level().isClientSide() && this.FromItem != null){
            BlockPos blockPos = pResult.getEntity().getOnPos();
            this.FromItem.set(daiDataComponentTypes.BLOCKPOS, blockPos.above());
            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, this.level());
            lightningBolt.teleportTo(this.blockPosition().getX(), this.blockPosition().getY(), this.blockPosition().getZ());
            this.level().addFreshEntity(lightningBolt);
            this.remove(RemovalReason.DISCARDED);
        }
    }

    public void setFromItem(ItemStack fromItem) {
        this.FromItem = fromItem;
    }

    public ItemStack getFromItem() {
        return FromItem;
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return ItemStack.EMPTY;
    }
}
