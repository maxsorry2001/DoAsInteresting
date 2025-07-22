package net.Gmaj7.funny_world.daiBlocks.blockEntity;

import net.Gmaj7.funny_world.daiBlocks.daiBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class LavaChickenCoreBE extends BlockEntity {
    private int time = 1;
    public LavaChickenCoreBE(BlockPos pos, BlockState blockState) {
        super(daiBlockEntities.LAVA_CHICKEN_BE.get(), pos, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, LavaChickenCoreBE lavaChickenCoreBE){
        lavaChickenCoreBE.time ++;
        if(lavaChickenCoreBE.time == 20){
            lavaChickenCoreBE.time = 0;
            List<Chicken> list = level.getEntitiesOfClass(Chicken.class, new AABB(pos).inflate(5, 1, 5));
            for (Chicken chicken : list){
                if(chicken.isBaby()) continue;
                ItemStack itemStack = new ItemStack(Items.COOKED_CHICKEN);
                ItemEntity itemEntity = new ItemEntity(level, chicken.getX(), chicken.getY(), chicken.getZ(), itemStack);
                level.addFreshEntity(itemEntity);
                chicken.remove(Entity.RemovalReason.DISCARDED);
            }
        }
    }
}
