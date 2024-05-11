package net.Gmaj7.doAsInteresting.eventDispose;

import net.Gmaj7.doAsInteresting.DoAsInteresting;
import net.Gmaj7.doAsInteresting.daiEnchantments.daiEnchantments;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = DoAsInteresting.MODID)
public class DamageDispose {

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