package net.Gmaj7.funny_world.mixin;

import net.Gmaj7.funny_world.daiInit.daiUniqueData.daiUniqueDataGet;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.windcharge.WindCharge;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.item.WindChargeItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(WindChargeItem.class)
public abstract class WindChargeItemMixin extends Item implements ProjectileItem {
    public WindChargeItemMixin(Properties pProperties) {
        super(pProperties);
    }

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/windcharge/WindCharge;shootFromRotation(Lnet/minecraft/world/entity/Entity;FFFFF)V"), locals = LocalCapture.CAPTURE_FAILSOFT)
    public void useMixin(Level pLevel, Player pPlayer, InteractionHand pHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> info, WindCharge windcharge){
        ((daiUniqueDataGet)windcharge).getWindChargeSet().setItemSet(pPlayer.getItemInHand(pHand));
    }
}
