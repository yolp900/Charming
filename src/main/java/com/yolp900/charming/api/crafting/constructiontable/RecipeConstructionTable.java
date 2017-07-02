package com.yolp900.charming.api.crafting.constructiontable;

import com.yolp900.charming.Charming;
import com.yolp900.charming.api.crafting.CraftingMechanic;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class RecipeConstructionTable extends CraftingMechanic {
    private ItemStack output;
    private List<Object> gridInputs = new ArrayList<>();
    private List<Object> secInputs = new ArrayList<>();
    private boolean shapeless;
    private boolean infusion;

    public RecipeConstructionTable(@Nonnull ItemStack output, List<Object> gridInputs, List<Object> secInputs, boolean shapeless, boolean infusion) {
        this.output = output;
        this.gridInputs = gridInputs;
        this.secInputs = secInputs;
        this.shapeless = shapeless;
        this.infusion = infusion;
    }

    public boolean matches(NonNullList<ItemStack> grid, NonNullList<ItemStack> sec) {
        boolean gridMatches;
        if (isShapeless()) {
            gridMatches = matchesShapeless(grid, getRecipeGridInputs());
        } else {
            gridMatches = matchesShaped(grid, getRecipeGridInputs());
        }
        return gridMatches && matchesShapeless(sec, getRecipeSecInputs());
    }

    public ItemStack getOutput() {
        return output;
    }

    private List<Object> getRecipeGridInputs() {
        return gridInputs;
    }

    private List<Object> getRecipeSecInputs() {
        return secInputs;
    }

    public boolean isShapeless() {
        return shapeless;
    }

    public boolean isInfusion() {
        return infusion;
    }
}
