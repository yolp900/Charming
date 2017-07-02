package com.yolp900.charming.common.items.base;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public interface IModItem {

    String getItemUnlocalizedName();

    @Nonnull
    ResourceLocation getItemRegistryName();

    CreativeTabs getItemCreativeTab();

    void registerItem();

    void registerRender();

}
