package com.yolp900.charming.client.render;

import com.yolp900.charming.common.blocks.ModBlocks;
import com.yolp900.charming.common.blocks.base.IModBlock;
import com.yolp900.charming.reference.LibLocations;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRenderHandler {

    @SideOnly (Side.CLIENT)
    public static void registerBlockRenders() {
        for (Block block : ModBlocks.modBlocks) {
            if (block instanceof IModBlock) {
                registerBlockRender(block);
            }
        }
    }

    private static void registerBlockRender(Block block) {
        IModBlock iModBlock = (IModBlock) block;
        if (iModBlock.usesDefaultRenderRegistry()) {
            Item itemBlock = Item.getItemFromBlock(block);
            ResourceLocation registryName = iModBlock.getBlockRegistryName();
            String domain = registryName.getResourceDomain();
            String path = LibLocations.ITEMBLOCK_MODEL_FOLDER_PREFIX + registryName.getResourcePath();
            ResourceLocation location = new ResourceLocation(domain, path);
            ModelLoader.setCustomModelResourceLocation(itemBlock, 0, new ModelResourceLocation(location.toString()));
        }
    }

}
