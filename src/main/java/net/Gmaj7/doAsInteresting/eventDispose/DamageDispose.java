package net.Gmaj7.doAsInteresting.eventDispose;

import net.Gmaj7.doAsInteresting.DoAsInteresting;
import net.Gmaj7.doAsInteresting.daiEffects.daiMobEffects;
import net.Gmaj7.doAsInteresting.daiEnchantments.daiEnchantments;
import net.Gmaj7.doAsInteresting.daiInit.daiTiers;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.TieredItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingAttackEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = DoAsInteresting.MODID)
public class DamageDispose {
    @SubscribeEvent
    public static void attackDeal(LivingAttackEvent event){
        Entity direct = event.getSource().getDirectEntity();
        LivingEntity target = event.getEntity();
        if(direct instanceof LivingEntity && ((LivingEntity) direct).getMainHandItem().getItem() instanceof TieredItem && ((TieredItem) ((LivingEntity) direct).getMainHandItem().getItem()).getTier() == daiTiers.JISTGABBURASH)
            target.addEffect(new MobEffectInstance(daiMobEffects.IIIIII, 300));
    }

    @SubscribeEvent
    public static void damageDeal(LivingDamageEvent event){
        DamageSource damageSource = event.getSource();
        LivingEntity target = event.getEntity();
        if(!target.level().isClientSide()){
            if(damageSource.is(DamageTypeTags.IS_EXPLOSION) && target.getItemBySlot(EquipmentSlot.CHEST).getEnchantmentLevel(daiEnchantments.EXPLOSION_GET.get()) > 0)
                event.setCanceled(true);
        }
    }
}