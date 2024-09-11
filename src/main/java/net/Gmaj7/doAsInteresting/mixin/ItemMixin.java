package net.Gmaj7.doAsInteresting.mixin;

import net.Gmaj7.doAsInteresting.daiInit.daiDataComponentTypes;
import net.Gmaj7.doAsInteresting.daiItems.daiItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureElement;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
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
    }
}
