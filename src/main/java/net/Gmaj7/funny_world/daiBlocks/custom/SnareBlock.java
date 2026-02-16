package net.Gmaj7.funny_world.daiBlocks.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.NoteBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class SnareBlock extends DrumBlock{
    public static final MapCodec<SnareBlock> CODEC = simpleCodec(SnareBlock::new);
    protected SnareBlock(Properties properties) {
        super(properties);
    }

    @Override
    public Holder.Reference<SoundEvent> getSound() {
        return SoundEvents.NOTE_BLOCK_SNARE;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        level.playSeededSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.NOTE_BLOCK_SNARE, SoundSource.RECORDS, 3, NoteBlock.getPitchFromNote(12), level.random.nextLong());
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }
}
