package net.Gmaj7.funny_world.eventDispose;

import net.Gmaj7.funny_world.FunnyWorld;
import net.Gmaj7.funny_world.daiEffects.daiMobEffects;
import net.Gmaj7.funny_world.daiEnchantments.daiEnchantments;
import net.Gmaj7.funny_world.daiEntities.custom.BrickEntity;
import net.Gmaj7.funny_world.daiEntities.custom.NetherBrickEntity;
import net.Gmaj7.funny_world.daiInit.daiArmorMaterials;
import net.Gmaj7.funny_world.daiInit.daiFunctions;
import net.Gmaj7.funny_world.daiInit.daiTiers;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TieredItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = FunnyWorld.MODID)
public class DamageDispose {
    @SubscribeEvent
    public static void damageDeal(LivingDamageEvent.Pre event){
        DamageSource damageSource = event.getSource();
        LivingEntity target = event.getEntity();
        Entity source = damageSource.getEntity();
        Entity direct = damageSource.getDirectEntity();
        if(!target.level().isClientSide()){
            if(source instanceof LivingEntity livingEntity){
                if(livingEntity.getMainHandItem().getItem() instanceof TieredItem && ((TieredItem) ((LivingEntity) source).getMainHandItem().getItem()).getTier() == daiTiers.JISTGABBURASH)
                    target.addEffect(new MobEffectInstance(daiMobEffects.IIIIII, 300));
                if(direct == source){
                    float damageMul = 1,damageAdd = 0;
                    if(livingEntity.getMainHandItem().getEnchantmentLevel(daiFunctions.getHolder(livingEntity.level(), daiEnchantments.CONVINCE_PEOPLE_BY_REASON)) > 0)
                        damageMul = damageMul * 1.5F;
                    if (livingEntity.getMainHandItem().getEnchantmentLevel(daiFunctions.getHolder(livingEntity.level(), daiEnchantments.CONVINCE_PEOPLE_BY_REASON)) > 0){
                        damageAdd += livingEntity.getMainHandItem().getCount();
                        damageMul = damageMul * livingEntity.getMainHandItem().getEnchantmentLevel(daiFunctions.getHolder(livingEntity.level(), daiEnchantments.CONVINCE_PEOPLE_BY_REASON));
                    }
                    event.setNewDamage((event.getOriginalDamage() + damageAdd) * damageMul);
                }
            }
            if(damageSource.is(DamageTypeTags.IS_EXPLOSION) && target.getItemBySlot(EquipmentSlot.CHEST).getEnchantmentLevel(daiFunctions.getHolder(target.level(), daiEnchantments.EXPLOSION_GET)) > 0)
                event.setNewDamage(0);
            if (source != null) {
                EquipmentSlot[] equipmentSlot = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
                int count = 0, nether_count = 0;
                for (EquipmentSlot slot : equipmentSlot) {
                    if (target.getItemBySlot(slot).getItem() instanceof ArmorItem armorItem && armorItem.getMaterial().is(daiArmorMaterials.BRICK))
                        count++;
                    if (target.getItemBySlot(slot).getItem() instanceof ArmorItem armorItem && armorItem.getMaterial().is(daiArmorMaterials.NETHER_BRICK))
                        nether_count++;
                }
                if(count > 0){
                    BrickEntity brickEntity = new BrickEntity(target.level(), target.getX(), target.getY() + 1, target.getZ(), new ItemStack(Items.BRICK));
                    brickEntity.setOwner(target);
                    brickEntity.setHitDamage(count);
                    brickEntity.setCan_get(false);
                    brickEntity.shoot(source.getX() - target.getX(), source.getY() - target.getY(), source.getZ() - target.getZ(), 3.0F, 0.1F);
                    target.level().addFreshEntity(brickEntity);
                }
                if(nether_count > 0){
                    NetherBrickEntity brickEntity = new NetherBrickEntity(target.level(), target.getX(), target.getY() + 1, target.getZ(), new ItemStack(Items.NETHER_BRICK));
                    brickEntity.setOwner(target);
                    brickEntity.setHitDamage(nether_count);
                    brickEntity.setCan_get(false);
                    brickEntity.shoot(source.getX() - target.getX(), source.getY() - target.getY(), source.getZ() - target.getZ(), 3.0F, 0.1F);
                    target.level().addFreshEntity(brickEntity);
                }
            }
        }
    }
}