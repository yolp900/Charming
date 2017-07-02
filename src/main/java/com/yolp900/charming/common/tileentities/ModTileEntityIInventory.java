package com.yolp900.charming.common.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public abstract class ModTileEntityIInventory extends ModTileEntity implements IInventory {

    @Override
    public boolean isUsableByPlayer(@Nonnull EntityPlayer player) {
        return world.getTileEntity(pos) == this && player.getDistanceSq(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) < 64;
    }

    @Override
    public abstract int getSizeInventory();

    @Override
    public abstract boolean isEmpty();

    @Override
    @Nonnull
    public abstract ItemStack getStackInSlot(int index);

    @Override
    @Nonnull
    public abstract ItemStack decrStackSize(int index, int amount);

    @Override
    @Nonnull
    public abstract ItemStack removeStackFromSlot(int index);

    @Override
    public abstract void setInventorySlotContents(int index, @Nonnull ItemStack stack);

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void openInventory(@Nonnull EntityPlayer player) {

    }

    @Override
    public void closeInventory(@Nonnull EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int index, @Nonnull ItemStack stack) {
        return true;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public abstract void clear();

    @Override
    @Nonnull
    public abstract String getName();

    @Override
    public boolean hasCustomName() {
        return false;
    }

}
