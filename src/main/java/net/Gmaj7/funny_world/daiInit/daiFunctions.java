package net.Gmaj7.funny_world.daiInit;

import net.Gmaj7.funny_world.daiBlocks.custom.RedstoneMagnet;
import net.Gmaj7.funny_world.daiBlocks.daiBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.entity.PartEntity;

import java.util.ArrayList;
import java.util.List;

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

    public static <T> Holder<T> getHolder(Level level, ResourceKey<Registry<T>> registry, ResourceKey<T> resourceKey){
        return level.registryAccess().registryOrThrow(registry).getHolderOrThrow(resourceKey);
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

    public static void redstoneMagnetAttractEntity(Entity entity, BlockPos blockPos) {
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

    public static void windBlockAttractEntity(Entity entity, BlockPos blockPos) {
        for (int i = -4; i <= 4; i++){
            for (int j = -4; j<= 4; j++){
                for (int k = -4; k <= 4; k++){
                    BlockPos blockPos1 = new BlockPos(blockPos.getX() + i, blockPos.getY() + j, blockPos.getZ() + k);
                    BlockState blockState = entity.level().getBlockState(blockPos1);
                    if(blockState.is(daiBlocks.WIND_BLOCK.get()) && !(entity.level().getBlockState(blockPos1.above()).getBlock() instanceof AnvilBlock)) {
                        Vec3 vec3 = new Vec3(blockPos1.getX() - entity.getX(), blockPos1.getY() - entity.getY(), blockPos1.getZ() - entity.getZ());
                        Vec3 vec31 = vec3;
                        if (vec3.length() > 1) {
                            vec31 = vec31.normalize();
                            double d = Math.max(vec3.length() * vec3.length(), entity instanceof LivingEntity ? 1 : 10);
                            entity.addDeltaMovement(new Vec3(vec31.x() / d, vec31.y(), vec31.z() / d));
                        }
                        else entity.addDeltaMovement(new Vec3(0, entity instanceof LivingEntity ? 3 : 1, 0));
                    }
                }
            }
        }
    }

    public static BlockHitResult getHitBlock(Level level, Entity source, Vec3 start, Vec3 end){
        return level.clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, source));
    }

    public static RayHitResult getLineHitResult(Level level, Entity source, Vec3 start, Vec3 end, boolean checkForBlocks, float bbInflation) {
        BlockHitResult blockHitResult;
        if (checkForBlocks) {
            blockHitResult = getHitBlock(level, source, start, end);
            end = blockHitResult.getLocation();
        }
        AABB range = source.getBoundingBox().expandTowards(end.subtract(start));
        List<HitResult> hits = new ArrayList<>();
        List<? extends Entity> entities = level.getEntities(source, range);
        for (Entity target : entities) {
            HitResult hit = checkEntityIntersecting(target, start, end, bbInflation);
            if (hit.getType() != HitResult.Type.MISS) {
                hits.add(hit);
            }
        }
        return new RayHitResult(end, hits);
    }

    public static HitResult checkEntityIntersecting(Entity entity, Vec3 start, Vec3 end, float bbInflation) {
        Vec3 hitPos = null;
        if (entity.isMultipartEntity()) {
            for (PartEntity p : entity.getParts()) {
                var hit = p.getBoundingBox().inflate(bbInflation).clip(start, end).orElse(null);
                if (hit != null) {
                    hitPos = hit;
                    break;
                }
            }
        } else {
            hitPos = entity.getBoundingBox().inflate(bbInflation).clip(start, end).orElse(null);
        }
        if (hitPos != null)
            return new EntityHitResult(entity, hitPos);
        else
            return BlockHitResult.miss(end, Direction.UP, BlockPos.containing(end));
    }

    public static class RayHitResult{
        private Vec3 end;
        private List<HitResult> targets;

        public RayHitResult(Vec3 end, List<HitResult> hits) {
            this.end = end;
            this.targets = hits;
        }

        public List<HitResult> getTargets() {
            return targets;
        }

        public HitResult getNearest(LivingEntity livingEntity){
            HitResult nearest = null;
            double distance = 0;
            for (HitResult target : getTargets()){
                if(nearest == null) {
                    nearest = target;
                    distance = target.distanceTo(livingEntity);
                }
                else {
                    nearest =  target.distanceTo(livingEntity) < distance ? target : nearest;
                }
            }
            return nearest;
        }

        public Vec3 getEnd() {
            return end;
        }
    }
}
