package net.Gmaj7.funny_world.daiInit;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;

public class daiFunctions {
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

    public static Holder<Enchantment> getHolder(Level level, ResourceKey<Enchantment> resourceKey){
        return level.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(resourceKey);
    }
}
