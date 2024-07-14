package net.Gmaj7.doAsInteresting.daiInit;

import net.Gmaj7.doAsInteresting.daiItems.daiItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class daiItemProperties {
    public static void addCustomItemProperties(){
        makeBow(daiItems.GRAVITATION_BOW.get());
        makeBow(daiItems.ELECTROMAGNETIC_BOW.get());
    }

    private static void makeBow(Item item){
        ItemProperties.register(item, new ResourceLocation("pull"), (pStack, pLevel, pEntity, pSeed) -> {
            if(pEntity == null){
                return 0.0F;
            }
            else {
                return pEntity.getUseItem() != pStack ? 0.0F : (float) (pStack.getUseDuration() - pEntity.getUseItemRemainingTicks()) / 20.0F;
            }
        });
        ItemProperties.register(item, new ResourceLocation("pulling"), (pStack, pLevel, pEntity, pSeed) -> {
            return pEntity != null && pEntity.isUsingItem() && pEntity.getUseItem() == pStack ? 1.0F : 0.0F;
        });
    }
}