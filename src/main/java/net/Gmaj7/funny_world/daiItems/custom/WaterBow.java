package net.Gmaj7.funny_world.daiItems.custom;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.client.IArmPoseTransformer;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

public class WaterBow extends Item {
    public WaterBow(Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 10000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        pPlayer.startUsingItem(pHand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.CUSTOM;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 0XAAAAFF;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        int i = stack.getCapability(Capabilities.FluidHandler.ITEM).getTankCapacity(0);
        return Math.round(13.0F - (2000 - i) * 13.0F / 2000);
    }

    public static IClientItemExtensions iClientItemExtensions = new IClientItemExtensions() {
        @Override
        public HumanoidModel.@Nullable ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
                return HumanoidModel.ArmPose.valueOf("FUNNY_WORLD_ARMPOSE");
        }
    };
}
