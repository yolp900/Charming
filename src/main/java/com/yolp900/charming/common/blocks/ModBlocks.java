package com.yolp900.charming.common.blocks;

import com.yolp900.charming.common.blocks.base.IModBlock;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {

    public static List<IModBlock> modBlocks = new ArrayList<>();

    public static BlockConstructionTable ConstructionTable;
    public static BlockElevator Elevator;
    public static BlockLevitator Levitator;
    public static BlockTintedSapling TintedSapling;
    public static BlockTintedLog TintedLog;
    public static BlockTintedLeaves TintedLeaves;
    public static BlockTintedPlanks TintedPlanks;

    public static void registerBlocks() {
        ConstructionTable = new BlockConstructionTable();
        Elevator = new BlockElevator();
        Levitator = new BlockLevitator();
        TintedSapling = new BlockTintedSapling();
        TintedLog = new BlockTintedLog();
        TintedLeaves = new BlockTintedLeaves();
        TintedPlanks = new BlockTintedPlanks();

        for (IModBlock block : modBlocks) {
            block.registerBlock();
        }
    }

}
