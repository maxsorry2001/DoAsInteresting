package net.Gmaj7.funny_world.mixin;

import net.Gmaj7.funny_world.villager.daiVillagers;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.npc.VillagerProfession;
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

@Mixin(targets = "net/minecraft/world/entity/npc/VillagerTrades$ItemsForEmeralds")
public class ItemsForEmeraldsMixin {
    @Shadow
    @Final
    private int emeraldCost;

    @Shadow
    @Final
    private int maxUses;

    @Shadow
    @Final
    private int villagerXp;

    @Inject(method = "getOffer", at = @At("HEAD"), cancellable = true)
    private void getOfferMixin(Entity pTrader, RandomSource randomSource, CallbackInfoReturnable<MerchantOffer> cir){
        if(pTrader instanceof VillagerDataHolder villager && villager.getVariant() == daiVillagers.ILLAGER_CHANGE.get() && villager.getVillagerData().getLevel() == 3 && villager.getVillagerData().getProfession() == VillagerProfession.CLERIC){
            cir.setReturnValue(new MerchantOffer(new ItemCost(Items.EMERALD, emeraldCost), new ItemStack(Items.TOTEM_OF_UNDYING), maxUses, villagerXp, 0.05F));
        }
    }
}
