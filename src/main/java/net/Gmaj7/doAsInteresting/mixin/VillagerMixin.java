package net.Gmaj7.doAsInteresting.mixin;

import net.Gmaj7.doAsInteresting.daiInit.daiDataComponentTypes;
import net.Gmaj7.doAsInteresting.daiItems.daiItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.gossip.GossipType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Villager.class)
public abstract class VillagerMixin extends Mob {
    protected VillagerMixin(EntityType<? extends Villager> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    public void mobInteractMixin(Player pPlayer, InteractionHand pHand, CallbackInfoReturnable<InteractionResult> callbackInfoReturnable){
        if(pPlayer.getItemInHand(pHand).is(daiItems.RED_PACKET.get())){
            if(!pPlayer.getItemInHand(pHand).getComponents().has(daiDataComponentTypes.EMERALD_NUM.get())
                    && !pPlayer.getItemInHand(pHand).getComponents().has(daiDataComponentTypes.EMERALD_BLOCK_NUM.get())){
                pPlayer.getItemInHand(pHand).set(daiDataComponentTypes.EMERALD_NUM, 0);
                pPlayer.getItemInHand(pHand).set(daiDataComponentTypes.EMERALD_BLOCK_NUM, 0);
            }
            if(pPlayer.getItemInHand(pHand).getComponents().get(daiDataComponentTypes.EMERALD_NUM.get()) > 0
                    || pPlayer.getItemInHand(pHand).getComponents().get(daiDataComponentTypes.EMERALD_BLOCK_NUM.get()) > 0){
                if(this.level().isClientSide()){
                    for (int j = 1; j < 45; j++){
                        double r = j * 2 *Math.PI / 45;
                        level().addParticle(ParticleTypes.HAPPY_VILLAGER, getX() + Math.sin(r), getY() + 1, getZ() + Math.cos(r), 0, 0, 0);
                    }
                }
                else {
                    int a = pPlayer.getItemInHand(pHand).getComponents().get(daiDataComponentTypes.EMERALD_NUM.get());
                    int b = pPlayer.getItemInHand(pHand).getComponents().get(daiDataComponentTypes.EMERALD_BLOCK_NUM.get());
                    if(a > 0) {
                        Mob mob = this;
                        if(mob instanceof Villager villager){
                            villager.getGossips().add(pPlayer.getUUID(), GossipType.MAJOR_POSITIVE, 10 + Mth.floor(a / 6) + 1);
                            villager.getGossips().add(pPlayer.getUUID(), GossipType.MINOR_POSITIVE, 15 + Mth.floor(a / 6) + 1);
                        }
                    }
                    if(b > 0) pPlayer.addEffect(new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, b * 600, Mth.floor(b / 20) + 1));
                    pPlayer.getItemInHand(pHand).shrink(1);
                }
                callbackInfoReturnable.setReturnValue(InteractionResult.sidedSuccess(this.level().isClientSide()));
            }
        }
    }
}
