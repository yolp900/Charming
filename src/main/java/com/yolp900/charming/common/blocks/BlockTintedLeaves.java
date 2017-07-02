package com.yolp900.charming.common.blocks;

import com.yolp900.charming.common.blocks.base.ModBlockLeaves;
import com.yolp900.charming.reference.LibBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.Random;

public class BlockTintedLeaves extends ModBlockLeaves {

    public BlockTintedLeaves() {
        super(LibBlocks.TINTED_LEAVES);
    }

    @Override
    protected NonNullList<ItemStack> getStackDropped(IBlockState state, Random rand, int fortune) {
        return NonNullList.withSize(1, new ItemStack(ModBlocks.TintedSapling));
    }
}
