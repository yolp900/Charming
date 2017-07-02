package com.yolp900.charming.common.world;

import com.yolp900.charming.common.blocks.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class TreeGenerator implements IWorldGenerator {

    private final int minTreeHeight;
    private final int treeHeightRange;
    private final IBlockState log;
    private final IBlockState leaves;

    public TreeGenerator(int minTreeHeight, int treeHeightRange, IBlockState log, IBlockState leaves) {
        this.minTreeHeight = minTreeHeight;
        this.treeHeightRange = treeHeightRange;
        this.log = log;
        this.leaves = leaves;
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {

    }

    public void generateTree(World world, Random random, BlockPos pos) {
        int height = random.nextInt(this.treeHeightRange) + this.minTreeHeight;
        int yPos = pos.getY();

        if(yPos >= 1 && yPos + height + 1 <= 256) {
            IBlockState state = world.getBlockState(pos.down());
            Block soil = state.getBlock();

            if (soil.canSustainPlant(state, world, pos.down(), EnumFacing.UP, ModBlocks.TintedSapling)) {
                soil.onPlantGrow(state, world, pos.down(), pos);
                placeCanopy(world, pos, height, random);
                placeTrunk(world, pos, height);
            }
        }
    }

    private void placeCanopy(World world, BlockPos pos, int height, Random rand) {
        pos = pos.up(height + 2);

        setLeaves(world, pos, LeafRarity.RARE, rand);

        pos = pos.down();

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 || j == 0) {
                    setLeaves(world, pos.add(i, 0, j), LeafRarity.ALWAYS, rand);
                }
                setLeaves(world, pos.add(i, 0, j), LeafRarity.COMMON, rand);
            }
        }

        pos = pos.down();

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (!(i == 0 && j == 0)){
                    setLeaves(world, pos.add(i, 0, j), LeafRarity.ALWAYS, rand);
                }
            }
        }

        setLeaves(world, pos.add(-2, 0, 0), LeafRarity.COMMON, rand);
        setLeaves(world, pos.add(0, 0, -2), LeafRarity.COMMON, rand);
        setLeaves(world, pos.add(2, 0, 0), LeafRarity.COMMON, rand);
        setLeaves(world, pos.add(0, 0, 2), LeafRarity.COMMON, rand);

        pos = pos.down();

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (!(i == 0 && j == 0)){
                    setLeaves(world, pos.add(i, 0, j), LeafRarity.ALWAYS, rand);
                }
            }
        }

        setLeaves(world, pos.add(-2, 0, 0), LeafRarity.COMMON, rand);
        setLeaves(world, pos.add(0, 0, -2), LeafRarity.COMMON, rand);
        setLeaves(world, pos.add(2, 0, 0), LeafRarity.COMMON, rand);
        setLeaves(world, pos.add(0, 0, 2), LeafRarity.COMMON, rand);

        pos = pos.down();

        setLeaves(world, pos.add(-1, 0, 0), LeafRarity.COMMON, rand);
        setLeaves(world, pos.add(0, 0, -1), LeafRarity.COMMON, rand);
        setLeaves(world, pos.add(1, 0, 0), LeafRarity.COMMON, rand);
        setLeaves(world, pos.add(0, 0, 1), LeafRarity.COMMON, rand);
    }

    private void placeTrunk(World world, BlockPos pos, int height) {
        while(height >= 0) {
            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();
            if(block.isAir(state, world, pos) || block.isReplaceable(world, pos) || block.isLeaves(state, world, pos)) {
                setLog(world, pos);
            }

            pos = pos.up();
            height--;
        }
    }

    private void setLog(World world, BlockPos pos) {
        setBlockAndMetadata(world, pos, log);
    }

    private void setLeaves(World world, BlockPos pos, LeafRarity rarity, Random rand) {
        if (rarity == LeafRarity.ALWAYS) {
            setBlockAndMetadata(world, pos, leaves);
        } else if (rarity == LeafRarity.COMMON) {
            if (rand.nextDouble() < 0.7) setBlockAndMetadata(world, pos, leaves);
        } else if (rarity == LeafRarity.RARE) {
            if (rand.nextDouble() < 0.3) setBlockAndMetadata(world, pos, leaves);
        }
    }

    private void setBlockAndMetadata(World world, BlockPos pos, IBlockState stateNew) {
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if(block.isAir(state, world, pos) || block.canPlaceBlockAt(world, pos) || world.getBlockState(pos) == leaves) {
            world.setBlockState(pos, stateNew, 2);
        }
    }

    private enum LeafRarity {
        ALWAYS,
        COMMON,
        RARE
    }

}
