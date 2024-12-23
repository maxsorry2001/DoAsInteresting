package net.Gmaj7.funny_world.daiItems.custom;

import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class Convex extends Item {
    private final float f = 1.5F;
    public Convex(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(pPlayer.isCurrentlyGlowing()){
            Vec3 vec3 = pPlayer.getLookAngle().normalize().scale(2 * f);
            pPlayer.teleportTo(pPlayer.getX() + vec3.x(), vec3.y() > 0 ? pPlayer.getY() + vec3.y() : pPlayer.getY(), pPlayer.getZ() + vec3.z());
        }
        else if(pPlayer.getNearestViewDirection() == Direction.UP && pLevel.isDay())
            pPlayer.addEffect(new MobEffectInstance(MobEffects.GLOWING, 500));
        pPlayer.getCooldowns().addCooldown(this, 10);
        pPlayer.swing(pUsedHand);
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
