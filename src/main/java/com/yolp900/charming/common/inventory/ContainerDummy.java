package com.yolp900.charming.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

import javax.annotation.Nonnull;

public class ContainerDummy extends Container {

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer player) {
        return false;
    }

}
