package net.Gmaj7.funny_world.daiInit;

import net.Gmaj7.funny_world.FunnyWorld;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class daiTags {
    public class daiItemTags{
        public static final TagKey<Item> CARBON = create("carbon");
        public static final TagKey<Item> IRON_ITEM = create("iron_item");
        public static final TagKey<Item> BOOKS_ENCHANTMENT = create("enchantable/books");
        public static final TagKey<Item> WIND_CHARGE_ENCHANTMENT = create("enchantable/wind_charge");
        public static final TagKey<Item> HONEY_ENCHANTMENT = create("enchantable/honey");

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

    public class daiEntityTypeTags{
        public static final TagKey<EntityType<?>> IRON_ENTITY = creat("iron_entity");
        public static TagKey<EntityType<?>> creat(String name){
            return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, name));
        }
    }
}
