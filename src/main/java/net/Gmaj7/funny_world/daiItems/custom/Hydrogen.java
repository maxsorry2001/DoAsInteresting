package net.Gmaj7.funny_world.daiItems.custom;

import net.Gmaj7.funny_world.daiEntities.custom.ThrownHydrogenEntity;
import net.Gmaj7.funny_world.daiInit.daiFunctions;
import net.minecraft.core.registries.Registries;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class Hydrogen extends Item {
    public Hydrogen(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if(!pLevel.isClientSide()){
            ThrownHydrogenEntity thrownHydrogenEntity = new ThrownHydrogenEntity(pLevel, pPlayer);
            thrownHydrogenEntity.setItem(itemStack);
            thrownHydrogenEntity.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.5F, 1.0F);
            pLevel.addFreshEntity(thrownHydrogenEntity);
        }
        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        pPlayer.swing(pUsedHand, true);
        pPlayer.getCooldowns().addCooldown(this,10);
        if(!pPlayer.getAbilities().instabuild && !(pPlayer.getItemInHand(pUsedHand).getEnchantmentLevel(daiFunctions.getHolder(pLevel, Registries.ENCHANTMENT, Enchantments.INFINITY)) > 0)){
            itemStack.shrink(1);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
