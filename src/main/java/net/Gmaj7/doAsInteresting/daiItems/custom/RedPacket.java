package net.Gmaj7.doAsInteresting.daiItems.custom;

import net.Gmaj7.doAsInteresting.daiInit.daiDataComponentTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class RedPacket extends Item {
    public RedPacket(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(pUsedHand == InteractionHand.MAIN_HAND && !pLevel.isClientSide()){
            ItemStack itemStackMain = pPlayer.getMainHandItem();
            ItemStack itemStackOff = pPlayer.getOffhandItem();
            if(!itemStackMain.getComponents().has(daiDataComponentTypes.EMERALD_NUM.get())) itemStackMain.set(daiDataComponentTypes.EMERALD_NUM, 0);
            if(!itemStackMain.getComponents().has(daiDataComponentTypes.EMERALD_BLOCK_NUM.get())) itemStackMain.set(daiDataComponentTypes.EMERALD_BLOCK_NUM, 0);
            if(itemStackOff.getItem() == Items.EMERALD){
                while (itemStackMain.getComponents().get(daiDataComponentTypes.EMERALD_NUM.get()) < 64 && itemStackOff.getCount() > 0) {
                    itemStackOff.shrink(1);
                    itemStackMain.set(daiDataComponentTypes.EMERALD_NUM.get(), itemStackMain.getComponents().get(daiDataComponentTypes.EMERALD_NUM.get()) + 1);
                }
            }
            else if(itemStackOff.getItem() == Items.EMERALD_BLOCK){
                while (itemStackMain.getComponents().get(daiDataComponentTypes.EMERALD_BLOCK_NUM.get()) < 64 && itemStackOff.getCount() > 0) {
                    itemStackOff.shrink(1);
                    itemStackMain.set(daiDataComponentTypes.EMERALD_BLOCK_NUM.get(), itemStackMain.getComponents().get(daiDataComponentTypes.EMERALD_BLOCK_NUM.get()) + 1);
                }
            }
            else if(itemStackOff.isEmpty()){
                if(itemStackMain.getComponents().get(daiDataComponentTypes.EMERALD_NUM.get()) > 0){
                    pPlayer.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.EMERALD, itemStackMain.getComponents().get(daiDataComponentTypes.EMERALD_NUM.get())));
                    itemStackMain.set(daiDataComponentTypes.EMERALD_NUM.get(), 0);
                }
                else if(itemStackMain.getComponents().get(daiDataComponentTypes.EMERALD_BLOCK_NUM.get()) > 0){
                    pPlayer.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.EMERALD_BLOCK, itemStackMain.getComponents().get(daiDataComponentTypes.EMERALD_BLOCK_NUM.get())));
                    itemStackMain.set(daiDataComponentTypes.EMERALD_BLOCK_NUM.get(), 0);
                }
            }
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
        if(!pStack.getComponents().has(daiDataComponentTypes.EMERALD_NUM.get())) pStack.set(daiDataComponentTypes.EMERALD_NUM, 0);
        else pTooltipComponents.add(Component.translatable("emerald_num").append(Component.literal(String.valueOf(pStack.getComponents().get(daiDataComponentTypes.EMERALD_NUM.get())))));
        if(!pStack.getComponents().has(daiDataComponentTypes.EMERALD_BLOCK_NUM.get())) pStack.set(daiDataComponentTypes.EMERALD_BLOCK_NUM, 0);
        else pTooltipComponents.add(Component.translatable("emerald_block_num").append(Component.literal(String.valueOf(pStack.getComponents().get(daiDataComponentTypes.EMERALD_BLOCK_NUM.get())))));
    }
}