package com.yolp900.charming.common.blocks.base;

import com.yolp900.charming.Charming;
import com.yolp900.charming.common.blocks.ModBlocks;
import com.yolp900.charming.reference.Reference;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

public abstract class ModBlockSapling extends BlockSapling implements IModBlock {
    private String name;

    public ModBlockSapling(String name) {
        this.name = name;
        this.setSoundType(SoundType.PLANT);
        ModBlocks.modBlocks.add(this);

        this.setDefaultState(this.blockState.getBaseState().withProperty(STAGE, 0));
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
    public float getBlockHardness(IBlockState state, World world, BlockPos pos) {
        return 0.0F;
    }

    @Override
    public abstract void generateTree(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random rand);

    @Override
    public void getSubBlocks(@Nonnull Item item, CreativeTabs tab, @Nonnull NonNullList<ItemStack> list) {
        list.add(new ItemStack(item, 1, 0));
    }

    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(STAGE, (meta & 8) >> 3);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        i = i | state.getValue(STAGE) << 3;
        return i;
    }

}
