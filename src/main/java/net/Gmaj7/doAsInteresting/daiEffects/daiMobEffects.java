package net.Gmaj7.doAsInteresting.daiEffects;

import net.Gmaj7.doAsInteresting.DoAsInteresting;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class daiMobEffects {
    public static final DeferredRegister<MobEffect> DAI_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, DoAsInteresting.MODID);

    public static final DeferredHolder<MobEffect ,MobEffect> IIIIII = DAI_EFFECTS.register("iiiiiii",
            () -> new daiMobEffect(MobEffectCategory.NEUTRAL, 99638872));
    public static final DeferredHolder<MobEffect, MobEffect> ELECTRIC_CHARGE = DAI_EFFECTS.register("electric_charge",
            () -> new ElectricChargeEffect(MobEffectCategory.NEUTRAL, 1190028));
    public static final DeferredHolder<MobEffect, MobEffect> NEGATIVE_CHARGE = DAI_EFFECTS.register("negative_charge",
            () -> new NegativeChargeEffect(MobEffectCategory.NEUTRAL,  77261198));
    public static final DeferredHolder<MobEffect, MobEffect> RADIATION = DAI_EFFECTS.register("radiation",
            () -> new RadiationEffect(MobEffectCategory.HARMFUL, 11827736)
                    .addAttributeModifier(Attributes.ARMOR, "98AC77DC-77DD-CCAD-BC54-1665DC7EAF54", -4.0F, AttributeModifier.Operation.ADD_VALUE));
    public static void register(IEventBus eventBus){DAI_EFFECTS.register(eventBus);}
}
