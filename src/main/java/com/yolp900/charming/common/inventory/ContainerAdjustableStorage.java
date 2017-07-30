package com.yolp900.charming.common.inventory;

import com.yolp900.charming.common.tileentities.TileEntityAdjustableStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ContainerAdjustableStorage extends ModContainer {
    private TileEntityAdjustableStorage tile;

    public ContainerAdjustableStorage(InventoryPlayer inventory, TileEntityAdjustableStorage tile) {
        this.tile = tile;
        registerSlots(inventory, tile);
    }

    private void registerSlots(InventoryPlayer inventory, TileEntityAdjustableStorage tile) {
        int n = tile.getNumOfSlots();
        int h = TileEntityAdjustableStorage.TOP_BLANK_GAP;

        if (n > 0) {
            h += tile.getNumOfRows() * TileEntityAdjustableStorage.SLOTS_SIZE;
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
                this.addSlotToContainer(new Slot(tile, y * TileEntityAdjustableStorage.NUM_OF_SLOTS_IN_ROW + x, 8 + x * TileEntityAdjustableStorage.SLOTS_SIZE,  TileEntityAdjustableStorage.TOP_BLANK_GAP + 1 + y * TileEntityAdjustableStorage.SLOTS_SIZE));
            }
        }
        h += TileEntityAdjustableStorage.BOTTOM_BLANK_GAP;
        registerInventorySlots(inventory, h);
    }

    private void registerInventorySlots(InventoryPlayer inventory, int startHeight) {
        for (int x = 0; x < 9; x++) { // Hotbar
            this.addSlotToContainer(new Slot(inventory, x, 8 + x * TileEntityAdjustableStorage.SLOTS_SIZE, startHeight + 58)); // 58 = From start height (the first row's itemstack height, not texture) to the hotbar's height (itemstacks)
        }

        for (int y = 0; y < 3; y++) { // Inventory slots
            for (int x = 0; x < 9; x++) {
                this.addSlotToContainer(new Slot(inventory, x + y * TileEntityAdjustableStorage.NUM_OF_SLOTS_IN_ROW + TileEntityAdjustableStorage.NUM_OF_SLOTS_IN_ROW, 8 + x * TileEntityAdjustableStorage.SLOTS_SIZE, startHeight + y * TileEntityAdjustableStorage.SLOTS_SIZE));
            }
        }
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemPlaced = ItemStack.EMPTY;
        Slot clickedSlot = this.inventorySlots.get(index);
        int decr = tile.getSizeInventory() - tile.getNumOfSlots();

        if (clickedSlot != null && clickedSlot.getHasStack() && player.isServerWorld()) {
            ItemStack itemClicked = clickedSlot.getStack();
            if (itemClicked != ItemStack.EMPTY) {
                itemPlaced = itemClicked.copy();

                if (index < tile.getSizeInventory() - decr) { // Shift Click Out of Storage
                    if (!this.mergeItemStack(itemClicked, tile.getSizeInventory() - decr, tile.getSizeInventory() + 9 - decr, true)) {
                        if (!this.mergeItemStack(itemClicked, tile.getSizeInventory() + 9 - decr, tile.getSizeInventory() + 27 + 9 - decr, true)) {
                            return ItemStack.EMPTY;
                        }
                        return ItemStack.EMPTY;
                    }
                } else { // Shift Click Out of Player Inventory
                    if (!this.mergeItemStack(itemClicked, 0, tile.getSizeInventory() - decr, false)) {
                        if (index < tile.getSizeInventory() + 9 - decr) { // Shift Click Out of Hotbar
                            if (!this.mergeItemStack(itemClicked, tile.getSizeInventory() + 9 - decr, tile.getSizeInventory() + 9 + 27 - decr, false)) {
                                return ItemStack.EMPTY;
                            }
                        } else if (index < tile.getSizeInventory() + 9 + 27 - decr) { // Shift Click Out of Inventory
                            if (!this.mergeItemStack(itemClicked, tile.getSizeInventory() - decr, tile.getSizeInventory() + 9 - decr, false)) {
                                return ItemStack.EMPTY;
                            }
                        }
                        return ItemStack.EMPTY;
                    }
                }

                if (itemClicked.isEmpty()) {
                    clickedSlot.putStack(ItemStack.EMPTY);
                } else {
                    clickedSlot.onSlotChanged();
                }

                if (itemClicked.getCount() == itemPlaced.getCount()) {
                    return ItemStack.EMPTY;
                }

                clickedSlot.onSlotChanged();
            }
        }

        return itemPlaced;
    }

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer player) {
        return tile.isUsableByPlayer(player);
    }
}
