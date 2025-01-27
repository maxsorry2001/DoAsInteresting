package net.Gmaj7.funny_world.daiItems;

import net.Gmaj7.funny_world.FunnyWorld;
import net.Gmaj7.funny_world.daiBlocks.daiBlocks;
import net.Gmaj7.funny_world.daiInit.daiArmorMaterials;
import net.Gmaj7.funny_world.daiInit.daiDataComponentTypes;
import net.Gmaj7.funny_world.daiInit.daiTiers;
import net.Gmaj7.funny_world.daiItems.custom.*;
import net.Gmaj7.funny_world.daiSounds.daiJukeboxSongs;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class daiItems {
    public static final DeferredRegister.Items DAI_ITEMS = DeferredRegister.createItems(FunnyWorld.MODID);

    public static final Supplier<Item> EXPLOSION_STORAGE = DAI_ITEMS.registerItem("explosion_storage", ExplosionStorage::new);
    public static final Supplier<Item> RED_PACKET = DAI_ITEMS.register("red_packet",
            () -> new RedPacket(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> MAHJONG = DAI_ITEMS.register("mahjong",
            () -> new MahjongItem(new Item.Properties().stacksTo(1).durability(108).component(daiDataComponentTypes.MAHJONG_PATTERN, 0).component(daiDataComponentTypes.MAHJONG_POINTS, 0)));

    public static final Supplier<Item> BLUE_ICE_BOOTS = DAI_ITEMS.register("blue_ice_boots",
            () -> new ArmorItem(daiArmorMaterials.BLUE_ICE, ArmorItem.Type.BOOTS, new Item.Properties().stacksTo(1).durability(128)));

    public static final Supplier<Item> BRICK_HELMET = DAI_ITEMS.register("brick_helmet",
            () -> new ArmorItem(daiArmorMaterials.BRICK, ArmorItem.Type.HELMET, new Item.Properties().durability(64)));
    public static final Supplier<Item> BRICK_CHESTPLATE = DAI_ITEMS.register("brick_chestplate",
            () -> new ArmorItem(daiArmorMaterials.BRICK, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(96)));
    public static final Supplier<Item> BRICK_LEGGINGS = DAI_ITEMS.register("brick_leggings",
            () -> new ArmorItem(daiArmorMaterials.BRICK, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(80)));
    public static final Supplier<Item> BRICK_BOOTS = DAI_ITEMS.register("brick_boots",
            () -> new ArmorItem(daiArmorMaterials.BRICK, ArmorItem.Type.BOOTS, new Item.Properties().durability(64)));
    public static final Supplier<Item> NETHER_BRICK_HELMET = DAI_ITEMS.register("nether_brick_helmet",
            () -> new ArmorItem(daiArmorMaterials.NETHER_BRICK, ArmorItem.Type.HELMET, new Item.Properties().durability(80)));
    public static final Supplier<Item> NETHER_BRICK_CHESTPLATE = DAI_ITEMS.register("nether_brick_chestplate",
            () -> new ArmorItem(daiArmorMaterials.NETHER_BRICK, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(203)));
    public static final Supplier<Item> NETHER_BRICK_LEGGINGS = DAI_ITEMS.register("nether_brick_leggings",
            () -> new ArmorItem(daiArmorMaterials.NETHER_BRICK, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(96)));
    public static final Supplier<Item> NETHER_BRICK_BOOTS = DAI_ITEMS.register("nether_brick_boots",
            () -> new ArmorItem(daiArmorMaterials.NETHER_BRICK, ArmorItem.Type.BOOTS, new Item.Properties().durability(80)));
    public static final Supplier<Item> BELL_HELMET = DAI_ITEMS.register("bell_helmet",
            () -> new ArmorItem(daiArmorMaterials.BELL, ArmorItem.Type.HELMET, new Item.Properties().durability(127)));


    public static final Supplier<Item> JISTGABBURASH = DAI_ITEMS.registerItem("jistgabburash", Jistgabburash::new);
    public static final Supplier<Item> ELECTRIC_CHARGE = DAI_ITEMS.registerItem("electric_charge", ElectricCharge::new);
    public static final Supplier<Item> NEGATIVE_CHARGE = DAI_ITEMS.registerItem("negative_charge", NegativeCharge::new);
    public static final Supplier<Item> HYDROGEN = DAI_ITEMS.registerItem("hydrogen", Hydrogen::new);
    public static final Supplier<Item> OXYGEN = DAI_ITEMS.registerItem("oxygen", Oxygen::new);
    public static final Supplier<Item> CARBON_DIOXIDE = DAI_ITEMS.register("carbon_dioxide",
            () -> new  CarbonDioxide(new Item.Properties().fireResistant()));
    public static final Supplier<Item> CARBON_MONOXIDE = DAI_ITEMS.registerItem("carbon_monoxide", CarbonMonoxide::new);

    public static final Supplier<SwordItem> JISTGABBURASH_SWORD = DAI_ITEMS.register("jistgabburash_sword",
            () -> new SwordItem(daiTiers.JISTGABBURASH, new Item.Properties().attributes(SwordItem.createAttributes(daiTiers.JISTGABBURASH, 0, 6))));
    public static final Supplier<PickaxeItem> JISTGABBURASH_PICKAXE = DAI_ITEMS.register("jistgabburash_pickaxe",
            () -> new PickaxeItem(daiTiers.JISTGABBURASH, new Item.Properties().attributes(PickaxeItem.createAttributes(daiTiers.JISTGABBURASH, 0, 6))));
    public static final Supplier<AxeItem> JISTGABBURASH_AXE = DAI_ITEMS.register("jistgabburash_axe",
            () -> new AxeItem(daiTiers.JISTGABBURASH, new Item.Properties().attributes(AxeItem.createAttributes(daiTiers.JISTGABBURASH, 0, 6))));
    public static final Supplier<ShovelItem> JISTGABBURASH_SHOVEL = DAI_ITEMS.register("jistgabburash_shovel",
            () -> new ShovelItem(daiTiers.JISTGABBURASH, new Item.Properties().attributes(ShovelItem.createAttributes(daiTiers.JISTGABBURASH, 0, 6))));
    public static final Supplier<HoeItem> JISTGABBURASH_HOE = DAI_ITEMS.register("jistgabburash_hoe",
            () -> new HoeItem(daiTiers.JISTGABBURASH, new Item.Properties().attributes(HoeItem.createAttributes(daiTiers.JISTGABBURASH, 0, 6))));

    public static final Supplier<Item> GRAVITATION_BOW = DAI_ITEMS.register("gravitation_bow",
            () -> new GravitationBow(new Item.Properties().stacksTo(1).durability(255)));
    public static final Supplier<Item> ELECTROMAGNETIC_BOW = DAI_ITEMS.register("electromagnetic_bow",
            () -> new ElectromagneticBow(new Item.Properties().stacksTo(1).durability(255)));
    public static final Supplier<Item> THUNDER_SWORD = DAI_ITEMS.register("thunder_shadow_sword",
            () -> new ThunderShadowSword(new Item.Properties().stacksTo(1).attributes(ThunderShadowSword.createAttributes()).durability(255)));
    public static final Supplier<Item> ROD_OF_LIGHTING = DAI_ITEMS.register("lighting_bolt_rod",
            () -> new LightingBoltRod(new Item.Properties().stacksTo(1).durability(255).attributes(LightingBoltRod.createAttributes())));
    public static final Supplier<Item> CONVEX = DAI_ITEMS.register("convex",
            () -> new Convex(new Item.Properties().stacksTo(1)));

    public static final Supplier<Item> EDIBLE_FLINT_AND_STEEL = DAI_ITEMS.register("edible_flint_and_steel",
            () -> new EdibleFlintAndSteel(new Item.Properties().stacksTo(1).food(daiFoods.EDIBLE_FLINT_AND_STEEL).durability(64)));

    public static final Supplier<Item> TT_DISC = DAI_ITEMS.register("tt_disc",
            () -> new Item(new Item.Properties().stacksTo(1).jukeboxPlayable(daiJukeboxSongs.GXFC)));


    public static final Supplier<BlockItem> SCULK_TNT = DAI_ITEMS.registerSimpleBlockItem("sculk_tnt", daiBlocks.SCULK_TNT);
    public static final Supplier<BlockItem> ELECTROMAGNETIC_TNT = DAI_ITEMS.registerSimpleBlockItem("electromagnetic_tnt", daiBlocks.ELECTROMAGNETIC_TNT);
    public static final Supplier<BlockItem> REDSTONE_MAGNET = DAI_ITEMS.registerSimpleBlockItem("redstone_magnet", daiBlocks.REDSTONE_MAGNET);
}
