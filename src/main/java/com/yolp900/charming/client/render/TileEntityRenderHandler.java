package com.yolp900.charming.client.render;

import com.yolp900.charming.common.tileentities.TileEntityAdjustableStorage;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityRenderHandler {

    @SideOnly(Side.CLIENT)
    public static void registerTileEntityRenders() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAdjustableStorage.class, new AdjustableStorageSpecialRenderer());
    }

}
