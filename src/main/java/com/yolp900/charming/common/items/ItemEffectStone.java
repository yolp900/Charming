package com.yolp900.charming.common.items;

import com.yolp900.charming.api.CharmingAPI;
import com.yolp900.charming.common.items.base.IMetaItem;
import com.yolp900.charming.common.items.base.ModItem;
import com.yolp900.charming.reference.LibItems;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

public class ItemEffectStone extends ModItem implements IMetaItem {

    public ItemEffectStone() {
        super(LibItems.EFFECT_STONE);
        CharmingAPI.Levitator.blacklistItemStackFromLevitator(new ItemStack(this, 1, EnumStoneEffects.Inversion.ordinal()));
    }

    @Override
    public int getNumOfTypes() {
        return EnumStoneEffects.values().length;
    }

    @Override
    public String getTypeName(int meta) {
        return EnumStoneEffects.values()[meta].getName();
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        if (stack.getItemDamage() == EnumStoneEffects.None.ordinal()) {
            return super.getItemStackLimit(stack);
        }
        return 1;
    }

    public enum EnumStoneEffects implements IStringSerializable {
        None,
        Inversion,
        Conversion;

        @Override
        @Nonnull
        public String getName() {
            return name();
        }
    }
}
