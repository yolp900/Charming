package com.yolp900.charming.common.blocks;

import com.yolp900.charming.api.tiles.IConvertible;
import com.yolp900.charming.common.blocks.base.ModBlock;
import com.yolp900.charming.common.tileentities.TileEntityAttractor;
import com.yolp900.charming.common.tileentities.TileEntityImpeller;
import com.yolp900.charming.reference.LibBlocks;
import com.yolp900.charming.reference.LibMisc;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockImpeller extends ModBlock implements IConvertible {
    public static final PropertyBool ON_STATE = PropertyBool.create(LibMisc.ON_STATE);
    public static final PropertyBool INVERTED = PropertyBool.create(LibMisc.INVERTED);
    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625, 0.0625, 0.0625, 0.9375, 0.9375, 0.9375);

    public BlockImpeller() {
        super(LibBlocks.IMPELLER, 1.5F, 10.F, Material.ROCK);

        this.setDefaultState(blockState.getBaseState().withProperty(ON_STATE, false).withProperty(INVERTED, false));
    }

    @Override
    @SideOnly(Side.CLIENT)
    @Nonnull
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    @Nonnull
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOUNDING_BOX;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
        return getBoundingBox(state, world, pos);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
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
    @Nonnull
    public IBlockState getActualState(@Nonnull IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile == null || !(tile instanceof TileEntityImpeller)) return super.getActualState(state, world, pos);
        TileEntityImpeller tileImpeller = (TileEntityImpeller) tile;
        return state.withProperty(ON_STATE, tileImpeller.isOn()).withProperty(INVERTED, tileImpeller.isInverted());
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
        return new TileEntityImpeller();
    }

    @Override
    public boolean usesDefaultBlockRegistry() {
        return true;
    }

    @Override
    public boolean usesDefaultRenderRegistry() {
        return true;
    }

    @Override
    public void convertState(World world, BlockPos pos, EntityPlayer player) {
        TileEntity tile = world.getTileEntity(pos);
        if (!(tile instanceof TileEntityImpeller)) return;
        boolean inverted = ((TileEntityImpeller) tile).isInverted();

        world.setBlockState(pos, ModBlocks.Attractor.getDefaultState());

        tile = world.getTileEntity(pos);
        if (tile instanceof TileEntityAttractor) {
            ((TileEntityAttractor) tile).setInverted(inverted);
        }
    }

}
