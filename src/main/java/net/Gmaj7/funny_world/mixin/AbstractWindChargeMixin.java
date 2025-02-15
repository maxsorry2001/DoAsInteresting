package net.Gmaj7.funny_world.mixin;

import net.Gmaj7.funny_world.daiInit.daiUniqueData.WindChargeSet;
import net.Gmaj7.funny_world.daiInit.daiUniqueData.daiUniqueDataGet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.windcharge.AbstractWindCharge;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractWindCharge.class)
public abstract class AbstractWindChargeMixin extends AbstractHurtingProjectile implements ItemSupplier, daiUniqueDataGet {
    @Unique
    protected WindChargeSet windChargeSet = new WindChargeSet();
    protected AbstractWindChargeMixin(EntityType<? extends AbstractHurtingProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(method = "onHitBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/windcharge/AbstractWindCharge;discard()V"))
    private void onHitBlockMixin(BlockHitResult pResult, CallbackInfo info){
        Vec3 vec3 = pResult.getBlockPos().clampLocationWithin(pResult.getLocation());
        EnchantmentHelper.onHitBlock(
                (ServerLevel) level(),
                ((daiUniqueDataGet)this).getWindChargeSet().getItemSet(),
                this.getOwner() instanceof LivingEntity livingEntity ? livingEntity : null,
                this,
                null,
                vec3,
                level().getBlockState(pResult.getBlockPos()),
                p_348680_ -> this.kill()
        );
    }

    @Unique
    @Override
    public WindChargeSet getWindChargeSet(){
        return this.windChargeSet;
    }
}
