package net.Gmaj7.funny_world.daiInit.daiUniqueData;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public interface daiUniqueDataGet {
    WindChargeSet getWindChargeSet();

    boolean isBlockUsePass(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult);
}
