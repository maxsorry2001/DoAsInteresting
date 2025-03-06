package net.Gmaj7.funny_world.daiInit;

import net.Gmaj7.funny_world.daiItems.daiItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class daiItemProperties {
    public static void addCustomItemProperties(){
        makeBow(daiItems.GRAVITATION_BOW.get());
        makeBow(daiItems.ELECTROMAGNETIC_BOW.get());
        makeThunderSword(daiItems.THUNDER_SWORD.get());
        makeFlame(Items.WOODEN_SWORD);
        makeFlame(Items.STONE_SWORD);
        makeFlame(Items.IRON_SWORD);
        makeFlame(Items.GOLDEN_SWORD);
        makeFlame(Items.DIAMOND_SWORD);
        makeFlame(Items.NETHERITE_SWORD);
        makeMahjong(daiItems.MAHJONG.get());
        makeHoneyBottle(Items.HONEY_BOTTLE);
    }

    private static void makeBow(Item item){
        ItemProperties.register(item, ResourceLocation.parse("pull"), (pStack, pLevel, pEntity, pSeed) -> {
            if(pEntity == null){
                return 0.0F;
            }
            else {
                return pEntity.getUseItem() != pStack ? 0.0F : (float) (pStack.getUseDuration(pEntity) - pEntity.getUseItemRemainingTicks()) / 20.0F;
            }
        });
        ItemProperties.register(item, ResourceLocation.parse("pulling"), (pStack, pLevel, pEntity, pSeed) -> {
            return pEntity != null && pEntity.isUsingItem() && pEntity.getUseItem() == pStack ? 1.0F : 0.0F;
        });
    }

    private static void makeThunderSword(Item item){
        ItemProperties.register(item, ResourceLocation.parse("cannot_shoot"), (pStack, pLevel, pEntity, pSeed) -> {
            return pStack.has(daiDataComponentTypes.CANNOT_SHOOT) ? 1.0F : 0.0F;
        });
        ItemProperties.register(item, ResourceLocation.parse("block_pos"), (pStack, pLevel, pEntity, pSeed) -> {
            return pStack.has(daiDataComponentTypes.BLOCKPOS) ? 1.0F : 0.0F;
        });
    }

    private static void makeFlame(Item item){
        ItemProperties.register(item, ResourceLocation.parse("flame"), ((pStack, pLevel, pEntity, pSeed) -> {
            return pStack.has(daiDataComponentTypes.HEAT_BY_FRICTION) ? 1.0F : 0.0F;
        }));
    }

    private static void makeMahjong(Item item){
        ItemProperties.register(item, ResourceLocation.parse("mahjong"), ((pStack, pLevel, pEntity, pSeed) -> {
            return pStack.get(daiDataComponentTypes.MAHJONG_POINTS) > 0 && pStack.get(daiDataComponentTypes.MAHJONG_PATTERN) > 0 ?
                    pStack.get(daiDataComponentTypes.MAHJONG_PATTERN) + 0.1F * pStack.get(daiDataComponentTypes.MAHJONG_POINTS) : 0.0F;
        }));
    }

    private static void makeHoneyBottle(Item item){
        ItemProperties.register(item, ResourceLocation.parse("absorbed"), ((pStack, pLevel, pEntity, pSpeed) -> {
            return pStack.has(daiDataComponentTypes.HONEY_EFFECTS) && !pStack.get(daiDataComponentTypes.HONEY_EFFECTS).effects().isEmpty() ?
                    1.0F : 0.0F;
        }));
    }
}