package net.Gmaj7.funny_world.daiInit;

import net.Gmaj7.funny_world.FunnyWorld;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;

public class daiArmorMaterials {
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIAL = DeferredRegister.create(Registries.ARMOR_MATERIAL, FunnyWorld.MODID);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> BLUE_ICE = ARMOR_MATERIAL.register("blueice",
            () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), p -> {
                p.put(ArmorItem.Type.BOOTS, 2);
                p.put(ArmorItem.Type.LEGGINGS, 4);
                p.put(ArmorItem.Type.CHESTPLATE, 5);
                p.put(ArmorItem.Type.HELMET, 2);
                p.put(ArmorItem.Type.BODY, 5);
            }),
                    15,
                    SoundEvents.ARMOR_EQUIP_ELYTRA,
                    () -> Ingredient.of(Items.BLUE_ICE),
                    List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "blueice"))),
                    0.0F,
                    0.0F));

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> BRICK = ARMOR_MATERIAL.register("brick",
            () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), p -> {
                p.put(ArmorItem.Type.BOOTS, 2);
                p.put(ArmorItem.Type.LEGGINGS, 4);
                p.put(ArmorItem.Type.CHESTPLATE, 5);
                p.put(ArmorItem.Type.HELMET, 2);
                p.put(ArmorItem.Type.BODY, 5);
            }),
                    15,
                    SoundEvents.ARMOR_EQUIP_ELYTRA,
                    () -> Ingredient.of(Items.BRICK),
                    List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "brick"))),
                    0.0F,
                    5.0F));

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> NETHER_BRICK = ARMOR_MATERIAL.register("nether_brick",
            () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), p -> {
                p.put(ArmorItem.Type.BOOTS, 2);
                p.put(ArmorItem.Type.LEGGINGS, 4);
                p.put(ArmorItem.Type.CHESTPLATE, 5);
                p.put(ArmorItem.Type.HELMET, 2);
                p.put(ArmorItem.Type.BODY, 5);
            }),
                    20,
                    SoundEvents.ARMOR_EQUIP_ELYTRA,
                    () -> Ingredient.of(Items.NETHER_BRICK),
                    List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "nether_brick"))),
                    2.0F,
                    10.0F));

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> BELL = ARMOR_MATERIAL.register("bell",
            () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), p -> {
                p.put(ArmorItem.Type.BOOTS, 2);
                p.put(ArmorItem.Type.LEGGINGS, 4);
                p.put(ArmorItem.Type.CHESTPLATE, 5);
                p.put(ArmorItem.Type.HELMET, 2);
                p.put(ArmorItem.Type.BODY, 5);
            }),
                    20,
                    SoundEvents.ARMOR_EQUIP_GOLD,
                    () -> Ingredient.of(Items.BELL),
                    List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "bell"))),
                    0.0F,
                    0.0F));

    public static void register(IEventBus eventBus){ARMOR_MATERIAL.register(eventBus);}
}
