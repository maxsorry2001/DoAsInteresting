package net.Gmaj7.doAsInteresting.daiEnchantments.custom;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Random;

public class Fission extends Enchantment {
    public Fission(EnchantmentDefinition pDefinition) {
        super(pDefinition);
    }

    @Override
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {
        super.doPostAttack(pAttacker, pTarget, pLevel);
        for (int i = 1 ; i <= 10 * pLevel; i++){
            Arrow arrow = new Arrow(pTarget.level(),  pTarget.getX(), pTarget.getY(), pTarget.getZ(), new ItemStack(Items.ARROW));
            arrow.setOwner(pAttacker);
            arrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            double rad = 2 * Math.PI * i / (10 * pLevel);
            float yRot = new Random().nextFloat(1.0F);
            float speed = new Random().nextFloat(2.0F);
            arrow.shoot(3 * Math.sin(rad), yRot, 5 * Math.cos(rad), speed, 10.0F);
            pTarget.level().addFreshEntity(arrow);
        }
    }

    @Override
    public boolean isAllowedOnBooks() {
        return true;
    }

    @Override
    public boolean canEnchant(ItemStack pStack) {
        return pStack.getItem() instanceof BowItem || pStack.getItem() instanceof CrossbowItem;
    }
}
