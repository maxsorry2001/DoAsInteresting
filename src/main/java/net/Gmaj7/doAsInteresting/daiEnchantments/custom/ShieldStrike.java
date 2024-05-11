package net.Gmaj7.doAsInteresting.daiEnchantments.custom;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.Enchantment;

public class ShieldStrike extends Enchantment {

    public ShieldStrike(EnchantmentDefinition pDefinition) {
        super(pDefinition);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return true;
    }

    @Override
    public boolean canEnchant(ItemStack pStack) {
        return pStack.getItem() instanceof ShieldItem;
    }
}