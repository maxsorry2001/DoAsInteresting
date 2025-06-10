package net.Gmaj7.funny_world.datagen;

import net.Gmaj7.funny_world.daiItems.daiItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class daiRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public daiRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, daiItems.HUMANITY_POCKET.get())
                .pattern("a a")
                .pattern("aba")
                .pattern("aaa")
                .define('a', Items.STRING)
                .define('b', Items.ROTTEN_FLESH)
                .unlockedBy("has_string", has(Items.STRING)).save(recipeOutput);
    }
}
