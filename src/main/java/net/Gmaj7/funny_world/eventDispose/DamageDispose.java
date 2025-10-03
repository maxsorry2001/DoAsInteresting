package net.Gmaj7.funny_world.eventDispose;

import net.Gmaj7.funny_world.FunnyWorld;
import net.Gmaj7.funny_world.daiEffects.daiMobEffects;
import net.Gmaj7.funny_world.daiEnchantments.daiEnchantments;
import net.Gmaj7.funny_world.daiEntities.custom.BrickEntity;
import net.Gmaj7.funny_world.daiEntities.custom.NetherBrickEntity;
import net.Gmaj7.funny_world.daiInit.*;
import net.Gmaj7.funny_world.daiInit.daiUniqueData.daiUniqueDataGet;
import net.Gmaj7.funny_world.daiItems.daiItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Collection;
import java.util.List;

@EventBusSubscriber(modid = FunnyWorld.MODID)
public class DamageDispose {

    @SubscribeEvent
    public static void damageComingDeal(LivingIncomingDamageEvent event){
        DamageSource damageSource = event.getSource();
        LivingEntity target = event.getEntity();
        Entity source = damageSource.getEntity();
        if(source instanceof Player && ((Player) source).getMainHandItem().getItem() instanceof SwordItem && ((Player) source).getMainHandItem().has(daiDataComponentTypes.SWEEPING_TYPE)){
            Entity hurtTarget = ((daiUniqueDataGet)source).getAttackTarget();
            if(hurtTarget != target) {
                Vec3 distance, from, to;
                int sweeping_type = ((Player) source).getMainHandItem().get(daiDataComponentTypes.SWEEPING_TYPE);
                switch (sweeping_type){
                    case 0 -> {
                        distance = new Vec3(hurtTarget.getX() - source.getX(), hurtTarget.getY(), hurtTarget.getZ() - source.getZ()).multiply(100, 1, 100);
                        from = new Vec3(hurtTarget.getX() - distance.z(), hurtTarget.getY(), hurtTarget.getZ() + distance.x());
                        to = new Vec3(hurtTarget.getX() + distance.z(), hurtTarget.getY(), hurtTarget.getZ() - distance.x());
                    }
                    default -> {
                        from = new Vec3(1, 1, 1);
                        to = new Vec3(1, 1, 1);
                    }
                }
                //Vec3 start = new Vec3(source.getX(), source.getY(), source.getZ()),
                //        end = new Vec3(hurtTarget.getX(), hurtTarget.getY(), hurtTarget.getZ()),
                //        distance = new Vec3(end.x() - start.x(), end.y(), end.z() - start.z()).multiply(100, 1, 100),
                //        from = new Vec3(end.x() - distance.z(), end.y(), end.z() + distance.x()),
                //        to = new Vec3(end.x() + distance.z(), end.y(), end.z() - distance.x());
                if (target.getBoundingBox().inflate(1.5).clip(from, to).isEmpty()) {
                    event.setCanceled(true);
                }
            }
        }
    }

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
                    if(livingEntity.getMainHandItem().getEnchantmentLevel(daiFunctions.getHolder(livingEntity.level(), Registries.ENCHANTMENT, daiEnchantments.CONVINCE_PEOPLE_BY_REASON)) > 0)
                        damageMul = damageMul * livingEntity.getMainHandItem().getEnchantmentLevel(daiFunctions.getHolder(livingEntity.level(), Registries.ENCHANTMENT, daiEnchantments.CONVINCE_PEOPLE_BY_REASON));
                    if (livingEntity.getMainHandItem().getEnchantmentLevel(daiFunctions.getHolder(livingEntity.level(), Registries.ENCHANTMENT, daiEnchantments.CONVINCE_PEOPLE_BY_REASON)) > 0){
                        damageAdd += livingEntity.getMainHandItem().getCount();
                        damageMul = damageMul * livingEntity.getMainHandItem().getEnchantmentLevel(daiFunctions.getHolder(livingEntity.level(), Registries.ENCHANTMENT, daiEnchantments.CONVINCE_PEOPLE_BY_REASON));
                    }
                    event.setNewDamage((event.getNewDamage() + damageAdd) * damageMul);
                }
            }
            if(source instanceof Player player){
                int humanity = ((daiUniqueDataGet)player).getHumanitySet().getHumanity();
                if(humanity > 50)
                    event.setNewDamage(event.getNewDamage() * (1 + 0.25F * (humanity - 25) / 25));
            }
            if(target instanceof Player player){
                int humanity = ((daiUniqueDataGet)player).getHumanitySet().getHumanity();
                if(humanity > 50) event.setNewDamage((float) (event.getNewDamage() * Math.pow (0.9, (humanity - 25) / 25)));

            }
            if(damageSource.is(DamageTypeTags.IS_EXPLOSION) && target.getItemBySlot(EquipmentSlot.CHEST).getEnchantmentLevel(daiFunctions.getHolder(target.level(), Registries.ENCHANTMENT, daiEnchantments.EXPLOSION_GET)) > 0)
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

    @SubscribeEvent
    public static void deathDeal(LivingDeathEvent event){
        LivingEntity livingEntity = event.getEntity();
        Entity source = event.getSource().getEntity();
        if(livingEntity.getItemBySlot(EquipmentSlot.HEAD).is(daiItems.BELL_HELMET.get())){
            livingEntity.level().addFreshEntity(new ItemEntity(livingEntity.level(), livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), new ItemStack(daiItems.BELL_HELMET.get())));
        }
        if(source instanceof Player && !source.level().isClientSide()) {
            if(livingEntity instanceof Enemy) {
                ((daiUniqueDataGet) source).getHumanitySet().addHumanity();
                PacketDistributor.sendToAllPlayers(new daiPackets.daiHumanityPacket(((daiUniqueDataGet) source).getHumanitySet().getHumanity()));
            }
            else {
                ((daiUniqueDataGet) source).getHumanitySet().decreaseHumanity();
                PacketDistributor.sendToAllPlayers(new daiPackets.daiHumanityPacket(((daiUniqueDataGet) source).getHumanitySet().getHumanity()));
            }
            if(((Player) source).hasEffect(daiMobEffects.LAVA_CHICKEN_POWER)){
                Chicken chicken = EntityType.CHICKEN.create(source.level());
                if(chicken.isBaby()) chicken.setBaby(false);
                chicken.teleportTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
                chicken.igniteForSeconds(20);
                source.level().addFreshEntity(chicken);
            }
        }
        if(livingEntity instanceof Chicken && event.getSource().is(DamageTypes.LAVA)){
            List<ItemEntity> list = livingEntity.level().getEntitiesOfClass(ItemEntity.class, livingEntity.getBoundingBox().inflate(2));
            for (ItemEntity itemEntity : list){
                if(itemEntity.getItem().is(Items.NETHERITE_INGOT)){
                    ItemStack itemStack = new ItemStack(daiItems.LAVA_CHICKEN_INGOT.get(), itemEntity.getItem().getCount());
                    itemEntity.setItem(itemStack);
                }
            }
        }
    }

    @SubscribeEvent
    public static void dropDeal(LivingDropsEvent event){
        Entity source = event.getSource().getEntity();
        if(source instanceof Player && ((daiUniqueDataGet)source).getHumanitySet().getHumanity() < 0){
            Collection<ItemEntity> drops = event.getDrops();
            if(!drops.isEmpty()) {
                int a = (-((daiUniqueDataGet) source).getHumanitySet().getHumanity() + 50) / 50;
                for (int i = 0; i < a; i++)
                    for (ItemEntity item : drops)
                        source.level().addFreshEntity(item.copy());
            }
        }
    }
}