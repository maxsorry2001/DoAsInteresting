package net.Gmaj7.doAsInteresting.eventDispose;

import net.Gmaj7.doAsInteresting.DoAsInteresting;
import net.Gmaj7.doAsInteresting.daiEffects.daiMobEffects;
import net.Gmaj7.doAsInteresting.daiEnchantments.daiEnchantments;
import net.Gmaj7.doAsInteresting.daiEntities.custom.BrickEntity;
import net.Gmaj7.doAsInteresting.daiEntities.custom.NetherBrickEntity;
import net.Gmaj7.doAsInteresting.daiInit.daiArmorMaterials;
import net.Gmaj7.doAsInteresting.daiInit.daiDataComponentTypes;
import net.Gmaj7.doAsInteresting.daiInit.daiTiers;
import net.minecraft.core.registries.Registries;
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

@EventBusSubscriber(modid = DoAsInteresting.MODID)
public class DamageDispose {
    @SubscribeEvent
    public static void damageDeal(LivingDamageEvent.Pre event){
        DamageSource damageSource = event.getSource();
        LivingEntity target = event.getEntity();
        Entity source = damageSource.getEntity();
        Entity direct = damageSource.getDirectEntity();
        if(!target.level().isClientSide()){
            if(source instanceof LivingEntity && ((LivingEntity) source).getMainHandItem().getItem() instanceof TieredItem && ((TieredItem) ((LivingEntity) source).getMainHandItem().getItem()).getTier() == daiTiers.JISTGABBURASH)
                target.addEffect(new MobEffectInstance(daiMobEffects.IIIIII, 300));
            if(damageSource.is(DamageTypeTags.IS_EXPLOSION) && target.getItemBySlot(EquipmentSlot.CHEST).getEnchantmentLevel(target.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(daiEnchantments.EXPLOSION_GET)) > 0)
                event.setNewDamage(0);
            if(source instanceof LivingEntity && direct == source && ((LivingEntity) source).getMainHandItem().has(daiDataComponentTypes.HEAT_BY_FRICTION)){
                event.setNewDamage(event.getOriginalDamage() * 1.5F);
            }
            EquipmentSlot[] equipmentSlot = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
            int count = 0, nether_count = 0;
            for (EquipmentSlot slot : equipmentSlot) {
                if (target.getItemBySlot(slot).getItem() instanceof ArmorItem armorItem && armorItem.getMaterial().is(daiArmorMaterials.BRICK))
                    count++;
                if (target.getItemBySlot(slot).getItem() instanceof ArmorItem armorItem && armorItem.getMaterial().is(daiArmorMaterials.NETHER_BRICK))
                    nether_count++;
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