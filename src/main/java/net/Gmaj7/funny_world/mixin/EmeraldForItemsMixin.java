package net.Gmaj7.funny_world.mixin;

import net.Gmaj7.funny_world.villager.daiVillagers;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.entity.BannerPatterns;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net/minecraft/world/entity/npc/VillagerTrades$EmeraldForItems")
public class EmeraldForItemsMixin {
    @Shadow
    @Final
    private int maxUses;

    @Shadow
    @Final
    private int villagerXp;

    @Inject(method = "getOffer", at = @At("HEAD"), cancellable = true)
    private void getOfferMixin(Entity pTrader, RandomSource randomSource, CallbackInfoReturnable<MerchantOffer> cir){
        if(pTrader instanceof VillagerDataHolder villager && villager.getVariant() == daiVillagers.ILLAGER_CHANGE.get() && villager.getVillagerData().getProfession() == VillagerProfession.CLERIC){
            if(villager.getVillagerData().getLevel() == 1){
                BannerPatternLayers bannerpatternlayers = new BannerPatternLayers.Builder()
                        .addIfRegistered(pTrader.level().registryAccess().lookupOrThrow(Registries.BANNER_PATTERN), BannerPatterns.RHOMBUS_MIDDLE, DyeColor.CYAN)
                        .addIfRegistered(pTrader.level().registryAccess().lookupOrThrow(Registries.BANNER_PATTERN), BannerPatterns.STRIPE_BOTTOM, DyeColor.LIGHT_GRAY)
                        .addIfRegistered(pTrader.level().registryAccess().lookupOrThrow(Registries.BANNER_PATTERN), BannerPatterns.STRIPE_CENTER, DyeColor.GRAY)
                        .addIfRegistered(pTrader.level().registryAccess().lookupOrThrow(Registries.BANNER_PATTERN), BannerPatterns.BORDER, DyeColor.LIGHT_GRAY)
                        .addIfRegistered(pTrader.level().registryAccess().lookupOrThrow(Registries.BANNER_PATTERN), BannerPatterns.STRIPE_MIDDLE, DyeColor.BLACK)
                        .addIfRegistered(pTrader.level().registryAccess().lookupOrThrow(Registries.BANNER_PATTERN), BannerPatterns.HALF_HORIZONTAL, DyeColor.LIGHT_GRAY)
                        .addIfRegistered(pTrader.level().registryAccess().lookupOrThrow(Registries.BANNER_PATTERN), BannerPatterns.CIRCLE_MIDDLE, DyeColor.LIGHT_GRAY)
                        .addIfRegistered(pTrader.level().registryAccess().lookupOrThrow(Registries.BANNER_PATTERN), BannerPatterns.BORDER, DyeColor.BLACK)
                        .build();
                cir.setReturnValue(new MerchantOffer(
                        new ItemCost(Items.WHITE_BANNER)
                                .withComponents(p -> p.expect(DataComponents.BANNER_PATTERNS, bannerpatternlayers)
                                        .expect(DataComponents.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE)
                                        .expect(DataComponents.ITEM_NAME,  Component.translatable("block.minecraft.ominous_banner").withStyle(ChatFormatting.GOLD))),
                        new ItemStack(Items.EMERALD, 4),
                        this.maxUses, this.villagerXp,
                        0.05F));
            }
            if(villager.getVillagerData().getLevel() == 2)
                cir.setReturnValue(new MerchantOffer(new ItemCost(Items.SADDLE), new ItemStack(Items.EMERALD, 10), this.maxUses, this.villagerXp, 0.05F));
        }
    }
}
