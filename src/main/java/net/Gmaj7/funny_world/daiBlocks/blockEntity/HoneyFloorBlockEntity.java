package net.Gmaj7.funny_world.daiBlocks.blockEntity;

import net.Gmaj7.funny_world.daiBlocks.daiBlockEntities;
import net.Gmaj7.funny_world.daiInit.daiHoneyEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;

public class HoneyFloorBlockEntity extends BlockEntity {
    public ItemStack honey_bottle = new ItemStack(Items.HONEY_BOTTLE);
    public HoneyFloorBlockEntity(BlockPos pos, BlockState blockState) {
        super(daiBlockEntities.HONEY_FLOOR_BE.get(), pos, blockState);
    }

    public ItemStack getHoney_bottle() {
        return honey_bottle;
    }

    public void setHoney_bottle(ItemStack honey_bottle) {
        this.honey_bottle = honey_bottle;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("honey_item", honey_bottle.save(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        honey_bottle = ItemStack.parse(registries, tag.getCompound("honey_item")).get();
    }
}
