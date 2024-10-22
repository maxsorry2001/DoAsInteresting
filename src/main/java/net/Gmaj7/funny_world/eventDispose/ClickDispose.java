package net.Gmaj7.funny_world.eventDispose;

import net.Gmaj7.funny_world.FunnyWorld;
import net.Gmaj7.funny_world.daiEffects.daiMobEffects;
import net.Gmaj7.funny_world.daiEnchantments.daiEnchantments;
import net.Gmaj7.funny_world.daiEntities.custom.BrickEntity;
import net.Gmaj7.funny_world.daiEntities.custom.NetherBrickEntity;
import net.Gmaj7.funny_world.daiInit.daiDataComponentTypes;
import net.Gmaj7.funny_world.daiInit.daiFunctions;
import net.Gmaj7.funny_world.daiInit.daiTiers;
import net.Gmaj7.funny_world.daiItems.daiItems;
import net.Gmaj7.funny_world.villager.daiVillagers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.gossip.GossipType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.List;
import java.util.Random;

@EventBusSubscriber(modid = FunnyWorld.MODID)
public class ClickDispose {

    @SubscribeEvent
    public static void entityDispose(PlayerInteractEvent.EntityInteractSpecific event){
        Player player = event.getEntity();
        Entity target = event.getTarget();
        InteractionHand hand = event.getHand();
        if(!player.level().isClientSide()){
            int i = player.getItemInHand(hand).getEnchantmentLevel(daiFunctions.getHolder(player.level(), daiEnchantments.SHIELD_STRIKE));
            if(i > 0 && !player.getCooldowns().isOnCooldown(player.getItemInHand(hand).getItem())){
                if(target instanceof LivingEntity){
                    List<LivingEntity> list = target.level().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(1, 1, 1));
                    for (LivingEntity livingEntity : list){
                        if(livingEntity == player) continue;
                        livingEntity.knockback(i, - player.getForward().x(), - player.getForward().z());
                        livingEntity.hurt(new DamageSource(player.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.GENERIC), player) , 3 * i);
                    }
                    player.moveTo(new Vec3(target.getBoundingBox().getCenter().x(), target.getY(), target.getBoundingBox().getCenter().z()));
                    player.getItemInHand(hand).hurtAndBreak(1, player, hand == InteractionHand.MAIN_HAND? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
                    player.level().playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.IRON_GOLEM_DAMAGE, SoundSource.PLAYERS, 0.5F, 0.4F / (player.level().getRandom().nextFloat()*0.4F + 0.8F));
                }
            }
            if(hand == InteractionHand.MAIN_HAND && player.getItemInHand(hand).getEnchantmentLevel(daiFunctions.getHolder(player.level(), daiEnchantments.PROBATION)) > 0 && target.getType().is(EntityTypeTags.ILLAGER)){
                Villager villager = new Villager(EntityType.VILLAGER, player.level(), daiVillagers.ILLAGER_CHANGE.get());
                villager.teleportTo(target.getX(), target.getY(), target.getZ());
                villager.getGossips().add(player.getUUID(), GossipType.MINOR_POSITIVE, 15 + (player.getItemInHand(hand).getEnchantmentLevel(daiFunctions.getHolder(player.level(), daiEnchantments.PROBATION)) - 1) * 5);
                villager.getGossips().add(player.getUUID(), GossipType.MAJOR_POSITIVE, 15 + (player.getItemInHand(hand).getEnchantmentLevel(daiFunctions.getHolder(player.level(), daiEnchantments.PROBATION)) - 1) * 5);
                target.remove(Entity.RemovalReason.DISCARDED);
                player.level().addFreshEntity(villager);
            }
        }
        else if(player.level().isClientSide()){
            if(hand == InteractionHand.MAIN_HAND && player.getItemInHand(hand).getEnchantmentLevel(daiFunctions.getHolder(player.level(), daiEnchantments.PROBATION)) > 0 && target.getType().is(EntityTypeTags.ILLAGER)){
                player.swing(hand);
                for (int j = 1; j < 45; j++){
                    double r = j * 2 *Math.PI / 45;
                    player.level().addParticle(ParticleTypes.HAPPY_VILLAGER, target.getX() + Math.sin(r), target.getY() + 1, target.getZ() + Math.cos(r), 0, 0, 0);
                }
            }
        }
    }

    @SubscribeEvent
    public static void RightClickBlock(PlayerInteractEvent.RightClickBlock event){
        Player player = event.getEntity();
        BlockEntity blockEntity = player.level().getBlockEntity(event.getHitVec().getBlockPos());
        EntityDispose.totemChestSummon(player, blockEntity);
        if(EnchantmentHelper.getEnchantmentLevel(player.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(daiEnchantments.ELECTRIFICATION_BY_FRICTION), player) > 0 && event.getHand() == InteractionHand.MAIN_HAND && player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()){
            if(player.level().isClientSide())
                player.swing(InteractionHand.MAIN_HAND);
            else {
                boolean flag = new Random().nextBoolean();
                Item item = daiItems.NEGATIVE_CHARGE.get();
                if(flag) {
                    item = daiItems.ELECTRIC_CHARGE.get();
                    player.addEffect(new MobEffectInstance(daiMobEffects.NEGATIVE_CHARGE, 300));
                }
                else player.addEffect(new MobEffectInstance(daiMobEffects.ELECTRIC_CHARGE, 300));
                ItemEntity itemEntity = new ItemEntity(player.level(), player.getX(), player.getY(), player.getZ(), new ItemStack(item));
                player.level().addFreshEntity(itemEntity);
            }
        }
    }
    @SubscribeEvent
    public static void RightClickItem(PlayerInteractEvent.RightClickItem event){
        Player player = event.getEntity();
        ItemStack itemStackHand = player.getItemInHand(event.getHand());
        if (itemStackHand.is(Items.GOLDEN_SWORD) && EnchantmentHelper.getEnchantmentLevel(player.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(daiEnchantments.ELECTRIFICATION_BY_FRICTION), player) > 0 && !event.getLevel().isClientSide()){
            ItemStack itemStack = new ItemStack(daiItems.THUNDER_SWORD.get());
            EnchantmentHelper.setEnchantments(itemStack, player.getItemInHand(event.getHand()).getAllEnchantments(player.registryAccess().lookupOrThrow(Registries.ENCHANTMENT)));
            player.setItemInHand(event.getHand(), itemStack);
            player.swing(event.getHand());
        }
        if (itemStackHand.getEnchantmentLevel(player.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(daiEnchantments.HEAT_BY_FRICTION)) > 0)
            itemStackHand.set(daiDataComponentTypes.HEAT_BY_FRICTION.get(), 300);
        if (itemStackHand.is(Items.BRICK)){
            ItemStack itemStack = player.getItemInHand(event.getHand()).copy();
            itemStack.setCount(1);
            BrickEntity brickEntity = new BrickEntity(player, player.level(), itemStack);
            int i = player.getItemInHand(event.getHand()).getEnchantmentLevel(player.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(Enchantments.PUNCH));
            if(i > 0) brickEntity.setPunch(i);
            int j = player.getItemInHand(event.getHand()).getEnchantmentLevel(player.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(Enchantments.PIERCING));
            if(j > 0) brickEntity.setPiercing(j);
            int k = player.getItemInHand(event.getHand()).getEnchantmentLevel(player.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(Enchantments.POWER));
            if(j > 0) brickEntity.setHitDamage(k);
            brickEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 1.0F, 0.1F);
            player.level().addFreshEntity(brickEntity);
            if (!player.isCreative()) player.getItemInHand(event.getHand()).shrink(1);
            player.swing(event.getHand());
            player.level().playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS);
        }
        if (itemStackHand.is(Items.NETHER_BRICK)){
            ItemStack itemStack = player.getItemInHand(event.getHand()).copy();
            itemStack.setCount(1);
            NetherBrickEntity brickEntity = new NetherBrickEntity(player, player.level(), itemStack);
            int i = player.getItemInHand(event.getHand()).getEnchantmentLevel(player.level().registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(Enchantments.PUNCH));
            if(i > 0) brickEntity.setPunch(i);
            int j = player.getItemInHand(event.getHand()).getEnchantmentLevel(player.level().registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(Enchantments.PIERCING));
            if(j > 0) brickEntity.setPiercing(j);
            int k = player.getItemInHand(event.getHand()).getEnchantmentLevel(player.level().registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(Enchantments.POWER));
            if(j > 0) brickEntity.setHitDamage(k);
            brickEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 1.5F, 0.1F);
            player.level().addFreshEntity(brickEntity);
            if (!player.isCreative()) player.getItemInHand(event.getHand()).shrink(1);
            player.swing(event.getHand());
            player.level().playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS);
        }
    }

    @SubscribeEvent
    public static void breakBlock(BlockEvent.BreakEvent event){
        Player player = event.getPlayer();
        BlockPos blockPos = event.getPos();
        if(!player.isCreative()){
            if(player.getMainHandItem().getItem() instanceof TieredItem && ((TieredItem) player.getMainHandItem().getItem()).getTier() == daiTiers.JISTGABBURASH){
                event.setCanceled(true);
                getJistgabburash(blockPos, player);
            }
            else {
                int flag = new Random().nextInt(10);
                if(flag == 1){
                    event.setCanceled(true);
                    getJistgabburash(blockPos, player);
                }
            }
        }
        if(player.hasEffect(daiMobEffects.MELTING)){
            event.setCanceled(true);
            player.level().setBlockAndUpdate(blockPos, Blocks.LAVA.defaultBlockState());
        }
    }

    private static void getJistgabburash(BlockPos blockPos, Player player){
        player.level().setBlock(blockPos, Blocks.AIR.defaultBlockState(), 1);
        ItemEntity itemEntity = new ItemEntity(player.level(),blockPos.getX(), blockPos.getY(), blockPos.getZ(), new ItemStack(daiItems.JISTGABBURASH.get()));
        player.level().addFreshEntity(itemEntity);
        player.getMainHandItem().hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
    }
}