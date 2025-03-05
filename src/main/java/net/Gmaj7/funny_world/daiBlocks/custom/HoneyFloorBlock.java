package net.Gmaj7.funny_world.daiBlocks.custom;

import com.mojang.serialization.MapCodec;
import net.Gmaj7.funny_world.daiInit.daiHoneyEffects;
import net.Gmaj7.funny_world.daiInit.daiUniqueData.daiUniqueDataGet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class HoneyFloorBlock extends Block {
    public static final MapCodec<HoneyFloorBlock> CODEC = simpleCodec(HoneyFloorBlock::new);
    protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
    public HoneyFloorBlock(Properties properties) {
        super(properties);
    }

    public MapCodec<? extends HoneyFloorBlock> codec() {
        return CODEC;
    }

    protected VoxelShape getShape(BlockState p_152917_, BlockGetter p_152918_, BlockPos p_152919_, CollisionContext p_152920_) {
        return SHAPE;
    }

    protected BlockState updateShape(BlockState p_152926_, Direction p_152927_, BlockState p_152928_, LevelAccessor p_152929_, BlockPos p_152930_, BlockPos p_152931_) {
        return !p_152926_.canSurvive(p_152929_, p_152930_) ? Blocks.AIR.defaultBlockState() : super.updateShape(p_152926_, p_152927_, p_152928_, p_152929_, p_152930_, p_152931_);
    }

    protected boolean canSurvive(BlockState p_152922_, LevelReader p_152923_, BlockPos p_152924_) {
        return !p_152923_.isEmptyBlock(p_152924_.below());
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        double d0 = Math.abs(entity.getDeltaMovement().y);
        if (d0 < 0.1 && !entity.isSteppingCarefully()) {
            double d1 = 0.4 + d0 * 0.2;
            entity.setDeltaMovement(entity.getDeltaMovement().multiply(d1, 1.0, d1));
        }
        List<daiHoneyEffects.Entry> list = ((daiUniqueDataGet)state).getHoneyAbsorbEffect().getEffect();
        if(list != null && !list.isEmpty() && entity instanceof LivingEntity livingEntity){
            for (daiHoneyEffects.Entry entry : list){
                livingEntity.addEffect(new MobEffectInstance(entry.effect(), entry.duration(), entry.effectLevel()));
            }
        }
        super.entityInside(state, level, pos, entity);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(stack.is(Items.GLASS_BOTTLE)){
            if(!player.isCreative())
                stack.shrink(1);
            ItemStack itemStack = new ItemStack(Items.HONEY_BOTTLE);
            if (!player.getInventory().add(itemStack)) {
                player.drop(itemStack, false);
            }
            level.destroyBlock(pos, false);
            player.swing(hand);
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }
}
