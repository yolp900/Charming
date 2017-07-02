package com.yolp900.charming.common.blocks;

import com.yolp900.charming.Charming;
import com.yolp900.charming.client.gui.GuiHandler;
import com.yolp900.charming.client.particle.ModParticles;
import com.yolp900.charming.common.blocks.base.ModBlock;
import com.yolp900.charming.common.items.ModItems;
import com.yolp900.charming.common.network.MessageParticle;
import com.yolp900.charming.common.network.MessageSound;
import com.yolp900.charming.common.network.NetworkHandler;
import com.yolp900.charming.common.tileentities.TileEntityConstructionTable;
import com.yolp900.charming.reference.LibBlocks;
import com.yolp900.charming.util.SoundHandler;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockConstructionTable extends ModBlock {

    public BlockConstructionTable() {
        super(LibBlocks.CONSTRUCTION_TABLE);
    }

    @Override
    public float getBlockHardness(IBlockState state, World world, BlockPos pos) {
        return 1.5F;
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, @Nonnull Entity exploder, Explosion explosion) {
        return 10.0F;
    }

    @Nonnull
    @Override
    public Material getMaterial(IBlockState state) {
        return Material.ROCK;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
        return new TileEntityConstructionTable();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (player.isSneaking()) return false;

        ItemStack heldItem = player.getHeldItem(hand);
        TileEntity tile = world.getTileEntity(pos);
        boolean insert = false;
        if (tile instanceof TileEntityConstructionTable && !heldItem.isEmpty() && heldItem.getItem() == ModItems.SlotUpgrade) {
            TileEntityConstructionTable constructionTable = (TileEntityConstructionTable) tile;
            ItemStack upgradeSlot = constructionTable.getStackInSlot(18);
            if (constructionTable.getSlotUpgradeLevel() == 0) {
                constructionTable.setInventorySlotContents(18, new ItemStack(heldItem.getItem(), 1));
                insert = true;
                if (heldItem.getCount() == 1) {
                    player.setHeldItem(hand, ItemStack.EMPTY);
                } else {
                    player.getHeldItem(hand).setCount(heldItem.getCount() - 1);
                }
            } else if (upgradeSlot.getItem() == ModItems.SlotUpgrade) {
                if (constructionTable.getSlotUpgradeLevel() < 8) {
                    constructionTable.getStackInSlot(18).setCount(upgradeSlot.getCount() + 1);
                    insert = true;
                    if (heldItem.getCount() == 1) {
                        player.setHeldItem(hand, ItemStack.EMPTY);
                    } else {
                        player.getHeldItem(hand).setCount(heldItem.getCount() - 1);
                    }
                }
            }
            if (insert && !world.isRemote) {
                NetworkHandler.sendToAllAround(new MessageParticle(ModParticles.Particles.SlotUpgrade, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 0, 0.5, 0), player.dimension, pos.getX(), pos.getY(), pos.getZ(), 16);
                NetworkHandler.sendToAllAround(new MessageSound(SoundHandler.ModSounds.Ding, pos.getX(), pos.getY(), pos.getZ(), 1, ((double) (constructionTable.getSlotUpgradeLevel()) / 10) + 0.4, true), player.dimension, pos.getX(), pos.getY(), pos.getZ(), 16);
            }
        }

        if (!insert) {
            player.openGui(Charming.instance, GuiHandler.Guis.ConstructionTable.getID(), world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public void breakBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        dropInventory(world, pos.getX(), pos.getY(), pos.getZ());
        super.breakBlock(world, pos, state);
    }

    private void dropInventory(World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        if (tileEntity == null || world.isRemote || !(tileEntity instanceof TileEntityConstructionTable)) {
            return;
        }

        TileEntityConstructionTable inventory = (TileEntityConstructionTable) tileEntity;

        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            if (i == 0) continue; // Don't drop the output slot!
            ItemStack stack = inventory.getStackInSlot(i);
            if (!stack.isEmpty()) {
                InventoryHelper.spawnItemStack(world, x, y, z, stack);
            }
        }
    }

}
