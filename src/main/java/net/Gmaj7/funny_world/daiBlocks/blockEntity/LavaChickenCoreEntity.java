package net.Gmaj7.funny_world.daiBlocks.blockEntity;

import net.Gmaj7.funny_world.daiBlocks.daiBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class LavaChickenCoreEntity extends BlockEntity {
    public LavaChickenCoreEntity(BlockPos pos, BlockState blockState) {
        super(daiBlockEntities.LAVA_CHICKEN_BE.get(), pos, blockState);
    }
}
