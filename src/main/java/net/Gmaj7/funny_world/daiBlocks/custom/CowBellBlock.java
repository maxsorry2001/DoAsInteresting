package net.Gmaj7.funny_world.daiBlocks.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CowBellBlock extends DrumBlock{
    public static final MapCodec<CowBellBlock> CODEC = simpleCodec(CowBellBlock::new);
    protected static final VoxelShape AABB = Block.box(6.0, 0.0, 4.0, 9.0, 1.0, 11.0);
    public CowBellBlock(Properties properties) {
        super(properties);
    }

    @Override
    public Holder.Reference<SoundEvent> getSound() {
        return SoundEvents.NOTE_BLOCK_COW_BELL;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return AABB;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}
