package net.Gmaj7.doAsInteresting.daiInit;

import net.Gmaj7.doAsInteresting.DoAsInteresting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class daiTags {
    public class daiItemTags{
        public static final TagKey<Item> SHIELD_ENCHANTABLE = create("enchantable/dai_shield");
        public static final TagKey<Item> TOTEM_ENCHANTABLE = create("enchantable/dai_totem");
        public static final TagKey<Item> CHEST_ARMOR_ENCHANTABLE = create("enchantable/dai_chest_armor");

        public static TagKey<Item> create(String name) {
            return ItemTags.create(new ResourceLocation(DoAsInteresting.MODID, name));
        }
    }

    public class daiBlockTags{
        public static final TagKey<Block> JISTGABBURASH_CANT_BREAK = creat("tier/jistgabburash_cant_break");
        public static TagKey<Block> creat(String name) {
            return BlockTags.create(new ResourceLocation(DoAsInteresting.MODID, name));
        }
    }
}