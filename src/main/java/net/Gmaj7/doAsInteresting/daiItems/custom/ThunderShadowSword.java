package net.Gmaj7.doAsInteresting.daiItems.custom;

import net.Gmaj7.doAsInteresting.daiEnchantments.daiEnchantments;
import net.Gmaj7.doAsInteresting.daiEntities.custom.ThunderWedgeEntity;
import net.Gmaj7.doAsInteresting.daiInit.daiDataComponentTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class ThunderShadowSword extends Item {
    public ThunderShadowSword(Properties pProperties) {
        super(pProperties);
    }

    public static ItemAttributeModifiers createAttributes() {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 8.0, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(BASE_ATTACK_SPEED_ID,  -2.9F, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                )
                .build();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if(!pLevel.isClientSide()){
            if (pPlayer.isShiftKeyDown()){
                itemStack.remove(daiDataComponentTypes.BLOCKPOS);
                itemStack.remove(daiDataComponentTypes.CANNOT_SHOOT);
            }
            else if(!itemStack.has(daiDataComponentTypes.BLOCKPOS) && !itemStack.has(daiDataComponentTypes.CANNOT_SHOOT)){
                ThunderWedgeEntity thunderWedgeEntity = new ThunderWedgeEntity(pLevel, pPlayer, itemStack);
                thunderWedgeEntity.pickup = AbstractArrow.Pickup.DISALLOWED;
                thunderWedgeEntity.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0, 2.0F, 0.1F);
                pLevel.addFreshEntity(thunderWedgeEntity);
                itemStack.set(daiDataComponentTypes.CANNOT_SHOOT, 1);
                itemStack.hurtAndBreak(1, pPlayer, LivingEntity.getSlotForHand(pUsedHand));
            }
            else if(itemStack.has(daiDataComponentTypes.BLOCKPOS)){
                BlockPos blockPos = itemStack.get(daiDataComponentTypes.BLOCKPOS);
                if(itemStack.get(daiDataComponentTypes.CANNOT_SHOOT) > itemStack.getEnchantmentLevel(pLevel.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(daiEnchantments.FLYING_SHADOWS))){
                    itemStack.remove(daiDataComponentTypes.CANNOT_SHOOT);
                    itemStack.remove(daiDataComponentTypes.BLOCKPOS);
                }
                else {
                    itemStack.set(daiDataComponentTypes.BLOCKPOS, pPlayer.blockPosition());
                    itemStack.set(daiDataComponentTypes.CANNOT_SHOOT, itemStack.get(daiDataComponentTypes.CANNOT_SHOOT) + 1);
                }
                LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, pLevel);
                lightningBolt.teleportTo(pPlayer.getX(), pPlayer.getY(), pPlayer.getZ());
                pPlayer.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 60));
                pPlayer.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100));
                pPlayer.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 100));
                pPlayer.teleportTo(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                pLevel.addFreshEntity(lightningBolt);
                List<LivingEntity> list = pLevel.getEntitiesOfClass(LivingEntity.class, new AABB(blockPos).inflate(4));
                for (LivingEntity target : list){
                    if(target == pPlayer) continue;
                    target.knockback(2, blockPos.getX() - target.getX(), blockPos.getZ() - target.getZ());
                    DamageSource damageSource = new DamageSource(pLevel.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.LIGHTNING_BOLT), pPlayer);
                    target.hurt(damageSource, this.getDamage(itemStack) + this.getAttackDamageBonus(target, this.getDamage(itemStack), damageSource));
                    if (pLevel instanceof ServerLevel)EnchantmentHelper.doPostAttackEffects((ServerLevel) pLevel, target, damageSource);
                    target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100));
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100));
                }
                itemStack.hurtAndBreak(1, pPlayer, LivingEntity.getSlotForHand(pUsedHand));
            }
        }
        else {
            if (itemStack.has(daiDataComponentTypes.BLOCKPOS)){
                BlockPos blockPos = itemStack.get(daiDataComponentTypes.BLOCKPOS);
                pLevel.playSound(null, blockPos, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.NEUTRAL);
                for (int j = 1; j < 135; j++){
                    double r = j % 45 * 2 *Math.PI / 45;
                    float R = 2F + 2F *  j / 135F;
                    float H = 0.5F + 2F * j / 135F;
                    pLevel.addParticle(ParticleTypes.DRAGON_BREATH, blockPos.getX() + R * Math.sin(r), blockPos.getY() + H, blockPos.getZ() + R * Math.cos(r), 0, 0, 0);
                }
            }
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
        if(pStack.has(daiDataComponentTypes.BLOCKPOS)){
            BlockPos blockPos = pStack.get(daiDataComponentTypes.BLOCKPOS);
            pTooltipComponents.add(Component.translatable("pos").append(Component.literal(": " + blockPos.getX() + " " + blockPos.getY() + " " + blockPos.getZ())));
        }
    }

    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return 22;
    }
}
