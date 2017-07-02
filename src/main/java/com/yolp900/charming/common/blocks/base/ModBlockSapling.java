package com.yolp900.charming.common.blocks.base;

import com.yolp900.charming.Charming;
import com.yolp900.charming.common.blocks.ModBlocks;
import com.yolp900.charming.reference.LibLocations;
import com.yolp900.charming.reference.Reference;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;
import java.util.Random;

public abstract class ModBlockSapling extends BlockSapling implements IModBlock {
    private String name;

    public ModBlockSapling(String name) {
        this.name = name;
        this.setUnlocalizedName(getBlockUnlocalizedName());
        this.setRegistryName(getBlockRegistryName());
        this.setCreativeTab(getBlockCreativeTab());
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
    public void registerBlock() {
        GameRegistry.register(this);
        ItemBlock itemBlock = new ItemBlock(this);
        itemBlock.setRegistryName(getBlockRegistryName());
        itemBlock.setUnlocalizedName(getBlockUnlocalizedName());
        GameRegistry.register(itemBlock);
    }

    @Override
    public void registerRender() {
        ItemBlock itemBlock = (ItemBlock) Item.getItemFromBlock(this);
        ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(TYPE).build());
        ResourceLocation registryName = this.getBlockRegistryName();
        String domain = registryName.getResourceDomain();
        String path = LibLocations.ITEMBLOCK_MODEL_FOLDER_PREFIX + registryName.getResourcePath();
        ResourceLocation location = new ResourceLocation(domain, path);
        ModelLoader.setCustomModelResourceLocation(itemBlock, 0, new ModelResourceLocation(location.toString()));
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
