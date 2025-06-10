package net.Gmaj7.funny_world.daiItems.custom;

import net.Gmaj7.funny_world.daiInit.daiDataComponentTypes;
import net.Gmaj7.funny_world.daiInit.daiPackets;
import net.Gmaj7.funny_world.daiInit.daiUniqueData.HumanitySet;
import net.Gmaj7.funny_world.daiInit.daiUniqueData.daiUniqueDataGet;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

public class HumanityPocket extends Item {
    public HumanityPocket(Properties properties) {
        super(properties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 36000;
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if(livingEntity instanceof Player player && !level.isClientSide()){
            int humanityStack = stack.get(daiDataComponentTypes.HUMANITY);
            HumanitySet humanityPlayer = ((daiUniqueDataGet)player).getHumanitySet();
            int num = humanityPlayer.getHumanity();
            if(!player.isShiftKeyDown() && humanityStack > 0){
                stack.set(daiDataComponentTypes.HUMANITY, humanityStack - 1);
                humanityPlayer.setHumanity(humanityPlayer.getHumanity() + 1);
                PacketDistributor.sendToAllPlayers(new daiPackets.daiHumanityPacket(num + 1));
            }
            else if (player.isShiftKeyDown()){
                stack.set(daiDataComponentTypes.HUMANITY, humanityStack + 1);
                humanityPlayer.setHumanity(humanityPlayer.getHumanity() - 1);
                PacketDistributor.sendToAllPlayers(new daiPackets.daiHumanityPacket(num - 1));
            }
            else player.stopUsingItem();
        }
        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);
        player.startUsingItem(usedHand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.translatable("humanity").append(String.valueOf(stack.get(daiDataComponentTypes.HUMANITY))));
    }
}
