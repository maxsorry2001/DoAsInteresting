package net.Gmaj7.doAsInteresting.mixin;

import net.Gmaj7.doAsInteresting.daiInit.daiDataComponentTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
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
        if(pStack.has(daiDataComponentTypes.HEAT_BY_FRICTION.get()) && pStack.getItem() instanceof TieredItem tieredItem){
            if((tieredItem.getTier() == Tiers.WOOD || tieredItem.getTier() == Tiers.DIAMOND) && pEntity instanceof Player && pLevel instanceof ServerLevel)
                pStack.hurtAndBreak(1, (ServerLevel) pLevel, (Player)pEntity, p -> {});
        }
    }
}
