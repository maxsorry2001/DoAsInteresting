package net.Gmaj7.doAsInteresting;

import net.Gmaj7.doAsInteresting.daiInit.daiItemTags;
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

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DoAsInteresting.MODID);

    public static final DeferredHolder<CreativeModeTab ,CreativeModeTab> DAI_TAB = CREATIVE_MODE_TAB.register("dai_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(daiItems.EXPLOSION_STORAGE.get()))
                    .title(Component.translatable("creativetab.dai_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(daiItems.EXPLOSION_STORAGE.get());
                        Set<TagKey<Item>> set = Set.of(
                                daiItemTags.SHIELD_ENCHANTABLE,
                                daiItemTags.TOTEM_ENCHANTABLE
                        );
                        pParameters.holders()
                                .lookup(Registries.ENCHANTMENT)
                                .ifPresent(
                                        p -> {
                                            generateEnchantmentBookTypesOnlyMaxLevel(
                                                    pOutput, p, set, CreativeModeTab.TabVisibility.PARENT_TAB_ONLY, pParameters.enabledFeatures()
                                            );
                                            generateEnchantmentBookTypesAllLevels(
                                                    pOutput, p, set, CreativeModeTab.TabVisibility.SEARCH_TAB_ONLY, pParameters.enabledFeatures()
                                            );
                                        }
                                );
                    }).build());
    private static void generateEnchantmentBookTypesOnlyMaxLevel(
            CreativeModeTab.Output pOutput,
            HolderLookup<Enchantment> pEnchantments,
            Set<TagKey<Item>> pItems,
            CreativeModeTab.TabVisibility pTabVisibility,
            FeatureFlagSet pRequiredFeatures
    ) {
        pEnchantments.listElements()
                .map(Holder::value)
                .filter(p_337914_ -> p_337914_.isEnabled(pRequiredFeatures))
                .filter(p_270008_ -> p_270008_.allowedInCreativeTab(Items.ENCHANTED_BOOK, pItems))
                .map(p_270038_ -> EnchantedBookItem.createForEnchantment(new EnchantmentInstance(p_270038_, p_270038_.getMaxLevel())))
                .forEach(p_269989_ -> pOutput.accept(p_269989_, pTabVisibility));
    }

    private static void generateEnchantmentBookTypesAllLevels(
            CreativeModeTab.Output p_270961_,
            HolderLookup<Enchantment> pEnchantments,
            Set<TagKey<Item>> pItems,
            CreativeModeTab.TabVisibility pTabVisibility,
            FeatureFlagSet pRequiredFeatures
    ) {
        pEnchantments.listElements()
                .map(Holder::value)
                .filter(p_337930_ -> p_337930_.isEnabled(pRequiredFeatures))
                .filter(p_269991_ -> p_269991_.allowedInCreativeTab(Items.ENCHANTED_BOOK, pItems))
                .flatMap(
                        p_270024_ -> IntStream.rangeClosed(p_270024_.getMinLevel(), p_270024_.getMaxLevel())
                                .mapToObj(p_270006_ -> EnchantedBookItem.createForEnchantment(new EnchantmentInstance(p_270024_, p_270006_)))
                )
                .forEach(p_270017_ -> p_270961_.accept(p_270017_, pTabVisibility));
    }
}
