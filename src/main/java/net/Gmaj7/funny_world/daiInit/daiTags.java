package net.Gmaj7.funny_world.daiInit;

import net.Gmaj7.funny_world.FunnyWorld;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class daiTags {
    public class daiItemTags{
        public static final TagKey<Item> CARBON = create("carbon");
        public static final TagKey<Item> CAN_SHOOT_IRON = create("can_shoot_iron");
        public static final TagKey<Item> BOOKS_ENCHANTMENT = create("enchantment/books");

        public static TagKey<Item> create(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, name));
        }
    }

    public class daiBlockTags{
        public static final TagKey<Block> JISTGABBURASH_CANT_BREAK = creat("tier/jistgabburash_cant_break");
        public static final TagKey<Block> ELECTROMAGNETIC_BREAK = creat("electromagnetic_break");
        public static TagKey<Block> creat(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, name));
        }
    }
}
