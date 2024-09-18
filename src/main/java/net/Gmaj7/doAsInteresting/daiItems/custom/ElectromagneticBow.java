package net.Gmaj7.doAsInteresting.daiItems.custom;

import net.Gmaj7.doAsInteresting.daiEntities.custom.IronShootEntity;
import net.Gmaj7.doAsInteresting.daiInit.daiSerchItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class ElectromagneticBow extends Item {
    public static final int MAX_DRAW_DURATION = 20;
    public static final int DEFAULT_RANGE = 15;
    public ElectromagneticBow(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        if(pLivingEntity instanceof Player pPlayer){
            ItemStack itemStack = daiSerchItem.getIronShootItem(pPlayer);
            ItemStack itemStack1 = itemStack.copy();
            itemStack1.setCount(1);
            itemStack.shrink(1);
            IronShootEntity ironShootEntity = new IronShootEntity(pLevel, pPlayer, itemStack1);
            int k = EnchantmentHelper.getItemEnchantmentLevel(pLevel.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(Enchantments.POWER), pStack);
            if (k > 0) {
                ironShootEntity.setBaseDamage(ironShootEntity.getBaseDamage() + (double)k * 0.5 + 0.5);
            }

            if (EnchantmentHelper.getItemEnchantmentLevel(pLevel.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(Enchantments.FLAME), pStack) > 0) {
                ironShootEntity.igniteForSeconds(100);
            }
            ironShootEntity.setCharge(getPowerForTime(this.getUseDuration(pStack, pLivingEntity) - pTimeCharged));
            ironShootEntity.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0, 5 * getPowerForTime(this.getUseDuration(pStack, pLivingEntity) - pTimeCharged), 0.1F);
            pLevel.addFreshEntity(ironShootEntity);
            pStack.hurtAndBreak(1, pPlayer, Player.getSlotForHand(pPlayer.getUsedItemHand()));
        }
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack pStack, LivingEntity pEntity) {
        return 72000;
    }

    public static float getPowerForTime(int pCharge) {
        float f = (float)pCharge / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        if(!daiSerchItem.getIronShootItem(pPlayer).isEmpty()){
            pPlayer.startUsingItem(pHand);
            return InteractionResultHolder.success(pPlayer.getItemInHand(pHand));
        }
        else return InteractionResultHolder.fail(pPlayer.getItemInHand(pHand));
    }
}
