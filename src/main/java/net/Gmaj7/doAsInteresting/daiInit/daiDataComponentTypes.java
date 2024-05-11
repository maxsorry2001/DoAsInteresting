package net.Gmaj7.doAsInteresting.daiInit;

import com.mojang.serialization.Codec;
import net.Gmaj7.doAsInteresting.DoAsInteresting;
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

    public static void register(IEventBus eventBus){DAI_DATA_COMPONENT_TYPE.register(eventBus);}
}