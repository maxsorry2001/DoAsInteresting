package net.Gmaj7.doAsInteresting;

import net.Gmaj7.doAsInteresting.daiBlocks.daiBlocks;
import net.Gmaj7.doAsInteresting.daiInit.daiTags;
import net.Gmaj7.doAsInteresting.daiItems.daiItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;
import java.util.stream.IntStream;

public class DoAsInterestingTabs {
    public static final String DAI_TAB_STRING = "dai_tab";

    public static final DeferredRegister<CreativeModeTab> DAI_CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DoAsInteresting.MODID);

    public static final DeferredHolder<CreativeModeTab ,CreativeModeTab> DAI_TAB = DAI_CREATIVE_MODE_TABS.register("dai_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(daiItems.EXPLOSION_STORAGE.get()))
                    .title(Component.translatable("creativetab.dai_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(daiItems.EXPLOSION_STORAGE.get());
                        pOutput.accept(daiItems.ELECTRIC_CHARGE.get());
                        pOutput.accept(daiItems.NEGATIVE_CHARGE.get());
                        pOutput.accept(daiItems.HYDROGEN.get());
                        pOutput.accept(daiItems.OXYGEN.get());
                        pOutput.accept(daiItems.CARBON_MONOXIDE.get());
                        pOutput.accept(daiItems.CARBON_DIOXIDE.get());
                        pOutput.accept(daiItems.JISTGABBURASH.get());
                        pOutput.accept(daiItems.JISTGABBURASH_SWORD.get());
                        pOutput.accept(daiItems.JISTGABBURASH_PICKAXE.get());
                        pOutput.accept(daiItems.JISTGABBURASH_AXE.get());
                        pOutput.accept(daiItems.JISTGABBURASH_SHOVEL.get());
                        pOutput.accept(daiItems.JISTGABBURASH_HOE.get());
                        pOutput.accept(daiItems.BLUE_ICE_BOOTS.get());
                        pOutput.accept(daiItems.BRICK_HELMET.get());
                        pOutput.accept(daiItems.BRICK_CHESTPLATE.get());
                        pOutput.accept(daiItems.BRICK_LEGGINGS.get());
                        pOutput.accept(daiItems.BRICK_BOOTS.get());
                        pOutput.accept(daiItems.NETHER_BRICK_HELMET.get());
                        pOutput.accept(daiItems.NETHER_BRICK_CHESTPLATE.get());
                        pOutput.accept(daiItems.NETHER_BRICK_LEGGINGS.get());
                        pOutput.accept(daiItems.NETHER_BRICK_BOOTS.get());
                        pOutput.accept(daiItems.GRAVITATION_BOW.get());
                        pOutput.accept(daiItems.ELECTROMAGNETIC_BOW.get());
                        pOutput.accept(daiItems.RED_PACKET.get());
                        pOutput.accept(daiItems.THUNDER_SWORD.get());
                        pOutput.accept(daiItems.EDIBLE_FLINT_AND_STEEL.get());

                        pOutput.accept(daiBlocks.SCULK_TNT.get());
                        pOutput.accept(daiBlocks.ELECTROMAGNETIC_TNT.get());
                    }).build());
}
