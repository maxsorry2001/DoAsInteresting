package net.Gmaj7.funny_world.mixin;

import net.Gmaj7.funny_world.daiInit.daiAttachmentTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin extends Projectile {
    protected AbstractArrowMixin(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(at = @At("HEAD"), method = "onHitEntity")
    public void onHitEntity(EntityHitResult pResult, CallbackInfo ci){
        if(this.hasData(daiAttachmentTypes.FISSION_ARROW) && this.getData(daiAttachmentTypes.FISSION_ARROW) > 0 && this.getOwner() != null){
            Entity pTarget = pResult.getEntity();
            int pLevel = this.getData(daiAttachmentTypes.FISSION_ARROW);
            Entity pAttacker = this.getOwner();
            for (int i = 1 ; i <= 10 * pLevel; i++){
                Arrow arrow = new Arrow(pTarget.level(),  pTarget.getX(), pTarget.getY() + 1, pTarget.getZ(), new ItemStack(Items.ARROW), null);
                arrow.setOwner(pAttacker);
                arrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                arrow.setData(daiAttachmentTypes.FISSION_ARROW, this.getData(daiAttachmentTypes.FISSION_ARROW));
                double rad = 2 * Math.PI * i / (10 * pLevel);
                float yRot = new Random().nextFloat(1.0F);
                float speed = new Random().nextFloat(2.0F);
                arrow.shoot(5 * Math.sin(rad), yRot, 5 * Math.cos(rad), speed, 10.0F);
                pTarget.level().addFreshEntity(arrow);
            }
        }
    }

}
