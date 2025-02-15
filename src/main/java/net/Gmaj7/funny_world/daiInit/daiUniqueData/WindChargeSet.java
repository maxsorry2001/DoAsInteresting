package net.Gmaj7.funny_world.daiInit.daiUniqueData;

import net.Gmaj7.funny_world.daiInit.daiFunctions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;

public class WindChargeSet {
        private ItemStack itemStack = new ItemStack(Items.WIND_CHARGE);
        public void setItemSet(ItemStack itemStack){
            this.itemStack = itemStack;
        }

        public ItemStack getItemSet(){
            return itemStack;
        }
}
