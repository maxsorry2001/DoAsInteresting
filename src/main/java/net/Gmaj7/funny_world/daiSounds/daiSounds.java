package net.Gmaj7.funny_world.daiSounds;

import net.Gmaj7.funny_world.FunnyWorld;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class daiSounds {
    public static final DeferredRegister<SoundEvent> DAI_SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, FunnyWorld.MODID);


    public static final DeferredHolder<SoundEvent, SoundEvent> GXFC = DAI_SOUNDS.register("gxfc",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "gxfc")));

    public static void register(IEventBus eventBus){
        DAI_SOUNDS.register(eventBus);
    }
}
