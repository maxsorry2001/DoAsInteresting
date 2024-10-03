package net.Gmaj7.funny_world.daiInit;

import net.Gmaj7.funny_world.FunnyWorld;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;

public interface daiDamageTypes extends DamageTypes {
    ResourceKey<DamageType> RADIATION = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "dai_radiation"));
    ResourceKey<DamageType> INTERNAL_INJURY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "internal_injury"));
    ResourceKey<DamageType> CARBON_MONOXIDE_POISONING = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "carbon_monoxide_poisoning"));
    ResourceKey<DamageType> POWER_OF_KNOWLEDGE = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "power_of_knowledge"));
}
