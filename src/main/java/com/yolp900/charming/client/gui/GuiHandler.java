package com.yolp900.charming.client.gui;

import com.yolp900.charming.common.inventory.ContainerConstructionTable;
import com.yolp900.charming.common.tileentities.TileEntityConstructionTable;
import com.yolp900.charming.reference.LibLocations;
import com.yolp900.charming.reference.LibMisc;
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
        ConstructionTable() {
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
                return LibMisc.GUI_TITLE_PREFIX + "ConstructionTable";
            }

            @Override
            public ResourceLocation getBackground(EntityPlayer player, World world, BlockPos pos, TileEntity tile) {
                return new ResourceLocation(Reference.MOD_ID, LibLocations.GUI_BACKGROUNDS + "construction_table" + ".png");
            }

            @Override
            public int getWidth(EntityPlayer player, World world, BlockPos pos, TileEntity tile) {
                return 206;
            }

            @Override
            public int getHeight(EntityPlayer player, World world, BlockPos pos, TileEntity tile) {
                return 243;
            }
        },;

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
