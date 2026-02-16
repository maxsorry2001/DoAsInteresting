package net.Gmaj7.funny_world.daiBlocks.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class BaseDrumBlock extends DrumBlock{
    public static final MapCodec<BaseDrumBlock> CODEC = simpleCodec(BaseDrumBlock::new);
    public BaseDrumBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        int i = 1;
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public Holder.Reference<SoundEvent> getSound() {
        return SoundEvents.NOTE_BLOCK_BASEDRUM;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
