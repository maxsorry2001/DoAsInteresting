package net.Gmaj7.funny_world.daiItems.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        livingEntity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 100, 0));
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
                return HumanoidModel.ArmPose.valueOf("FUNNY_WORLD_SIDEWAYS_BOW");
        }

        @Override
        public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack stack, float partialTicks, float equipProcess, float swingProcess) {
            int k = arm == HumanoidArm.RIGHT ? 1 : -1;
            if(player.isUsingItem() && player.getUseItem().is(stack.getItem())) {
                poseStack.translate((float) k * 0.56F, -0.52F + equipProcess * -0.6F, -0.72F);
                poseStack.translate((float) k * -0.3985682F, 0.18344387F, 0.15731531F);
                poseStack.mulPose(Axis.XP.rotationDegrees(0F));
                poseStack.mulPose(Axis.YP.rotationDegrees(k * -22.5F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(k * 90F));
            }
            else {
                poseStack.translate((float)k * 0.56F, -0.52F + equipProcess * -0.6F, -0.72F);
                poseStack.translate((float)k * -0.2785682F, 0.18344387F, 0.15731531F);
                poseStack.mulPose(Axis.XP.rotationDegrees(-13.935F));
                poseStack.mulPose(Axis.YP.rotationDegrees((float)k * 35.3F));
                poseStack.mulPose(Axis.ZP.rotationDegrees((float)k * -9.785F));
                float f8 = (float)stack.getUseDuration(player) - ((float)player.getUseItemRemainingTicks() - partialTicks + 1.0F);
                float f12 = f8 / 20.0F;
                f12 = (f12 * f12 + f12 * 2.0F) / 3.0F;
                if (f12 > 1.0F) {
                    f12 = 1.0F;
                }

                poseStack.translate(f12 * 0.0F, f12 * 0.0F, f12 * 0.04F);
                poseStack.scale(1.0F, 1.0F, 1.0F + f12 * 0.2F);
                poseStack.mulPose(Axis.YN.rotationDegrees((float)k * 45.0F));
            }
            return true;
        }
    };
}
