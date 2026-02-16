package net.Gmaj7.funny_world.daiBlocks.custom;

import net.Gmaj7.funny_world.daiBlocks.blockEntity.DrumBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public abstract class DrumBlock extends BaseEntityBlock {
    protected DrumBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new DrumBlockEntity(blockPos, blockState);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        if (level.isClientSide) return;

        // 获取新方块的实体
        if (!(level.getBlockEntity(pos) instanceof DrumBlockEntity newEntity)) return;

        // 收集相邻鼓组的核心位置
        Set<BlockPos> neighborCores = new HashSet<>();
        for (Direction dir : Direction.values()) {
            BlockPos neighbor = pos.relative(dir);
            if (level.getBlockEntity(neighbor) instanceof DrumBlockEntity neighborEntity) {
                BlockPos core = neighborEntity.isCore ? neighbor : neighborEntity.getCorePos();
                if (core != null) neighborCores.add(core);
            }
        }
        if (neighborCores.isEmpty()) {
            // 无相邻鼓组，自己成为核心
            newEntity.initAsCore();
        } else if (neighborCores.size() == 1) {
            // 只有一个相邻鼓组，加入它
            BlockPos corePos = neighborCores.iterator().next();
            if (level.getBlockEntity(corePos) instanceof DrumBlockEntity core) {
                core.addMember(pos);
            }
        } else {
            // 有多个相邻鼓组，需要合并
            // 选择第一个作为主核心
            Iterator<BlockPos> it = neighborCores.iterator();
            BlockPos mainCorePos = it.next();
            if (!(level.getBlockEntity(mainCorePos) instanceof DrumBlockEntity mainCore)) return;

            // 将其余核心合并到主核心
            while (it.hasNext()) {
                BlockPos otherCorePos = it.next();
                mainCore.mergeGroup(otherCorePos);
            }
            // 将新方块加入主核心
            mainCore.addMember(pos);
        }
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof DrumBlockEntity entity) {
            if (entity.isCore) {
                // 核心被破坏：选举新核心，并让新核心检查分裂
                Set<BlockPos> members = entity.memberNotes.keySet(); // 所有成员位置
                members.remove(pos); // 移除自身
                if (!members.isEmpty()) {
                    // 选举第一个成员作为新核心（例如按位置排序）
                    BlockPos newCorePos = members.stream().min(BlockPos::compareTo).get();
                    if (level.getBlockEntity(newCorePos) instanceof DrumBlockEntity newCore) {
                        entity.transferDataTo(newCore);
                        // transferDataTo 内部应已调用 newCore.checkSplit()
                    }
                }
                // 如果成员为空，鼓组消失，无需额外操作
            } else {
                // 成员被破坏：从核心中移除，并让核心检查分裂
                BlockPos corePos = entity.getCorePos();
                if (corePos != null && level.getBlockEntity(corePos) instanceof DrumBlockEntity core) {
                    core.removeMember(pos);
                    core.checkSplit(); // 检查是否分裂
                }
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    public abstract Holder.Reference<SoundEvent> getSound();

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}
