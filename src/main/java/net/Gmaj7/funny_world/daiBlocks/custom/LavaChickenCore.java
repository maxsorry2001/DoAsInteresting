package net.Gmaj7.funny_world.daiBlocks.custom;

import com.mojang.serialization.MapCodec;
import net.Gmaj7.funny_world.daiBlocks.blockEntity.LavaChickenCoreBE;
import net.Gmaj7.funny_world.daiBlocks.daiBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class LavaChickenCore extends BaseEntityBlock {
    public static final MapCodec<LavaChickenCore> CODEC = simpleCodec(LavaChickenCore::new);

    public LavaChickenCore(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new LavaChickenCoreBE(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return blockEntityType == daiBlockEntities.LAVA_CHICKEN_BE.get() ? createTickerHelper(blockEntityType, daiBlockEntities.LAVA_CHICKEN_BE.get(), LavaChickenCoreBE::tick) : null;
    }
}
