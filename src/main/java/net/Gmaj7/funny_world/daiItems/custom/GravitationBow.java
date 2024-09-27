package net.Gmaj7.funny_world.daiItems.custom;

import net.Gmaj7.funny_world.daiInit.daiFunctions;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class GravitationBow extends Item {
    public static final int MAX_DRAW_DURATION = 20;
    public static final int DEFAULT_RANGE = 15;
    public GravitationBow(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        if(pLivingEntity instanceof Player pPlayer){
            List<Entity> list = pLevel.getEntitiesOfClass(Entity.class, pPlayer.getBoundingBox().inflate(6));
            for(Entity target : list){
                if(target == pPlayer) continue;
                Vec3 vec3 = new Vec3(target.getX() - pPlayer.getX(), target.getY() - pPlayer.getY(), target.getZ() - pPlayer.getZ()).normalize();
                int i = this.getUseDuration(pStack, pLivingEntity) - pTimeCharged;
                float f = getPowerForTime(i) + pStack.getEnchantmentLevel(daiFunctions.getHolder(pLevel, Enchantments.PUNCH));
                target.setDeltaMovement(vec3.x() * 5 * f, vec3.y() * 5 * f, vec3.z() * 5 * f);
                int k = pStack.getEnchantmentLevel(daiFunctions.getHolder(pLevel, Enchantments.FLAME));
                if(k > 0 && target instanceof LivingEntity) target.igniteForSeconds(k * 5);
                int j = pStack.getEnchantmentLevel(daiFunctions.getHolder(pLevel, Enchantments.POWER));
                if(j > 0) target.hurt(new DamageSource(pLevel.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.PLAYER_ATTACK), pPlayer), j);
                pStack.hurtAndBreak(1, pPlayer, Player.getSlotForHand(pPlayer.getUsedItemHand()));
            }
            pLevel.playSound(pPlayer, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS);
            super.releaseUsing(pStack, pLevel, pLivingEntity, pTimeCharged);
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
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration);
        if(pLivingEntity instanceof Player pPlayer){
            List<Entity> list = pLevel.getEntitiesOfClass(Entity.class, pPlayer.getBoundingBox().inflate(6));
            for(Entity target : list){
                if(target == pPlayer) continue;
                Vec3 vec3 = new Vec3(pPlayer.getX() - target.getX(), pPlayer.getY() - target.getY(), pPlayer.getZ() - target.getZ());
                Vec3 vec31 = vec3;
                if(vec3.length() > 1) vec31 = vec31.normalize();
                target.setDeltaMovement(vec31.x() / 5, vec31.y() / 5, vec31.z() / 5);
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        pPlayer.startUsingItem(pHand);
        return InteractionResultHolder.consume(itemstack);
    }
}
