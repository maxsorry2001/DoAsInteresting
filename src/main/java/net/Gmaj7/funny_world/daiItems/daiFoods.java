package net.Gmaj7.funny_world.daiItems;

import net.minecraft.world.food.FoodProperties;

public class daiFoods {
    public static final FoodProperties EDIBLE_FLINT_AND_STEEL = new FoodProperties.Builder()
            .nutrition(4)
            .saturationModifier(0.2F)
            .alwaysEdible()
            .build();

    public static final FoodProperties EAT_OF_WORLDS_LV1 = new FoodProperties.Builder()
            .nutrition(5)
            .saturationModifier(0.3F)
            .build();

    public static final FoodProperties EAT_OF_WORLDS_LV2 = new FoodProperties.Builder()
            .nutrition(5)
            .saturationModifier(0.6F)
            .build();

    public static final FoodProperties EAT_OF_WORLDS_LV3 = new FoodProperties.Builder()
            .nutrition(5)
            .saturationModifier(0.9F)
            .build();
}
