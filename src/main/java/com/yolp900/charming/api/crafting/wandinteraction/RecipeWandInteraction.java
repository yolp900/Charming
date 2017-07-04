package com.yolp900.charming.api.crafting.wandinteraction;

import com.yolp900.charming.api.crafting.CraftingMechanic;
import com.yolp900.charming.api.crafting.OreDictStack;
import com.yolp900.charming.api.items.ITransmutationWand;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class RecipeWandInteraction extends CraftingMechanic {
    private TransmutationStructure structure;
    private List<Object> ingredients = new ArrayList<>();
    private List<Object> ingredientsToRemove = new ArrayList<>();
    private boolean keepsAroundBlocks;
    private int minimalWandLevel;

    public RecipeWandInteraction(@Nonnull TransmutationStructure structure, @Nullable List<Object> ingredients, List<Object> ingredientsToRemove, boolean keepAroundBlocks, int minimalWandLevel) {
        this.structure = structure;
        this.ingredients = ingredients;
        this.ingredientsToRemove = ingredientsToRemove;
        this.keepsAroundBlocks = keepAroundBlocks;
        this.minimalWandLevel = minimalWandLevel;
    }

    public boolean matches(World world, EntityPlayer player, ItemStack wand, BlockPos pos, List<EntityItem> ingredients) {
        return player.isSneaking() && matchesPlayerWand(player, wand, getMinimalWandLevel()) && matchesStructure(world, player, pos, getStructure()) && matchesIngredients(ingredients, getIngredients());
    }

    protected boolean matchesPlayerWand(EntityPlayer player, ItemStack wand, int minimalWandLevel) {
        if (wand == null || wand.isEmpty()) {
            return minimalWandLevel == -1;
        } else if (wand.getItem() instanceof ITransmutationWand) {
            return ((ITransmutationWand) wand.getItem()).getWandTransmutationLevel(wand) >= minimalWandLevel;
        }
        return false;
    }

    protected boolean matchesStructure(World world, EntityPlayer player, BlockPos pos, TransmutationStructure structure) {
        return structure == null || structure.matches(world, player, pos);
    }

    protected boolean matchesIngredients(List<EntityItem> worldIngredients, List<Object> recipeIngredients) {
        if (recipeIngredients == null) return true;
        List<Object> recipeIngredientsCopy = new ArrayList<>();
        recipeIngredientsCopy.addAll(recipeIngredients);
        NonNullList<ItemStack> worldItemStacks = NonNullList.create();
        for (EntityItem item : worldIngredients) {
            if (!item.getItem().isEmpty()) worldItemStacks.add(item.getItem());
        }
        for (int i = 0; i < recipeIngredientsCopy.size(); i++) {
            Object recipeObj = recipeIngredientsCopy.get(i);
            for (int j = 0; j < worldItemStacks.size(); j++) {
                ItemStack worldIngredient = worldItemStacks.get(j);
                if (isStackTheSameAsRecipeObj(worldIngredient, recipeObj)) {
                    if (removeFromListsAfterMatching(recipeObj, i, recipeIngredientsCopy, worldIngredient, j, worldItemStacks)) {
                        break;
                    }
                }
            }
        }
        for (Object recipeIngredient : recipeIngredientsCopy) {
            if (!(recipeIngredient == null || recipeIngredient == ItemStack.EMPTY || recipeIngredient == Blocks.AIR.getDefaultState())) {
                return false;
            }
        }
        return true;
    }

    public abstract List<EntityItem> handleInteractionReturnWorldItems(World world, EntityPlayer player, ItemStack stack, BlockPos pos, List<EntityItem> entityItems);

    protected void removeBlocks(World world, BlockPos pos, EntityPlayer player, TransmutationStructure structure) {
        if (structure == null) return;
        structure.removeBlocks(world, pos, player);
    }

    protected List<EntityItem> removeItemStacks(World world, List<EntityItem> worldIngredients, List<Object> ingredientsToRemove) {
        if (ingredientsToRemove == null) return worldIngredients;
        if (!world.isRemote) {
            List<Object> recipeIngredientsCopy = new ArrayList<>();
            recipeIngredientsCopy.addAll(ingredientsToRemove);
            for (int i = 0; i < recipeIngredientsCopy.size(); i++ ){
                Object recipeObject = recipeIngredientsCopy.get(i);
                for (int j = 0; j < worldIngredients.size(); j++) {
                    EntityItem worldIngredient = worldIngredients.get(j);
                    if (isStackTheSameAsRecipeObj(worldIngredient.getItem(), recipeObject)) {
                        if (removeItemStackFromWorldAfterMatching(world, recipeObject, i, recipeIngredientsCopy, worldIngredient, j, worldIngredients)) {
                            break;
                        }
                    }
                }
            }
        }
        return worldIngredients;
    }

    protected boolean removeItemStackFromWorldAfterMatching(World world, Object recipeObject, int recipeIndex, List<Object> recipe, EntityItem worldIngredient, int worldIngredientsIndex, List<EntityItem> worldIngredients) {
        if (recipeObject instanceof ItemStack) {
            ItemStack recipeStack = (ItemStack) recipeObject;
            if (recipeStack.getCount() > worldIngredient.getItem().getCount()) {
                worldIngredients.remove(worldIngredientsIndex);
                world.removeEntity(worldIngredient);
                worldIngredient.setDead();
                ItemStack setStack = recipeStack.copy();
                setStack.setCount(recipeStack.getCount() - worldIngredient.getItem().getCount());
                recipe.set(recipeIndex, setStack);
                return false;
            } else if (recipeStack.getCount() == worldIngredient.getItem().getCount()) {
                worldIngredients.remove(worldIngredientsIndex);
                world.removeEntity(worldIngredient);
                worldIngredient.setDead();
                recipe.set(recipeIndex, null);
                return true;
            } else if (recipeStack.getCount() < worldIngredient.getItem().getCount()) {
                ItemStack setStack = worldIngredient.getItem().copy();
                setStack.setCount(worldIngredient.getItem().getCount() - recipeStack.getCount());
                worldIngredient.setItem(setStack);
                recipe.set(recipeIndex, null);
                return true;
            }
        } else {
            if (recipeObject instanceof Item) {
                return removeItemStackFromWorldAfterMatching(world, new ItemStack((Item) recipeObject), recipeIndex, recipe, worldIngredient, worldIngredientsIndex, worldIngredients);
            } else if (recipeObject instanceof Block) {
                return removeItemStackFromWorldAfterMatching(world, new ItemStack((Block) recipeObject), recipeIndex, recipe, worldIngredient, worldIngredientsIndex, worldIngredients);
            } else if (recipeObject instanceof String) {
                NonNullList<ItemStack> oreDictList = OreDictionary.getOres((String) recipeObject);
                for (ItemStack oreStack : oreDictList) {
                    if (removeItemStackFromWorldAfterMatching(world, oreStack, recipeIndex, recipe, worldIngredient, worldIngredientsIndex, worldIngredients)) {
                        return true;
                    }
                }
            } else if (recipeObject instanceof OreDictStack) {
                NonNullList<ItemStack> oreDictList = OreDictionary.getOres(((OreDictStack) recipeObject).getOreDictEntry());
                for (ItemStack oreStack : oreDictList) {
                    oreStack.setCount(((OreDictStack) recipeObject).getStackSize());
                    if (removeItemStackFromWorldAfterMatching(world, oreStack, recipeIndex, recipe, worldIngredient, worldIngredientsIndex, worldIngredients)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public List<Object> getIngredients() {
        return ingredients;
    }

    public List<Object> getIngredientsToRemove() {
        return ingredientsToRemove;
    }

    public boolean keepsAroundBlocks() {
        return keepsAroundBlocks;
    }

    public int getMinimalWandLevel() {
        return minimalWandLevel;
    }

    public TransmutationStructure getStructure() {
        return structure;
    }
}
