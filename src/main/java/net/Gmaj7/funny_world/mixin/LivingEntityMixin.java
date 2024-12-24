package net.Gmaj7.funny_world.mixin;

import net.Gmaj7.funny_world.daiEnchantments.daiEnchantments;
import net.Gmaj7.funny_world.daiInit.daiAttachmentTypes;
import net.Gmaj7.funny_world.daiInit.daiDamageTypes;
import net.Gmaj7.funny_world.daiInit.daiFunctions;
import net.Gmaj7.funny_world.daiItems.daiItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable, net.neoforged.neoforge.common.extensions.ILivingEntityExtension {
    private LivingEntityMixin(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Shadow public abstract boolean hasEffect(Holder<MobEffect> pEffect);
    @Shadow public abstract ItemStack getItemBySlot(EquipmentSlot pSlot);
    @Shadow public abstract float getMaxHealth();

    @Inject(method = "tick", at = @At("HEAD"))
    public void tickMixin(CallbackInfo info){
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
        if(this.hasData(daiAttachmentTypes.THUNDER_HIT) && this.getData(daiAttachmentTypes.THUNDER_HIT) == 4){
            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, this.level());
            lightningBolt.teleportTo(this.getX(), this.getY(), this.getZ());
            this.level().addFreshEntity(lightningBolt);
            this.removeData(daiAttachmentTypes.THUNDER_HIT);
        }
        if(this instanceof Enemy && this.getItemBySlot(EquipmentSlot.HEAD).is(daiItems.BELL_HELMET.get()) && this.tickCount % 40 == 0){
            this.hurt(new DamageSource(level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(daiDamageTypes.BELL_HELMET)), Math.max(5F, this.getMaxHealth() / 7));
            level().playSound(this, this.getOnPos().above(2), SoundEvents.BELL_BLOCK, SoundSource.NEUTRAL, 2, 1);
        }
        if(this.hasData(daiAttachmentTypes.RENDER_SCALE)){
            float a = this.getData(daiAttachmentTypes.RENDER_SCALE);
            if(Math.abs(a - 1) < 10E-2) {
                this.removeData(daiAttachmentTypes.RENDER_SCALE);
                if(this.hasData(daiAttachmentTypes.RENDER_UP_DOWN))
                    this.removeData(daiAttachmentTypes.RENDER_UP_DOWN);
            }
            else this.setData(daiAttachmentTypes.RENDER_SCALE, a > 1 ? a - (a - 1) / 100 : a + (1 - a) / 100);
        }
    }
}
