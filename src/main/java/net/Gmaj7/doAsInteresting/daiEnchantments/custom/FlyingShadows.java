package net.Gmaj7.doAsInteresting.daiEnchantments.custom;

import net.Gmaj7.doAsInteresting.daiItems.daiItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class FlyingShadows extends Enchantment {
    public FlyingShadows(EnchantmentDefinition pDefinition) {
        super(pDefinition);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return true;
    }

    @Override
    public boolean canEnchant(ItemStack pStack) {
        return pStack.is(daiItems.THUNDER_SWORD.get());
    }
}
