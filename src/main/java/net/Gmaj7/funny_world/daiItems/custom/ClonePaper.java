package net.Gmaj7.funny_world.daiItems.custom;

import net.Gmaj7.funny_world.daiInit.daiDataComponentTypes;
import net.Gmaj7.funny_world.daiItems.daiItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ClonePaper extends Item {
    public static final DispenseItemBehavior DISPENSE_ITEM_BEHAVIOR = new DefaultDispenseItemBehavior(){
        @Override
        protected ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
            Vec3 vec3 = blockSource.pos().relative(blockSource.state().getValue(DispenserBlock.FACING)).getCenter();
            if(itemStack.is(daiItems.CLONE_PAPER.get()) && itemStack.has(daiDataComponentTypes.ENTITY_TYPE) && itemStack.has(daiDataComponentTypes.ENTITY_DATA)){
                Entity entity =  BuiltInRegistries.ENTITY_TYPE.getOptional(itemStack.get(daiDataComponentTypes.ENTITY_TYPE)).get().create(blockSource.level());
                if(entity instanceof LivingEntity) {
                    ((LivingEntity) entity).readAdditionalSaveData(itemStack.get(daiDataComponentTypes.ENTITY_DATA));
                    entity.teleportTo(vec3.x(), vec3.y() + 1, vec3.z());
                    blockSource.level().addFreshEntity(entity);
                }
            }
            return itemStack;
        }
    };
    public ClonePaper(Properties properties) {
        super(properties);
        DispenserBlock.registerBehavior(this, DISPENSE_ITEM_BEHAVIOR);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack itemStack = context.getItemInHand();
        if(itemStack.is(daiItems.CLONE_PAPER.get()) && itemStack.has(daiDataComponentTypes.ENTITY_TYPE) && itemStack.has(daiDataComponentTypes.ENTITY_DATA)){
            Entity entity =  BuiltInRegistries.ENTITY_TYPE.getOptional(itemStack.get(daiDataComponentTypes.ENTITY_TYPE)).get().create(context.getLevel());
            if(entity instanceof LivingEntity) {
                ((LivingEntity) entity).readAdditionalSaveData(itemStack.get(daiDataComponentTypes.ENTITY_DATA));
                BlockPos blockPos = context.getClickedPos().relative(context.getClickedFace());
                entity.teleportTo(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ());
                context.getLevel().addFreshEntity(entity);
                context.getPlayer().swing(context.getHand());
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        if(stack.has(daiDataComponentTypes.ENTITY_TYPE))
            tooltipComponents.add(Component.translatable("dai.clone_entity").append(BuiltInRegistries.ENTITY_TYPE.getOptional(stack.get(daiDataComponentTypes.ENTITY_TYPE)).get().getDescription()));
    }
}
