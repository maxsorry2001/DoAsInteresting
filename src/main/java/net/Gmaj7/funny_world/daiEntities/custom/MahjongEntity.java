package net.Gmaj7.funny_world.daiEntities.custom;

import net.Gmaj7.funny_world.daiEnchantments.daiEnchantments;
import net.Gmaj7.funny_world.daiEntities.daiEntities;
import net.Gmaj7.funny_world.daiInit.daiDataComponentTypes;
import net.Gmaj7.funny_world.daiInit.daiFunctions;
import net.Gmaj7.funny_world.daiItems.daiItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class MahjongEntity extends AbstractArrow {

    private  ItemStack shootItem;
    private int pattern;
    private int point;
    public MahjongEntity(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        shootItem = new ItemStack(daiItems.MAHJONG.get());
    }

    public MahjongEntity(Level pLevel) {
        super(daiEntities.MAHJONG_ENTITY.get(), pLevel);
        shootItem = new ItemStack(daiItems.MAHJONG.get());
    }

    public MahjongEntity(Level pLevel, LivingEntity pOwner, ItemStack itemStack){
        super(daiEntities.MAHJONG_ENTITY.get(), pLevel);
        this.setOwner(pOwner);
        this.setPos(pOwner.getX(), pOwner.getEyeY() - 0.1, pOwner.getZ());
        this.shootItem = itemStack.copy();
    }
    public MahjongEntity(LivingEntity pOwner, Level pLevel) {
        super(daiEntities.MAHJONG_ENTITY.get(), pLevel);
        shootItem = new ItemStack(daiItems.MAHJONG.get());
        this.setOwner(pOwner);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        if(!shootItem.isEmpty()){
            if(shootItem.getEnchantmentLevel(daiFunctions.getHolder(level(), daiEnchantments.ALL_OF_SAME)) <= 0)
                shootItem.set(daiDataComponentTypes.MAHJONG_PATTERN, 0);
            shootItem.set(daiDataComponentTypes.MAHJONG_POINTS, 0);
            level().addFreshEntity(new ItemEntity(level(), getOwner().getX(), getOwner().getY(), getOwner().getZ(), shootItem));
        }
        Entity entity = pResult.getEntity();
        if(entity instanceof LivingEntity livingEntity){
            switch (pattern){
                case 1 -> {livingEntity.hurt(new DamageSource(level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.GENERIC), getOwner()), point * 2);}
                case 2 -> {
                    List<LivingEntity> list = level().getEntitiesOfClass(LivingEntity.class, new AABB(livingEntity.getOnPos().above()).inflate(2.5));
                    for (LivingEntity target : list){
                        if(target != getOwner()) target.hurt(new DamageSource(level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.GENERIC), getOwner()), point);
                    }
                }
                case 3 -> {
                    List<LivingEntity> list = level().getEntitiesOfClass(LivingEntity.class, new AABB(livingEntity.getOnPos().above()).inflate(7));
                    for (LivingEntity target : list){
                        if(target != getOwner() && (Math.abs(target.getX() - livingEntity.getX()) < 0.5 || Math.abs(target.getZ() - livingEntity.getZ()) < 0.5)
                            && Math.abs(target.getY() - livingEntity.getY()) < 4)
                            target.hurt(new DamageSource(level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.GENERIC), getOwner()), point);
                    }
                }
            }
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        if(!shootItem.isEmpty()){
            level().addFreshEntity(new ItemEntity(level(), getOwner().getX(), getOwner().getY(), getOwner().getZ(), shootItem));
        }
        this.discard();
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return this.shootItem;
    }


    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.put("mahjong",this.shootItem.isEmpty() ? ItemStack.EMPTY.save(this.registryAccess()) : this.shootItem.save(this.registryAccess()));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        ItemStack itemStack = ItemStack.parse(this.registryAccess(), pCompound.getCompound("mahjong")).get();
        if(itemStack.isEmpty()) this.shootItem = ItemStack.EMPTY;
        else this.shootItem = itemStack;
    }

    @Override
    public void tick() {
        super.tick();
    }

    public void setPattern(int pattern) {
        this.pattern = pattern;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.STONE_BREAK;
    }
}
