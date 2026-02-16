package net.Gmaj7.funny_world.daiBlocks.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BaseDrumBlock extends DrumBlock{
    public static final MapCodec<BaseDrumBlock> CODEC = simpleCodec(BaseDrumBlock::new);
    protected static final VoxelShape AABB = Block.box(2.0, 2.0, 3.0, 14.0, 14.0, 13.0);
    public BaseDrumBlock(Properties properties) {
        super(properties);
    }

    @Override
    public Holder.Reference<SoundEvent> getSound() {
        return SoundEvents.NOTE_BLOCK_BASEDRUM;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return AABB;
    }
}
