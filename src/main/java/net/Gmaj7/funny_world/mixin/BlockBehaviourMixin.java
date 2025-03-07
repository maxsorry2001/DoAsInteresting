package net.Gmaj7.funny_world.mixin;


import net.Gmaj7.funny_world.daiInit.daiUniqueData.daiUniqueDataGet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureElement;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockBehaviour.class)
public abstract class BlockBehaviourMixin implements FeatureElement, daiUniqueDataGet {
    @Shadow protected abstract InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult);

    @Override
    public boolean isBlockUsePass(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        return useWithoutItem(state, level, pos, player, hitResult).consumesAction();
    }
}

