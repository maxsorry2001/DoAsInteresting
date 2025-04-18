package net.Gmaj7.funny_world.mixin;

import net.Gmaj7.funny_world.daiEnchantments.daiEnchantments;
import net.Gmaj7.funny_world.daiInit.daiFunctions;
import net.Gmaj7.funny_world.daiItems.daiItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(BowItem.class)
public abstract class BowMixin extends ProjectileWeaponItem {
    public BowMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "releaseUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    public void releaseUsingMixin(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft, CallbackInfo ci, Player player, ItemStack itemstack){
        if(itemstack.isEmpty() && EnchantmentHelper.getEnchantmentLevel(daiFunctions.getHolder(level, Registries.ENCHANTMENT, daiEnchantments.SACRIFICE_ARROWS), player) > 0){
            int i = this.getUseDuration(stack, entityLiving) - timeLeft;
            if (i < 0) ci.cancel();
            LivingEntity livingEntity = player.level().getNearestEntity(LivingEntity.class, TargetingConditions.forNonCombat().range(6), player, player.getX(), player.getY(), player.getZ(), player.getBoundingBox().inflate(6));
            if (livingEntity != null) {
                ItemStack ammo = new ItemStack(daiItems.ENTITIES_ARROW.get());
                List<ItemStack> list = draw(stack, ammo, player);
                if (level instanceof ServerLevel serverlevel && !list.isEmpty()) {
                    this.shoot(serverlevel, player, player.getUsedItemHand(), stack, list, 2.0F, 1.0F, false, null);
                }
                livingEntity.hurt(new DamageSource(daiFunctions.getHolder(level, Registries.DAMAGE_TYPE, DamageTypes.GENERIC_KILL), player), livingEntity.getHealth());
                level.playSound(
                        null,
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        SoundEvents.ARROW_SHOOT,
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) +  0.5F
                );
                player.awardStat(Stats.ITEM_USED.get(this));
                if(!player.isCreative())
                    stack.hurtAndBreak(5, player, player.getEquipmentSlotForItem(stack));
            }
        }
    }
}
