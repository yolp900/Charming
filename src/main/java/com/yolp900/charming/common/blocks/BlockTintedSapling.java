package com.yolp900.charming.common.blocks;

import com.yolp900.charming.common.blocks.base.ModBlockSapling;
import com.yolp900.charming.common.world.TreeGenerator;
import com.yolp900.charming.reference.LibBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.TerrainGen;

import javax.annotation.Nonnull;
import java.util.Random;

public class BlockTintedSapling extends ModBlockSapling {

    public BlockTintedSapling() {
        super(LibBlocks.TINTED_SAPLING);
    }

    @Override
    public void generateTree(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random rand) {
        if (!TerrainGen.saplingGrowTree(world, rand, pos)) return;

        TreeGenerator treeGen = new TreeGenerator(3, 2, ModBlocks.TintedLog.getDefaultState(), ModBlocks.TintedLeaves.getDefaultState());

        world.setBlockToAir(pos);

        treeGen.generateTree(world, rand, pos);
    }

}
