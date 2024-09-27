package net.Gmaj7.funny_world.daiBlocks;

import net.Gmaj7.funny_world.FunnyWorld;
import net.Gmaj7.funny_world.daiBlocks.custom.ElectromagneticTNT;
import net.Gmaj7.funny_world.daiBlocks.custom.SculkTNT;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class daiBlocks {
    public static final DeferredRegister.Blocks DAI_BLOCKS = DeferredRegister.createBlocks(FunnyWorld.MODID);


    public static final Supplier<Block> SCULK_TNT = DAI_BLOCKS.register("sculk_tnt",
            () -> new SculkTNT(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).sound(SoundType.SCULK_SENSOR)));

    public static final Supplier<Block> ELECTROMAGNETIC_TNT = DAI_BLOCKS.register("electromagnetic_tnt",
            () -> new ElectromagneticTNT(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).sound(SoundType.LODESTONE)));
}
