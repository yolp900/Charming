package com.yolp900.charming.common.blocks.base;

import com.yolp900.charming.Charming;
import com.yolp900.charming.common.blocks.ModBlocks;
import com.yolp900.charming.reference.LibLocations;
import com.yolp900.charming.reference.LibMisc;
import com.yolp900.charming.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class ModBlock extends Block implements IModBlock {
    private String name;
    private float hardness;
    private float resistance;
    private Material material;
    private MapColor mapColor;

    public ModBlock(String name) {
        this(name, Material.ROCK);
    }

    public ModBlock(String name, Material material) {
        super(material);
        this.name = name;
        this.material = material;
        ModBlocks.modBlocks.add(this);
    }

    public ModBlock(String name, float hardness, float resistance, Material material, MapColor mapColor) {
       this(name, material);
       this.hardness = hardness;
       this.resistance = resistance;
       this.mapColor = mapColor;
    }

    public ModBlock(String name, float hardness, float resistance, Material material) {
        this(name, hardness, resistance, material, material.getMaterialMapColor());
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
        return hardness;
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, @Nullable Entity exploder, Explosion explosion) {
        return resistance;
    }

    @Override
    @Nonnull
    public Material getMaterial(IBlockState state) {
        return material;
    }

    @Override
    @Nonnull
    public MapColor getMapColor(IBlockState state) {
        return mapColor;
    }

    @Override
    public void getSubBlocks(@Nonnull Item item, CreativeTabs tab, NonNullList<ItemStack> list) {
        super.getSubBlocks(item, tab, list);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

}
