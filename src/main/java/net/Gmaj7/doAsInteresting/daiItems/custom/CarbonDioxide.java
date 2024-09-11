package net.Gmaj7.doAsInteresting.daiItems.custom;

import net.Gmaj7.doAsInteresting.daiEntities.custom.ThrownCarbonDioxideEntity;
import net.Gmaj7.doAsInteresting.daiEntities.custom.ThrownOxygenEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class CarbonDioxide extends Item {
    public CarbonDioxide(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if(!pLevel.isClientSide()){
            if(pPlayer.isInWater()) pPlayer.setAirSupply(Math.min(pPlayer.getAirSupply() - 150, pPlayer.getMaxAirSupply()));
            else {
                ThrownCarbonDioxideEntity thrownCarbonDioxideEntity = new ThrownCarbonDioxideEntity(pLevel, pPlayer);
                thrownCarbonDioxideEntity.setItem(itemStack);
                thrownCarbonDioxideEntity.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.5F, 1.0F);
                pLevel.addFreshEntity(thrownCarbonDioxideEntity);
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
