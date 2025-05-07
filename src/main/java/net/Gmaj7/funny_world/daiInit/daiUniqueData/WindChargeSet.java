package net.Gmaj7.funny_world.daiInit.daiUniqueData;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class WindChargeSet {
        private ItemStack itemStack = new ItemStack(Items.WIND_CHARGE);
        public void setItemSet(ItemStack itemStack){
            this.itemStack = itemStack;
        }

        public ItemStack getItemSet(){
            return itemStack;
        }
}
