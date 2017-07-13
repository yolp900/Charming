package com.yolp900.charming.common.blocks.base;

import com.yolp900.charming.Charming;
import com.yolp900.charming.common.blocks.ModBlocks;
import com.yolp900.charming.reference.Reference;
import net.minecraft.block.BlockBush;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nonnull;

public class ModBlockBush extends BlockBush implements IModBlock, IPlantable {
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
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Desert;
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
    public boolean usesDefaultBlockRegistry() {
        return true;
    }

    @Override
    public boolean usesDefaultRenderRegistry() {
        return true;
    }
}
