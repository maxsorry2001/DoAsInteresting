package net.Gmaj7.doAsInteresting.daiInit;

import com.mojang.serialization.Codec;
import net.Gmaj7.doAsInteresting.DoAsInteresting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class daiDataComponentTypes {
    public static final DeferredRegister<DataComponentType<?>> DAI_DATA_COMPONENT_TYPE =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, DoAsInteresting.MODID);

    public static final Supplier<DataComponentType<Float>> ExplosionStorageRadius = DAI_DATA_COMPONENT_TYPE.register("explosion_storage_radius",
            () -> DataComponentType.<Float>builder().persistent(Codec.FLOAT).build());

    public static final Supplier<DataComponentType<Integer>> EMERALD_NUM = DAI_DATA_COMPONENT_TYPE.register("emerald_num",
            () -> DataComponentType.<Integer>builder().persistent(Codec.INT).build());

    public static final Supplier<DataComponentType<Integer>> EMERALD_BLOCK_NUM = DAI_DATA_COMPONENT_TYPE.register("emerald_block_num",
            () -> DataComponentType.<Integer>builder().persistent(Codec.INT).build());

    public static final Supplier<DataComponentType<BlockPos>> BLOCKPOS = DAI_DATA_COMPONENT_TYPE.register("block_pos",
            () -> DataComponentType.<BlockPos>builder().persistent(BlockPos.CODEC).build());

    public static final Supplier<DataComponentType<Integer>> CANNOT_SHOOT = DAI_DATA_COMPONENT_TYPE.register("can_shoot",
            () -> DataComponentType.<Integer>builder().persistent(Codec.INT).build());

    public static void register(IEventBus eventBus){DAI_DATA_COMPONENT_TYPE.register(eventBus);}
}