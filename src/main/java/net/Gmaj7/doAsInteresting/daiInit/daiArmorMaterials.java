package net.Gmaj7.doAsInteresting.daiInit;

import net.Gmaj7.doAsInteresting.DoAsInteresting;
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
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIAL = DeferredRegister.create(Registries.ARMOR_MATERIAL, DoAsInteresting.MODID);

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
                    List.of(new ArmorMaterial.Layer(new ResourceLocation(DoAsInteresting.MODID, "blueice"))),
                    0.0F,
                    0.0F));

    public static void register(IEventBus eventBus){ARMOR_MATERIAL.register(eventBus);}
}
