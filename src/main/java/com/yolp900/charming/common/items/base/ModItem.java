package com.yolp900.charming.common.items.base;

import com.yolp900.charming.Charming;
import com.yolp900.charming.common.items.ModItems;
import com.yolp900.charming.reference.LibMisc;
import com.yolp900.charming.reference.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public abstract class ModItem extends Item implements IModItem {

    private String name;

    public ModItem(String name) {
        this.name = name;
        this.setUnlocalizedName(getItemUnlocalizedName());
        this.setRegistryName(getItemRegistryName());
        this.setCreativeTab(getItemCreativeTab());
        ModItems.modItems.add(this);
        if (this instanceof IMetaItem) {
            setHasSubtypes(true);
        }
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
    @Nonnull
    public String getUnlocalizedName(ItemStack stack) {
        if (stack.getItem() instanceof IMetaItem) {
            IMetaItem item = (IMetaItem) stack.getItem();
            return super.getUnlocalizedName(stack) + "." + item.getTypeName(stack.getItemDamage());
        } else {
            return super.getUnlocalizedName(stack);
        }
    }

    @Override
    public int getMetadata(int damage) {
        if (this instanceof IMetaItem) {
            return damage;
        } else {
            return super.getMetadata(damage);
        }
    }

    @SideOnly (Side.CLIENT)
    public void getSubItems(@Nonnull Item item, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        if (item instanceof IMetaItem) {
            for (int i = 0; i < ((IMetaItem) item).getNumOfTypes(); i++) {
                subItems.add(new ItemStack(this, 1, i));
            }
        } else {
            super.getSubItems(item, tab, subItems);
        }
    }

    @Override
    public void registerItem() {
        GameRegistry.register(this);
    }

    @Override
    public void registerRender() {
        if (this instanceof IMetaItem) {
            for (int i = 0; i < ((IMetaItem) this).getNumOfTypes(); i++) {
                ModelResourceLocation mrl = new ModelResourceLocation(new ResourceLocation(getItemRegistryName().getResourceDomain(), getItemRegistryName().getResourcePath() + "_" + ((IMetaItem) this).getTypeName(i).toLowerCase()), LibMisc.INVENTORY_VARIANT);
                ModelLoader.setCustomModelResourceLocation(this, i, mrl);
            }
        } else {
            ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getItemRegistryName().toString()));
        }
    }

}
