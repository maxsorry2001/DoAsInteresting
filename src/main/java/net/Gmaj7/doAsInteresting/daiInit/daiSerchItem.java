package net.Gmaj7.doAsInteresting.daiInit;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class daiSerchItem {
    public static ItemStack getIronShootItem(Player player){
        Inventory inventory = player.getInventory();
        ItemStack itemStack = ItemStack.EMPTY;
        for (int i = 0; i < inventory.getContainerSize(); i++){
            if(inventory.getItem(i).is(daiTags.daiItemTags.CAN_SHOOT_IRON)){
                itemStack = inventory.getItem(i);
                break;
            }
        }
        return itemStack;
    }
}
