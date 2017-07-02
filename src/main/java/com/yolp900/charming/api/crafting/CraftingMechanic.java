package com.yolp900.charming.api.crafting;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public abstract class CraftingMechanic {

    protected boolean matchesShaped(NonNullList<ItemStack> inputStacks, List<Object> recipeStacks) {
        if (inputStacks.size() != recipeStacks.size()) return false;
        for (int i = 0; i < recipeStacks.size(); i++) {
            Object recipeStack = recipeStacks.get(i);
            ItemStack inputStack = inputStacks.get(i);
            if (inputStack.isEmpty()) {
                if (recipeStack != null && recipeStack != ItemStack.EMPTY) return false;
            } else {
                if (inputStack.isEmpty() || !isStackTheSameAsRecipeObj(inputStacks.get(i), recipeStack)) return false;
            }
        }
        return true;
    }

    protected boolean matchesShapeless(NonNullList<ItemStack> inputs, List<Object> recipe) {
        List<Object> copyOfRecipe = new ArrayList<>();
        copyOfRecipe.addAll(recipe);
        NonNullList<ItemStack> copyOfInputs = NonNullList.create();
        copyOfInputs.addAll(inputs);
        for (int i = 0; i < copyOfInputs.size(); i++) {
            ItemStack currStack = copyOfInputs.get(i);
            if (currStack.isEmpty()) continue;
            for (int j = 0; j < copyOfRecipe.size(); j++) {
                Object recipeObj = copyOfRecipe.get(j);
                if (recipeObj == null || recipeObj == ItemStack.EMPTY) continue;
                if (isStackTheSameAsRecipeObj(currStack, recipeObj)) {
                    if (removeFromListsAfterMatching(recipeObj, j, copyOfRecipe, currStack, i, copyOfInputs)) {
                        break;
                    }
                }
            }
        }
        for (Object recipeObject : copyOfRecipe) {
            if (recipeObject != null) {
                return false;
            }
        }
        for (ItemStack stack : copyOfInputs) {
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    protected boolean isStackTheSameAsRecipeObj(ItemStack stack, Object recipeObj) {
        if (recipeObj == null || stack.isEmpty()) return false;
        if (recipeObj instanceof ItemStack) {
            return areItemStacksEqualWOStackSize(stack, (ItemStack) recipeObj);
        } else if (recipeObj instanceof Item) {
            return isStackTheSameAsRecipeObj(stack, new ItemStack((Item) recipeObj));
        } else if (recipeObj instanceof Block) {
            return isStackTheSameAsRecipeObj(stack, new ItemStack((Block) recipeObj));
        } else if (recipeObj instanceof String) {
            NonNullList<ItemStack> oreDictList = OreDictionary.getOres((String) recipeObj);
            for (ItemStack oreStack : oreDictList) {
                if (isStackTheSameAsRecipeObj(stack, oreStack)) {
                    return true;
                }
            }
        } else if (recipeObj instanceof OreDictStack) {
            NonNullList<ItemStack> oreDictList = OreDictionary.getOres(((OreDictStack) recipeObj).getOreDictEntry());
            for (ItemStack oreStack : oreDictList) {
                oreStack.setCount(((OreDictStack) recipeObj).getStackSize());
                if (isStackTheSameAsRecipeObj(stack, oreStack)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean areItemStacksEqualWOStackSize(ItemStack firstStack, ItemStack secStack) {
        return !firstStack.isEmpty() && firstStack.getItem() == secStack.getItem() && (!secStack.getHasSubtypes() || secStack.getMetadata() == firstStack.getMetadata()) && ItemStack.areItemStackTagsEqual(secStack, firstStack);
    }

    protected boolean removeFromListsAfterMatching(Object recipeObject, int recipeIndex, List<Object> recipe, ItemStack input, int inputsIndex, NonNullList<ItemStack> inputs) {
        if (recipeObject instanceof ItemStack) {
            ItemStack recipeStack = (ItemStack) recipeObject;
            if (recipeStack.getCount() > input.getCount()) {
                inputs.set(inputsIndex, ItemStack.EMPTY);
                ItemStack setStack = recipeStack.copy();
                setStack.setCount(recipeStack.getCount() - input.getCount());
                recipe.set(recipeIndex, setStack);
                return false;
            } else if (recipeStack.getCount() == input.getCount()) {
                inputs.set(inputsIndex, ItemStack.EMPTY);
                recipe.set(recipeIndex, null);
                return true;
            } else if (recipeStack.getCount() < input.getCount()) {
                ItemStack setStack = input.copy();
                setStack.setCount(input.getCount() - recipeStack.getCount());
                inputs.set(inputsIndex, setStack);
                recipe.set(recipeIndex, null);
                return true;
            }
        } else {
            if (recipeObject instanceof Item) {
                return removeFromListsAfterMatching(new ItemStack((Item) recipeObject), recipeIndex, recipe, input, inputsIndex, inputs);
            } else if (recipeObject instanceof Block) {
                return removeFromListsAfterMatching(new ItemStack((Block) recipeObject), recipeIndex, recipe, input, inputsIndex, inputs);
            } else if (recipeObject instanceof String) {
                NonNullList<ItemStack> oreDictList = OreDictionary.getOres((String) recipeObject);
                for (ItemStack oreStack : oreDictList) {
                    if (removeFromListsAfterMatching(oreStack, recipeIndex, recipe, input, inputsIndex, inputs)) {
                        return true;
                    }
                }
            } else if (recipeObject instanceof OreDictStack) {
                NonNullList<ItemStack> oreDictList = OreDictionary.getOres(((OreDictStack) recipeObject).getOreDictEntry());
                for (ItemStack oreStack : oreDictList) {
                    oreStack.setCount(((OreDictStack) recipeObject).getStackSize());
                    if (removeFromListsAfterMatching(oreStack, recipeIndex, recipe, input, inputsIndex, inputs)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
