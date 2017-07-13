package com.yolp900.charming.common.blocks;

import com.yolp900.charming.common.blocks.base.ModBlockLeaves;
import com.yolp900.charming.reference.LibBlocks;
import com.yolp900.charming.reference.LibLocations;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

import java.util.Random;

public class BlockTintedLeaves extends ModBlockLeaves {

    public BlockTintedLeaves() {
        super(LibBlocks.TINTED_LEAVES);
    }

    @Override
    protected NonNullList<ItemStack> getStackDropped(IBlockState state, Random rand, int fortune) {
        return NonNullList.withSize(1, new ItemStack(ModBlocks.TintedSapling));
    }

    @Override
    public boolean usesDefaultBlockRegistry() {
        return true;
    }

    @Override
    public boolean usesDefaultRenderRegistry() {
        ItemBlock itemBlock = (ItemBlock) Item.getItemFromBlock(this);
        ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(CHECK_DECAY, DECAYABLE).build());
        ResourceLocation registryName = this.getBlockRegistryName();
        String domain = registryName.getResourceDomain();
        String path = LibLocations.ITEMBLOCK_MODEL_FOLDER_PREFIX + registryName.getResourcePath();
        ResourceLocation location = new ResourceLocation(domain, path);
        ModelLoader.setCustomModelResourceLocation(itemBlock, 0, new ModelResourceLocation(location.toString()));
        return false;
    }

}
