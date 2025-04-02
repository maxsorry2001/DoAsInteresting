package net.Gmaj7.funny_world.daiFluids;

import net.Gmaj7.funny_world.FunnyWorld;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.SoundAction;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.joml.Vector3f;

import java.util.function.Supplier;

public class daiFluidTypes {
    public static final ResourceLocation WATER_STILL_LOCATION = ResourceLocation.parse("block/water_still");
    public static final ResourceLocation WATER_FLOW_LOCATION = ResourceLocation.parse("block/water_flow");
    public static final ResourceLocation EXTRACTANT = ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "misc/extractant");

    public static final DeferredRegister<FluidType> FLUID_TYPE =
            DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, FunnyWorld.MODID);

    public static final Supplier<FluidType> EXTRACTANT_FLUID = FLUID_TYPE.register("extractant_fluid",
            () -> new BaseFluidType(WATER_STILL_LOCATION, WATER_FLOW_LOCATION, EXTRACTANT, 0xFFFFFFFF, new Vector3f(224f / 255f, 56f / 255f, 208f / 255f),
                    FluidType.Properties.create().lightLevel(3).density(15).viscosity(5).sound(SoundAction.get("drink"), SoundEvents.GENERIC_DRINK)));

    public static void register(IEventBus eventBus){
        FLUID_TYPE.register(eventBus);
    }
}
