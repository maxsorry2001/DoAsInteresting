package net.Gmaj7.funny_world.daiItems.custom;

import net.Gmaj7.funny_world.daiEntities.custom.ThunderBallEntity;
import net.Gmaj7.funny_world.daiInit.daiAttachmentTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;

public class LightingBoltRod extends Item {
    public LightingBoltRod(Properties pProperties) {
        super(pProperties);
    }

    public static ItemAttributeModifiers createAttributes() {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 2, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -1.9F, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND
                )
                .build();
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        if(pTimeCharged >= 2 && !pLevel.isClientSide() && pLivingEntity instanceof Player){
            ThunderBallEntity thunderBallEntity = new ThunderBallEntity(pLivingEntity.getX(), pLivingEntity.getY() + 1.7, pLivingEntity.getZ(), pLevel);
            thunderBallEntity.setTime(pTimeCharged);
            thunderBallEntity.setOwner(pLivingEntity);
            thunderBallEntity.shootFromRotation(pLivingEntity, pLivingEntity.getXRot(), pLivingEntity.getYRot() + 1F, 0, 2.0F, 0.1F);
            pLevel.addFreshEntity(thunderBallEntity);
            ((Player) pLivingEntity).getCooldowns().addCooldown(pStack.getItem(), 10);
            pStack.hurtAndBreak(1, pLivingEntity, EquipmentSlot.MAINHAND);
        }
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack pStack, LivingEntity pEntity) {
        return 100;
    }
    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration);
        if(pRemainingUseDuration <= 1 && pLivingEntity instanceof Player && !pLevel.isClientSide()){
            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, pLevel);
            lightningBolt.teleportTo(pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ());
            pLevel.addFreshEntity(lightningBolt);
            pLivingEntity.stopUsingItem();
            ((Player) pLivingEntity).getCooldowns().addCooldown(this, 20);
            pStack.hurtAndBreak(1, pLivingEntity, EquipmentSlot.MAINHAND);
        }
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        pPlayer.startUsingItem(pHand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        if (pTarget.hasData(daiAttachmentTypes.THUNDER_HIT)) {
            pTarget.setData(daiAttachmentTypes.THUNDER_HIT, pTarget.getData(daiAttachmentTypes.THUNDER_HIT) + 1);
        }
        else {
            pTarget.setData(daiAttachmentTypes.THUNDER_HIT, 1);
        }
        pStack.hurtAndBreak(1, pTarget, EquipmentSlot.MAINHAND);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }
}
