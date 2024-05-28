package net.Gmaj7.doAsInteresting.eventDispose;

import net.Gmaj7.doAsInteresting.DoAsInteresting;
import net.Gmaj7.doAsInteresting.daiEnchantments.daiEnchantments;
import net.Gmaj7.doAsInteresting.daiInit.daiTiers;
import net.Gmaj7.doAsInteresting.daiItems.daiItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDestroyBlockEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.List;
import java.util.Random;

@EventBusSubscriber(modid = DoAsInteresting.MODID)
public class ClickDispose {

    @SubscribeEvent
    public static void entityDispose(PlayerInteractEvent.EntityInteractSpecific event){
        Player player = event.getEntity();
        Entity target = event.getTarget();
        for (InteractionHand hand : InteractionHand.values()){
            int i = player.getItemInHand(hand).getEnchantmentLevel(daiEnchantments.SHIELD_STRIKE.get());
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
                    break;
                }
            }
        }
    }

    @SubscribeEvent
    public static void RightClickBlock(PlayerInteractEvent.RightClickBlock event){
        Player player = event.getEntity();
        BlockEntity blockEntity = player.level().getBlockEntity(event.getHitVec().getBlockPos());
        EntityDispose.totemChestSummon(player, blockEntity);
    }

    @SubscribeEvent
    public static void tt(BlockEvent.BreakEvent event){
        Player player = event.getPlayer();
        BlockPos blockPos = event.getPos();
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

    private static void getJistgabburash(BlockPos blockPos, Player player){
        player.level().setBlock(blockPos, Blocks.AIR.defaultBlockState(), 1);
        ItemEntity itemEntity = new ItemEntity(player.level(),blockPos.getX(), blockPos.getY(), blockPos.getZ(), new ItemStack(daiItems.JISTGABBURASH.get()));
        player.level().addFreshEntity(itemEntity);
        player.getMainHandItem().hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
    }
}