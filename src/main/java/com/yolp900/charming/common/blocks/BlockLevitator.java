package com.yolp900.charming.common.blocks;

import com.yolp900.charming.common.blocks.base.ModBlock;
import com.yolp900.charming.common.tileentities.TileEntityLevitator;
import com.yolp900.charming.reference.LibBlocks;
import com.yolp900.charming.reference.LibMisc;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockLevitator extends ModBlock {
    public static final PropertyBool ON_STATE = PropertyBool.create(LibMisc.ON_STATE); //On = true, Off = false.
    public static final PropertyBool INVERTED = PropertyBool.create(LibMisc.INVERTED);

    public BlockLevitator() {
        super(LibBlocks.LEVITATOR);
        this.setDefaultState(blockState.getBaseState().withProperty(ON_STATE, false).withProperty(INVERTED, false));
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, ON_STATE, INVERTED);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        return super.getStateFromMeta(meta);
    }

    @Override
    public float getBlockHardness(IBlockState state, World world, BlockPos pos) {
        return 3F;
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, @Nullable Entity exploder, Explosion explosion) {
        return 7F;
    }

    @Override
    @Nonnull
    public Material getMaterial(IBlockState state) {
        return Material.WOOD;
    }

    @Override
    @Nonnull
    public IBlockState getActualState(@Nonnull IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile == null || !(tile instanceof TileEntityLevitator)) return super.getActualState(state, world, pos);
        TileEntityLevitator tileLevitator = (TileEntityLevitator) tile;
        return state.withProperty(ON_STATE, tileLevitator.on()).withProperty(INVERTED, tileLevitator.isInverted());
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
        return new TileEntityLevitator();
    }

    @Override
    public boolean usesDefaultBlockRegistry() {
        return true;
    }

    @Override
    public boolean usesDefaultRenderRegistry() {
        return true;
    }
}
