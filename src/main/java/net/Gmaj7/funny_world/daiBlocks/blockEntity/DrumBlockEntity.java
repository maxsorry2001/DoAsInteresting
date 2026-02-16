package net.Gmaj7.funny_world.daiBlocks.blockEntity;

import net.Gmaj7.funny_world.daiBlocks.custom.DrumBlock;
import net.Gmaj7.funny_world.daiBlocks.daiBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.*;

public class DrumBlockEntity extends BlockEntity {
    protected BlockPos corePos;
    public boolean isCore;
    protected int note = 1;
    private final Map<BlockPos, Set<BlockPos>> adjacency = new HashMap<>();
    public final Map<BlockPos, Integer> memberNotes = new HashMap<>();
    public DrumBlockEntity(BlockPos pos, BlockState blockState) {
        super(daiBlockEntities.DRUM_BE.get(), pos, blockState);
    }

    public void initAsCore(){
        if (level.isClientSide())return;
       this.corePos = getBlockPos();
       this.isCore = true;
       memberNotes.clear();
       adjacency.clear();
       addMember(getBlockPos());
    }

    public void addMember(BlockPos memberPos){
        if(level == null || !isCore) return;
        if(!(level.getBlockEntity(memberPos) instanceof DrumBlockEntity drumBlock)) return;
        if (!memberPos.equals(getBlockPos())){
            drumBlock.setCorePos(getBlockPos());
            drumBlock.isCore = false;
            drumBlock.setChanged();
        }
        memberNotes.put(memberPos, drumBlock.getNote());
        Set<BlockPos> neighbors = new HashSet<>();
        for (Direction dir : Direction.values()) {
            BlockPos neighbor = memberPos.relative(dir);
            if (adjacency.containsKey(neighbor)) {
               neighbors.add(neighbor);
               adjacency.get(neighbor).add(memberPos);
            }
        }
        adjacency.put(memberPos, neighbors);
        if (memberPos.equals(getBlockPos())) {
            memberNotes.put(getBlockPos(), this.note);
        }
        setChanged();
    }

    public void removeMember(BlockPos memberPos){
        if (!isCore) return;
        if (!adjacency.containsKey(memberPos)) return;

        memberNotes.remove(memberPos);
        Set<BlockPos> neighbors = adjacency.remove(memberPos);
        if (neighbors != null) {
            for (BlockPos nb : neighbors) {
                Set<BlockPos> nbSet = adjacency.get(nb);
                if (nbSet != null) nbSet.remove(memberPos);
            }
        }
        setChanged();
    }

    public void mergeGroup(BlockPos otherCorePos) {
        if (level == null || !isCore) return;
        if (!(level.getBlockEntity(otherCorePos) instanceof DrumBlockEntity otherCore)) return;
        if (!otherCore.isCore) return; // 确保对方是核心

        // 获取对方的所有成员
        Set<BlockPos> otherMembers = otherCore.memberNotes.keySet();
        for (BlockPos pos : otherMembers) {
            if (pos.equals(otherCorePos)) continue; // 对方核心本身稍后处理

            // 更新对方成员的 corePos 为本核心
            if (level.getBlockEntity(pos) instanceof DrumBlockEntity member) {
                member.corePos = worldPosition;
                member.isCore = false;
                member.setChanged();
            }
            // 将成员音高加入本核心
            memberNotes.put(pos, otherCore.memberNotes.get(pos));
            // 邻接关系需要在全部添加后重建，这里暂时不处理
        }

        // 处理对方核心本身：将其降级为成员
        otherCore.corePos = worldPosition;
        otherCore.isCore = false;
        otherCore.memberNotes.clear();
        otherCore.adjacency.clear();
        otherCore.setChanged();

        // 将对方核心的音高加入本核心
        memberNotes.put(otherCorePos, otherCore.getNote());

        // 现在需要重建所有邻接关系（因为成员位置变了）
        rebuildAdjacency();

        setChanged();
    }

    private void rebuildAdjacency() {
        adjacency.clear();
        Set<BlockPos> members = memberNotes.keySet();
        for (BlockPos pos : members) {
            Set<BlockPos> neighbors = new HashSet<>();
            for (Direction dir : Direction.values()) {
                BlockPos neighbor = pos.relative(dir);
                if (members.contains(neighbor)) neighbors.add(neighbor);
            }
            adjacency.put(pos, neighbors);
        }
    }

    public void transferDataTo(DrumBlockEntity newCore) {
        if (!isCore) return;
        if (newCore == this) return;

        // 新核心初始化
        newCore.corePos = newCore.worldPosition;
        newCore.isCore = true;
        newCore.memberNotes.clear();
        newCore.adjacency.clear();

        // 复制所有成员音高
        newCore.memberNotes.putAll(this.memberNotes);
        // 更新所有成员的 corePos 指向新核心
        for (BlockPos pos : memberNotes.keySet()) {
            if (level.getBlockEntity(pos) instanceof DrumBlockEntity member) {
                member.corePos = newCore.worldPosition;
                member.isCore = false;
                member.setChanged();
            }
        }
        // 新核心自身的音高要从 memberNotes 中取
        newCore.note = memberNotes.get(newCore.worldPosition);

        // 重建邻接
        newCore.rebuildAdjacency();

        // 清除原核心的数据（它即将被销毁）
        this.memberNotes.clear();
        this.adjacency.clear();
        this.isCore = false;
        this.corePos = null;
        setChanged();
        newCore.setChanged();

        newCore.checkSplit();
    }

