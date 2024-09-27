package net.Gmaj7.funny_world;

import net.Gmaj7.funny_world.daiBlocks.daiBlocks;
import net.Gmaj7.funny_world.daiItems.daiItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FunnyWorldTabs {
    public static final String DAI_TAB_STRING = "dai_tab";

    public static final DeferredRegister<CreativeModeTab> DAI_CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FunnyWorld.MODID);

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
