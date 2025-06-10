package net.Gmaj7.funny_world.datagen;

import net.Gmaj7.funny_world.daiBlocks.daiBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class daiBlockLootTableProvider extends BlockLootSubProvider {
    protected daiBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(daiBlocks.WIND_BLOCK.get());
        dropSelf(daiBlocks.REDSTONE_MAGNET.get());
        dropSelf(daiBlocks.ELECTROMAGNETIC_TNT.get());
        dropSelf(daiBlocks.SCULK_TNT.get());
        dropSelf(daiBlocks.WIND_BLOCK.get());
        dropSelf(daiBlocks.WIND_TNT.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return daiBlocks.DAI_BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
