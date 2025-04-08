package net.Gmaj7.funny_world.daiFluids;

import net.Gmaj7.funny_world.FunnyWorld;
import net.Gmaj7.funny_world.daiBlocks.daiBlocks;
import net.Gmaj7.funny_world.daiItems.daiItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class daiFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, FunnyWorld.MODID);

    public static final Supplier<FlowingFluid> EXTRACTANT_STILL = FLUIDS.register("extractant_still", () -> new BaseFlowingFluid.Source(Properties.EXTRACTANT_PROPERTIES));
    public static final Supplier<FlowingFluid> EXTRACTANT_FLOW = FLUIDS.register("ectractant_flow", () -> new BaseFlowingFluid.Flowing(Properties.EXTRACTANT_PROPERTIES));

    public static class Properties{
        public static final BaseFlowingFluid.Properties EXTRACTANT_PROPERTIES = new BaseFlowingFluid.Properties(daiFluidTypes.EXTRACTANT_FLUID, EXTRACTANT_STILL, EXTRACTANT_FLOW).bucket(daiItems.EXTRACTANT_FLUID_BUCKET).slopeFindDistance(2).levelDecreasePerBlock(1).block(daiBlocks.EXTRACTANT_FLUID_BLOCK);
    }

    public static void register(IEventBus eventBus){
        FLUIDS.register(eventBus);
    }
}
