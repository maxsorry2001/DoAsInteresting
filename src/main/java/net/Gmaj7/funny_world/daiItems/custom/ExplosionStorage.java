package net.Gmaj7.funny_world.daiItems.custom;

import net.Gmaj7.funny_world.daiEntities.custom.ExplosionStorageEntity;
import net.Gmaj7.funny_world.daiInit.daiDataComponentTypes;
import net.Gmaj7.funny_world.daiInit.daiFunctions;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.List;

public class ExplosionStorage extends Item {

    public ExplosionStorage(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        pLevel.playSound(pPlayer, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(),
                SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 0.5F, 0.4F / (pLevel.getRandom().nextFloat()*0.4F + 0.8F));
        if(!pLevel.isClientSide()){
            float radius;
            if(!itemStack.getComponents().isEmpty() && itemStack.getComponents().has(daiDataComponentTypes.ExplosionStorageRadius.get()))
                radius = itemStack.getComponents().get(daiDataComponentTypes.ExplosionStorageRadius.get());
            else radius = 4F;
            ExplosionStorageEntity explosionStorageEntity = new ExplosionStorageEntity(pLevel, pPlayer);
            explosionStorageEntity.setRadius(radius);
            explosionStorageEntity.setItem(itemStack);
            explosionStorageEntity.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.5F, 1.0F);
            pLevel.addFreshEntity(explosionStorageEntity);
        }
        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        pPlayer.swing(pUsedHand, true);
        pPlayer.getCooldowns().addCooldown(this,10);
        if(!pPlayer.getAbilities().instabuild && !(pPlayer.getItemInHand(pUsedHand).getEnchantmentLevel(daiFunctions.getHolder(pLevel, Enchantments.INFINITY)) > 0)){
            itemStack.shrink(1);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        if(pStack.getComponents().isEmpty() || !pStack.getComponents().has(daiDataComponentTypes.ExplosionStorageRadius.get()))
            pStack.set(daiDataComponentTypes.ExplosionStorageRadius.get(), 4.0F);
        else pTooltipComponents.add(Component.translatable("radius").append(Component.literal(String.valueOf(pStack.getComponents().get(daiDataComponentTypes.ExplosionStorageRadius.get())))));
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
}