package com.yolp900.charming.api.crafting.wandinteraction;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TransmutationStructure {
    private IBlockState blockClicked;
    private List<IBlockState> blocksSides = new ArrayList<>(4);
    private List<IBlockState> blocksCorners = new ArrayList<>(4);
    private IBlockState blockDown;

    public static final TransmutationStructure EMPTY = new TransmutationStructure(null, null, null, null);

    public TransmutationStructure(@Nullable IBlockState blockClicked, @Nullable List<IBlockState> blocksSides, @Nullable List<IBlockState> blocksCorners, @Nullable IBlockState blockDown) {
        this.blockClicked = blockClicked;
        this.blocksSides = blocksSides;
        this.blocksCorners = blocksCorners;
        this.blockDown = blockDown;
    }

    protected boolean matches(World world, EntityPlayer player, BlockPos pos) {
        if (this == EMPTY) {
            return true;
        }
        if (getBlockClicked() != null) {
            if (!blocksMatch(world.getBlockState(pos), getBlockClicked())) return false;
        }
        if (getBlocksSides() != null) {
            if (!blocksMatch(player, getBlocks(world, getSidePositions(pos)), getBlocksSides())) return false;
        }
        if (getBlocksCorners() != null) {
            if (!blocksMatch(player, getBlocks(world, getCornerPositions(pos)), getBlocksCorners())) return false;
        }
        if (getBlockDown() != null) {
            if (!blocksMatch(world.getBlockState(pos.down()), getBlockDown())) return false;
        }
        return true;
    }

    private boolean blocksMatch(@Nonnull IBlockState worldBlock, @Nonnull IBlockState recipeBlock) {
        return worldBlock.equals(recipeBlock);
    }

    protected boolean blocksMatch(EntityPlayer player, @Nonnull List<IBlockState> worldBlocks, @Nonnull List<IBlockState> recipeBlocks) {
        List<IBlockState> recipeBlocksCopy = new ArrayList<>(4);
        recipeBlocksCopy.addAll(recipeBlocks);
        for (int i = 0; i < recipeBlocksCopy.size(); i++) {
            IBlockState recipeBlock = recipeBlocksCopy.get(i);
            if (recipeBlock == null) continue;
            for (int j = 0; j < worldBlocks.size(); j++) {
                IBlockState worldBlock = worldBlocks.get(j);
                if (worldBlock == null) continue;
                if (blocksMatch(worldBlock, recipeBlock)) {
                    recipeBlocksCopy.set(i, null);
                    worldBlocks.set(j, null);
                    break;
                }
            }
        }
        for (IBlockState worldBlock : worldBlocks) {
            if (worldBlock != null) return false;
        }
        for (IBlockState recipeBlock : recipeBlocksCopy) {
            if (recipeBlock != null) return false;
        }
        return true;
    }

    public void removeBlocks(World world, BlockPos pos, EntityPlayer player) {
        if (getBlockClicked() != null) world.setBlockToAir(pos);
        if (getBlocksSides() != null) removeBlocks(world, player, getSidePositions(pos), getBlocksSides());
        if (getBlocksCorners() != null) removeBlocks(world, player, getCornerPositions(pos), getBlocksCorners());
    }

    protected void removeBlocks(World world, EntityPlayer player, List<BlockPos> positions, List<IBlockState> structureBlocks) {
        List<IBlockState> structureBlocksCopy = new ArrayList<>();
        structureBlocksCopy.addAll(structureBlocks);
        for (BlockPos pos : positions) {
            IBlockState worldBlock = world.getBlockState(pos);
            for (int i = 0; i < structureBlocksCopy.size(); i++) {
                if (blocksMatch(worldBlock, structureBlocksCopy.get(i))) {
                    world.setBlockToAir(pos);
                    structureBlocksCopy.remove(i);
                    break;
                }
            }
        }
    }

    private List<BlockPos> getSidePositions(BlockPos pos) {
        List<BlockPos> ret = new ArrayList<>(4);
        ret.add(pos.north());
        ret.add(pos.south());
        ret.add(pos.west());
        ret.add(pos.east());
        return ret;
    }

    private List<BlockPos> getCornerPositions(BlockPos pos) {
        List<BlockPos> ret = new ArrayList<>(4);
        ret.add(pos.north().west());
        ret.add(pos.north().east());
        ret.add(pos.south().west());
        ret.add(pos.south().east());
        return ret;
    }

    private List<IBlockState> getBlocks(World world, List<BlockPos> positions) {
        List<IBlockState> ret = new ArrayList<>();
        for (BlockPos pos : positions) {
            ret.add(world.getBlockState(pos));
        }
        return ret;
    }

    public IBlockState getBlockClicked() {
        return blockClicked;
    }

    public List<IBlockState> getBlocksSides() {
        return blocksSides;
    }

    public List<IBlockState> getBlocksCorners() {
        return blocksCorners;
    }

    public IBlockState getBlockDown() {
        return blockDown;
    }

}
