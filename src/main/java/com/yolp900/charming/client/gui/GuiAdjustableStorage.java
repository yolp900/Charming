package com.yolp900.charming.client.gui;

import com.yolp900.charming.Charming;
import com.yolp900.charming.common.inventory.ContainerAdjustableStorage;
import com.yolp900.charming.common.tileentities.TileEntityAdjustableStorage;
import com.yolp900.charming.util.TextHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class GuiAdjustableStorage extends ModGuiContainer {
    private InventoryPlayer inventory;
    private TileEntityAdjustableStorage tile;

    public GuiAdjustableStorage(InventoryPlayer inventory, TileEntityAdjustableStorage tile) {
        super(new ContainerAdjustableStorage(inventory, tile), inventory, tile, GuiHandler.Guis.AdjustableStorage);
        this.inventory = inventory;
        this.tile = tile;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GuiHandler.Guis.AdjustableStorage.getBackground(inventory.player, tile.getWorld(), tile.getPos(), tile));
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, TileEntityAdjustableStorage.WIDTH, TileEntityAdjustableStorage.TOP_BLANK_GAP);
        for (int i = 0; i < tile.getNumOfRows(); i++) {
            drawTexturedModalRect(guiLeft, guiTop + TileEntityAdjustableStorage.TOP_BLANK_GAP + i * TileEntityAdjustableStorage.SLOTS_SIZE, 0, TileEntityAdjustableStorage.SLOTS_BACKGROUND_TEXTURE_YPOS, TileEntityAdjustableStorage.WIDTH, TileEntityAdjustableStorage.SLOTS_SIZE);
        }
        int n = tile.getNumOfSlots();
        if (n > 0) {
            for (int i = 0; i < n; i++) {
                int j = i;
                int x = i % TileEntityAdjustableStorage.NUM_OF_SLOTS_IN_ROW;
                int y = 0;
                if (j >= TileEntityAdjustableStorage.NUM_OF_SLOTS_IN_ROW) {
                    while (j >= TileEntityAdjustableStorage.NUM_OF_SLOTS_IN_ROW) {
                        j -= TileEntityAdjustableStorage.NUM_OF_SLOTS_IN_ROW;
                        y++;
                    }
                }
                drawTexturedModalRect(guiLeft + TileEntityAdjustableStorage.SLOT_TEXTURE_XPOS + x * TileEntityAdjustableStorage.SLOTS_SIZE, guiTop + TileEntityAdjustableStorage.TOP_BLANK_GAP + y * TileEntityAdjustableStorage.SLOTS_SIZE, TileEntityAdjustableStorage.SLOT_TEXTURE_XPOS, TileEntityAdjustableStorage.SLOT_TEXTURE_YPOS, TileEntityAdjustableStorage.SLOTS_SIZE, TileEntityAdjustableStorage.SLOTS_SIZE);
            }
        }
        drawTexturedModalRect(guiLeft, guiTop + TileEntityAdjustableStorage.TOP_BLANK_GAP + tile.getNumOfRows() * TileEntityAdjustableStorage.SLOTS_SIZE, 0, TileEntityAdjustableStorage.PLAYER_INVENTORY_AND_GAP_TEXTURE_YPOS,  TileEntityAdjustableStorage.WIDTH, TileEntityAdjustableStorage.PLAYER_INVENTORY_AND_GAP_TEXTURE_HEIGHT);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String title = TextHelper.getFormattedText(GuiHandler.Guis.AdjustableStorage.getTitle(inventory.player, tile.getWorld(), tile.getPos(), tile));
        this.fontRenderer.drawString(title, 24, 8, 4210752);
        this.fontRenderer.drawString(inventory.getDisplayName().getUnformattedText(), 8, this.ySize - TileEntityAdjustableStorage.PLAYER_INVENTORY_AND_GAP_TEXTURE_HEIGHT + 4, 4210752);

        GlStateManager.color(1F, 1F, 1F, 1F);
        RenderHelper.enableGUIStandardItemLighting();
        ItemStack adjustableChest = new ItemStack(tile.getBlockType());
        mc.getRenderItem().renderItemIntoGUI(adjustableChest, 4, 4);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        if (tile.isOpen()) {
            tile.setOpen(false);
        }
    }

}
