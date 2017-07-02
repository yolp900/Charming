package com.yolp900.charming.common.items;

import com.yolp900.charming.common.items.base.IModItem;

import java.util.ArrayList;
import java.util.List;

public class ModItems {

    public static List<IModItem> modItems = new ArrayList<>();

    public static ItemTransmutationWand TransmutationWand;
    public static ItemSlotUpgrade SlotUpgrade;
    public static ItemEffectStone EffectStone;
    public static ItemHalfPearl HalfPearl;

    public static void registerItems() {
        TransmutationWand = new ItemTransmutationWand();
        SlotUpgrade = new ItemSlotUpgrade();
        EffectStone = new ItemEffectStone();
        HalfPearl = new ItemHalfPearl();

        for (IModItem item : modItems) {
            item.registerItem();
        }
    }

}
