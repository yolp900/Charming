package com.yolp900.charming.common.crafting;

import com.yolp900.charming.api.CharmingAPI;
import com.yolp900.charming.api.crafting.constructiontable.RecipeConstructionTable;
import com.yolp900.charming.common.inventory.ContainerDummy;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ConstructionTableCraftingHandler {

    @Nonnull
    public static ItemStack getVanillaRecipeOutput(NonNullList<ItemStack> grid, World world) {
        InventoryCrafting ic = new InventoryCrafting(new ContainerDummy(), 3, 3);
        for (int i = 0; i < 9; i++) {
            ic.setInventorySlotContents(i, grid.get(i));
        }
        return CraftingManager.getInstance().findMatchingRecipe(ic, world);
    }

    @Nonnull
    public static ItemStack getCTShapedRecipeOutput(NonNullList<ItemStack> grid, NonNullList<ItemStack> sec) {
        for (RecipeConstructionTable recipe : CharmingAPI.ConstructionTable.constructionTableRecipeList) {
            if (!recipe.isShapeless() && !recipe.isInfusion() && recipe.matches(grid, sec)) return recipe.getOutput();
        }
        return ItemStack.EMPTY;
    }

    @Nonnull
    public static ItemStack getCTShapelessRecipeOutput(NonNullList<ItemStack> grid, NonNullList<ItemStack> sec) {
        for (RecipeConstructionTable recipe : CharmingAPI.ConstructionTable.constructionTableRecipeList) {
            if (recipe.isShapeless() && !recipe.isInfusion() && recipe.matches(grid, sec)) return recipe.getOutput();
        }
        return ItemStack.EMPTY;
    }

    @Nonnull
    public static ItemStack getCTInfuseRecipeOutput(NonNullList<ItemStack> grid, NonNullList<ItemStack> sec) {
        for (RecipeConstructionTable recipe : CharmingAPI.ConstructionTable.constructionTableRecipeList) {
            if (recipe.isInfusion() && recipe.matches(grid, sec)) return recipe.getOutput();
        }
        return ItemStack.EMPTY;
    }

    public static RecipeType getCurrentRecipeType(NonNullList<ItemStack> gridInputs, NonNullList<ItemStack> secInputs, World world) {
        RecipeType currentType = RecipeType.None;

        if (!getVanillaRecipeOutput(gridInputs, world).isEmpty()) currentType = RecipeType.Vanilla;
        if (!getCTShapelessRecipeOutput(gridInputs, secInputs).isEmpty()) currentType = RecipeType.CTShapeless;
        if (!getCTShapedRecipeOutput(gridInputs, secInputs).isEmpty()) currentType = RecipeType.CTShaped;
        if (!getCTInfuseRecipeOutput(gridInputs, secInputs).isEmpty()) currentType = RecipeType.CTInfuse;

        return currentType;
    }

    public enum RecipeType {
        None, Vanilla, // Shaped and Shapeless, doesn't matter.
        CTShaped, CTShapeless, CTInfuse // Shaped and Shapeless, doesn't matter
    }

}
