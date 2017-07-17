package com.yolp900.charming.api.items;

import net.minecraft.item.ItemStack;

public interface ITransmutationWand {

    /**
     * The transmutation wand's level. Wooden = 0, Stone = 1, Iron = 2, Obsidian = 3, Diamond = 4
     *
     * @param stack - The ItemStack instance of the wand
     * @return - The wand's transmutation level
     */
    int getWandTransmutationLevel(ItemStack stack);

}
