package net.Gmaj7.doAsInteresting.daiInit;

import net.Gmaj7.doAsInteresting.DoAsInteresting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class daiItemTags {

    public static final TagKey<Item> SHIELD_ENCHANTABLE = create("enchantable/daishield");
    public static final TagKey<Item> TOTEM_ENCHANTABLE = create("enchantable/daitotem");

    public static TagKey<Item> create(String name) {
        return ItemTags.create(new ResourceLocation(DoAsInteresting.MODID, name));
    }
}
