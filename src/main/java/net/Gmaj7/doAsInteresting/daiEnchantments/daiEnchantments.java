package net.Gmaj7.doAsInteresting.daiEnchantments;

import net.Gmaj7.doAsInteresting.DoAsInteresting;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;

public class daiEnchantments {

    public static final ResourceKey<Enchantment> EXPLOSION_GET = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(DoAsInteresting.MODID, "explosion_get"));
    public static final ResourceKey<Enchantment> SHIELD_STRIKE = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(DoAsInteresting.MODID, "shield_strike"));
    public static final ResourceKey<Enchantment> PLUNDER = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(DoAsInteresting.MODID, "plunder"));
    public static final ResourceKey<Enchantment> ELECTRIFICATION_BY_FRICTION = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(DoAsInteresting.MODID, "electrification_by_friction"));
    public static final ResourceKey<Enchantment> FISSION = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(DoAsInteresting.MODID,"fission"));
    public static final ResourceKey<Enchantment> FLYING_SHADOWS = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(DoAsInteresting.MODID, "flying_shadows"));
}