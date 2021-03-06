package com.yolp900.charming.common.tileentities;

import com.yolp900.charming.reference.LibTileEntities;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntities {

    public static void registerTileEntities() {
        registerTileEntity(TileEntityConstructionTable.class, LibTileEntities.CONSTRUCTION_TABLE);
        registerTileEntity(TileEntityLevitator.class, LibTileEntities.LEVITATOR);
        registerTileEntity(TileEntityAttractor.class, LibTileEntities.ATTRACTOR);
        registerTileEntity(TileEntityImpeller.class, LibTileEntities.IMPELLER);
        registerTileEntity(TileEntityAdjustableStorage.class, LibTileEntities.ADJUSTABLE_STORAGE);
    }

    private static void registerTileEntity(Class<? extends TileEntity> classTile, String ID) {
        GameRegistry.registerTileEntity(classTile, ID);
    }

}
