package net.Gmaj7.funny_world.eventDispose;

import net.Gmaj7.funny_world.FunnyWorld;
import net.Gmaj7.funny_world.daiBlocks.blockEntity.HoneyFloorBlockEntity;
import net.Gmaj7.funny_world.daiBlocks.daiBlocks;
import net.Gmaj7.funny_world.daiEffects.daiMobEffects;
import net.Gmaj7.funny_world.daiEnchantments.daiEnchantments;
import net.Gmaj7.funny_world.daiEntities.custom.BrickEntity;
import net.Gmaj7.funny_world.daiEntities.custom.EntitiesArrowEntity;
import net.Gmaj7.funny_world.daiEntities.custom.NetherBrickEntity;
import net.Gmaj7.funny_world.daiInit.*;
import net.Gmaj7.funny_world.daiInit.daiUniqueData.daiUniqueDataGet;
import net.Gmaj7.funny_world.daiItems.daiFoods;
import net.Gmaj7.funny_world.daiItems.daiItems;
import net.Gmaj7.funny_world.villager.daiVillagers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.gossip.GossipType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
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
        ItemStack handStack = player.getItemInHand(hand);
        if(!player.level().isClientSide()){
            int i = handStack.getEnchantmentLevel(daiFunctions.getHolder(player.level(), Registries.ENCHANTMENT, daiEnchantments.SHIELD_STRIKE));
            if(i > 0 && !player.getCooldowns().isOnCooldown(handStack.getItem())){
                if(target instanceof LivingEntity){
                    List<LivingEntity> list = target.level().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(1, 1, 1));
                    for (LivingEntity livingEntity : list){
                        if(livingEntity == player) continue;
                        livingEntity.knockback(i, - player.getForward().x(), - player.getForward().z());
                        livingEntity.hurt(new DamageSource(player.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.GENERIC), player) , 3 * i);
                    }
                    player.moveTo(new Vec3(target.getBoundingBox().getCenter().x(), target.getY(), target.getBoundingBox().getCenter().z()));
                    handStack.hurtAndBreak(1, player, hand == InteractionHand.MAIN_HAND? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
                    player.level().playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.IRON_GOLEM_DAMAGE, SoundSource.PLAYERS, 0.5F, 0.4F / (player.level().getRandom().nextFloat()*0.4F + 0.8F));
                }
            }
            if(hand == InteractionHand.MAIN_HAND && handStack.getEnchantmentLevel(daiFunctions.getHolder(player.level(), Registries.ENCHANTMENT, daiEnchantments.PROBATION)) > 0 && target.getType().is(EntityTypeTags.ILLAGER)){
                Villager villager = new Villager(EntityType.VILLAGER, player.level(), daiVillagers.ILLAGER_CHANGE.get());
                villager.teleportTo(target.getX(), target.getY(), target.getZ());
                villager.getGossips().add(player.getUUID(), GossipType.MINOR_POSITIVE, 15 + (handStack.getEnchantmentLevel(daiFunctions.getHolder(player.level(), Registries.ENCHANTMENT, daiEnchantments.PROBATION)) - 1) * 5);
                villager.getGossips().add(player.getUUID(), GossipType.MAJOR_POSITIVE, 15 + (handStack.getEnchantmentLevel(daiFunctions.getHolder(player.level(), Registries.ENCHANTMENT, daiEnchantments.PROBATION)) - 1) * 5);
                target.remove(Entity.RemovalReason.DISCARDED);
                player.level().addFreshEntity(villager);
            }
        }
        else if(player.level().isClientSide()){
            if(hand == InteractionHand.MAIN_HAND && player.getItemInHand(hand).getEnchantmentLevel(daiFunctions.getHolder(player.level(), Registries.ENCHANTMENT, daiEnchantments.PROBATION)) > 0 && target.getType().is(EntityTypeTags.ILLAGER)){
                player.swing(hand);
                for (int j = 1; j < 45; j++){
                    double r = j * 2 *Math.PI / 45;
                    player.level().addParticle(ParticleTypes.HAPPY_VILLAGER, target.getX() + Math.sin(r), target.getY() + 1, target.getZ() + Math.cos(r), 0, 0, 0);
                }
            }
        }
        if (handStack.is(daiItems.BELL_HELMET.get()) && target instanceof Enemy){
            ItemStack itemStack = handStack.copy();
            if(!player.isCreative()) handStack.shrink(1);
            ((LivingEntity) target).setItemSlot(EquipmentSlot.HEAD, itemStack);
            event.setCanceled(true);
        }
        if(handStack.is(daiItems.CONVEX.get()) && target instanceof LivingEntity){
            if (target.isCurrentlyGlowing()) {
                Vec3 vec3 = new Vec3(player.getX() - target.getX(), player.getY() - target.getY(), player.getZ() - target.getZ());
                Vec3 vec31 = vec3;
                double u = vec3.length();
                double f = 1.5;
                if (u > f) {
                    if (u < 1.8) u = 1.8;
                    vec31.scale(f / (u - f));
                    float scaleNum = (float) (f / (u - f));
                    if(target.hasData(daiAttachmentTypes.RENDER_SCALE)) scaleNum = scaleNum * target.getData(daiAttachmentTypes.RENDER_SCALE);
                    target.setData(daiAttachmentTypes.RENDER_SCALE, scaleNum);
                    if(target.hasData(daiAttachmentTypes.RENDER_UP_DOWN)) target.setData(daiAttachmentTypes.RENDER_UP_DOWN, !target.getData(daiAttachmentTypes.RENDER_UP_DOWN));
                    else target.setData(daiAttachmentTypes.RENDER_UP_DOWN, true);
                    target.teleportTo(player.getX() + vec31.x(), vec31.y() < 0 ? player.getY() : player.getY() + vec31.y(), player.getZ() + vec31.z());
                }
            }
            else if (target.level().isDay() && !target.isCurrentlyGlowing()) ((LivingEntity) target).addEffect(new MobEffectInstance(MobEffects.GLOWING, 500));
                player.swing(hand);
            event.setCanceled(true);
        }
        if(handStack.isEmpty() && target instanceof LivingEntity){
            EntitiesArrowEntity entitiesArrowEntity = new EntitiesArrowEntity(player.level(), player);
            entitiesArrowEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 1, 1);
            entitiesArrowEntity.setEntityHealth(((LivingEntity) target).getHealth());
            player.level().addFreshEntity(entitiesArrowEntity);
        }
    }

    @SubscribeEvent
    public static void RightClickBlock(PlayerInteractEvent.RightClickBlock event){
        Player player = event.getEntity();
        BlockPos blockPos = event.getHitVec().getBlockPos();
        BlockEntity blockEntity = player.level().getBlockEntity(blockPos);
        BlockState blockState = player.level().getBlockState(blockPos);
        EntityDispose.totemChestSummon(player, blockEntity);
        ItemStack mainHandItem = player.getItemInHand(InteractionHand.MAIN_HAND);
        if(EnchantmentHelper.getEnchantmentLevel(daiFunctions.getHolder(player.level(), Registries.ENCHANTMENT, daiEnchantments.ELECTRIFICATION_BY_FRICTION), player) > 0 && event.getHand() == InteractionHand.MAIN_HAND && mainHandItem.isEmpty()){
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
        int EOW = EnchantmentHelper.getEnchantmentLevel(daiFunctions.getHolder(player.level(), Registries.ENCHANTMENT, daiEnchantments.EATER_OF_WORLDS), player);
        int hunger = (int) Math.ceil((double) player.getFoodData().getFoodLevel() / 5);
        if(EOW > 0 && player.getMainHandItem().isEmpty()) {
            ItemStack itemStack = new ItemStack(blockState.getBlock().asItem());
            switch (EOW){
                case 1 -> itemStack.set(DataComponents.FOOD, daiFoods.EAT_OF_WORLDS_LV1);
                case 2 -> itemStack.set(DataComponents.FOOD, daiFoods.EAT_OF_WORLDS_LV2);
                case 3 -> itemStack.set(DataComponents.FOOD, daiFoods.EAT_OF_WORLDS_LV3);
            }
            switch (hunger) {
                case 0 -> {
                    player.level().setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
                    ItemEntity itemEntity = new ItemEntity(player.level(), player.getX(), player.getY(), player.getZ(), itemStack);
                    player.level().addFreshEntity(itemEntity);
                    player.swing(InteractionHand.MAIN_HAND);
                }
                case 1 -> {
                    if(blockState.getBlock().defaultDestroyTime() > 0){
                        player.level().setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
                        ItemEntity itemEntity = new ItemEntity(player.level(), player.getX(), player.getY(), player.getZ(), itemStack);
                        player.level().addFreshEntity(itemEntity);
                        player.swing(InteractionHand.MAIN_HAND);
                    }
                }
                case 2 -> {
                    if(blockState.getBlock().defaultDestroyTime() > 0 && !blockState.is(BlockTags.NEEDS_DIAMOND_TOOL)){
                        player.level().setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
                        ItemEntity itemEntity = new ItemEntity(player.level(), player.getX(), player.getY(), player.getZ(), itemStack);
                        player.level().addFreshEntity(itemEntity);
                        player.swing(InteractionHand.MAIN_HAND);
                    }
                }
                case 3 -> {
                    if(blockState.getBlock().defaultDestroyTime() > 0 && !blockState.is(BlockTags.NEEDS_DIAMOND_TOOL) && !blockState.is(BlockTags.NEEDS_IRON_TOOL)){
                        player.level().setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
                        ItemEntity itemEntity = new ItemEntity(player.level(), player.getX(), player.getY(), player.getZ(), itemStack);
                        player.level().addFreshEntity(itemEntity);
                        player.swing(InteractionHand.MAIN_HAND);
                    }
                }
                case 4 -> {
                    if(blockState.getBlock().defaultDestroyTime() > 0 && !blockState.is(BlockTags.NEEDS_DIAMOND_TOOL) && !blockState.is(BlockTags.NEEDS_IRON_TOOL) && !blockState.is(BlockTags.NEEDS_STONE_TOOL)){
                        player.level().setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
                        ItemEntity itemEntity = new ItemEntity(player.level(), player.getX(), player.getY(), player.getZ(), itemStack);
                        player.level().addFreshEntity(itemEntity);
                        player.swing(InteractionHand.MAIN_HAND);
                    }
                }
                default -> {
                }
            }
        }
        if(event.getHand() == InteractionHand.MAIN_HAND && mainHandItem.is(Items.HONEY_BOTTLE) && !((daiUniqueDataGet)blockState.getBlock()).isBlockUsePass(blockState, player.level(), blockPos, player, event.getHitVec())){
            if(player.level().isClientSide()) {
                player.swing(InteractionHand.MAIN_HAND);
                player.playSound(SoundType.HONEY_BLOCK.getPlaceSound());
            }
            else {
                BlockState honey_floor = daiBlocks.HONEY_FLOOR.get().defaultBlockState();
                player.level().setBlockAndUpdate(blockPos.above(), honey_floor);
                BlockEntity blockEntity1 = player.level().getBlockEntity(blockPos.above());
                if(blockEntity1 instanceof HoneyFloorBlockEntity honeyFloorBlockEntity)
                    honeyFloorBlockEntity.setHoney_bottle(mainHandItem.copyWithCount(1));
                if(!player.isCreative()) mainHandItem.shrink(1);
            }
        }
    }
    @SubscribeEvent
    public static void RightClickItem(PlayerInteractEvent.RightClickItem event){
        Player player = event.getEntity();
        ItemStack itemStackHand = player.getItemInHand(event.getHand());
        if (itemStackHand.is(Items.BOW) && EnchantmentHelper.getEnchantmentLevel(daiFunctions.getHolder(player.level(), Registries.ENCHANTMENT, daiEnchantments.ENTITY_ARROWS), player) > 0){
            LivingEntity livingEntity = player.level().getNearestEntity(LivingEntity.class, TargetingConditions.forNonCombat().range(6), player, player.getX(), player.getY(), player.getZ(), player.getBoundingBox().inflate(6));
            if(livingEntity != null && livingEntity != player) player.startUsingItem(event.getHand());
        }
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