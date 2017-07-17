package com.yolp900.charming.common.blocks.base;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

public interface IMetaBlock {

    IEnumType[] getTypes();

    interface IEnumType extends IStringSerializable {
        ;

        @Override
        @Nonnull
        String getName();

        int getMetadata();

        float getHardness();

        float getResistance();

        @Nonnull
        Material getMaterial();

        MapColor getMapColor();
    }

    class ModMetaItemBlock extends ItemBlock {

        private IMetaBlock block;

        public ModMetaItemBlock(Block block) {
            super(block);
            if (block instanceof IMetaBlock) {
                this.block = (IMetaBlock) block;
                setHasSubtypes(true);
            }
        }

        @Override
        @Nonnull
        public String getUnlocalizedName(ItemStack stack) {
            if (block != null) {
                return super.getUnlocalizedName(stack) + "." + block.getTypes()[stack.getItemDamage()].getName();
            }
            return super.getUnlocalizedName(stack);
        }

        @Override
        public int getMetadata(int damage) {
            return damage;
        }
    }

    /*
    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, ENUM_TYPE);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(ENUM_TYPE).getMetadata();
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        if (meta >= getTypes().length) {
            meta = 0;
        }
        return getDefaultState().withProperty(ENUM_TYPE, ENUM_TYPE.values()[meta]);
    }

    @Override
    public void getSubBlocks(@Nonnull Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list) {
        for (int i = 0; i < getTypes().length; i++) {
            list.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    public float getBlockHardness(IBlockState state, World world, BlockPos pos) {
        return getTypes()[getMetaFromState(state)].getHardness();
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, @Nullable Entity exploder, Explosion explosion) {
        return getTypes()[getMetaFromState(world.getBlockState(pos))].getResistance();
    }

    @Override
    @Nonnull
    public Material getMaterial(IBlockState state) {
        return getTypes()[getMetaFromState(state)].getMaterial();
    }

    @Override
    @Nonnull
    public MapColor getMapColor(IBlockState state) {
        return getTypes()[getMetaFromState(state)].getMapColor();
    }
    
    @Override
    public boolean usesDefaultBlockRegistry() {
        GameRegistry.register(this);
        IMetaBlock.ModMetaItemBlock itemBlock = new IMetaBlock.ModMetaItemBlock(this);
        itemBlock.setRegistryName(getBlockRegistryName());
        itemBlock.setUnlocalizedName(getBlockUnlocalizedName());
        GameRegistry.register(itemBlock);
        return false;
    }

    @Override
    public boolean usesDefaultRenderRegistry() {
        Item item = Item.getItemFromBlock(this);
        if (!(item instanceof IMetaBlock.ModMetaItemBlock)) {
            return true;
        }
        IMetaBlock.ModMetaItemBlock itemBlock = (IMetaBlock.ModMetaItemBlock) item;

        for (int i = 0; i < getTypes().length; i++) {
            ModelResourceLocation mrl = new ModelResourceLocation(new ResourceLocation(getBlockRegistryName().getResourceDomain(), LibLocations.ITEMBLOCK_MODEL_FOLDER_PREFIX + getBlockRegistryName().getResourcePath() + "_" + getTypes()[i].getName()), LibMisc.INVENTORY_VARIANT);
            ModelLoader.setCustomModelResourceLocation(itemBlock, i, mrl);
        }
        return false;
    }
     */

}
