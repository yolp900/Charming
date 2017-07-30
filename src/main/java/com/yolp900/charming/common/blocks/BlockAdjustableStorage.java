package com.yolp900.charming.common.blocks;

import com.yolp900.charming.Charming;
import com.yolp900.charming.client.gui.GuiHandler;
import com.yolp900.charming.client.particle.ModParticles;
import com.yolp900.charming.common.blocks.base.ModBlock;
import com.yolp900.charming.common.items.ModItems;
import com.yolp900.charming.common.network.MessageParticle;
import com.yolp900.charming.common.network.MessageSound;
import com.yolp900.charming.common.network.NetworkHandler;
import com.yolp900.charming.common.tileentities.TileEntityAdjustableStorage;
import com.yolp900.charming.common.tileentities.TileEntityConstructionTable;
import com.yolp900.charming.reference.LibBlocks;
import com.yolp900.charming.reference.LibMisc;
import com.yolp900.charming.util.SoundHandler;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockAdjustableStorage extends ModBlock {

    protected BlockAdjustableStorage() {
        super(LibBlocks.ADJUSTABLE_STORAGE, 3F, 7F, Material.WOOD);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
        return new TileEntityAdjustableStorage();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (player.isSneaking()) {
            return false;
        }

        ItemStack heldItem = player.getHeldItem(hand);
        TileEntity tile = world.getTileEntity(pos);
        boolean insert = false;

        if (tile instanceof TileEntityAdjustableStorage) {
            TileEntityAdjustableStorage adjustableStorage = (TileEntityAdjustableStorage) tile;
            if (!heldItem.isEmpty() && heldItem.getItem() == ModItems.SlotUpgrade) {
                ItemStack upgradeSlot = adjustableStorage.getStackInSlot(adjustableStorage.UPGRADE_SLOT);
                if (adjustableStorage.getNumOfSlots() == 0) {
                    adjustableStorage.setInventorySlotContents(adjustableStorage.UPGRADE_SLOT, new ItemStack(heldItem.getItem(), 1));
                    insert = true;
                    decrSlotUpgradeFromHand(player, hand, heldItem);
                } else if (upgradeSlot.getItem() == ModItems.SlotUpgrade) {
                    if (adjustableStorage.getNumOfSlots() < adjustableStorage.getSizeInventory() - 1) {
                        adjustableStorage.getStackInSlot(adjustableStorage.UPGRADE_SLOT).setCount(upgradeSlot.getCount() + 1);
                        insert = true;
                        decrSlotUpgradeFromHand(player, hand, heldItem);
                    }
                }
                if (insert && !world.isRemote) {
                    NetworkHandler.sendToAllAround(new MessageParticle(ModParticles.Particles.SlotUpgrade, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 0, 0.5, 0), player.dimension, pos.getX(), pos.getY(), pos.getZ(), 16);
                    NetworkHandler.sendToAllAround(new MessageSound(SoundHandler.ModSounds.Ding, pos.getX(), pos.getY(), pos.getZ(), 1, ((double) (adjustableStorage.getNumOfSlots()) / 40) + 0.4, true), player.dimension, pos.getX(), pos.getY(), pos.getZ(), 16);
                }
            }
            if (!insert) {
                if (!adjustableStorage.isOpen()) {
                    adjustableStorage.setOpen(true);
                }
            }
        }
        if (!insert) {
            player.openGui(Charming.instance, GuiHandler.Guis.AdjustableStorage.getID(), world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    private void decrSlotUpgradeFromHand(EntityPlayer player, EnumHand hand, ItemStack heldItem) {
        if (heldItem.getCount() == 1) {
            player.setHeldItem(hand, ItemStack.EMPTY);
        } else {
            player.getHeldItem(hand).setCount(heldItem.getCount() - 1);
        }
    }

    @Override
    public void breakBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        dropInventory(world, pos.getX(), pos.getY(), pos.getZ());
        super.breakBlock(world, pos, state);
    }

    private void dropInventory(World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        if (tileEntity == null || world.isRemote || !(tileEntity instanceof TileEntityAdjustableStorage)) {
            return;
        }

        TileEntityAdjustableStorage inventory = (TileEntityAdjustableStorage) tileEntity;
        int i = 0;
        while (i < inventory.getSizeInventory()) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (i == inventory.UPGRADE_SLOT) {
                    dropSlotUpgrades(inventory, world, x, y, z);
                } else {
                    InventoryHelper.spawnItemStack(world, x, y, z, stack);
                }
            }
            i++;
        }
    }

    private void dropSlotUpgrades(TileEntityAdjustableStorage tile, World world, int x, int y, int z) {
        ItemStack stack = tile.getStackInSlot(tile.UPGRADE_SLOT);
        int num = stack.getCount();
        while (num > 8) {
            stack.setCount(8);
            InventoryHelper.spawnItemStack(world, x, y, z, stack);
            num -= 8;
        }
        if (num > 0) {
            stack.setCount(num);
            InventoryHelper.spawnItemStack(world, x, y, z, stack);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public boolean isBlockNormalCube(IBlockState blockState) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState blockState) {
        return false;
    }

    @Override
    public boolean usesDefaultBlockRegistry() {
        return true;
    }

    @Override
    public boolean usesDefaultRenderRegistry() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getBlockRegistryName(), LibMisc.INVENTORY_VARIANT));
        return false;
    }

}
