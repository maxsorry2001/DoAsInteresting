package net.Gmaj7.funny_world.eventDispose;

import net.Gmaj7.funny_world.FunnyWorld;
import net.Gmaj7.funny_world.daiEffects.daiMobEffects;
import net.Gmaj7.funny_world.daiEnchantments.daiEnchantments;
import net.Gmaj7.funny_world.daiInit.daiDataComponentTypes;
import net.Gmaj7.funny_world.daiInit.daiFunctions;
import net.Gmaj7.funny_world.daiInit.daiTags;
import net.Gmaj7.funny_world.daiItems.daiItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import static net.Gmaj7.funny_world.daiInit.daiFunctions.attrackEntity;

@EventBusSubscriber(modid = FunnyWorld.MODID)
public class TickDispose {

    @SubscribeEvent
    public static void EntityTickPreDeal(EntityTickEvent.Pre event){
        Entity entity = event.getEntity();
        if(entity instanceof LivingEntity){
            if(((LivingEntity) entity).getItemBySlot(EquipmentSlot.FEET).is(daiItems.BLUE_ICE_BOOTS.get())) {
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
                    ((LivingEntity) entity).setItemSlot(EquipmentSlot.FEET, ItemStack.EMPTY);
                }
                if (blockState.is(Blocks.MAGMA_BLOCK)){
                    entity.level().setBlockAndUpdate(blockPos, Blocks.OBSIDIAN.defaultBlockState());
                    ((LivingEntity) entity).getItemBySlot(EquipmentSlot.FEET).hurtAndBreak(1, (LivingEntity) entity, EquipmentSlot.FEET);
                }
                if (entity.isOnFire()){
                    entity.clearFire();
                    ((LivingEntity) entity).getItemBySlot(EquipmentSlot.FEET).hurtAndBreak(1, (LivingEntity) entity, EquipmentSlot.FEET);
                }
            }
        }
    }

    @SubscribeEvent
    public static void Text(ItemTooltipEvent event){
        ItemStack itemStack = event.getItemStack();
        if(itemStack.is(Items.BRICK)){
            event.getToolTip().add(Component.translatable("you_know_damage"));
        }
        if(itemStack.is(Items.NETHER_BRICK)){
            event.getToolTip().add(Component.translatable("you_know_damage_and_from_nether"));
        }
        if(itemStack.has(daiDataComponentTypes.HEAT_BY_FRICTION) && itemStack.getItem() instanceof TieredItem){
            TieredItem item = (TieredItem) itemStack.getItem();
            if(item.getTier() == Tiers.WOOD || item.getTier() == Tiers.DIAMOND)
                event.getToolTip().add(Component.translatable("quick_use"));
            else event.getToolTip().add(Component.translatable("high temperature"));
        }
        if(event.getEntity() != null && itemStack.getEnchantmentLevel(daiFunctions.getHolder(event.getEntity().level(), daiEnchantments.PROBATION)) > 0 && itemStack.is(Items.EMERALD)){
            event.getToolTip().add(Component.translatable("try_to_use_on_illager"));
        }
    }

    @SubscribeEvent
    public static void entityTickPostDeal(EntityTickEvent.Post event){
        Entity entity = event.getEntity();
        BlockPos blockPos = entity.blockPosition();
        if(entity instanceof LivingEntity livingEntity) {
            if (livingEntity.getItemBySlot(EquipmentSlot.FEET).is(daiItems.BLUE_ICE_BOOTS.get()) && ((LivingEntity) entity).walkAnimation.isMoving() && entity.onGround())
                livingEntity.setDeltaMovement(entity.getDeltaMovement().add(entity.getDeltaMovement().normalize().x() * 0.1, 0 ,entity.getDeltaMovement().normalize().z() * 0.1));
            if(livingEntity.walkAnimation.isMoving() && ((LivingEntity) entity).hasEffect(daiMobEffects.IIIIII)) entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.1 ,0));
            if(daiFunctions.withIronOut(livingEntity)){
                attrackEntity(livingEntity, blockPos);
            }
        }
        if(entity.getType().is(daiTags.daiEntityTypeTags.IRON_ENTITY) || (entity instanceof ItemEntity && ((ItemEntity) entity).getItem().is(daiTags.daiItemTags.IRON_ITEM))){
            attrackEntity(entity, blockPos);
        }
    }


}