package net.Gmaj7.funny_world.datagen;

import net.Gmaj7.funny_world.daiItems.daiItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
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

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, daiItems.ACACIA_ICE_BOAT.get())
                .pattern("aba")
                .pattern("aaa")
                .define('a', Blocks.ACACIA_PLANKS)
                .define('b', Blocks.ICE)
                .unlockedBy("has_ice", has(Blocks.ICE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, daiItems.DARK_OAK_ICE_BOAT.get())
                .pattern("aba")
                .pattern("aaa")
                .define('a', Blocks.DARK_OAK_PLANKS)
                .define('b', Blocks.ICE)
                .unlockedBy("has_ice", has(Blocks.ICE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, daiItems.BAMBOO_ICE_RAFT.get())
                .pattern("aba")
                .pattern("aaa")
                .define('a', Blocks.BAMBOO_PLANKS)
                .define('b', Blocks.ICE)
                .unlockedBy("has_ice", has(Blocks.ICE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, daiItems.BIRCH_ICE_BOAT.get())
                .pattern("aba")
                .pattern("aaa")
                .define('a', Blocks.BIRCH_PLANKS)
                .define('b', Blocks.ICE)
                .unlockedBy("has_ice", has(Blocks.ICE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, daiItems.OAK_ICE_BOAT.get())
                .pattern("aba")
                .pattern("aaa")
                .define('a', Blocks.OAK_PLANKS)
                .define('b', Blocks.ICE)
                .unlockedBy("has_ice", has(Blocks.ICE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, daiItems.CHERRY_ICE_BOAT.get())
                .pattern("aba")
                .pattern("aaa")
                .define('a', Blocks.CHERRY_PLANKS)
                .define('b', Blocks.ICE)
                .unlockedBy("has_ice", has(Blocks.ICE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, daiItems.JUNGLE_ICE_BOAT.get())
                .pattern("aba")
                .pattern("aaa")
                .define('a', Blocks.JUNGLE_PLANKS)
                .define('b', Blocks.ICE)
                .unlockedBy("has_ice", has(Blocks.ICE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, daiItems.MANGROVE_ICE_BOAT.get())
                .pattern("aba")
                .pattern("aaa")
                .define('a', Blocks.MANGROVE_PLANKS)
                .define('b', Blocks.ICE)
                .unlockedBy("has_ice", has(Blocks.ICE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, daiItems.SPRUCE_ICE_BOAT.get())
                .pattern("aba")
                .pattern("aaa")
                .define('a', Blocks.SPRUCE_PLANKS)
                .define('b', Blocks.ICE)
                .unlockedBy("has_ice", has(Blocks.ICE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, daiItems.CLONE_PAPER.get())
                .pattern("aba")
                .pattern("aca")
                .pattern("aba")
                .define('a', Items.PAPER)
                .define('b', Items.EGG)
                .define('c', Items.NETHER_STAR)
                .unlockedBy("has_star", has(Items.NETHER_STAR)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, daiItems.WATER_BOW.get())
                .pattern(" ab")
                .pattern("a b")
                .pattern(" ab")
                .define('a', Items.POTION)
                .define('b', Items.STRING)
                .unlockedBy("has_potion", has(Items.POTION)).save(recipeOutput);
    }
}
