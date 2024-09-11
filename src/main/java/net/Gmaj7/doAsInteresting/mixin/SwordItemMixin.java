package net.Gmaj7.doAsInteresting.mixin;

import net.Gmaj7.doAsInteresting.daiInit.daiDataComponentTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.flag.FeatureElement;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SwordItem.class)
public abstract class SwordItemMixin extends TieredItem{

    public SwordItemMixin(Tier pTier, Properties pProperties) {
        super(pTier, pProperties);
    }

    @Inject(at = @At("HEAD"), method = "postHurtEnemy")
    private void postHurtEnemyMixin(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker, CallbackInfo ci){
        if(pStack.has(daiDataComponentTypes.HEAT_BY_FRICTION)){
            pTarget.setRemainingFireTicks(300);
        }
    }
}
