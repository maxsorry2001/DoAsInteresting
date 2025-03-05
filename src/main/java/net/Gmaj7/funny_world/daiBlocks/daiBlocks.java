package net.Gmaj7.funny_world.daiBlocks;

import net.Gmaj7.funny_world.FunnyWorld;
import net.Gmaj7.funny_world.daiBlocks.custom.ElectromagneticTNT;
import net.Gmaj7.funny_world.daiBlocks.custom.HoneyFloorBlock;
import net.Gmaj7.funny_world.daiBlocks.custom.RedstoneMagnet;
import net.Gmaj7.funny_world.daiBlocks.custom.SculkTNT;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class daiBlocks {
    public static final DeferredRegister.Blocks DAI_BLOCKS = DeferredRegister.createBlocks(FunnyWorld.MODID);


    public static final Supplier<Block> SCULK_TNT = DAI_BLOCKS.register("sculk_tnt",
            () -> new SculkTNT(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).sound(SoundType.SCULK_SENSOR)));

    public static final Supplier<Block> ELECTROMAGNETIC_TNT = DAI_BLOCKS.register("electromagnetic_tnt",
            () -> new ElectromagneticTNT(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).sound(SoundType.LODESTONE)));

    public static final Supplier<Block> REDSTONE_MAGNET = DAI_BLOCKS.register("redstone_magnet",
            () -> new RedstoneMagnet(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).strength(5.0F, 6.0F).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops()));

    public static final Supplier<Block> HONEY_FLOOR = DAI_BLOCKS.register("honey_floor",
            () -> new HoneyFloorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).sound(SoundType.HONEY_BLOCK)));
}
