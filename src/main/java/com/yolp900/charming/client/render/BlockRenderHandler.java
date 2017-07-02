package com.yolp900.charming.client.render;

import com.yolp900.charming.common.blocks.ModBlocks;
import com.yolp900.charming.common.blocks.base.IModBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRenderHandler {

    @SideOnly (Side.CLIENT)
    public static void registerBlockRenders() {
        for (IModBlock block : ModBlocks.modBlocks) {
            block.registerRender();
        }
    }

}
