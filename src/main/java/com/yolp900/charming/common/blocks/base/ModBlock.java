package com.yolp900.charming.common.blocks.base;

import com.yolp900.charming.Charming;
import com.yolp900.charming.common.blocks.ModBlocks;
import com.yolp900.charming.reference.LibLocations;
import com.yolp900.charming.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class ModBlock extends Block implements IModBlock {
    private String name;

    public ModBlock(String name) {
        super(Material.ROCK);
        this.name = name;
        this.setUnlocalizedName(getBlockUnlocalizedName());
        this.setRegistryName(getBlockRegistryName());
        this.setCreativeTab(getBlockCreativeTab());
        ModBlocks.modBlocks.add(this);
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
    public abstract float getBlockHardness(IBlockState state, World world, BlockPos pos);

    @Override
    public abstract float getExplosionResistance(World world, BlockPos pos, @Nullable Entity exploder, Explosion explosion);

    @Override
    @Nonnull
    public abstract Material getMaterial(IBlockState state);

    @Override
    @Nonnull
    public MapColor getMapColor(IBlockState state) {
        return getMaterial(state).getMaterialMapColor();
    }

    @Override
    public void registerBlock() {
        GameRegistry.register(this);
        ItemBlock itemBlock = new ItemBlock(this);
        itemBlock.setRegistryName(getBlockRegistryName());
        itemBlock.setUnlocalizedName(getBlockUnlocalizedName());
        GameRegistry.register(itemBlock);
    }

    @Override
    @SideOnly (Side.CLIENT)
    public void registerRender() {
        Item itemBlock = Item.getItemFromBlock(this);

        ResourceLocation registryName = getBlockRegistryName();
        String domain = registryName.getResourceDomain();
        String path = LibLocations.ITEMBLOCK_MODEL_FOLDER_PREFIX + registryName.getResourcePath();
        ResourceLocation location = new ResourceLocation(domain, path);
        ModelLoader.setCustomModelResourceLocation(itemBlock, 0, new ModelResourceLocation(location.toString()));
    }

}
