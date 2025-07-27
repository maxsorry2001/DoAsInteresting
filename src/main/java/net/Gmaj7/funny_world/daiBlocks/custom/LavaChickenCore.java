package net.Gmaj7.funny_world.daiBlocks.custom;

import com.mojang.serialization.MapCodec;
import net.Gmaj7.funny_world.daiBlocks.blockEntity.LavaChickenCoreBE;
import net.Gmaj7.funny_world.daiBlocks.daiBlockEntities;
import net.Gmaj7.funny_world.daiItems.daiItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class LavaChickenCore extends BaseEntityBlock {
    public static final MapCodec<LavaChickenCore> CODEC = simpleCodec(LavaChickenCore::new);
    protected static final VoxelShape SHAPE = Block.box(5.0, 5.0, 5.0, 11.0, 11.0, 11.0);

    public LavaChickenCore(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
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

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return blockEntityType == daiBlockEntities.LAVA_CHICKEN_BE.get() ? createTickerHelper(blockEntityType, daiBlockEntities.LAVA_CHICKEN_BE.get(), LavaChickenCoreBE::tick) : null;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if(blockEntity instanceof LavaChickenCoreBE lavaChickenCoreBE){
            int i = lavaChickenCoreBE.getIngot();
            ItemEntity item = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(daiItems.LAVA_CHICKEN_INGOT.get(), i));
            level.addFreshEntity(item);
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }
}
