package net.Gmaj7.doAsInteresting.eventDispose;

import net.Gmaj7.doAsInteresting.daiEnchantments.daiEnchantments;
import net.Gmaj7.doAsInteresting.daiInit.daiAttachmentTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingUseTotemEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber
public class EntityDispose {

    @SubscribeEvent
    public static void TotemDeal(LivingUseTotemEvent event){
        LivingEntity livingEntity = event.getEntity();
        ItemStack totem = event.getTotem();
        if(livingEntity instanceof Player && totem.getEnchantmentLevel(livingEntity.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(daiEnchantments.PLUNDER)) > 0){
            totem.shrink(1);
            BlockPos blockPosLeft = new BlockPos(livingEntity.getBlockX(), livingEntity.getBlockY(), livingEntity.getBlockZ());
            BlockPos blockPosRight = new BlockPos(livingEntity.getBlockX() + 1, livingEntity.getBlockY(), livingEntity.getBlockZ());
            BlockState blockStateLeft = Blocks.CHEST.defaultBlockState();
            BlockState blockStateRight = Blocks.CHEST.defaultBlockState();
            livingEntity.level().destroyBlock(blockPosLeft, true);
            livingEntity.level().destroyBlock(blockPosRight, true);
            livingEntity.level().setBlockAndUpdate(blockPosLeft, blockStateLeft.setValue(ChestBlock.TYPE, ChestType.LEFT));
            livingEntity.level().setBlockAndUpdate(blockPosRight, blockStateRight.setValue(ChestBlock.TYPE, ChestType.RIGHT));
            BlockEntity chestLeft = livingEntity.level().getBlockEntity(blockPosLeft);
            BlockEntity chestRight = livingEntity.level().getBlockEntity(blockPosRight);
            chestLeft.setChanged();
            chestLeft.setData(daiAttachmentTypes.DAI_TOTEM, 1);
            chestRight.setChanged();
            chestRight.setData(daiAttachmentTypes.DAI_TOTEM, 1);
            if(chestLeft instanceof ChestBlockEntity && chestRight instanceof ChestBlockEntity){
                int i = 0;
                TotemChestFill((Player) livingEntity, (ChestBlockEntity) chestRight, i);
                if(i < ((Player) livingEntity).getInventory().getContainerSize()){
                    TotemChestFill((Player) livingEntity, (ChestBlockEntity) chestLeft, i);
                }
            }
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void BlockDestroy(BlockEvent.BreakEvent event){
        Player player = event.getPlayer();
        BlockEntity blockEntity = player.level().getBlockEntity(event.getPos());
        totemChestSummon(player, blockEntity);
    }

    protected static void totemChestSummon(Player player, BlockEntity blockEntity) {
        if(blockEntity != null && blockEntity.hasData(daiAttachmentTypes.DAI_TOTEM)){
            Vindicator vindicator = new Vindicator(EntityType.VINDICATOR, player.level());
            vindicator.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.IRON_AXE));
            vindicator.moveTo(player.getX(), player.getY(), player.getZ());
            player.level().addFreshEntity(vindicator);
        }
    }

    protected static void TotemChestFill(Player livingEntity, ChestBlockEntity chestLeft, int i) {
        for(int j = 0; j < 27; j++){
            for (; i < livingEntity.getInventory().getContainerSize(); i++){
                if(livingEntity.getInventory().getItem(i).isEmpty()) continue;
                chestLeft.setItem(j, livingEntity.getInventory().getItem(i));
                livingEntity.getInventory().removeItem(livingEntity.getInventory().getItem(i));
                break;
            }
        }
    }
}