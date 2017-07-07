package com.yolp900.charming.common.blocks;

import com.yolp900.charming.common.blocks.base.ModMetaBlock;
import com.yolp900.charming.reference.LibBlocks;
import com.yolp900.charming.reference.LibMisc;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockFlower extends ModMetaBlock implements IPlantable {
    public static final PropertyEnum<EnumTypes> TYPE = PropertyEnum.create(LibMisc.BLOCK_TYPE, EnumTypes.class);

    public BlockFlower() {
        super(LibBlocks.FLOWER);
        this.setSoundType(SoundType.PLANT);

        this.setDefaultState(blockState.getBaseState().withProperty(TYPE, EnumTypes.DesertRose));
    }

    @Override
    protected IProperty<?> getTypeEnum() {
        return TYPE;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE).getMetadata();
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        if (meta >= EnumTypes.values().length) {
            meta = 0;
        }
        return getDefaultState().withProperty(TYPE, EnumTypes.values()[meta]);
    }

    @Override
    protected int getNumOfTypes() {
        return EnumTypes.values().length;
    }

    @Override
    public String getTypeName(int meta) {
        return EnumTypes.values()[meta].getName();
    }

    @Override
    protected float getBlockHardness(int meta) {
        return EnumTypes.values()[meta].getHardness();
    }

    @Override
    protected float getExplosionResistance(int meta) {
        return EnumTypes.values()[meta].getResistance();
    }

    @Nonnull
    @Override
    protected Material getMaterial(int meta) {
        return EnumTypes.values()[meta].getMaterial();
    }

    @Nonnull
    @Override
    protected MapColor getMapColor(int meta) {
        return EnumTypes.values()[meta].getMapColor();
    }

    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0.30000001192092896D, 0.0D, 0.30000001192092896D, 0.699999988079071D, 0.6000000238418579D, 0.699999988079071D);
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, @Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos) {
        return Block.NULL_AABB;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Nonnull
    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Desert;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
        return null;
    }

    public enum EnumTypes implements IEnumType {
        DesertRose;

        @Nonnull
        @Override
        public String getName() {
            return name().toLowerCase();
        }

        @Override
        public int getMetadata() {
            return ordinal();
        }

        @Override
        public float getHardness() {
            return 0;
        }

        @Override
        public float getResistance() {
            return 0;
        }

        @Nonnull
        @Override
        public Material getMaterial() {
            return Material.PLANTS;
        }

        @Nonnull
        @Override
        public MapColor getMapColor() {
            return getMaterial().getMaterialMapColor();
        }
    }

}
