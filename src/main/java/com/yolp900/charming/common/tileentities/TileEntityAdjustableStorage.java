package com.yolp900.charming.common.tileentities;

import com.yolp900.charming.reference.LibMisc;
import com.yolp900.charming.reference.LibTileEntities;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

public class TileEntityAdjustableStorage extends ModTileEntityIInventory {
    private NonNullList<ItemStack> slots;
    private int size = 73;
    public final int UPGRADE_SLOT = size - 1;

    // Values for textures (Gui) and slots (Container)
    public static final int WIDTH = 176;
    public static final int DEFAULT_HEIGHT = 114;
    public static final int SLOTS_SIZE = 18;
    public static final int SLOT_TEXTURE_XPOS = 7;
    public static final int SLOT_TEXTURE_YPOS = 31;
    public static final int NUM_OF_SLOTS_IN_ROW = 9;
    public static final int TOP_BLANK_GAP = 24;
    public static final int PLAYER_INVENTORY_AND_GAP_TEXTURE_YPOS = 17;
    public static final int PLAYER_INVENTORY_AND_GAP_TEXTURE_HEIGHT = 97;
    public static final int SLOTS_BACKGROUND_TEXTURE_YPOS = 6;
    public static final int BOTTOM_BLANK_GAP = 15;

    public TileEntityAdjustableStorage() {
        this.slots = NonNullList.withSize(size, ItemStack.EMPTY);
    }

    @Override
    public int getSizeInventory() {
        return size;
    }

    public NonNullList<ItemStack> getInventory() {
        return slots;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : slots)
            if (!stack.isEmpty()) return false;

        return true;
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int index) {
        return slots.get(index);
    }

    @Nonnull
    @Override
    public ItemStack decrStackSize(int index, int amount) {
        if (!getStackInSlot(index).isEmpty()) {
            if (!getWorld().isRemote)
                getWorld().notifyBlockUpdate(getPos(), getWorld().getBlockState(getPos()), getWorld().getBlockState(getPos()), 3);

            if (getStackInSlot(index).getCount() <= amount) {
                ItemStack itemStack = getStackInSlot(index);
                setInventorySlotContents(index, ItemStack.EMPTY);
                markDirty();
                return itemStack;
            }

            ItemStack itemStack = getStackInSlot(index).splitStack(amount);
            markDirty();
            return itemStack;
        }
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public ItemStack removeStackFromSlot(int index) {
        if (!getStackInSlot(index).isEmpty()) {
            ItemStack itemStack = getStackInSlot(index);
            setInventorySlotContents(index, ItemStack.EMPTY);
            return itemStack;
        }
        markDirty();
        return ItemStack.EMPTY;
    }

    @Override
    public void setInventorySlotContents(int index, @Nonnull ItemStack stack) {
        slots.set(index, stack);
        if (!stack.isEmpty() && stack.getCount() > getInventoryStackLimit()) {
            stack.setCount(getInventoryStackLimit());
        }
        markDirty();
        if (!getWorld().isRemote)
            getWorld().notifyBlockUpdate(getPos(), getWorld().getBlockState(getPos()), getWorld().getBlockState(getPos()), 3);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        slots = NonNullList.withSize(slots.size(), ItemStack.EMPTY);
        NBTTagList customSlotsTag = tag.getTagList(LibMisc.INVENTORY_NBT_KEY, 10);
        for (int i = 0; i < customSlotsTag.tagCount(); i++) {
            NBTTagCompound data = customSlotsTag.getCompoundTagAt(i);
            int slot = data.getByte(LibMisc.SLOT_NBT_KEY);
            if (slot >= 0 && slot < slots.size()) {
                slots.set(slot, new ItemStack(data));
            }
        }
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        NBTTagList tags = new NBTTagList();
        for (int i = 0; i < slots.size(); i++) {
            if (!getStackInSlot(i).isEmpty()) {
                NBTTagCompound data = new NBTTagCompound();
                data.setByte(LibMisc.SLOT_NBT_KEY, (byte) i);
                getStackInSlot(i).writeToNBT(data);
                tags.appendTag(data);
            }
        }

        tag.setTag(LibMisc.INVENTORY_NBT_KEY, tags);
        return tag;
    }

    @Override
    public void clear() {
        this.slots = NonNullList.withSize(size, ItemStack.EMPTY);
    }

    @Nonnull
    @Override
    public String getName() {
        return LibTileEntities.ADJUSTABLE_STORAGE;
    }

    public int getNumOfSlots() {
        ItemStack slots = getStackInSlot(UPGRADE_SLOT);
        int n = 0;
        if (!slots.isEmpty() && slots.getCount() > 0) {
            n = slots.getCount();
        }
        return n;
    }

    public int getNumOfRows() {
        int n = getNumOfSlots();
        if (n == 0) {
            return 0;
        } else {
            return ((n - 1) - ((n - 1) % 9)) / 9 + 1;
        }
    }
}
