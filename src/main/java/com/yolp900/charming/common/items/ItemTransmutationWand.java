package com.yolp900.charming.common.items;

import com.yolp900.charming.api.items.ITransmutationWand;
import com.yolp900.charming.common.items.base.IMetaItem;
import com.yolp900.charming.common.items.base.ModItem;
import com.yolp900.charming.reference.LibItems;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

public class ItemTransmutationWand extends ModItem implements IMetaItem, ITransmutationWand {

    public ItemTransmutationWand() {
        super(LibItems.TRANSMUTATION_WAND);
        this.setMaxStackSize(1);
    }

    @Override
    public int getNumOfTypes() {
        return EnumTypes.values().length;
    }

    @Override
    public String getTypeName(int meta) {
        return EnumTypes.values()[meta].getName();
    }

    @Override
    public int getWandTransmutationLevel(ItemStack stack) {
        return stack.getItemDamage();
    }

    public enum EnumTypes implements IStringSerializable {
        Wood, Stone, Iron, Obsidian, Diamond;

        @Nonnull
        @Override
        public String getName() {
            return name();
        }

    }
}
