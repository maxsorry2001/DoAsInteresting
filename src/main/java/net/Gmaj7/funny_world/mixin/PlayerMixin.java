package net.Gmaj7.funny_world.mixin;


import net.Gmaj7.funny_world.daiInit.daiDataComponentTypes;
import net.Gmaj7.funny_world.daiInit.daiUniqueData.HumanitySet;
import net.Gmaj7.funny_world.daiInit.daiUniqueData.daiUniqueDataGet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.extensions.IPlayerExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements IPlayerExtension, daiUniqueDataGet {
    @Unique
    private HumanitySet humanitySet = new HumanitySet(50);

    @Unique
    private Entity attackTarget;

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    @Unique
    public HumanitySet getHumanitySet() {
        return humanitySet;
    }

    @Override
    public LivingEntity self() {
        return super.self();
    }

    @Inject(method = "addAdditionalSaveData", at = @At("HEAD"))
    public void addAdditionalSaveDataMixin(CompoundTag compound, CallbackInfo ci){
        compound.putInt("dai_humanity", humanitySet.getHumanity());
    }

    @Inject(method = "readAdditionalSaveData", at = @At("HEAD"))
    public void readAdditionalSaveDataMixin(CompoundTag compound, CallbackInfo ci){
        if (compound.contains("dai_humanity")) {
            this.humanitySet.setHumanity(compound.getInt("dai_humanity"));
        }
    }

    @Inject(method = "entityInteractionRange", at = @At("HEAD"), cancellable = true)
    public void entityInteractionRangeMixin(CallbackInfoReturnable ci){
        if(this.getMainHandItem().getItem() instanceof SwordItem && this.getMainHandItem().has(daiDataComponentTypes.SWEEPING_TYPE))
            ci.setReturnValue(this.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE) * 2);
    }

    @Inject(method = "attack", at = @At("HEAD"))
    public void attackMixin(Entity target, CallbackInfo ci){
        this.setAttackTarget(target);
    }

    @ModifyArgs(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/AABB;inflate(DDD)Lnet/minecraft/world/phys/AABB;"))
    public void attackArgs(Args args){
        if(this.getMainHandItem().getItem() instanceof SwordItem && this.getMainHandItem().has(daiDataComponentTypes.SWEEPING_TYPE)) {
            args.set(0, 5.0D);
            args.set(1, 5.0D);
            args.set(2, 5.0D);
        }
    }

    @ModifyArgs(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;knockback(DDD)V", ordinal = 1))
    public void attackArgs2(Args args){
        args.set(0, 0.0D);
    }

    @Override
    public Entity getAttackTarget() {
        return attackTarget;
    }

    public void setAttackTarget(Entity attackTarget) {
        this.attackTarget = attackTarget;
    }
}
