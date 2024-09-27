package net.Gmaj7.funny_world.daiInit;

import net.Gmaj7.funny_world.daiItems.daiItems;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class daiTiers {
    public static final Tier JISTGABBURASH = new SimpleTier(
            daiTags.daiBlockTags.JISTGABBURASH_CANT_BREAK, 63, 5, 0, 20,  () -> Ingredient.of(daiItems.JISTGABBURASH.get()));
}
