package net.Gmaj7.doAsInteresting.daiEnchantments.custom;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;

public class Plunder extends Enchantment {

    public Plunder(EnchantmentDefinition pDefinition) {
        super(pDefinition);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return true;
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    @Override
    public boolean canEnchant(ItemStack pStack) {
        return pStack.getItem() == Items.TOTEM_OF_UNDYING;
    }
}
