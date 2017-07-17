package com.yolp900.charming.common.items;

import com.yolp900.charming.common.items.base.IModItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;

public class ModItems {

    public static List<Item> modItems = new ArrayList<>();

    public static ItemTransmutationWand TransmutationWand;
    public static ItemSlotUpgrade SlotUpgrade;
    public static ItemEffectStone EffectStone;
    public static ItemHalfPearl HalfPearl;
    public static ItemCore Core;

    public static void registerItems() {
        TransmutationWand = new ItemTransmutationWand();
        SlotUpgrade = new ItemSlotUpgrade();
        EffectStone = new ItemEffectStone();
        HalfPearl = new ItemHalfPearl();
        Core = new ItemCore();

        for (Item item : modItems) {
            if (item instanceof IModItem) {
                registerItem(item);
            }
        }
    }

    private static void registerItem(Item item) {
        IModItem iModItem = (IModItem) item;
        if (iModItem.usesDefaultItemRegistry()) {
            GameRegistry.register(item);
        }
    }

}
