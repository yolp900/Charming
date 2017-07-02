package com.yolp900.charming.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

public abstract class ModGuiContainer extends GuiContainer {

    public ModGuiContainer(Container container, InventoryPlayer inventory, TileEntity tile, GuiHandler.Guis gui) {
        super(container);

        this.xSize = gui.getWidth(inventory.player, tile.getWorld(), tile.getPos(), tile);
        this.ySize = gui.getHeight(inventory.player, tile.getWorld(), tile.getPos(), tile);
    }

    @Override
    protected abstract void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY);

    @Override
    protected abstract void drawGuiContainerForegroundLayer(int mouseX, int mouseY);


}
