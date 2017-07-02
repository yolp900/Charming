package com.yolp900.charming.api.crafting.wandinteraction;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class RecipeWandInteractionItem extends RecipeWandInteraction {
    private NonNullList<ItemStack> outputs;

    public RecipeWandInteractionItem(@Nonnull NonNullList<ItemStack> outputs, @Nonnull TransmutationStructure structure, @Nullable List<Object> ingredients, boolean keepItems, boolean keepAroundBlocks, int minimalWandLevel) {
        super(structure, ingredients, keepItems, keepAroundBlocks, minimalWandLevel);
        this.outputs = outputs;
    }

    @Override
    public void handleInteraction(World world, EntityPlayer player, ItemStack stack, BlockPos pos, List<EntityItem> entityItems) {
        if (!keepsAroundBlocks()) {
            removeBlocks(world, pos, player, getStructure());
        }
        if (!keepsItems()) {
            removeItemStacks(world, entityItems, getIngredients());
        }
        spawnOutputs(world, pos, getOutputs());
    }

    private void spawnOutputs(World world, BlockPos pos, NonNullList<ItemStack> outputs) {
        if (!world.isRemote) {
            for (ItemStack output : outputs) {
                InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY() + 1, pos.getZ(), output.copy());
            }
        }
    }

    public NonNullList<ItemStack> getOutputs() {
        return outputs;
    }

}
