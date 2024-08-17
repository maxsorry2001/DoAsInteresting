package net.Gmaj7.doAsInteresting.eventDispose;

import net.Gmaj7.doAsInteresting.DoAsInteresting;
import net.Gmaj7.doAsInteresting.daiEffects.daiMobEffects;
import net.Gmaj7.doAsInteresting.daiEnchantments.daiEnchantments;
import net.Gmaj7.doAsInteresting.daiEntities.custom.BrickEntity;
import net.Gmaj7.doAsInteresting.daiEntities.custom.NetherBrickEntity;
import net.Gmaj7.doAsInteresting.daiInit.daiArmorMaterials;
import net.Gmaj7.doAsInteresting.daiInit.daiTiers;
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
        Entity source = damageSource.getEntity();
        if(!target.level().isClientSide()){
            if(damageSource.is(DamageTypeTags.IS_EXPLOSION) && target.getItemBySlot(EquipmentSlot.CHEST).getEnchantmentLevel(daiEnchantments.EXPLOSION_GET.get()) > 0)
                event.setCanceled(true);
            EquipmentSlot[] equipmentSlot = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
            int count = 0, nether_count = 0;
            for (int i = 0; i < equipmentSlot.length; i++){
                if (target.getItemBySlot(equipmentSlot[i]).getItem() instanceof ArmorItem armorItem && armorItem.getMaterial().is(daiArmorMaterials.BRICK))
                    count ++ ;
                if (target.getItemBySlot(equipmentSlot[i]).getItem() instanceof ArmorItem armorItem && armorItem.getMaterial().is(daiArmorMaterials.NETHER_BRICK))
                    nether_count ++ ;
            }
            if (source != null) {
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