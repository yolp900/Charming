package com.yolp900.charming.common.tileentities;

import com.yolp900.charming.common.crafting.ConstructionTableCraftingHandler;
import com.yolp900.charming.reference.LibMisc;
import com.yolp900.charming.reference.LibTileEntities;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

public class TileEntityConstructionTable extends ModTileEntityIInventory {
    private NonNullList<ItemStack> slots;
    private int size = 19;
    private ConstructionTableCraftingHandler.RecipeType currRecipeType;
    public final int UPGRADE_SLOT = 18;
    public final int OUTPUT_SLOT = 0;

    // Values for textures (Gui) and slots (Container)
    public static final int WIDTH = 206;
    public static final int HEIGHT = 243;
    public static final int SLOTS_SIZE = 18;
    public static final int SLOT_TEXTURE_XPOS = 22;
    public static final int SLOT_TEXTURE_YPOS = 157;


    public TileEntityConstructionTable() {
        this.slots = NonNullList.withSize(size, ItemStack.EMPTY);
    }

    @Override
    public int getSizeInventory() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : slots)
            if (!stack.isEmpty()) return false;

        return true;
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int index) {
        return slots.get(index);
    }

    @Nonnull
    @Override
    public ItemStack decrStackSize(int index, int amount) {
        if (!getStackInSlot(index).isEmpty()) {
            if (!getWorld().isRemote)
                getWorld().notifyBlockUpdate(getPos(), getWorld().getBlockState(getPos()), getWorld().getBlockState(getPos()), 3);

            if (getStackInSlot(index).getCount() <= amount) {
                ItemStack itemStack = getStackInSlot(index);
                setInventorySlotContents(index, ItemStack.EMPTY);
                checkRecipes();
                markDirty();
                if (index == OUTPUT_SLOT) {
                    decrGrid(amount / itemStack.getCount());
                }
                return itemStack;
            }

            ItemStack itemStack = getStackInSlot(index).splitStack(amount);
            checkRecipes();
            markDirty();
            return itemStack;
        }
        return ItemStack.EMPTY;
    }

    private void decrGrid(int amount) {
        ConstructionTableCraftingHandler.RecipeType currRecipeType = getCurrRecipeType();

        if (currRecipeType == ConstructionTableCraftingHandler.RecipeType.None) return;
        if (currRecipeType == ConstructionTableCraftingHandler.RecipeType.Vanilla || currRecipeType == ConstructionTableCraftingHandler.RecipeType.CTInfuse) {
            for (int i = 1; i < 10; i++) {
                decrStackSizeAsCraft(i, amount);
            }
        } else {
            for (int i = 1; i < 18; i++) {
                decrStackSizeAsCraft(i, amount);
            }
        }
    }

    private void decrStackSizeAsCraft(int index, int amount) {
        if (!getStackInSlot(index).isEmpty() && !getWorld().isRemote) {
            if (getStackInSlot(index).getItem() instanceof ItemBucket) {
                setInventorySlotContents(index, new ItemStack(Items.BUCKET, 1));
            } else {
                decrStackSize(index, amount);
            }
        }
    }

    @Nonnull
    @Override
    public ItemStack removeStackFromSlot(int index) {
        if (!getStackInSlot(index).isEmpty()) {
            ItemStack itemStack = getStackInSlot(index);
            setInventorySlotContents(index, ItemStack.EMPTY);
            return itemStack;
        }
        checkRecipes();
        markDirty();
        return ItemStack.EMPTY;
    }

    @Override
    public void setInventorySlotContents(int index, @Nonnull ItemStack stack) {
        slots.set(index, stack);
        if (!stack.isEmpty() && stack.getCount() > getInventoryStackLimit()) {
            stack.setCount(getInventoryStackLimit());
        }
        if (index != OUTPUT_SLOT) {
            checkRecipes();
        }
        markDirty();
        if (!getWorld().isRemote) {
            getWorld().notifyBlockUpdate(getPos(), getWorld().getBlockState(getPos()), getWorld().getBlockState(getPos()), 3);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        slots = NonNullList.withSize(slots.size(), ItemStack.EMPTY);
        NBTTagList customSlotsTag = tag.getTagList(LibMisc.INVENTORY_NBT_KEY, 10);
        for (int i = 0; i < customSlotsTag.tagCount(); i++) {
            NBTTagCompound data = customSlotsTag.getCompoundTagAt(i);
            int slot = data.getByte(LibMisc.SLOT_NBT_KEY);
            if (slot >= 0 && slot < slots.size()) {
                slots.set(slot, new ItemStack(data));
            }
        }
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        NBTTagList tags = new NBTTagList();
        for (int i = 0; i < slots.size(); i++) {
            if (!getStackInSlot(i).isEmpty()) {
                NBTTagCompound data = new NBTTagCompound();
                data.setByte(LibMisc.SLOT_NBT_KEY, (byte) i);
                getStackInSlot(i).writeToNBT(data);
                tags.appendTag(data);
            }
        }

        tag.setTag(LibMisc.INVENTORY_NBT_KEY, tags);
        return tag;
    }

    @Override
    public void clear() {
        this.slots = NonNullList.withSize(size, ItemStack.EMPTY);
    }

    @Nonnull
    @Override
    public String getName() {
        return LibTileEntities.CONSTRUCTION_TABLE;
    }

    public int getSlotUpgradeLevel() {
        return getStackInSlot(UPGRADE_SLOT).getCount();
    }

    private NonNullList<ItemStack> getCurrentGridInputs() {
        NonNullList<ItemStack> ret = NonNullList.withSize(9, ItemStack.EMPTY);
        for (int i = 0; i < ret.size(); i++) {
            ret.set(i, getStackInSlot(i + 1));
        }
        return ret;
    }

    private NonNullList<ItemStack> getCurrSecInputs() {
        NonNullList<ItemStack> ret = NonNullList.withSize(8, ItemStack.EMPTY);
        for (int i = 0; i < ret.size(); i++) {
            ret.set(i, getStackInSlot(i + 10));
        }
        return ret;
    }

    public int getMaxStackSizeShiftClickOutput() {
        NonNullList<ItemStack> currGrid = getCurrentGridInputs();
        NonNullList<ItemStack> secGrid = getCurrSecInputs();
        ConstructionTableCraftingHandler.RecipeType recipeType = getCurrRecipeType();
        if (recipeType == ConstructionTableCraftingHandler.RecipeType.None) return 0;
        ItemStack output = getStackInSlot(OUTPUT_SLOT);
        int stackSize = 0;
        if (!output.isEmpty()) {
            int factor = output.getCount();
            int max = 64;
            for (ItemStack stack : currGrid) {
                if (!stack.isEmpty() && stack.getCount() != 0) {
                    max = Math.min(max, stack.getCount());
                }
            }
            if (recipeType != ConstructionTableCraftingHandler.RecipeType.Vanilla && recipeType != ConstructionTableCraftingHandler.RecipeType.CTInfuse) {
                for (ItemStack stack : secGrid) {
                    if (!stack.isEmpty() && stack.getCount() != 0) {
                        max = Math.min(max, stack.getCount());
                    }
                }
            }
            stackSize = factor * max;
        }

        return stackSize;
    }

    private void checkRecipes() {
        if (!getWorld().isRemote) {
            NonNullList<ItemStack> gridInputs = getCurrentGridInputs();
            NonNullList<ItemStack> secInputs = getCurrSecInputs();

            ConstructionTableCraftingHandler.RecipeType currRecipeType = ConstructionTableCraftingHandler.getCurrentRecipeType(gridInputs, secInputs, getWorld());
            setCurrRecipeType(currRecipeType);

            ItemStack output = ItemStack.EMPTY;
            if (currRecipeType != ConstructionTableCraftingHandler.RecipeType.None) {
                if (currRecipeType == ConstructionTableCraftingHandler.RecipeType.Vanilla) {
                    output = ConstructionTableCraftingHandler.getVanillaRecipeOutput(gridInputs, getWorld());
                } else if (currRecipeType == ConstructionTableCraftingHandler.RecipeType.CTShapeless) {
                    output = ConstructionTableCraftingHandler.getCTShapelessRecipeOutput(gridInputs, secInputs);
                } else if (currRecipeType == ConstructionTableCraftingHandler.RecipeType.CTShaped) {
                    output = ConstructionTableCraftingHandler.getCTShapedRecipeOutput(gridInputs, secInputs);
                } else if (currRecipeType == ConstructionTableCraftingHandler.RecipeType.CTInfuse) {
                    output = ConstructionTableCraftingHandler.getCTInfuseRecipeOutput(gridInputs, secInputs);
                }
            }
            setInventorySlotContents(0, output.copy());
        }
    }

    public ConstructionTableCraftingHandler.RecipeType getCurrRecipeType() {
        return currRecipeType;
    }

    private void setCurrRecipeType(ConstructionTableCraftingHandler.RecipeType currRecipeType) {
        this.currRecipeType = currRecipeType;
    }
}
