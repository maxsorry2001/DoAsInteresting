package net.Gmaj7.funny_world.daiItems.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.Gmaj7.funny_world.daiEffects.daiMobEffects;
import net.Gmaj7.funny_world.daiEntities.custom.WaterBomb;
import net.Gmaj7.funny_world.daiEntities.custom.WaterKnife;
import net.Gmaj7.funny_world.daiInit.daiDataComponentTypes;
import net.Gmaj7.funny_world.daiInit.daiFunctions;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.client.IArmPoseTransformer;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.List;

public class WaterBow extends Item {
    public static final int shootUse = 100;
    public WaterBow(Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 10000;
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        int model = (stack.get(daiDataComponentTypes.WATER_BOW_MODEL) == null || stack.get(daiDataComponentTypes.WATER_BOW_MODEL) > 2) ? 0 : stack.get(daiDataComponentTypes.WATER_BOW_MODEL);
        if(model == 0) {
            WaterKnife waterKnife = new WaterKnife(level, livingEntity, stack);
            waterKnife.shootFromRotation(livingEntity, livingEntity.getXRot(), livingEntity.getYRot(), 0 ,2.0F, 1.0F);
            level.addFreshEntity(waterKnife);
        }
        if(model == 1) {
            WaterBomb waterBomb = new WaterBomb(level, livingEntity, stack);
            waterBomb.shootFromRotation(livingEntity, livingEntity.getXRot(), livingEntity.getYRot(), 0, getPowerForTime(getUseDuration(stack, livingEntity) - timeCharged), 1.0F);
            level.addFreshEntity(waterBomb);
        }
        if(model != 2) {
            IFluidHandler iFluidHandler = stack.getCapability(Capabilities.FluidHandler.ITEM);
            iFluidHandler.drain(new FluidStack(Fluids.WATER, 10), IFluidHandler.FluidAction.EXECUTE);
        }
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if(stack.get(daiDataComponentTypes.WATER_BOW_MODEL) != 2) return;
        Vec3 start = livingEntity.getEyePosition().subtract(0, 0.25, 0);
        Vec3 end = livingEntity.getLookAngle().normalize().scale(10).add(start);
        daiFunctions.RayHitResult result = daiFunctions.getLineHitResult(livingEntity.level(), livingEntity, start, end, false, 0.5F);
        HitResult hitResult = result.getNearest(livingEntity);
        if(hitResult instanceof EntityHitResult){
            Entity entity = ((EntityHitResult) hitResult).getEntity();
            if(entity instanceof LivingEntity && remainingUseDuration % 10 == 0) {
                if(!((LivingEntity) entity).hasEffect(daiMobEffects.DROWN)) ((LivingEntity) entity).addEffect(new MobEffectInstance(daiMobEffects.DROWN, 200), livingEntity);
                else ((LivingEntity) entity).addEffect(new MobEffectInstance(daiMobEffects.DROWN, 200, ((LivingEntity) entity).getEffect(daiMobEffects.DROWN).getAmplifier() + 1), livingEntity);
                if(!level.isClientSide()) ((ServerLevel)level).sendParticles(new DustParticleOptions(new Vector3f((float) 0xAF / 0xFF, (float) 0xEE / 0xFF, (float) 0xEE / 0xFF), 1.0F), livingEntity.getX(), livingEntity.getEyeY(), livingEntity.getZ(), 10, livingEntity.level().random.nextFloat() - 0.5, livingEntity.level().random.nextFloat() - 0.5, livingEntity.level().random.nextFloat() - 0.5, 1.0);
                IFluidHandler iFluidHandler = stack.getCapability(Capabilities.FluidHandler.ITEM);
                iFluidHandler.drain(new FluidStack(Fluids.WATER, 20), IFluidHandler.FluidAction.EXECUTE);
                if(iFluidHandler.getFluidInTank(0).getAmount() < 20) livingEntity.stopUsingItem();
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player pPlayer, InteractionHand pHand) {
        BlockHitResult blockhitresult = getPlayerPOVHitResult(level, pPlayer, ClipContext.Fluid.SOURCE_ONLY);
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if(blockhitresult.getType() == HitResult.Type.MISS || !level.getBlockState(blockhitresult.getBlockPos()).is(Blocks.WATER)){
            if(itemstack.getCapability(Capabilities.FluidHandler.ITEM).getFluidInTank(0).getAmount() > shootUse){
                pPlayer.startUsingItem(pHand);
                return InteractionResultHolder.consume(itemstack);
            }
            else return InteractionResultHolder.fail(itemstack);
        }
        else {
            itemstack.getCapability(Capabilities.FluidHandler.ITEM).fill(new FluidStack(Fluids.WATER, 1000), IFluidHandler.FluidAction.EXECUTE);
            level.setBlockAndUpdate(blockhitresult.getBlockPos(), Blocks.AIR.defaultBlockState());
            pPlayer.playSound(SoundEvents.BUCKET_FILL);
            return InteractionResultHolder.success(itemstack);
        }
    }

    public static float getPowerForTime(int charge) {
        float f = (float)charge / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
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
        int i = stack.getCapability(Capabilities.FluidHandler.ITEM).getFluidInTank(0).getAmount();
        return Math.round(13.0F - (2000 - i) * 13.0F / 2000);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(1, Component.literal(String.valueOf(stack.getCapability(Capabilities.FluidHandler.ITEM).getFluidInTank(0).getAmount()) + "mb / 2000 mb"));
        tooltipComponents.add(2, Component.translatable("model_" + String.valueOf(stack.get(daiDataComponentTypes.WATER_BOW_MODEL))));
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
                int type = stack.get(daiDataComponentTypes.WATER_BOW_MODEL);
                float dx, rz;
                switch (type){
                    case 0 -> {
                        dx = k * -0.5885682F;
                        rz = k * 25F;
                    }
                    case 1 -> {
                        dx = k * -0.5085682F;
                        rz = k * 45F;
                    }
                    default -> {
                        dx = k * -0.4485682F;
                        rz = k * 90;
                    }
                }
                poseStack.translate((float)k * 0.56F, -0.52F + equipProcess * -0.6F, -0.72F);
                poseStack.translate(dx, 0.18344387F, 0.15731531F);
                poseStack.mulPose(Axis.XP.rotationDegrees(0F));
                poseStack.mulPose(Axis.YP.rotationDegrees(k * -20F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(rz));
                float f8 = (float)stack.getUseDuration(player) - ((float)player.getUseItemRemainingTicks() - partialTicks + 1.0F);
                float f12 = f8 / 20.0F;
                f12 = (f12 * f12 + f12 * 2.0F) / 3.0F;
                if (f12 > 1.0F) {
                    f12 = 1.0F;
                }
                if (f12 > 0.1F) {
                    float f15 = Mth.sin((f8 - 0.1F) * 1.3F);
                    float f18 = f12 - 0.1F;
                    float f20 = f15 * f18;
                    poseStack.translate(f20 * 0.0F, f20 * 0.004F, f20 * 0.0F);
                }
                poseStack.translate(f12 * 0.0F, f12 * 0.0F, f12 * 0.04F);
                poseStack.scale(1.0F, 1.0F, 1.0F + f12 * 0.2F);
            }
            else {
                float f5 = -0.4F * Mth.sin(Mth.sqrt(swingProcess) * (float) Math.PI);
                float f6 = 0.2F * Mth.sin(Mth.sqrt(swingProcess) * (float) (Math.PI * 2));
                float f10 = -0.2F * Mth.sin(swingProcess * (float) Math.PI);
                poseStack.translate((float)k * f5, f6, f10);
                poseStack.translate((float)k * 0.56F, -0.52F + equipProcess * -0.6F, -0.72F);
                float f = Mth.sin(swingProcess * swingProcess * (float) Math.PI);
                poseStack.mulPose(Axis.YP.rotationDegrees((float)k * (45.0F + f * -20.0F)));
                float f1 = Mth.sin(Mth.sqrt(swingProcess) * (float) Math.PI);
                poseStack.mulPose(Axis.ZP.rotationDegrees((float)k * f1 * -20.0F));
                poseStack.mulPose(Axis.XP.rotationDegrees(f1 * -80.0F));
                poseStack.mulPose(Axis.YP.rotationDegrees((float)k * -45.0F));
            }
            return true;
        }
    };
}
