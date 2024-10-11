package net.Gmaj7.funny_world.mixin;

import net.Gmaj7.funny_world.villager.daiVillagers;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;

@Mixin(targets = "net/minecraft/world/entity/npc/VillagerTrades$TypeSpecificTrade")
public record TypeSpecificTradeMixin(Map<VillagerType, VillagerTrades.ItemListing> trades) {

    @Inject(method = "getOffer", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/npc/VillagerData;getType()Lnet/minecraft/world/entity/npc/VillagerType;"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private void getOfferMixin(Entity pTrader, RandomSource pRandom, CallbackInfoReturnable<MerchantOffer> ci, VillagerDataHolder villagerdataholder){
        if(villagerdataholder.getVillagerData().getType() == daiVillagers.ILLAGER_CHANGE.get()){
            if(villagerdataholder.getVillagerData().getProfession() == VillagerProfession.LIBRARIAN){
                VillagerTrades.ItemListing villagertrades$itemlisting = this.trades.get(VillagerType.SWAMP);
                ci.setReturnValue(villagertrades$itemlisting == null ? null : villagertrades$itemlisting.getOffer(pTrader, pRandom));
            }
            else if (villagerdataholder.getVillagerData().getProfession() == VillagerProfession.ARMORER){
                VillagerTrades.ItemListing villagertrades$itemlisting = this.trades.get(VillagerType.TAIGA);
                ci.setReturnValue(villagertrades$itemlisting == null ? null : villagertrades$itemlisting.getOffer(pTrader, pRandom));
            }
            else if (villagerdataholder.getVillagerData().getProfession() == VillagerProfession.CARTOGRAPHER){
                VillagerTrades.ItemListing villagertrades$itemlisting = this.trades.get(VillagerType.JUNGLE);
                ci.setReturnValue(villagertrades$itemlisting == null ? null : villagertrades$itemlisting.getOffer(pTrader, pRandom));
            }
            else {
                VillagerTrades.ItemListing villagertrades$itemlisting = this.trades.get(VillagerType.PLAINS);
                ci.setReturnValue(villagertrades$itemlisting == null ? null : villagertrades$itemlisting.getOffer(pTrader, pRandom));
            }
        }
    }
}
