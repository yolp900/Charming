package com.yolp900.charming.common.items.base;

import com.yolp900.charming.Charming;
import com.yolp900.charming.common.items.ModItems;
import com.yolp900.charming.reference.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public abstract class ModItem extends Item implements IModItem {

    private String name;

    public ModItem(String name) {
        this.name = name;
        this.setUnlocalizedName(getItemUnlocalizedName());
        this.setRegistryName(getItemRegistryName());
        this.setCreativeTab(getItemCreativeTab());
        if (this instanceof IMetaItem) {
            setHasSubtypes(true);
        }
        ModItems.modItems.add(this);
    }

    public String getName() {
        return name;
    }

    @Override
    public String getItemUnlocalizedName() {
        return Reference.MOD_PREFIX + getName();
    }

    @Nonnull
    @Override
    public ResourceLocation getItemRegistryName() {
        return new ResourceLocation(Reference.MOD_ID, getName());
    }

    @Override
    public CreativeTabs getItemCreativeTab() {
        return Charming.creativeTab;
    }

    @Override
    public int getMetadata(int damage) {
        if (this instanceof IMetaItem) {
            return damage;
        } else {
            return super.getMetadata(damage);
        }
    }

}
