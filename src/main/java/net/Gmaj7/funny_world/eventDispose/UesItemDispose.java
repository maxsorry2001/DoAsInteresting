package net.Gmaj7.funny_world.eventDispose;

import net.Gmaj7.funny_world.FunnyWorld;
import net.Gmaj7.funny_world.daiEnchantments.daiEnchantments;
import net.Gmaj7.funny_world.daiInit.daiDataComponentTypes;
import net.Gmaj7.funny_world.daiInit.daiFunctions;
import net.Gmaj7.funny_world.daiInit.daiHoneyEffects;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;

import java.util.Collection;
import java.util.List;

@EventBusSubscriber(modid = FunnyWorld.MODID)
public class UesItemDispose {

    @SubscribeEvent
    public static void stop(LivingEntityUseItemEvent.Stop event){
        ItemStack itemStack = event.getItem();
        LivingEntity livingEntity = event.getEntity();
        Collection<MobEffectInstance> effects = livingEntity.getActiveEffects();
        if(itemStack.is(Items.HONEY_BOTTLE) && EnchantmentHelper.getEnchantmentLevel(daiFunctions.getHolder(livingEntity.level(), Registries.ENCHANTMENT, daiEnchantments.ABSORB_HONEY), livingEntity) > 0 && livingEntity.isShiftKeyDown()){
            for (MobEffectInstance mobEffectInstance : effects) {
                if (!itemStack.has(daiDataComponentTypes.HONEY_EFFECTS))
                    itemStack.set(daiDataComponentTypes.HONEY_EFFECTS, daiHoneyEffects.EMPTY);
                if (!mobEffectInstance.is(MobEffects.POISON))
                    itemStack.get(daiDataComponentTypes.HONEY_EFFECTS).effects().add(new daiHoneyEffects.Entry(mobEffectInstance.getEffect(), mobEffectInstance.getDuration(), mobEffectInstance.getAmplifier()));
            }
            livingEntity.removeAllEffects();
        }
    }

    @SubscribeEvent
    public static void finish(LivingEntityUseItemEvent.Finish event){
        ItemStack itemStack = event.getItem();
        LivingEntity livingEntity = event.getEntity();
        if(itemStack.is(Items.HONEY_BOTTLE) && itemStack.has(daiDataComponentTypes.HONEY_EFFECTS)){
            List<daiHoneyEffects.Entry> list = itemStack.get(daiDataComponentTypes.HONEY_EFFECTS).effects();
            if(!list.isEmpty()){
                for (daiHoneyEffects.Entry entry : list){
                    livingEntity.addEffect(new MobEffectInstance(entry.effect(), entry.duration(), entry.effectLevel()));
                }
            }
        }
    }
}
