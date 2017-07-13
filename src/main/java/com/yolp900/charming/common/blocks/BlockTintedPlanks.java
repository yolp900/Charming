package com.yolp900.charming.common.blocks;

import com.yolp900.charming.common.blocks.base.ModBlock;
import com.yolp900.charming.reference.LibBlocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockTintedPlanks extends ModBlock {

    public BlockTintedPlanks() {
        super(LibBlocks.TINTED_PLANKS);
        this.setSoundType(SoundType.WOOD);
    }

    @Override
    public float getBlockHardness(IBlockState state, World world, BlockPos pos) {
        return 2.0F;
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, @Nullable Entity exploder, Explosion explosion) {
        return 5.0F;
    }

    @Nonnull
    @Override
    public Material getMaterial(IBlockState state) {
        return Material.WOOD;
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
