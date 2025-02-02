package net.Gmaj7.funny_world.daiItems.custom;

import net.Gmaj7.funny_world.daiEnchantments.daiEnchantments;
import net.Gmaj7.funny_world.daiEntities.custom.MahjongEntity;
import net.Gmaj7.funny_world.daiInit.daiDataComponentTypes;
import net.Gmaj7.funny_world.daiInit.daiFunctions;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Random;

public class MahjongItem extends Item {
    public MahjongItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if(!pLevel.isClientSide()) {
            if (pPlayer.isShiftKeyDown()) {
                if(itemStack.get(daiDataComponentTypes.MAHJONG_PATTERN) == 0 || itemStack.getEnchantmentLevel(daiFunctions.getEnchantmentHolder(pLevel, daiEnchantments.ALL_OF_SAME)) <= 0)
                    itemStack.set(daiDataComponentTypes.MAHJONG_PATTERN, new Random().nextInt(3) + 1);
                itemStack.set(daiDataComponentTypes.MAHJONG_POINTS, new Random().nextInt(9) + 1);
            } else if (itemStack.get(daiDataComponentTypes.MAHJONG_PATTERN) > 0 && itemStack.get(daiDataComponentTypes.MAHJONG_POINTS) > 0) {
                itemStack.hurtAndBreak(1, pPlayer, EquipmentSlot.MAINHAND);
                MahjongEntity mahjongEntity = new MahjongEntity(pLevel, pPlayer, itemStack);
                mahjongEntity.setPattern(itemStack.get(daiDataComponentTypes.MAHJONG_PATTERN));
                mahjongEntity.setPoint(itemStack.get(daiDataComponentTypes.MAHJONG_POINTS));
                mahjongEntity.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.5F, 1.0F);
                pLevel.addFreshEntity(mahjongEntity);
                itemStack.shrink(1);
            } else return InteractionResultHolder.fail(itemStack);
        }
        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        pPlayer.swing(pUsedHand, true);
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
