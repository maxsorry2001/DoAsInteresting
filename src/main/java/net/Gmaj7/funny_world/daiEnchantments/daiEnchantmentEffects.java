package net.Gmaj7.funny_world.daiEnchantments;

import com.mojang.serialization.MapCodec;
import net.Gmaj7.funny_world.FunnyWorld;
import net.Gmaj7.funny_world.daiEnchantments.custom.CharmEnchantmentEffect;
import net.Gmaj7.funny_world.daiEnchantments.custom.EntropyEnchantmentEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class daiEnchantmentEffects {
    public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> DAI_ENCHANTMENT_EFFECTS =
            DeferredRegister.create(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, FunnyWorld.MODID);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> ENTROPY =
            DAI_ENCHANTMENT_EFFECTS.register("entropy_effect", () -> EntropyEnchantmentEffect.CODEC);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> CHARM =
            DAI_ENCHANTMENT_EFFECTS.register("charm", () -> CharmEnchantmentEffect.CODEC);

    public static void register(IEventBus eventBus){
        DAI_ENCHANTMENT_EFFECTS.register(eventBus);
    }
}
