package net.Gmaj7.doAsInteresting.mixin;

import net.Gmaj7.doAsInteresting.daiEffects.daiMobEffects;
import net.Gmaj7.doAsInteresting.daiInit.daiAttachmentTypes;
import net.Gmaj7.doAsInteresting.daiInit.daiDamageTypes;
import net.minecraft.core.Holder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable, net.neoforged.neoforge.common.extensions.ILivingEntityExtension {
    private LivingEntityMixin(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Shadow public abstract boolean hasEffect(Holder<MobEffect> pEffect);

    @Inject(method = "tick", at = @At("HEAD"))
    public void tickMixin(CallbackInfo info){
        if(!this.hasData(daiAttachmentTypes.TEMPERATURE)){
            this.setData(daiAttachmentTypes.TEMPERATURE, 20.0F);
        }
        float temperatureAdd = 0F;
        if(this.isOnFire()) temperatureAdd += 0.00025F;
        if(this.hasEffect(daiMobEffects.RADIATION)) temperatureAdd += 0.0005F;
        if(temperatureAdd > 0) this.setData(daiAttachmentTypes.TEMPERATURE, this.getData(daiAttachmentTypes.TEMPERATURE) + temperatureAdd);
        else this.setData(daiAttachmentTypes.TEMPERATURE, Math.max(20F, this.getData(daiAttachmentTypes.TEMPERATURE) - 0.001F));
        if(this.getData(daiAttachmentTypes.TEMPERATURE) > 40F) this.setRemainingFireTicks(100);
    }

    @Inject(method = "hurt", at = @At("HEAD"))
    public void hurtMixin(DamageSource pSource, float pAmount, CallbackInfoReturnable<InteractionResult> callbackInfoReturnable){
        if(pSource.is(daiDamageTypes.RADIATION)){
            if(this.hasData(daiAttachmentTypes.TEMPERATURE))
                this.setData(daiAttachmentTypes.TEMPERATURE, this.getData(daiAttachmentTypes.TEMPERATURE) + 0.1F);
            else this.setData(daiAttachmentTypes.TEMPERATURE, 20.1F);
        }
    }
}
