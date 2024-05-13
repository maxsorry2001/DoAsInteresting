package net.Gmaj7.doAsInteresting.daiItems;

import net.Gmaj7.doAsInteresting.DoAsInteresting;
import net.Gmaj7.doAsInteresting.daiBlocks.daiBlocks;
import net.Gmaj7.doAsInteresting.daiItems.custom.ExplosionStorage;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class daiItems {
    public static final DeferredRegister.Items DAI_ITEMS = DeferredRegister.createItems(DoAsInteresting.MODID);

    public static final Supplier<Item> EXPLOSION_STORAGE = DAI_ITEMS.registerItem("explosion_storage", ExplosionStorage::new);

    public static final Supplier<BlockItem> SCULK_TNT = DAI_ITEMS.registerSimpleBlockItem("sculk_tnt", daiBlocks.SCULK_TNT);
}