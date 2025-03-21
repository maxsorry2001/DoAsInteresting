package net.Gmaj7.funny_world.daiItems;

import net.Gmaj7.funny_world.FunnyWorld;
import net.Gmaj7.funny_world.daiEffects.daiMobEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class daiPotions {
    public static DeferredRegister<Potion> DAI_POTIONS = DeferredRegister.create(Registries.POTION, FunnyWorld.MODID);

    public static final Holder<Potion> GENE_MUTATION_POTION = DAI_POTIONS.register("gene_mutation_potion",
            () -> new Potion(new MobEffectInstance(daiMobEffects.GENE_MUTATION, 3000)));

    public static void register(IEventBus eventBus){
        DAI_POTIONS.register(eventBus);
    }
}
