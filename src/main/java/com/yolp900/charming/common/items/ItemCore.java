package com.yolp900.charming.common.items;

import com.yolp900.charming.common.items.base.IMetaItem;
import com.yolp900.charming.common.items.base.ModItem;
import com.yolp900.charming.reference.LibItems;
import com.yolp900.charming.reference.LibMisc;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class ItemCore extends ModItem implements IMetaItem {

    public ItemCore() {
        super(LibItems.CORE);
        this.setHasSubtypes(true);
    }

    @Override
    public int getNumOfTypes() {
        return EnumTypes.values().length;
    }

    @Override
    public String getTypeName(int meta) {
        return EnumTypes.values()[meta].getName();
    }

    @Override
    @Nonnull
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + "." + getTypeName(stack.getItemDamage());
    }

    @SideOnly (Side.CLIENT)
    public void getSubItems(@Nonnull Item item, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        for (int i = 0; i < getNumOfTypes(); i++) {
            subItems.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public boolean usesDefaultItemRegistry() {
        return true;
    }

    @Override
    public boolean usesDefaultRenderRegistry() {
        for (int i = 0; i < getNumOfTypes(); i++) {
            ModelResourceLocation mrl = new ModelResourceLocation(new ResourceLocation(getItemRegistryName().getResourceDomain(), getItemRegistryName().getResourcePath() + "_" + getTypeName(i).toLowerCase()), LibMisc.INVENTORY_VARIANT);
            ModelLoader.setCustomModelResourceLocation(this, i, mrl);
        }
        return false;
    }

    public enum EnumTypes implements IStringSerializable {
        Blank, Redstone, Motion, Repulsion;

        @Override
        @Nonnull
        public String getName() {
            return name();
        }
    }

}
