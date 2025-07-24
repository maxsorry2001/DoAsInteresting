package net.Gmaj7.funny_world.daiBlocks.custom;

import com.mojang.serialization.MapCodec;
import net.Gmaj7.funny_world.daiBlocks.blockEntity.LavaChickenCoreBE;
import net.Gmaj7.funny_world.daiBlocks.daiBlockEntities;
import net.Gmaj7.funny_world.daiItems.daiItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
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

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        ItemInteractionResult flag = ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        if(blockEntity instanceof LavaChickenCoreBE lavaChickenCoreBE){
            boolean flag2;
            if(stack.is(daiItems.LAVA_CHICKEN_INGOT.get())) {
                flag2 = lavaChickenCoreBE.ingotAdd();
                if(flag2) stack.shrink(1);
            }
            else {
                flag2 = lavaChickenCoreBE.ingotReduce();
                if(flag2) player.addItem(new ItemStack(daiItems.LAVA_CHICKEN_INGOT.get()));
            }
            if(flag2) flag = ItemInteractionResult.SUCCESS;
        }
        return flag;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return blockEntityType == daiBlockEntities.LAVA_CHICKEN_BE.get() ? createTickerHelper(blockEntityType, daiBlockEntities.LAVA_CHICKEN_BE.get(), LavaChickenCoreBE::tick) : null;
    }
}
