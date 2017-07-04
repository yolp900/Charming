package com.yolp900.charming.util;

import com.yolp900.charming.common.blocks.ModBlocks;
import com.yolp900.charming.reference.LibOreDict;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictHandler {

    public static void registerOreDicts() {
        for (BlockStoneBrick.EnumType type : BlockStoneBrick.EnumType.values()) {
            registerOre(LibOreDict.BRICK_STONE, Blocks.STONEBRICK, type.getMetadata());
        }

        registerOre(LibOreDict.LOG_WOOD, ModBlocks.TintedLog);
        registerOre(LibOreDict.SAPLING, ModBlocks.TintedSapling);
        registerOre(LibOreDict.LEAVES, ModBlocks.TintedLeaves);
        registerOre(LibOreDict.PLANK_WOOD, ModBlocks.TintedPlanks);
    }

    private static void registerOre(String name, Block block, int meta) {
        registerOre(name, new ItemStack(block, 1, meta));
    }

    private static void registerOre(String name, Block block) {
        registerOre(name, block, 0);
    }

    private static void registerOre(String name, Item item, int meta) {
        registerOre(name, new ItemStack(item, 1, meta));
    }

    private static void registerOre(String name, Item item) {
        registerOre(name, item, 0);
    }

    private static void registerOre(String name, ItemStack stack) {
        OreDictionary.registerOre(name, stack);
    }

}
