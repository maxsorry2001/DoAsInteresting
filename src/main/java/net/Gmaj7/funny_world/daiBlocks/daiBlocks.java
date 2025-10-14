package net.Gmaj7.funny_world.daiBlocks;

import net.Gmaj7.funny_world.FunnyWorld;
import net.Gmaj7.funny_world.daiBlocks.custom.*;
import net.Gmaj7.funny_world.daiFluids.daiFluids;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class daiBlocks {
    public static final DeferredRegister.Blocks DAI_BLOCKS = DeferredRegister.createBlocks(FunnyWorld.MODID);


    public static final DeferredBlock<Block> SCULK_TNT = DAI_BLOCKS.register("sculk_tnt",
            () -> new SculkTNT(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).sound(SoundType.SCULK_SENSOR)));
    public static final DeferredBlock<Block> ELECTROMAGNETIC_TNT = DAI_BLOCKS.register("electromagnetic_tnt",
            () -> new ElectromagneticTNT(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).sound(SoundType.LODESTONE)));
    public static final DeferredBlock<Block> REDSTONE_MAGNET = DAI_BLOCKS.register("redstone_magnet",
            () -> new RedstoneMagnet(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).strength(5.0F, 6.0F).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> HONEY_FLOOR = DAI_BLOCKS.register("honey_floor",
            () -> new HoneyFloorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).sound(SoundType.HONEY_BLOCK).noLootTable()));
    public static final DeferredBlock<Block> WIND_BLOCK = DAI_BLOCKS.register("wind_block",
            () -> new WindBlock(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE).sound(SoundType.SNOW).noCollission().isViewBlocking((blockState, blockGetter, blockPos) -> false)));
    public static final DeferredBlock<Block> WIND_TNT = DAI_BLOCKS.register("wind_tnt",
            () -> new WindTNT(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE).sound(SoundType.SNOW).noCollission().isViewBlocking((blockState, blockGetter, blockPos) -> false)));
    public static final DeferredBlock<LiquidBlock> EXTRACTANT_FLUID_BLOCK = DAI_BLOCKS.register("extractant_fluid",
            () -> new LiquidBlock(daiFluids.EXTRACTANT_STILL.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredBlock<Block> LAVA_CHICKEN_CORE = DAI_BLOCKS.register("lava_chicken_core",
            () -> new LavaChickenCore(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).sound(SoundType.METAL)));
    public static final DeferredBlock<Block> GLOW_BLOCK = DAI_BLOCKS.register("glow_block",
            () -> new AirBlock(BlockBehaviour.Properties.of().lightLevel(p -> 15).noOcclusion().noLootTable().replaceable().air()));
    public static final DeferredBlock<Block> GLOW_TNT = DAI_BLOCKS.register("glow_tnt",
            () -> new GlowTNT(BlockBehaviour.Properties.of().lightLevel(p -> 15)));
}
