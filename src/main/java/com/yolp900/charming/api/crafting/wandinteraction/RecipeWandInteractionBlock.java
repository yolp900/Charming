package com.yolp900.charming.api.crafting.wandinteraction;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class RecipeWandInteractionBlock extends RecipeWandInteraction {
    private IBlockState output;

    public RecipeWandInteractionBlock(@Nonnull IBlockState output, @Nonnull TransmutationStructure structure, @Nullable List<Object> ingredients, boolean keepItems, boolean keepAroundBlocks, int minimalWandLevel) {
        super(structure, ingredients, keepItems, keepAroundBlocks, minimalWandLevel);
        this.output = output;
    }

    @Override
    public void handleInteraction(World world, EntityPlayer player, ItemStack stack, BlockPos pos, List<EntityItem> entityItems) {
        if (!keepsAroundBlocks()) {
            removeBlocks(world, pos, player, getStructure());
        }
        changeBlock(world, pos, player, getOutput());
        if (!keepsItems()) {
            removeItemStacks(world, entityItems, getIngredients());
        }
    }

    protected void changeBlock(World world, BlockPos pos, EntityPlayer player, IBlockState recipeOutput) {
        if (!world.isRemote) {
            world.setBlockState(pos, recipeOutput);
        }
    }

    public IBlockState getOutput() {
        return output;
    }
}
