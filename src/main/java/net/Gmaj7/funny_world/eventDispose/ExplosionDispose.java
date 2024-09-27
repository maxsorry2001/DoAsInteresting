package net.Gmaj7.funny_world.eventDispose;

import net.Gmaj7.funny_world.FunnyWorld;
import net.Gmaj7.funny_world.daiEnchantments.daiEnchantments;
import net.Gmaj7.funny_world.daiInit.daiDataComponentTypes;
import net.Gmaj7.funny_world.daiItems.daiItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.ExplosionEvent;

import java.util.List;

@EventBusSubscriber(modid = FunnyWorld.MODID)
public class ExplosionDispose {

    @SubscribeEvent
    public static void explosionDispose(ExplosionEvent.Start start){
        Explosion explosion = start.getExplosion();
        Vec3 vec3 = explosion.center();
        float radius = explosion.radius();
        List<Player> list = start.getLevel().getEntitiesOfClass(Player.class, new AABB(new Vec3(vec3.x() - radius * 2 , vec3.y() - radius * 2, vec3.z() - radius * 2), new Vec3(vec3.x() + radius * 2, vec3.y() + radius * 2, vec3.z() + radius * 2)));
        if(!list.isEmpty()){
            for (Player player : list){
                if(player.getItemBySlot(EquipmentSlot.CHEST).getEnchantmentLevel(player.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(daiEnchantments.EXPLOSION_GET)) > 0
                    && Math.sqrt(Math.pow((player.getX() - vec3.x()), 2) + Math.pow((player.getY() - vec3.y()), 2) + Math.pow((player.getZ() - vec3.z()), 2)) < radius * 2
                    && !player.isShiftKeyDown()) {
                    ItemStack itemStack = new ItemStack(daiItems.EXPLOSION_STORAGE.get());
                    itemStack.set(daiDataComponentTypes.ExplosionStorageRadius.get(), explosion.radius());
                    ItemEntity itemEntity = new ItemEntity(player.level(), player.getX(), player.getY(), player.getZ(), itemStack);
                    player.level().addFreshEntity(itemEntity);
                    player.getItemBySlot(EquipmentSlot.CHEST).hurtAndBreak(1, player,EquipmentSlot.CHEST);
                    start.setCanceled(true);
                }
            }
        }
    }
}