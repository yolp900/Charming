package com.yolp900.charming.client.render;

import com.yolp900.charming.common.items.ModItems;
import com.yolp900.charming.common.items.base.IModItem;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRenderHandler {

    @SideOnly (Side.CLIENT)
    public static void registerItemRenders() {
        for (IModItem item : ModItems.modItems) {
            item.registerRender();
        }
    }

}
