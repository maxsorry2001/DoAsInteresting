package net.Gmaj7.funny_world.daiEnchantments;

import net.Gmaj7.funny_world.FunnyWorld;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;

public class daiEnchantments {

    public static final ResourceKey<Enchantment> EXPLOSION_GET = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "explosion_get"));
    public static final ResourceKey<Enchantment> SHIELD_STRIKE = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "shield_strike"));
    public static final ResourceKey<Enchantment> PLUNDER = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "plunder"));
    public static final ResourceKey<Enchantment> ELECTRIFICATION_BY_FRICTION = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "electrification_by_friction"));
    public static final ResourceKey<Enchantment> HEAT_BY_FRICTION = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "heat_by_friction"));
    public static final ResourceKey<Enchantment> FISSION = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID,"fission"));
    public static final ResourceKey<Enchantment> FLYING_SHADOWS = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "flying_shadows"));
    public static final ResourceKey<Enchantment> CONVINCE_PEOPLE_BY_REASON = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "convince_people_by_reason"));


}