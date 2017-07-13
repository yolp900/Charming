package com.yolp900.charming.common.blocks.base;

import com.yolp900.charming.Charming;
import com.yolp900.charming.common.blocks.ModBlocks;
import com.yolp900.charming.reference.Reference;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

import javax.annotation.Nonnull;

public abstract class ModBlockBush extends BlockBush implements IModBlock {
    private String name;

    public ModBlockBush(String name) {
        super();
        this.name = name;
        ModBlocks.modBlocks.add(this);
    }

    public String getName() {
        return name;
    }

    @Override
    @Nonnull
    public abstract EnumPlantType getPlantType(IBlockAccess world, BlockPos pos);

    @Override
    protected abstract boolean canSustainBush(IBlockState state);

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return canSustainBush(world.getBlockState(pos));
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

}
