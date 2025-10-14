package net.Gmaj7.funny_world.mixin;

import net.Gmaj7.funny_world.daiEnchantments.daiEnchantments;
import net.Gmaj7.funny_world.daiInit.daiDataComponentTypes;
import net.Gmaj7.funny_world.daiInit.daiFunctions;
import net.Gmaj7.funny_world.daiItems.daiFoods;
import net.Gmaj7.funny_world.daiItems.daiItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureElement;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.Tags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Item.class)
public abstract class ItemMixin implements FeatureElement, ItemLike, net.neoforged.neoforge.common.extensions.IItemExtension{

    @Inject(at = @At("HEAD"), method = "inventoryTick")
    private void inventoryTickMixin(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected, CallbackInfo ci){
        if(pStack.has(daiDataComponentTypes.HEAT_BY_FRICTION) && pStack.getItem() instanceof TieredItem tieredItem){
            int time = pStack.get(daiDataComponentTypes.HEAT_BY_FRICTION.get());
            if (time > 1) pStack.set(daiDataComponentTypes.HEAT_BY_FRICTION.get(), time - 1);
            else pStack.remove(daiDataComponentTypes.HEAT_BY_FRICTION.get());
            if((tieredItem.getTier() == Tiers.WOOD || tieredItem.getTier() == Tiers.DIAMOND) && pLevel instanceof ServerLevel && time % 30 == 0 && pEntity instanceof Player){
                pStack.hurtAndBreak(pStack.getMaxDamage() / 10 + 1, (ServerLevel) pLevel, null, p -> {});
                if (pStack.getDamageValue() >= pStack.getMaxDamage()){
                    ((Player) pEntity).addItem(new ItemStack(daiItems.CARBON_DIOXIDE.get()));
                }
            }
        }
        if(pStack.is(Items.GLOW_INK_SAC)) {
            pStack.set(DataComponents.FOOD, daiFoods.GLOW_INK_SAC);
        }
        else if(pEntity instanceof Player player && EnchantmentHelper.getEnchantmentLevel(daiFunctions.getHolder(pLevel, Registries.ENCHANTMENT, daiEnchantments.EATER_OF_WORLDS), player) > 0 && !pStack.is(Tags.Items.FOODS)){
            switch (EnchantmentHelper.getEnchantmentLevel(daiFunctions.getHolder(pLevel, Registries.ENCHANTMENT, daiEnchantments.EATER_OF_WORLDS), player)){
                case 1 -> pStack.set(DataComponents.FOOD, daiFoods.EAT_OF_WORLDS_LV1);
                case 2 -> pStack.set(DataComponents.FOOD, daiFoods.EAT_OF_WORLDS_LV2);
                case 3 -> pStack.set(DataComponents.FOOD, daiFoods.EAT_OF_WORLDS_LV3);
            }
        }
        else {
            if(!pStack.is(Tags.Items.FOODS) && pStack.has(DataComponents.FOOD)){
                pStack.remove(DataComponents.FOOD);
            }
        }
    }
}
