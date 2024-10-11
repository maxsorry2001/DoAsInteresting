package net.Gmaj7.funny_world.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.Gmaj7.funny_world.villager.daiVillagers;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Mixin(targets = "net/minecraft/world/entity/npc/VillagerTrades$EmeraldsForVillagerTypeItem")
public class EmeraldsForVillagerTypeItemMixin {
    @Shadow
    @Final
    private int cost;

    @Shadow
    @Final
    private int maxUses;

    @Shadow
    @Final
    private int villagerXp;

    @Shadow
    @Final
    private Map<VillagerType, Item> trades;

    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;filter(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;"))
    private Stream<VillagerType> wrapFilter(Stream<VillagerType> instance, Predicate<? super VillagerType> predicate, Operation<Stream<VillagerType>> original) {
        return original.call(instance, (Predicate<? super VillagerType>)(it) -> it != daiVillagers.ILLAGER_CHANGE.get() && predicate.test(it));
    }

    @Inject(method = "getOffer", at = @At("HEAD"), cancellable = true)
    private void getOfferMixin(Entity pTrader, RandomSource randomSource, CallbackInfoReturnable<MerchantOffer> cir){
        if(pTrader instanceof VillagerDataHolder villager && villager.getVariant() == daiVillagers.ILLAGER_CHANGE.get()){
            cir.setReturnValue(new MerchantOffer(new ItemCost(Items.EMERALD, cost), new ItemStack(Items.TOTEM_OF_UNDYING), maxUses, villagerXp, 0.05F));
        }
    }
}
