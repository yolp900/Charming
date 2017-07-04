package com.yolp900.charming.api;

import com.yolp900.charming.api.crafting.CraftingMechanic;
import com.yolp900.charming.api.crafting.constructiontable.RecipeConstructionTable;
import com.yolp900.charming.api.crafting.wandinteraction.RecipeWandInteraction;
import com.yolp900.charming.api.crafting.wandinteraction.RecipeWandInteractionBlock;
import com.yolp900.charming.api.crafting.wandinteraction.RecipeWandInteractionItem;
import com.yolp900.charming.api.crafting.wandinteraction.TransmutationStructure;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yolp900
 */
public class CharmingAPI {

    /**
     * Construction table recipes
     */
    public static class ConstructionTable {

        /**
         * The list of all construction table recipes
         */
        public static List<RecipeConstructionTable> constructionTableRecipeList = new ArrayList<>();

        /**
         * Registers a construction table recipe
         * @param output     - The recipe's output
         * @param gridInputs - The recipe's grid input
         * @param secInputs  - The recipe's secondary inputs
         * @param shapeless  - Whether the recipe is shapeless or not
         * @param infusion   - Whether you keep the secondary inputs or not
         * @return The registered recipe
         */
        public static RecipeConstructionTable registerConstructionTableRecipe(@Nonnull ItemStack output, List<Object> gridInputs, List<Object> secInputs, boolean shapeless, boolean infusion) {
            RecipeConstructionTable recipe = new RecipeConstructionTable(output, gridInputs, secInputs, shapeless, infusion);
            constructionTableRecipeList.add(recipe);
            return recipe;
        }

        /**
         * Registers a custom recipe. Use this in order to allow charming to recognise your recipes and use them.
         * @param recipe - The custom recipe.
         * @return the recipe after registration in Charming.
         */
        public static RecipeConstructionTable registerConstructionTableRecipe(@Nonnull RecipeConstructionTable recipe) {
            constructionTableRecipeList.add(recipe);
            return recipe;
        }

    }

    /**
     * Wand interaction recipes
     */
    public static class WandInteractions {

        /**
         * The list of all interaction recipes.
         */
        public static List<RecipeWandInteraction> interactionRecipeList = new ArrayList<>();

        /**
         * Registers a block interaction recipe.
         * @param output                - The recipe's output.
         * @param structure             - The recipe's block structure.
         * @param ingredients           - The recipe's item ingredients (on the transmuted block).
         * @param ingredientsToRemove   - The ingredients to remove from the world.
         * @param keepAroundBlocks      - Whether you keep the blocks or not.
         * @param minimalWandLevel      - minimal wand level for this recipe. 0 = wood, 1 = stone, 2 = iron, 3 = obsidian, 4 = diamond. set to -1 for an empty hand.
         * @return The registered recipe.
         */
        public static RecipeWandInteractionBlock registerBlockInteractionRecipe(@Nonnull IBlockState output, @Nonnull TransmutationStructure structure, @Nullable List<Object> ingredients, List<Object> ingredientsToRemove, boolean keepAroundBlocks, int minimalWandLevel) {
            RecipeWandInteractionBlock recipe = new RecipeWandInteractionBlock(output, structure, ingredients, ingredientsToRemove, keepAroundBlocks, minimalWandLevel);
            interactionRecipeList.add(recipe);
            return recipe;
        }

        /**
         * Registers an item interaction recipe.
         * @param outputs               - The recipe's outputs.
         * @param structure             - The recipe's block structure.
         * @param ingredients           - The recipe's item ingredients.
         * @param ingredientsToRemove   - The ingredients to remove from the world.
         * @param keepAroundBlocks      - Whether you keep the blocks or not.
         * @param minimalWandLevel      - minimal wand level for this recipe. 0 = wood, 1 = stone, 2 = iron, 3 = obsidian, 4 = diamond. set to -1 for an empty hand.
         * @return The registered recipe.
         */
        public static RecipeWandInteractionItem registerItemInteractionRecipe(@Nonnull NonNullList<ItemStack> outputs, @Nonnull TransmutationStructure structure, @Nullable List<Object> ingredients, List<Object> ingredientsToRemove, boolean keepAroundBlocks, int minimalWandLevel) {
            RecipeWandInteractionItem recipe = new RecipeWandInteractionItem(outputs, structure, ingredients, ingredientsToRemove, keepAroundBlocks, minimalWandLevel);
            interactionRecipeList.add(recipe);
            return recipe;
        }

        /**
         * Registers a custom recipe. Use this in order to allow charming to recognise your recipes and use them.
         * @param recipe - The custom recipe.
         */
        public static void registerInteractionRecipe(RecipeWandInteraction recipe) {
            interactionRecipeList.add(recipe);
        }

    }

    /**
     * Levitator blacklists.
     */
    public static class Levitator {
        /**
         * Blacklists for the levitator - Entities and ItemStacks.
         */
        public static List<Class<? extends Entity>> levitatorEntityBlacklist = new ArrayList<>();
        public static List<ItemStack> levitatorItemStackBlacklist = new ArrayList<>();

        /**
         * Blacklists an Entity from being moved by the levitator.
         * @param entityClass - the class for the blacklisted Entity.
         */
        public static void blacklistEntityFromLevitator(@Nonnull Class<? extends Entity> entityClass) {
            levitatorEntityBlacklist.add(entityClass);
        }

        /**
         * Checks if the provided Entity is blacklisted from being moved by the levitator.
         * @param entityClass - the class for the Entity.
         * @return Whether the entity is blacklisted.
         */
        public static boolean isEntityBlacklistedFromLevitator(@Nonnull Class<? extends Entity> entityClass) {
            return levitatorEntityBlacklist.contains(entityClass);
        }

        /**
         * Blacklist an ItemStack from bring moved by the levitator.
         * @param stack - the blacklisted stack. The ItemStack's metadata is checked, but not its NBT.
         */
        public static void blacklistItemStackFromLevitator(@Nonnull ItemStack stack) {
            levitatorItemStackBlacklist.add(stack);
        }

        /**
         * Checks if the provided ItemStack is blacklisted from being moved by the levitator.
         * @param stack - the ItemStack. The ItemStack's metadata is checked, but not its NBT.
         * @return Whether the ItemStack is blacklisted.
         */
        public static boolean isItemStackBlacklistedFromLevitator(@Nonnull ItemStack stack) {
            for (ItemStack blacklistedStack : levitatorItemStackBlacklist) {
                if (CraftingMechanic.areItemStacksEqualWOStackSize(stack, blacklistedStack)) {
                    return true;
                }
            }
            return false;
        }
    }

}
