package net.Gmaj7.funny_world.daiInit;

import net.Gmaj7.funny_world.daiBlocks.custom.RedstoneMagnet;
import net.Gmaj7.funny_world.daiBlocks.daiBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class daiFunctions {
    public static ItemStack getIronShootItem(Player player){
        Inventory inventory = player.getInventory();
        ItemStack itemStack = ItemStack.EMPTY;
        for (int i = 0; i < inventory.getContainerSize(); i++){
            if(inventory.getItem(i).is(daiTags.daiItemTags.IRON_ITEM)){
                itemStack = inventory.getItem(i);
                break;
            }
        }
        return itemStack;
    }

    public static Holder<Enchantment> getEnchantmentHolder(Level level, ResourceKey<Enchantment> resourceKey){
        return level.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(resourceKey);
    }

    public static Holder<DamageType> getDamagementHolder(Level level, ResourceKey<DamageType> resourceKey){
        return level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(resourceKey);
    }

    public static boolean withIronOut(LivingEntity plivingEntity){
        boolean flag = false;
        if(plivingEntity.getType().is(daiTags.daiEntityTypeTags.IRON_ENTITY)) return true;
        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()){
            if(plivingEntity.getItemBySlot(equipmentSlot).is(daiTags.daiItemTags.IRON_ITEM)){
                flag = true;
                break;
            }
        }
        return flag;
    }

    public static void attrackEntity(Entity entity, BlockPos blockPos) {
        for (int i = -7; i <= 7; i++){
            for (int j = -7; j<= 7; j++){
                for (int k = -7; k <= 7; k++){
                    BlockPos blockPos1 = new BlockPos(blockPos.getX() + i, blockPos.getY() + j, blockPos.getZ() + k);
                    BlockState blockState = entity.level().getBlockState(blockPos1);
                    if(blockState.is(daiBlocks.REDSTONE_MAGNET.get()) && blockState.getValue(RedstoneMagnet.POWERED)) {
                        Vec3 vec3 = new Vec3(blockPos1.getX() - entity.getX(), blockPos1.getY() - entity.getY(), blockPos1.getZ() - entity.getZ());
                        Vec3 vec31 = vec3;
                        if (vec3.length() > 1) {
                            vec31 = vec31.normalize();
                            double d = Math.max(vec3.length() * vec3.length(), 1);
                            entity.addDeltaMovement(new Vec3(vec31.x() / d, vec31.y(), vec31.z() / d));
                        }
                    }
                }
            }
        }
    }
}
