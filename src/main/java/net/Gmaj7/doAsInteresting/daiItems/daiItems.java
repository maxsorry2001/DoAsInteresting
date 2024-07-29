package net.Gmaj7.doAsInteresting.daiItems;

import net.Gmaj7.doAsInteresting.DoAsInteresting;
import net.Gmaj7.doAsInteresting.daiBlocks.daiBlocks;
import net.Gmaj7.doAsInteresting.daiInit.daiArmorMaterials;
import net.Gmaj7.doAsInteresting.daiInit.daiTiers;
import net.Gmaj7.doAsInteresting.daiItems.custom.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class daiItems {
    public static final DeferredRegister.Items DAI_ITEMS = DeferredRegister.createItems(DoAsInteresting.MODID);

    public static final Supplier<Item> EXPLOSION_STORAGE = DAI_ITEMS.registerItem("explosion_storage", ExplosionStorage::new);
    public static final Supplier<Item> RED_PACKET = DAI_ITEMS.register("red_packet",
            () -> new RedPacket(new Item.Properties().stacksTo(1)));

    public static final Supplier<Item> BLUE_ICE_BOOTS = DAI_ITEMS.register("blue_ice_boots",
            () -> new ArmorItem(daiArmorMaterials.BLUE_ICE, ArmorItem.Type.BOOTS, new Item.Properties().stacksTo(1).durability(128)));

    public static final Supplier<Item> JISTGABBURASH = DAI_ITEMS.registerItem("jistgabburash", Jistgabburash::new);
    public static final Supplier<Item> ELECTRIC_CHARGE = DAI_ITEMS.registerItem("electric_charge", ElectricCharge::new);
    public static final Supplier<Item> NEGATIVE_CHARGE = DAI_ITEMS.registerItem("negative_charge", NegativeCharge::new);

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

    public static final Supplier<BlockItem> SCULK_TNT = DAI_ITEMS.registerSimpleBlockItem("sculk_tnt", daiBlocks.SCULK_TNT);
    public static final Supplier<BlockItem> ELECTROMAGNETIC_TNT = DAI_ITEMS.registerSimpleBlockItem("electromagnetic_tnt", daiBlocks.ELECTROMAGNETIC_TNT);
}
