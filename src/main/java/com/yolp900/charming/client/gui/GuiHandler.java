package com.yolp900.charming.client.gui;

import com.yolp900.charming.common.inventory.ContainerAdjustableStorage;
import com.yolp900.charming.common.inventory.ContainerConstructionTable;
import com.yolp900.charming.common.tileentities.TileEntityAdjustableStorage;
import com.yolp900.charming.common.tileentities.TileEntityConstructionTable;
import com.yolp900.charming.reference.LibGuis;
import com.yolp900.charming.reference.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (player == null) return null;
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity tile = world.getTileEntity(pos);
        return Guis.values()[ID].getServerGuiElement(player, world, pos, tile);
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (player == null) return null;
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity tile = world.getTileEntity(pos);
        return Guis.values()[ID].getClientGuiElement(player, world, pos, tile);
    }

    public enum Guis {
        ConstructionTable {
            @Override
            public Object getServerGuiElement(EntityPlayer player, World world, BlockPos pos, TileEntity tile) {
                if (!(tile instanceof TileEntityConstructionTable)) return null;
                return new ContainerConstructionTable(player.inventory, (TileEntityConstructionTable) tile);
            }

            @Override
            public Object getClientGuiElement(EntityPlayer player, World world, BlockPos pos, TileEntity tile) {
                if (!(tile instanceof TileEntityConstructionTable)) return null;
                return new GuiConstructionTable(player.inventory, (TileEntityConstructionTable) tile);
            }

            @Override
            public String getTitle(EntityPlayer player, World world, BlockPos pos, TileEntity tile) {
                return LibGuis.CONSTRUCTION_TABLE_TITLE;
            }

            @Override
            public ResourceLocation getBackground(EntityPlayer player, World world, BlockPos pos, TileEntity tile) {
                return new ResourceLocation(Reference.MOD_ID, LibGuis.CONSTRUCTION_TABLE_BACKGROUND);
            }

            @Override
            public int getWidth(EntityPlayer player, World world, BlockPos pos, TileEntity tile) {
                return TileEntityConstructionTable.WIDTH;
            }

            @Override
            public int getHeight(EntityPlayer player, World world, BlockPos pos, TileEntity tile) {
                return TileEntityConstructionTable.HEIGHT;
            }
        },
        AdjustableStorage {
            @Override
            Object getServerGuiElement(EntityPlayer player, World world, BlockPos pos, TileEntity tile) {
                if (!(tile instanceof TileEntityAdjustableStorage)) return null;
                return new ContainerAdjustableStorage(player.inventory, (TileEntityAdjustableStorage) tile);
            }

            @Override
            Object getClientGuiElement(EntityPlayer player, World world, BlockPos pos, TileEntity tile) {
                if (!(tile instanceof TileEntityAdjustableStorage)) return null;
                return new GuiAdjustableStorage(player.inventory, (TileEntityAdjustableStorage) tile);
            }

            @Override
            String getTitle(EntityPlayer player, World world, BlockPos pos, TileEntity tile) {
                return LibGuis.ADJUSTABLE_STORAGE_TITLE;
            }

            @Override
            ResourceLocation getBackground(EntityPlayer player, World world, BlockPos pos, TileEntity tile) {
                return new ResourceLocation(Reference.MOD_ID, LibGuis.ADJUSTABLE_STORAGE_BACKGROUND);
            }

            @Override
            int getWidth(EntityPlayer player, World world, BlockPos pos, TileEntity tile) {
                return TileEntityAdjustableStorage.WIDTH;
            }

            @Override
            int getHeight(EntityPlayer player, World world, BlockPos pos, TileEntity tile) {
                if (!(tile instanceof TileEntityAdjustableStorage)) {
                    return TileEntityAdjustableStorage.DEFAULT_HEIGHT;
                }
                TileEntityAdjustableStorage adjustableStorage = (TileEntityAdjustableStorage) tile;
                int n = adjustableStorage.getNumOfRows();

                int h = TileEntityAdjustableStorage.TOP_BLANK_GAP;
                if (n > 0) {
                    h += n * TileEntityAdjustableStorage.SLOTS_SIZE;
                }
                h += TileEntityAdjustableStorage.PLAYER_INVENTORY_AND_GAP_TEXTURE_HEIGHT;
                return h;
            }

        };

        public int getID() {
            return ordinal();
        }

        abstract Object getServerGuiElement(EntityPlayer player, World world, BlockPos pos, TileEntity tile);

        abstract Object getClientGuiElement(EntityPlayer player, World world, BlockPos pos, TileEntity tile);

        abstract String getTitle(EntityPlayer player, World world, BlockPos pos, TileEntity tile);

        abstract ResourceLocation getBackground(EntityPlayer player, World world, BlockPos pos, TileEntity tile);

        abstract int getWidth(EntityPlayer player, World world, BlockPos pos, TileEntity tile);

        abstract int getHeight(EntityPlayer player, World world, BlockPos pos, TileEntity tile);
    }

}
