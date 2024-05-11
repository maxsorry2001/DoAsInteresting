package net.Gmaj7.doAsInteresting.eventDispose;

import net.Gmaj7.doAsInteresting.DoAsInteresting;
import net.Gmaj7.doAsInteresting.daiEnchantments.daiEnchantments;
import net.Gmaj7.doAsInteresting.daiInit.daiAttachmentTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.List;

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
        if(blockEntity != null && blockEntity.hasData(daiAttachmentTypes.DAI_TOTEM)){
            Vindicator vindicator = new Vindicator(EntityType.VINDICATOR, player.level());
            vindicator.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.IRON_AXE));
            vindicator.moveTo(player.getX(), player.getY(), player.getZ());
            player.level().addFreshEntity(vindicator);
        }
    }
}