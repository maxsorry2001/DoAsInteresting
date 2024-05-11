package net.Gmaj7.doAsInteresting.daiEffects;

import net.Gmaj7.doAsInteresting.DoAsInteresting;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class daiMobEffects {
    public static final DeferredRegister<MobEffect> DAI_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, DoAsInteresting.MODID);
    public static void register(IEventBus eventBus){DAI_EFFECTS.register(eventBus);}
}
