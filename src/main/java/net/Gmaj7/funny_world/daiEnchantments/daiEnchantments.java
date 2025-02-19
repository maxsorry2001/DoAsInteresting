package net.Gmaj7.funny_world.daiEnchantments;

import net.Gmaj7.funny_world.FunnyWorld;
import net.Gmaj7.funny_world.daiEnchantments.custom.EntropyEnchantmentEffect;
import net.Gmaj7.funny_world.daiInit.daiTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;
import net.minecraft.world.item.enchantment.effects.AllOf;

public class daiEnchantments {

    public static final ResourceKey<Enchantment> EXPLOSION_GET = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "explosion_get"));
    public static final ResourceKey<Enchantment> SHIELD_STRIKE = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "shield_strike"));
    public static final ResourceKey<Enchantment> PLUNDER = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "plunder"));
    public static final ResourceKey<Enchantment> ELECTRIFICATION_BY_FRICTION = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "electrification_by_friction"));
    public static final ResourceKey<Enchantment> HEAT_BY_FRICTION = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "heat_by_friction"));
    public static final ResourceKey<Enchantment> FISSION = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID,"fission"));
    public static final ResourceKey<Enchantment> FLYING_SHADOWS = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "flying_shadows"));
    public static final ResourceKey<Enchantment> CONVINCE_PEOPLE_BY_REASON = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "convince_people_by_reason"));
    public static final ResourceKey<Enchantment> PROBATION = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "probation"));
    public static final ResourceKey<Enchantment> EATER_OF_WORLDS = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "eater_of_worlds"));
    public static final ResourceKey<Enchantment> ALL_OF_SAME = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "all_of_same"));
    public static final ResourceKey<Enchantment> ENTROPY = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "entropy"));

    public static void bootstrap(BootstrapContext<Enchantment> context){
        var enchantments = context.lookup(Registries.ENCHANTMENT);
        var items = context.lookup(Registries.ITEM);

        register(context, ENTROPY, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(daiTags.daiItemTags.WIND_CHARGE_ENCHANTMENT),
                items.getOrThrow(daiTags.daiItemTags.WIND_CHARGE_ENCHANTMENT),
                5,
                3,
                Enchantment.dynamicCost(1, 3),
                Enchantment.dynamicCost(5, 3),
                2,
                EquipmentSlotGroup.MAINHAND))
                .withEffect(EnchantmentEffectComponents.HIT_BLOCK, AllOf.entityEffects(new EntropyEnchantmentEffect())));
    }
    private static void register(BootstrapContext<Enchantment> registry, ResourceKey<Enchantment> key, Enchantment.Builder builder){
        registry.register(key, builder.build(key.location()));
    }
}