package net.Gmaj7.funny_world.eventDispose;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.Gmaj7.funny_world.FunnyWorld;
import net.Gmaj7.funny_world.daiBlocks.daiBlocks;
import net.Gmaj7.funny_world.daiEffects.daiMobEffects;
import net.Gmaj7.funny_world.daiEnchantments.daiEnchantments;
import net.Gmaj7.funny_world.daiFluids.daiFluidTypes;
import net.Gmaj7.funny_world.daiInit.*;
import net.Gmaj7.funny_world.daiInit.daiUniqueData.daiUniqueDataGet;
import net.Gmaj7.funny_world.daiItems.custom.MusicalInstrument;
import net.Gmaj7.funny_world.daiItems.daiItems;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.GlowSquid;
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
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RenderHandEvent;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Random;

import static org.lwjgl.glfw.GLFW.*;


@EventBusSubscriber(modid = FunnyWorld.MODID)
public class TickDispose {
    private static final Int2IntMap KEY_TO_NOTE = new Int2IntOpenHashMap();
    static {
        // 第一八度 (音阶 1-12，F♯3～F4)
        KEY_TO_NOTE.put(GLFW_KEY_Z, 1);   // F♯3
        KEY_TO_NOTE.put(GLFW_KEY_S, 2);   // G3
        KEY_TO_NOTE.put(GLFW_KEY_X, 3);   // G♯3
        KEY_TO_NOTE.put(GLFW_KEY_D, 4);   // A3
        KEY_TO_NOTE.put(GLFW_KEY_C, 5);   // A♯3
        KEY_TO_NOTE.put(GLFW_KEY_V, 6);   // B3
        KEY_TO_NOTE.put(GLFW_KEY_G, 7);   // C4
        KEY_TO_NOTE.put(GLFW_KEY_B, 8);   // C♯4
        KEY_TO_NOTE.put(GLFW_KEY_H, 9);   // D4
        KEY_TO_NOTE.put(GLFW_KEY_N, 10);  // D♯4
        KEY_TO_NOTE.put(GLFW_KEY_J, 11);  // E4
        KEY_TO_NOTE.put(GLFW_KEY_M, 12);  // F4

        // 第二八度 (音阶 13-24，F♯4～F5)
        KEY_TO_NOTE.put(GLFW_KEY_W, 13);  // F♯4
        KEY_TO_NOTE.put(GLFW_KEY_3, 14);  // G4
        KEY_TO_NOTE.put(GLFW_KEY_E, 15);  // G♯4
        KEY_TO_NOTE.put(GLFW_KEY_4, 16);  // A4
        KEY_TO_NOTE.put(GLFW_KEY_R, 17);  // A♯4
        KEY_TO_NOTE.put(GLFW_KEY_T, 18);  // B4
        KEY_TO_NOTE.put(GLFW_KEY_6, 19);  // C5
        KEY_TO_NOTE.put(GLFW_KEY_Y, 20);  // C♯5
        KEY_TO_NOTE.put(GLFW_KEY_7, 21);  // D5
        KEY_TO_NOTE.put(GLFW_KEY_U, 22);  // D♯5
        KEY_TO_NOTE.put(GLFW_KEY_8, 23);  // E5
        KEY_TO_NOTE.put(GLFW_KEY_I, 24);  // F5
        KEY_TO_NOTE.put(GLFW_KEY_9, 25);  // F#5
    }
    @SubscribeEvent
    public static void EntityTickPreDeal(EntityTickEvent.Pre event){
        Entity target = event.getEntity();
        //if(target instanceof Player player){
        //    int humanity = ((daiUniqueDataGet)player).getHumanitySet().getHumanity();
        //    if(player.tickCount % 100 == 0){
        //        if (humanity < 0) {
        //            player.hurt(new DamageSource(daiFunctions.getHolder(player.level(), Registries.DAMAGE_TYPE, DamageTypes.OUTSIDE_BORDER)), (0.5F * (-humanity + 25)) / 25);
        //            if(!player.hasEffect(MobEffects.RAID_OMEN))
        //                player.addEffect(new MobEffectInstance(MobEffects.BAD_OMEN, 400));
        //            player.removeEffect(MobEffects.HERO_OF_THE_VILLAGE);
        //        }
        //        else if (humanity > 100) {
        //            player.heal( (humanity - 75) / 25);
        //            player.addEffect(new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, 400, 4));
        //            player.removeEffect(MobEffects.BAD_OMEN);
        //            player.removeEffect(MobEffects.RAID_OMEN);
        //        }
        //    }
        //}
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
            if((target instanceof GlowSquid || target.isCurrentlyGlowing()) && target.isAlive()){
                BlockPos newPos = target.blockPosition().above();
                BlockPos oldPos = ((daiUniqueDataGet)target).getOldPos();
                BlockState newState = target.level().getBlockState(newPos);
                if(oldPos == null || newPos.distManhattan(oldPos) > 0){
                    if(oldPos != null) target.level().setBlockAndUpdate(oldPos, Blocks.AIR.defaultBlockState());
                    if(newState.isAir() && !newState.is(daiBlocks.GLOW_BLOCK.get())) target.level().setBlockAndUpdate(newPos, daiBlocks.GLOW_BLOCK.get().defaultBlockState());
                    ((daiUniqueDataGet)target).setOldPos(newPos);
                }
            }
            else {
                BlockPos blockPos = ((daiUniqueDataGet)target).getOldPos();
                if(blockPos != null && target.level().getBlockState(blockPos).is(daiBlocks.GLOW_BLOCK))
                    target.level().setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
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
        if(!(entity instanceof Player && ((Player) entity).isCreative()))
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

    @SubscribeEvent
    public static void EntityRemoveDeal(EntityLeaveLevelEvent event){
        if(event.getEntity() instanceof GlowSquid glowSquid){
             BlockPos blockPos = ((daiUniqueDataGet)glowSquid).getOldPos();
             if(blockPos != null && event.getLevel().isLoaded(blockPos) && event.getLevel().getBlockState(blockPos).is(daiBlocks.GLOW_BLOCK))
                event.getLevel().setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
        }
    }

    @SubscribeEvent
    public static void RenderHandDeal(RenderHandEvent event){
        Player player = Minecraft.getInstance().player;
        if(player.isUsingItem() && player.getItemInHand(player.getUsedItemHand()).is(daiItems.WATER_BOW.get())){
            if(event.getHand() != player.getUsedItemHand()) event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void FovDeal(ComputeFovModifierEvent event){
        Player player = event.getPlayer();
        if(player.isUsingItem()){
            ItemStack itemStack = player.getUseItem();
            if(itemStack.is(daiItems.WATER_BOW.get())){
                int i = player.getTicksUsingItem();
                float f1 = (float)i / 20.0F;
                if (f1 > 1.0F) f1 = 1.0F;
                else f1 *= f1;
                event.setNewFovModifier(1.0F - f1 * 0.15F);
            }
        }
    }

    @SubscribeEvent
    public static void InputEvent(InputEvent.Key event){
        Player player = Minecraft.getInstance().player;
        if(player != null && event.getKey() != GLFW_KEY_Q && event.getKey() != GLFW_KEY_SLASH && player.getMainHandItem().getItem() instanceof MusicalInstrument musicalInstrument) {
            for (KeyMapping keyMapping : Minecraft.getInstance().options.keyMappings) {
                keyMapping.consumeClick();
                keyMapping.setDown(false);
            }
            if(KEY_TO_NOTE.containsKey(event.getKey()) && event.getAction() == GLFW_PRESS)
                PacketDistributor.sendToServer(new daiPackets.musicalInstrumentPacket(KEY_TO_NOTE.get(event.getKey())));
        }
    }
}