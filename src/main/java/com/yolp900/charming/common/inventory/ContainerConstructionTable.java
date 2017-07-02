package com.yolp900.charming.common.inventory;

import com.yolp900.charming.common.tileentities.TileEntityConstructionTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ContainerConstructionTable extends ModContainer {
    private TileEntityConstructionTable tile;

    public ContainerConstructionTable(InventoryPlayer inventory, TileEntityConstructionTable tile) {
        this.tile = tile;
        registerSlots(inventory, tile);
    }

    private void registerSlots(InventoryPlayer inventory, TileEntityConstructionTable tile) {
        this.addSlotToContainer(new SlotOutput(tile, 0, 170, 78));

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                this.addSlotToContainer(new SlotInput(tile, 1 + y * 3 + x, 45 + 18 * x, 60 + 18 * y));
            }
        }

        this.addSlotToContainer(new SlotSecInput(tile, 10, 63, 29));
        this.addSlotToContainer(new SlotSecInput(tile, 11, 112, 29));
        this.addSlotToContainer(new SlotSecInput(tile, 12, 112, 78));
        this.addSlotToContainer(new SlotSecInput(tile, 13, 112, 127));
        this.addSlotToContainer(new SlotSecInput(tile, 14, 63, 127));
        this.addSlotToContainer(new SlotSecInput(tile, 15, 14, 127));
        this.addSlotToContainer(new SlotSecInput(tile, 16, 14, 78));
        this.addSlotToContainer(new SlotSecInput(tile, 17, 14, 29));

        for (int x = 0; x < 9; x++) {
            this.addSlotToContainer(new Slot(inventory, x, 23 + x * 18, 216));
        }

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                this.addSlotToContainer(new Slot(inventory, x + y * 9 + 9, 23 + x * 18, 158 + y * 18));
            }
        }
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemPlaced = ItemStack.EMPTY;
        Slot clickedSlot = this.inventorySlots.get(index);

        if (clickedSlot != null && clickedSlot.getHasStack() && player.isServerWorld()) {
            ItemStack itemClicked = clickedSlot.getStack();
            if (itemClicked != ItemStack.EMPTY) {
                itemPlaced = itemClicked.copy();

                if (index == 0) {
                    if (!this.mergeItemStackOutput(itemClicked)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 1 && index <= 17) { // Shift Click Out of Crafting Grid + Secondary Grid
                    if (!this.mergeItemStack(itemClicked, 27, 54, false) && !this.mergeItemStack(itemClicked, 18, 27, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 18 && index <= 26) { // Shift Click Out of Hotbar.
                    if (!this.mergeItemStack(itemClicked, 27, 54, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 27 && index <= 53) {
                    if (!this.mergeItemStack(itemClicked, 18, 27, false)) {
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

    private boolean mergeItemStackOutput(ItemStack stack) {
        boolean space = false;
        int maxAmountOfSpace = 0;
        ItemStack copy = stack.copy();

        for (int i = 18; i < 54; i++) {
            Slot slot = getSlot(i);
            ItemStack slotStack = slot.getStack();
            if (!slot.getHasStack() || slotStack.isEmpty()) {
                space = true;
                maxAmountOfSpace += Math.min(slot.getSlotStackLimit(), stack.getMaxStackSize());
            } else if (areItemStacksEqualWOStackSize(slotStack, stack) && slotStack.getCount() < slotStack.getMaxStackSize()) {
                space = true;
                maxAmountOfSpace += Math.min(slot.getSlotStackLimit(), stack.getMaxStackSize()) - slot.getStack().getCount();
            }
        }
        if (space) {
            int outputSize = Math.min(maxAmountOfSpace - (maxAmountOfSpace % stack.getCount()), tile.getMaxStackSizeShiftClickOutput());
            tile.decrStackSize(0, outputSize);
            if (outputSize >= stack.getCount()) {
                while (outputSize > stack.getMaxStackSize()) {
                    copy.setCount(stack.getMaxStackSize());
                    outputSize -= stack.getMaxStackSize();
                    if (!super.mergeItemStack(copy.copy(), 18, 27, true)) {
                        super.mergeItemStack(copy.copy(), 27, 54, true);
                    }
                }
                copy.setCount(outputSize);

                return super.mergeItemStack(copy.copy(), 18, 27, true) || super.mergeItemStack(copy.copy(), 27, 54, true);
            }
        }
        return false;
    }

    @Override
    @Nonnull
    public ItemStack slotClick(int slot, int dragType, ClickType clickType, EntityPlayer player) {
        if (slot == 0) {
            if (dragType > 0) dragType = 0;
            ItemStack stack = inventorySlots.get(0).getStack();
            if (stack != ItemStack.EMPTY) {
                /*
                Item item = stack.getItem();
                if (item instanceof ICraftAchievement) {
                    player.addStat(((ICraftAchievement) item).getAchievementOnCraft(stack, player, tile));
                } else if (item instanceof ItemBlock && Block.getBlockFromItem(item) instanceof ICraftAchievement) {
                    player.addStat(((ICraftAchievement) Block.getBlockFromItem(item)).getAchievementOnCraft(stack, player, tile));
                }
                */
            }
        }
        return super.slotClick(slot, dragType, clickType, player);
    }

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer player) {
        return tile.isUsableByPlayer(player);
    }

    public static class SlotOutput extends Slot {

        public SlotOutput(TileEntityConstructionTable tile, int index, int x, int y) {
            super(tile, index, x, y);
        }

        @Override
        public boolean isItemValid(@Nullable ItemStack stack) {
            return false;
        }

    }

    public static class SlotInput extends Slot {

        public SlotInput(TileEntityConstructionTable tile, int index, int x, int y) {
            super(tile, index, x, y);
        }

        @Override
        public boolean isItemValid(@Nullable ItemStack stack) {
            return true;
        }
    }

    public static class SlotSecInput extends SlotInput {
        private TileEntityConstructionTable tile;
        private int index;

        public SlotSecInput(TileEntityConstructionTable tile, int index, int x, int y) {
            super(tile, index, x, y);
            this.tile = tile;
            this.index = index - 10;
        }

        @Override
        public boolean isEnabled() {
            return tile.getSlotUpgradeLevel() > index;
        }
    }

}
