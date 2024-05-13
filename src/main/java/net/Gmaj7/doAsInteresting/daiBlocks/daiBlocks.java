package net.Gmaj7.doAsInteresting.daiBlocks;

import net.Gmaj7.doAsInteresting.DoAsInteresting;
import net.Gmaj7.doAsInteresting.daiBlocks.custom.SculkTNT;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class daiBlocks {
    public static final DeferredRegister.Blocks DAI_BLOCKS = DeferredRegister.createBlocks(DoAsInteresting.MODID);


    public static final Supplier<Block> SCULK_TNT = DAI_BLOCKS.register("sculk_tnt",
            () -> new SculkTNT(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).sound(SoundType.SCULK_SENSOR)));
}
