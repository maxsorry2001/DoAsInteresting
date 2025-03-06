package net.Gmaj7.funny_world.daiBlocks;

import net.Gmaj7.funny_world.FunnyWorld;
import net.Gmaj7.funny_world.daiBlocks.blockEntity.HoneyFloorBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class daiBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> DAI_BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, FunnyWorld.MODID);

    public static final Supplier<BlockEntityType<HoneyFloorBlockEntity>> HONEY_FLOOR_BE =
            DAI_BLOCK_ENTITIES.register("honey_floor_be", () -> BlockEntityType.Builder.of(
                    HoneyFloorBlockEntity::new, daiBlocks.HONEY_FLOOR.get()).build(null));

    public static void register(IEventBus eventBus){
        DAI_BLOCK_ENTITIES.register(eventBus);
    }
}
