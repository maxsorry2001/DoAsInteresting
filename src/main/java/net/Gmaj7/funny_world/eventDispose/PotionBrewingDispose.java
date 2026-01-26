package net.Gmaj7.funny_world.eventDispose;

import net.Gmaj7.funny_world.FunnyWorld;
import net.Gmaj7.funny_world.daiItems.daiPotions;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

@EventBusSubscriber(modid = FunnyWorld.MODID)
public class PotionBrewingDispose {
    
    @SubscribeEvent
    public static void brewing(RegisterBrewingRecipesEvent event){
        PotionBrewing.Builder builder = event.getBuilder();
        builder.addMix(Potions.POISON, Items.GOLD_INGOT, daiPotions.GENE_MUTATION_POTION);
        builder.addMix(Potions.AWKWARD, Items.ECHO_SHARD, daiPotions.ECHO);
    }
}
