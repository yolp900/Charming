package com.yolp900.charming.client.render;

import com.yolp900.charming.common.items.ModItems;
import com.yolp900.charming.common.items.base.IModItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRenderHandler {

    @SideOnly (Side.CLIENT)
    public static void registerItemRenders() {
        for (Item item : ModItems.modItems) {
            if (item instanceof IModItem) {
                registerItemRender(item);
            }
        }
    }

    private static void registerItemRender(Item item) {
        IModItem iModItem = (IModItem) item;
        if (iModItem.usesDefaultRenderRegistry()) {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(iModItem.getItemRegistryName().toString()));
        }
    }

}
