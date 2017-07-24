package com.yolp900.charming.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public abstract class ModContainer extends Container {

    @Override
    @Nonnull
    public abstract ItemStack transferStackInSlot(EntityPlayer player, int clickedSlotNumber);

    @Override
    public abstract boolean canInteractWith(@Nonnull EntityPlayer player);

    @Override
    protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
        boolean mergeItemStack = super.mergeItemStack(stack, startIndex, endIndex, reverseDirection);
        if (mergeItemStack) this.detectAndSendChanges();
        return mergeItemStack;
    }

}
