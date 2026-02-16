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
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.NoteBlock;
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
    public final Map<Integer, BlockPos> keyMappings = new HashMap<>();
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
        if(keyMappings.containsValue(memberPos)){
            keyMappings.values().removeIf(v -> v.equals(memberPos));
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

        for (var entry : otherCore.keyMappings.entrySet()) {
            keyMappings.putIfAbsent(entry.getKey(), entry.getValue());
        }

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
        newCore.keyMappings.putAll(this.keyMappings);

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
        if (!isCore || level == null || level.isClientSide) return;

        List<Set<BlockPos>> components = findConnectedComponents();
        if (components.size() <= 1) return;

        // 找到包含当前核心的分量
        Set<BlockPos> mainComponent = components.stream()
                .filter(c -> c.contains(worldPosition))
                .findFirst().orElse(null);
        if (mainComponent == null) return;

        for (Set<BlockPos> comp : components) {
            if (comp == mainComponent) continue;
            createNewCoreFromComponent(comp);
        }

        rebuildAdjacency();
        setChanged();
    }

    private void createNewCoreFromComponent(Set<BlockPos> component) {
        // 选举新核心（位置最小者）
        BlockPos newCorePos = component.stream().min(BlockPos::compareTo).orElse(null);
        if (newCorePos == null || !(level.getBlockEntity(newCorePos) instanceof DrumBlockEntity newCore)) return;

        // 初始化新核心
        newCore.corePos = newCorePos;
        newCore.isCore = true;
        newCore.memberNotes.clear();
        newCore.adjacency.clear();

        // 复制成员音高
        for (BlockPos p : component) {
            newCore.memberNotes.put(p, memberNotes.get(p));
        }

        // 更新成员核心引用（除自身外）
        for (BlockPos p : component) {
            if (!p.equals(newCorePos) && level.getBlockEntity(p) instanceof DrumBlockEntity member) {
                member.corePos = newCorePos;
                member.isCore = false;
                member.setChanged();
            }
        }

        newCore.note = memberNotes.get(newCorePos);
        newCore.rebuildAdjacency();

        // 转移按键映射
        Iterator<Map.Entry<Integer, BlockPos>> it = keyMappings.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, BlockPos> entry = it.next();
            if (component.contains(entry.getValue())) {
                newCore.keyMappings.put(entry.getKey(), entry.getValue());
                it.remove();
            }
        }

        newCore.setChanged();

        // 从当前核心移除该分量数据
        for (BlockPos p : component) {
            memberNotes.remove(p);
            adjacency.remove(p);
        }
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
            ListTag keyMappingList = new ListTag();
            for (int i : keyMappings.keySet()) {
                CompoundTag keyMapping = new CompoundTag();
                keyMapping.putInt("key", i);
                keyMapping.putLong("block_pos", keyMappings.get(i).asLong());
                keyMappingList.add(keyMapping);
            }
            tag.put("key_mappings", keyMappingList);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        memberNotes.clear();
        keyMappings.clear();
        adjacency.clear();
        this.corePos = BlockPos.of(tag.getLong("core_pos"));
        this.isCore = tag.getBoolean("is_core");
        this.note = tag.getInt("note");
        ListTag memberLiseTag = tag.getList("members", Tag.TAG_COMPOUND);
        ListTag keyMappingListTag = tag.getList("key_mappings", Tag.TAG_COMPOUND);
        Set<BlockPos> memberPoses = new HashSet<>();
        for (int i = 0; i < memberLiseTag.size(); i++) {
            CompoundTag memberTag = memberLiseTag.getCompound(i);
            memberPoses.add(BlockPos.of(memberTag.getLong("pos")));
            memberNotes.put(BlockPos.of(memberTag.getLong("pos")), memberTag.getInt("note"));
        }
        for (int i = 0; i < keyMappingListTag.size(); i++){
            CompoundTag keyMappingTag = keyMappingListTag.getCompound(i);
            keyMappings.put(keyMappingTag.getInt("key"), BlockPos.of(keyMappingTag.getLong("block_pos")));
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
        BlockState blockState = level.getBlockState(blockPos);
        if(blockState.getBlock() instanceof DrumBlock drumBlock) return drumBlock.getSound();
        else return null;
    }

    public void playSound(int key){
        if(level.isClientSide()) return;
        if(this.isCore) {
            if(!keyMappings.containsKey(key)) return;
            BlockPos blockPos = keyMappings.get(key);
            level.playSeededSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), getSound(blockPos), SoundSource.BLOCKS, 3, NoteBlock.getPitchFromNote(memberNotes.get(blockPos)), level.random.nextLong());
        }
        else ((DrumBlockEntity)level.getBlockEntity(getCorePos())).playSound(key);
    }

    public void putKeyMapping(int key, BlockPos blockPos){
        if(level.isClientSide()) return;
        if(this.isCore) {
            keyMappings.values().removeIf(v -> v.equals(blockPos));
            keyMappings.put(key, blockPos);
        }
        else {
            ((DrumBlockEntity)level.getBlockEntity(getCorePos())).putKeyMapping(key, blockPos);
        }
    }
}
