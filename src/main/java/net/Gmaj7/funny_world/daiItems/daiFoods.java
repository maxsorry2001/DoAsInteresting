package net.Gmaj7.funny_world.daiItems;

import net.minecraft.world.food.FoodProperties;

public class daiFoods {
    public static final FoodProperties EDIBLE_FLINT_AND_STEEL = new FoodProperties.Builder()
            .nutrition(4)
            .saturationModifier(0.2F)
            .alwaysEdible()
            .build();
}
