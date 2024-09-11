package net.Gmaj7.doAsInteresting.mixin;

import net.Gmaj7.doAsInteresting.daiEnchantments.daiEnchantments;
import net.Gmaj7.doAsInteresting.daiInit.daiAttachmentTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ProjectileWeaponItem.class)
public abstract class ProjectileWeaponItemMixin extends Item {
    public ProjectileWeaponItemMixin(Properties pProperties) {
        super(pProperties);
    }

    @Inject(at = @At("RETURN"), method = "createProjectile", locals = LocalCapture.CAPTURE_FAILSOFT)
    public void createProjectileMixin(Level pLevel, LivingEntity pShooter, ItemStack pWeapon, ItemStack pAmmo, boolean pIsCrit, CallbackInfoReturnable ci, ArrowItem arrowitem, AbstractArrow abstractarrow){
        if(pWeapon.getEnchantmentLevel(pShooter.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(daiEnchantments.FISSION)) > 0){
            abstractarrow.setData(daiAttachmentTypes.FISSION_ARROW, pWeapon.getEnchantmentLevel(pShooter.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(daiEnchantments.FISSION)));
        }
    }
}
