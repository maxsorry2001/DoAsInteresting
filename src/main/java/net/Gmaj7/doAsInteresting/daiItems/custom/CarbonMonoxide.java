package net.Gmaj7.doAsInteresting.daiItems.custom;

import net.Gmaj7.doAsInteresting.daiEffects.daiMobEffects;
import net.Gmaj7.doAsInteresting.daiEntities.custom.ThrownCarbonDioxideEntity;
import net.Gmaj7.doAsInteresting.daiEntities.custom.ThrownCarbonMonoxideEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class CarbonMonoxide extends Item {
    public CarbonMonoxide(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if(!pLevel.isClientSide()){
            if(pPlayer.isInWater()) pPlayer.addEffect(new MobEffectInstance(daiMobEffects.CARBON_MONOXIDE_POISONING, 100));
            else {
                ThrownCarbonMonoxideEntity thrownCarbonMonoxideEntoty = new ThrownCarbonMonoxideEntity(pLevel, pPlayer);
                thrownCarbonMonoxideEntoty.setItem(itemStack);
                thrownCarbonMonoxideEntoty.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.5F, 1.0F);
                pLevel.addFreshEntity(thrownCarbonMonoxideEntoty);
            }
        }
        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        pPlayer.swing(pUsedHand, true);
        pPlayer.getCooldowns().addCooldown(this,10);
        if(!pPlayer.getAbilities().instabuild && !(pPlayer.getItemInHand(pUsedHand).getEnchantmentLevel(pLevel.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(Enchantments.INFINITY)) > 0)){
            itemStack.shrink(1);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
