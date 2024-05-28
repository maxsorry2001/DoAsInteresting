package net.Gmaj7.doAsInteresting.daiEffects;

import net.Gmaj7.doAsInteresting.DoAsInteresting;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class daiMobEffects {
    public static final DeferredRegister<MobEffect> DAI_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, DoAsInteresting.MODID);

    public static final DeferredHolder<MobEffect ,MobEffect> IIIIII = DAI_EFFECTS.register("iiiiiii",
            () -> new daiMobEffect(MobEffectCategory.NEUTRAL, 99638872));
    public static void register(IEventBus eventBus){DAI_EFFECTS.register(eventBus);}
}