    public void updateMemberNote(BlockPos memberPos, int newNote) {
        if (!isCore) return;
        if (memberNotes.containsKey(memberPos)) {
            memberNotes.put(memberPos, newNote);
            if (memberPos.equals(worldPosition)) {
                this.note = newNote;
            }
            setChanged();
        }
    }

    private List<Set<BlockPos>> findConnectedComponents() {
        List<Set<BlockPos>> components = new ArrayList<>();
        Set<BlockPos> visited = new HashSet<>();
        Set<BlockPos> allMembers = memberNotes.keySet();

        for (BlockPos start : allMembers) {
            if (!visited.contains(start)) {
                Set<BlockPos> comp = new HashSet<>();
                Queue<BlockPos> queue = new LinkedList<>();
                queue.add(start);
                visited.add(start);
                while (!queue.isEmpty()) {
                    BlockPos cur = queue.poll();
                    comp.add(cur);
                    for (BlockPos neighbor : adjacency.getOrDefault(cur, Collections.emptySet())) {
                        if (!visited.contains(neighbor) && allMembers.contains(neighbor)) {
                            visited.add(neighbor);
                            queue.add(neighbor);
                        }
                    }
                }
                components.add(comp);
            }
        }
        return components;
    }
    public void checkSplit() {
        if (!isCore) return;
        if (level == null || level.isClientSide) return;

        List<Set<BlockPos>> components = findConnectedComponents();
        if (components.size() <= 1) return; // 未分裂

        // 找到包含当前核心自身的分量作为主分量
        Set<BlockPos> mainComponent = null;
        for (Set<BlockPos> comp : components) {
            if (comp.contains(worldPosition)) {
                mainComponent = comp;
                break;
            }
        }
        if (mainComponent == null) {
            // 不应该发生，核心自身不在任何分量中，说明数据异常，直接返回
            return;
        }

        // 处理其他分量，为每个分量创建新鼓组
        for (Set<BlockPos> comp : components) {
            if (comp == mainComponent) continue;

            // 选举新核心（选择分量中位置最小的方块）
            BlockPos newCorePos = comp.stream().min(BlockPos::compareTo).orElse(null);
            if (newCorePos == null) continue;
            if (!(level.getBlockEntity(newCorePos) instanceof DrumBlockEntity newCore)) continue;

            // 升级为新核心
            newCore.corePos = newCorePos;
            newCore.isCore = true;
            newCore.memberNotes.clear();
            newCore.adjacency.clear();

            // 将该分量的成员数据复制到新核心
            for (BlockPos p : comp) {
                newCore.memberNotes.put(p, memberNotes.get(p));
            }
            // 更新这些成员的 corePos 指向新核心
            for (BlockPos p : comp) {
                if (!p.equals(newCorePos) && level.getBlockEntity(p) instanceof DrumBlockEntity member) {
                    member.corePos = newCorePos;
                    member.isCore = false;
                    member.setChanged();
                }
            }
            newCore.note = memberNotes.get(newCorePos);
            newCore.rebuildAdjacency();
            newCore.setChanged();

            // 从当前核心中移除该分量的数据
            for (BlockPos p : comp) {
                memberNotes.remove(p);
                adjacency.remove(p);
            }
        }

        // 重建当前核心的邻接表（因为移除了部分成员）
        rebuildAdjacency();
        setChanged();
    }

    @Nullable
    public BlockPos getCorePos() {
        return corePos;
    }

    public void setCorePos(BlockPos corePos) {
        this.corePos = corePos;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if(!level.isClientSide()){
            if(corePos != null)
                tag.putLong("core_pos", corePos.asLong());
            tag.putBoolean("is_core", isCore);
            tag.putInt("note", note);
            ListTag memberTagList = new ListTag();
            for (BlockPos blockPos : adjacency.keySet()) {
                CompoundTag memberTag = new CompoundTag();
                memberTag.putLong("pos", blockPos.asLong());
                memberTag.putInt("note", memberNotes.get(blockPos));
                memberTagList.add(memberTag);
            }
            tag.put("members", memberTagList);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if(level != null && !level.isClientSide()) {
            this.corePos = BlockPos.of(tag.getLong("core_pos"));
            this.isCore = tag.getBoolean("is_boolean");
            this.note = tag.getInt("note");
            ListTag memberLiseTag = tag.getList("members", Tag.TAG_COMPOUND);
            Set<BlockPos> memberPoses = new HashSet<>();
            for (int i = 0; i < memberLiseTag.size(); i++) {
                CompoundTag memberTag = memberLiseTag.getCompound(i);
                memberPoses.add(BlockPos.of(memberTag.getLong("pos")));
                memberNotes.put(BlockPos.of(memberTag.getLong("pos")), memberTag.getInt("note"));
            }
            for (BlockPos blockPos : memberPoses) {
                Set<BlockPos> neighbors = new HashSet<>();
                for (Direction direction : Direction.values()) {
                    BlockPos neighbor = blockPos.relative(direction);
                    if (memberPoses.contains(neighbor))
                        neighbors.add(neighbor);
                }
                adjacency.put(blockPos, neighbors);
            }
        }
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
        if (level != null && corePos != null && level.getBlockEntity(corePos) instanceof DrumBlockEntity core) {
            core.updateMemberNote(this.getBlockPos(), note);
        }
    }

    private Holder.Reference<SoundEvent> getSound(BlockPos blockPos){
        BlockState blockState = getLevel().getBlockState(blockPos);
        if(blockState.getBlock() instanceof DrumBlock drumBlock) return drumBlock.getSound();
        else return null;
    }

    @Override
    public void setChanged() {
        super.setChanged();
    }
}
