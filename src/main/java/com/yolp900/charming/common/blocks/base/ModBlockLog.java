package com.yolp900.charming.common.blocks.base;

import com.yolp900.charming.Charming;
import com.yolp900.charming.common.blocks.ModBlocks;
import com.yolp900.charming.reference.Reference;
import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public abstract class ModBlockLog extends BlockLog implements IModBlock {
    private String name;

    public ModBlockLog(String name) {
        this.name = name;
        this.setSoundType(SoundType.WOOD);
        ModBlocks.modBlocks.add(this);

        this.setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, LOG_AXIS);
    }

    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        IBlockState iblockstate = this.getDefaultState();

        switch (meta & 12) {
            case 0:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
                break;
            case 4:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
                break;
            case 8:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
                break;
            default:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE);
        }

        return iblockstate;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;

        switch (state.getValue(LOG_AXIS)) {
            case X:
                i |= 4;
                break;
            case Z:
                i |= 8;
                break;
            case NONE:
                i |= 12;
        }

        return i;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getBlockUnlocalizedName() {
        return Reference.MOD_PREFIX + getName();
    }

    @Nonnull
    @Override
    public ResourceLocation getBlockRegistryName() {
        return new ResourceLocation(Reference.MOD_ID, getName());
    }

    @Override
    public CreativeTabs getBlockCreativeTab() {
        return Charming.creativeTab;
    }

    @Override
    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
        return 2.0F;
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
