package net.Gmaj7.funny_world.daiInit;

import com.mojang.serialization.Codec;
import net.Gmaj7.funny_world.FunnyWorld;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class daiDataComponentTypes {
    public static final DeferredRegister<DataComponentType<?>> DAI_DATA_COMPONENT_TYPE =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, FunnyWorld.MODID);

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

    public static final Supplier<DataComponentType<Integer>> HEAT_BY_FRICTION = DAI_DATA_COMPONENT_TYPE.register("heat_by_friction",
            () -> DataComponentType.<Integer>builder().persistent(Codec.INT).build());

    public static final Supplier<DataComponentType<Integer>> MAHJONG_PATTERN = DAI_DATA_COMPONENT_TYPE.register("mahjong_pattern",
            () -> DataComponentType.<Integer>builder().persistent(Codec.INT).build());

    public static final Supplier<DataComponentType<Integer>> MAHJONG_POINTS = DAI_DATA_COMPONENT_TYPE.register("mahjong_points",
            () -> DataComponentType.<Integer>builder().persistent(Codec.INT).build());

    public static final Supplier<DataComponentType<Integer>> HUMANITY = DAI_DATA_COMPONENT_TYPE.register("humanity",
            () -> DataComponentType.<Integer>builder().persistent(Codec.INT).build());

    public static final Supplier<DataComponentType<daiHoneyEffects>> HONEY_EFFECTS = DAI_DATA_COMPONENT_TYPE.register("honey_effects",
            () -> DataComponentType.<daiHoneyEffects>builder().persistent(daiHoneyEffects.CODEC).networkSynchronized(daiHoneyEffects.STREAM_CODEC).build());

    public static final Supplier<DataComponentType<CompoundTag>> ENTITY_DATA = DAI_DATA_COMPONENT_TYPE.register("entity_data",
                    () -> DataComponentType.<CompoundTag>builder().persistent(CompoundTag.CODEC).build());

    public static final Supplier<DataComponentType<ResourceLocation>> ENTITY_TYPE = DAI_DATA_COMPONENT_TYPE.register("entity_type",
            () -> DataComponentType.<ResourceLocation>builder().persistent(ResourceLocation.CODEC).build());

    public static final Supplier<DataComponentType<Integer>> SWEEPING_TYPE = DAI_DATA_COMPONENT_TYPE.register("sweeping_direction",
            () -> DataComponentType.<Integer>builder().persistent(Codec.INT).build());

    public static final Supplier<DataComponentType<SimpleFluidContent>> SIMPLE_FLUID_CONTENT = DAI_DATA_COMPONENT_TYPE.register("simple_fluid_content",
            () -> DataComponentType.<SimpleFluidContent>builder().persistent(SimpleFluidContent.CODEC).build());

    public static final Supplier<DataComponentType<Integer>> WATER_BOW_MODEL = DAI_DATA_COMPONENT_TYPE.register("water_bow_model",
            () -> DataComponentType.<Integer>builder().persistent(Codec.INT).build());

    public static void register(IEventBus eventBus){DAI_DATA_COMPONENT_TYPE.register(eventBus);}
}