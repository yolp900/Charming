package com.yolp900.charming.client.gui;

import com.yolp900.charming.common.inventory.ContainerConstructionTable;
import com.yolp900.charming.common.tileentities.TileEntityConstructionTable;
import com.yolp900.charming.util.TextHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class GuiConstructionTable extends ModGuiContainer {
    private InventoryPlayer inventory;
    private TileEntityConstructionTable tile;

    public GuiConstructionTable(InventoryPlayer inventory, TileEntityConstructionTable tile) {
        super(new ContainerConstructionTable(inventory, tile), inventory, tile, GuiHandler.Guis.ConstructionTable);
        this.inventory = inventory;
        this.tile = tile;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GuiHandler.Guis.ConstructionTable.getBackground(inventory.player, tile.getWorld(), tile.getPos(), tile));
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        for (int i = 10; i < 18; i++) {
            Slot slot = inventorySlots.getSlot(i);
            if (!slot.isEnabled()) break;
            drawTexturedModalRect(guiLeft + slot.xPos - 1, guiTop + slot.yPos - 1, TileEntityConstructionTable.SLOT_TEXTURE_XPOS, TileEntityConstructionTable.SLOT_TEXTURE_YPOS, TileEntityConstructionTable.SLOTS_SIZE, TileEntityConstructionTable.SLOTS_SIZE);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String title = TextHelper.getFormattedText(GuiHandler.Guis.ConstructionTable.getTitle(inventory.player, tile.getWorld(), tile.getPos(), tile));
        this.fontRenderer.drawString(title, (xSize / 2) - (this.fontRenderer.getStringWidth(title) / 2), 8, 4210752);
        this.fontRenderer.drawString(inventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96, 4210752);

        GlStateManager.color(1F, 1F, 1F, 1F);
        RenderHelper.enableGUIStandardItemLighting();
        ItemStack constructionTable = new ItemStack(tile.getBlockType());
        mc.getRenderItem().renderItemIntoGUI(constructionTable, 4, 4);
    }
}
