package net.Gmaj7.funny_world.daiFluids;

import net.Gmaj7.funny_world.FunnyWorld;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.joml.Vector3f;

public class daiFluidTypes {
    public static final ResourceLocation WATER_STILL_LOCATION = ResourceLocation.parse("block/water_still");
    public static final ResourceLocation WATER_FLOW_LOCATION = ResourceLocation.parse("block/water_flow");
    public static final ResourceLocation EXTRACTANT = ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "block/extractant");

    public static final DeferredRegister<FluidType> FLUID_TYPE =
            DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, FunnyWorld.MODID);

    public static final DeferredHolder<FluidType, BaseFluidType> EXTRACTANT_FLUID = FLUID_TYPE.register("extractant_fluid",
            () -> new BaseFluidType(WATER_STILL_LOCATION, WATER_FLOW_LOCATION, EXTRACTANT, 0xEE00AAFF, new Vector3f(0f / 255f, 252f / 255f, 255f / 255f),
                    FluidType.Properties.create()
                            .density(3000)
                            .viscosity(7)
                            .canConvertToSource(true)
                            .supportsBoating(true)
                            .canSwim(true)
                            .canPushEntity(true)
                            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)));

    public static void register(IEventBus eventBus){
        FLUID_TYPE.register(eventBus);
    }
}
