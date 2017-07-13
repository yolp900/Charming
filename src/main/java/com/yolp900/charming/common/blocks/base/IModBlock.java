package com.yolp900.charming.common.blocks.base;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public interface IModBlock {

    String getBlockUnlocalizedName();

    @Nonnull
    ResourceLocation getBlockRegistryName();

    CreativeTabs getBlockCreativeTab();

    boolean usesDefaultBlockRegistry();

    boolean usesDefaultRenderRegistry();

}
