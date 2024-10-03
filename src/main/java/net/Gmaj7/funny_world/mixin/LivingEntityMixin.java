package net.Gmaj7.funny_world.mixin;

import net.Gmaj7.funny_world.daiEffects.daiMobEffects;
import net.Gmaj7.funny_world.daiEnchantments.daiEnchantments;
import net.Gmaj7.funny_world.daiInit.daiAttachmentTypes;
import net.Gmaj7.funny_world.daiInit.daiDamageTypes;
import net.Gmaj7.funny_world.daiInit.daiFunctions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

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
        if(this.getData(daiAttachmentTypes.TEMPERATURE) > 60F) this.setRemainingFireTicks(100);
        int lv = 0;
        for (int i = -2; i <= 2; i++){
            for (int j = -2; j<= 2; j++){
                for (int k = -2; k <= 2; k++){
                    BlockPos blockPos1 = new BlockPos(this.getBlockX() + i, this.getBlockY() + j, this.getBlockZ() + k);
                    Optional<ChiseledBookShelfBlockEntity> optional = this.level().getBlockEntity(blockPos1, BlockEntityType.CHISELED_BOOKSHELF);
                    if(optional.isPresent()){
                        ChiseledBookShelfBlockEntity blockEntity = optional.get();
                        for (int n = 0; n < blockEntity.count(); n++){
                            ItemStack itemStack = blockEntity.getItem(n);
                            if(itemStack.getEnchantmentLevel(daiFunctions.getHolder(this.level(), daiEnchantments.CONVINCE_PEOPLE_BY_REASON)) > 0)
                                lv += itemStack.getEnchantmentLevel(daiFunctions.getHolder(this.level(), daiEnchantments.CONVINCE_PEOPLE_BY_REASON));
                        }
                    }
                }
            }
        }
        if(lv > 0)
            this.hurt(new DamageSource(this.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(daiDamageTypes.POWER_OF_KNOWLEDGE)), lv);
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
