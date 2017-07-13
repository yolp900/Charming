package com.yolp900.charming.common.blocks;

import com.yolp900.charming.common.blocks.base.ModBlockSapling;
import com.yolp900.charming.common.world.TreeGenerator;
import com.yolp900.charming.reference.LibBlocks;
import com.yolp900.charming.reference.LibLocations;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.terraingen.TerrainGen;

import javax.annotation.Nonnull;
import java.util.Random;

public class BlockTintedSapling extends ModBlockSapling {

    public BlockTintedSapling() {
        super(LibBlocks.TINTED_SAPLING);
    }

    @Override
    public void generateTree(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random rand) {
        if (!TerrainGen.saplingGrowTree(world, rand, pos)) return;

        TreeGenerator treeGen = new TreeGenerator(3, 2, ModBlocks.TintedLog.getDefaultState(), ModBlocks.TintedLeaves.getDefaultState());

        world.setBlockToAir(pos);

        treeGen.generateTree(world, rand, pos);
    }

    @Override
    public boolean usesDefaultBlockRegistry() {
        return true;
    }

    @Override
    public boolean usesDefaultRenderRegistry() {
        ItemBlock itemBlock = (ItemBlock) Item.getItemFromBlock(this);
        ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(STAGE, TYPE).build());
        ResourceLocation registryName = this.getBlockRegistryName();
        String domain = registryName.getResourceDomain();
        String path = LibLocations.ITEMBLOCK_MODEL_FOLDER_PREFIX + registryName.getResourcePath();
        ResourceLocation location = new ResourceLocation(domain, path);
        ModelLoader.setCustomModelResourceLocation(itemBlock, 0, new ModelResourceLocation(location.toString()));
        return false;
    }
}
