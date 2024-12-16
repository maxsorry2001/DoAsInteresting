package net.Gmaj7.funny_world.mixin;

import net.Gmaj7.funny_world.daiItems.daiItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.BellBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(BellBlock.class)
public abstract class BellBlockMixin extends BaseEntityBlock {
    protected BellBlockMixin(Properties pProperties) {
        super(pProperties);
    }

    @Inject(method = "onHit", at = @At("HEAD"))
    public void onHitMixin(Level pLevel, BlockState pState, BlockHitResult pResult, @Nullable Player pPlayer, boolean pCanRingBell, CallbackInfoReturnable ci){
        List<ItemEntity> itemEntity = pLevel.getEntitiesOfClass(ItemEntity.class, new AABB(pResult.getBlockPos()).inflate(2));
        if(!itemEntity.isEmpty()){
            for (ItemEntity target : itemEntity){
                if(target.getItem().is(Items.GOLDEN_HELMET)){
                    target.setItem(new ItemStack(daiItems.BELL_HELMET.get()));
                    pLevel.addParticle(ParticleTypes.ELECTRIC_SPARK, target.getX(), target.getY() + 0.2, target.getZ(), 0, 0, 0);
                }
            }
        }
    }

}
