package net.Gmaj7.funny_world.eventDispose;

import net.Gmaj7.funny_world.FunnyWorld;
import net.Gmaj7.funny_world.daiBlocks.daiBlocks;
import net.Gmaj7.funny_world.daiEffects.daiMobEffects;
import net.Gmaj7.funny_world.daiEnchantments.daiEnchantments;
import net.Gmaj7.funny_world.daiFluids.daiFluidTypes;
import net.Gmaj7.funny_world.daiInit.*;
import net.Gmaj7.funny_world.daiInit.daiUniqueData.daiUniqueDataGet;
import net.Gmaj7.funny_world.daiItems.daiItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Random;


@EventBusSubscriber(modid = FunnyWorld.MODID)
public class TickDispose {

    @SubscribeEvent
    public static void EntityTickPreDeal(EntityTickEvent.Pre event){
        Entity target = event.getEntity();
        if(target instanceof Player player){
            int humanity = ((daiUniqueDataGet)player).getHumanitySet().getHumanity();
            if(player.tickCount % 100 == 0){
                if (humanity < 0) {
                    player.hurt(new DamageSource(daiFunctions.getHolder(player.level(), Registries.DAMAGE_TYPE, DamageTypes.OUTSIDE_BORDER)), (0.5F * (-humanity + 25)) / 25);
                    if(!player.hasEffect(MobEffects.RAID_OMEN))
                        player.addEffect(new MobEffectInstance(MobEffects.BAD_OMEN, 400));
                    player.removeEffect(MobEffects.HERO_OF_THE_VILLAGE);
                }
                else if (humanity > 100) {
                    player.heal( (humanity - 75) / 25);
                    player.addEffect(new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, 400, 4));
                    player.removeEffect(MobEffects.BAD_OMEN);
                    player.removeEffect(MobEffects.RAID_OMEN);
                }
            }
        }
        if(target instanceof LivingEntity entity){
            if(entity.isInFluidType(daiFluidTypes.EXTRACTANT_FLUID.get()) && entity.level().getServer() != null & entity.tickCount % 20 == 0){
                if(entity.getType().is(EntityTypeTags.ILLAGER) || entity instanceof Villager){
                    entity.level().addFreshEntity(new ItemEntity(entity.level(), entity.getX(), entity.getY(), entity.getZ(), new ItemStack(Items.EMERALD)));
                }
                else{
                    Player player = entity.level().getNearestPlayer(entity, 30);
                    entity.setLastHurtByPlayer(player);
                    ResourceKey<LootTable> resourcekey = entity.getLootTable();
                    LootTable loottable = entity.level().getServer().reloadableRegistries().getLootTable(resourcekey);
                    LootParams.Builder lootparams$builder = new LootParams.Builder((ServerLevel) entity.level())
                            .withParameter(LootContextParams.THIS_ENTITY, entity)
                            .withParameter(LootContextParams.ORIGIN, entity.position())
                            .withOptionalParameter(LootContextParams.ATTACKING_ENTITY, entity.getLastAttacker())
                            .withOptionalParameter(LootContextParams.DIRECT_ATTACKING_ENTITY, entity.getLastAttacker())
                            .withParameter(LootContextParams.DAMAGE_SOURCE, new DamageSource(daiFunctions.getHolder(entity.level(), Registries.DAMAGE_TYPE, DamageTypes.MAGIC), entity.level().getNearestPlayer(entity, 30)));
                    lootparams$builder = lootparams$builder.withLuck(2048F);
                    LootParams lootparams = lootparams$builder.create(LootContextParamSets.ENTITY);
                    loottable.getRandomItems(lootparams, entity.getLootTableSeed(), entity::spawnAtLocation);
                }
                entity.hurt(new DamageSource(daiFunctions.getHolder(entity.level(), Registries.DAMAGE_TYPE, DamageTypes.MAGIC)), entity.getMaxHealth() / 10);
            }
            if(entity.getItemBySlot(EquipmentSlot.FEET).is(daiItems.BLUE_ICE_BOOTS.get())) {
                BlockPos blockPos = entity.blockPosition().below();
                BlockState blockState = entity.level().getBlockState(blockPos);
                BlockPos[] blockPoses = {blockPos, blockPos.east(), blockPos.west(), blockPos.north(), blockPos.south()};
                for (BlockPos pos : blockPoses){
                    BlockState state = entity.level().getBlockState(pos);
                    if(state.is(Blocks.WATER))
                        entity.level().setBlockAndUpdate(pos, Blocks.BLUE_ICE.defaultBlockState());
                }
                if (blockState.is(Blocks.LAVA)){
                    if(entity.level().getFluidState(blockPos).is(Fluids.LAVA))
                        entity.level().setBlockAndUpdate(blockPos, Blocks.OBSIDIAN.defaultBlockState());
                    else entity.level().setBlockAndUpdate(blockPos, Blocks.COBBLESTONE.defaultBlockState());
                    entity.setItemSlot(EquipmentSlot.FEET, ItemStack.EMPTY);
                }
                if (blockState.is(Blocks.MAGMA_BLOCK)){
                    entity.level().setBlockAndUpdate(blockPos, Blocks.OBSIDIAN.defaultBlockState());
                    entity.getItemBySlot(EquipmentSlot.FEET).hurtAndBreak(1, entity, EquipmentSlot.FEET);
                }
                if (entity.isOnFire()){
                    entity.clearFire();
                    entity.getItemBySlot(EquipmentSlot.FEET).hurtAndBreak(1, entity, EquipmentSlot.FEET);
                }
            }
        }
        else if (target instanceof ItemEntity itemEntity){
            if(itemEntity.getItem().is(Items.GLOWSTONE_DUST) && itemEntity.isInWater()){
                itemEntity.level().setBlockAndUpdate(itemEntity.getOnPos(), daiBlocks.EXTRACTANT_FLUID_BLOCK.get().defaultBlockState());
                itemEntity.discard();
            }
        }
    }

    @SubscribeEvent
    public static void Text(ItemTooltipEvent event){
        ItemStack itemStack = event.getItemStack();
        if(event.getEntity() != null){
            if (itemStack.is(Items.BRICK)) {
                event.getToolTip().add(Component.translatable("you_know_damage"));
            }
            if (itemStack.is(Items.NETHER_BRICK)) {
                event.getToolTip().add(Component.translatable("you_know_damage_and_from_nether"));
            }
            if (itemStack.has(daiDataComponentTypes.HEAT_BY_FRICTION) && itemStack.getItem() instanceof TieredItem item) {
                if (item.getTier() == Tiers.WOOD || item.getTier() == Tiers.DIAMOND)
                    event.getToolTip().add(Component.translatable("quick_use"));
                else event.getToolTip().add(Component.translatable("high temperature"));
            }
            if (itemStack.getEnchantmentLevel(daiFunctions.getHolder(event.getEntity().level(), Registries.ENCHANTMENT, daiEnchantments.PROBATION)) > 0 && itemStack.is(Items.EMERALD)) {
                event.getToolTip().add(Component.translatable("try_to_use_on_illager"));
            }
            if(itemStack.is(Items.HONEY_BOTTLE) && itemStack.has(daiDataComponentTypes.HONEY_EFFECTS) && !itemStack.get(daiDataComponentTypes.HONEY_EFFECTS).effects().isEmpty()){
                event.getToolTip().add(Component.translatable("honey_has_effect"));
            }
        }
    }

    @SubscribeEvent
    public static void entityTickPostDeal(EntityTickEvent.Post event){
        Entity entity = event.getEntity();
        BlockPos blockPos = entity.blockPosition();
        daiFunctions.windBlockAttractEntity(entity, blockPos);
        if(entity instanceof LivingEntity livingEntity) {
            if (livingEntity.getItemBySlot(EquipmentSlot.FEET).is(daiItems.BLUE_ICE_BOOTS.get()) && ((LivingEntity) entity).walkAnimation.isMoving() && entity.onGround())
                livingEntity.setDeltaMovement(entity.getDeltaMovement().add(entity.getDeltaMovement().normalize().x() * 0.1, 0 ,entity.getDeltaMovement().normalize().z() * 0.1));
            if(livingEntity.walkAnimation.isMoving() && ((LivingEntity) entity).hasEffect(daiMobEffects.IIIIII)) entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.1 ,0));
            if(daiFunctions.withIronOut(livingEntity)){
                daiFunctions.redstoneMagnetAttractEntity(livingEntity, blockPos);
            }

        }
        if(entity.getType().is(daiTags.daiEntityTypeTags.IRON_ENTITY) || (entity instanceof ItemEntity && ((ItemEntity) entity).getItem().is(daiTags.daiItemTags.IRON_ITEM))){
            daiFunctions.redstoneMagnetAttractEntity(entity, blockPos);
        }
    }

    @SubscribeEvent
    public static void jumpDeal(LivingEvent.LivingJumpEvent event){
        LivingEntity livingEntity = event.getEntity();
        if(livingEntity.getItemBySlot(EquipmentSlot.HEAD).is(daiItems.BELL_HELMET.get()) && livingEntity.level().isClientSide()){
            BlockPos blockPos = livingEntity.getOnPos().above(3);
            if(!livingEntity.level().getBlockState(blockPos).is(Blocks.AIR)){
                BlockState blockState = livingEntity.level().getBlockState(blockPos);
                float time = blockState.getBlock().defaultDestroyTime();
                if(time > 0){
                    boolean flag = true;
                    int x = new Random().nextInt(8);
                    if(blockState.is(BlockTags.NEEDS_STONE_TOOL) && x < 4) flag = false;
                    else if (blockState.is(BlockTags.NEEDS_IRON_TOOL ) && x < 6) flag = false;
                    else if (blockState.is(BlockTags.NEEDS_DIAMOND_TOOL) && x < 7) flag = false;
                    if(flag){
                        PacketDistributor.sendToServer(new daiPackets.daiBellHelmetPacket(blockPos));
                        livingEntity.level().playSound(livingEntity, blockPos, SoundEvents.BELL_BLOCK, SoundSource.BLOCKS, 2.0F, 2.0F);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void RenderDeal(RenderLivingEvent.Pre event){
        if(event.getEntity().hasData(daiAttachmentTypes.RENDER_SCALE)){
            float i = event.getEntity().getData(daiAttachmentTypes.RENDER_SCALE);
            if(event.getEntity().getData(daiAttachmentTypes.RENDER_UP_DOWN)) {
                event.getPoseStack().scale(i, -i, i);
                event.getPoseStack().translate(0, -event.getEntity().getBoundingBox().getYsize(), 0);
            }
            else event.getPoseStack().scale(i, i, i);
        }
    }
}