package com.yolp900.charming.common.blocks;

import com.yolp900.charming.common.blocks.base.IModBlock;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {

    public static List<Block> modBlocks = new ArrayList<>();

    public static BlockConstructionTable ConstructionTable;
    public static BlockElevator Elevator;
    public static BlockLevitator Levitator;
    public static BlockTintedSapling TintedSapling;
    public static BlockTintedLog TintedLog;
    public static BlockTintedLeaves TintedLeaves;
    public static BlockTintedPlanks TintedPlanks;
    public static BlockFlower Flower;

    public static void registerBlocks() {
        ConstructionTable = new BlockConstructionTable();
        Elevator = new BlockElevator();
        Levitator = new BlockLevitator();
        TintedSapling = new BlockTintedSapling();
        TintedLog = new BlockTintedLog();
        TintedLeaves = new BlockTintedLeaves();
        TintedPlanks = new BlockTintedPlanks();
        Flower = new BlockFlower();

        for (Block block : modBlocks) {
            if (block instanceof IModBlock) {
                registerBlock(block);
            }
        }
    }

    private static void registerBlock(Block block) {
        IModBlock iModBlock = (IModBlock) block;
        if (iModBlock.usesDefaultBlockRegistry()) {
            block.setUnlocalizedName(iModBlock.getBlockUnlocalizedName());
            block.setRegistryName(iModBlock.getBlockRegistryName());
            block.setCreativeTab(iModBlock.getBlockCreativeTab());
            GameRegistry.register(block);
            ItemBlock itemBlock = new ItemBlock(block);
            itemBlock.setRegistryName(iModBlock.getBlockRegistryName());
            itemBlock.setUnlocalizedName(iModBlock.getBlockUnlocalizedName());
            GameRegistry.register(itemBlock);
        }
    }

}
