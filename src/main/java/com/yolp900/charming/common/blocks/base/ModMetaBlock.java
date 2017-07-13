package com.yolp900.charming.common.blocks.base;

import com.yolp900.charming.reference.LibLocations;
import com.yolp900.charming.reference.LibMisc;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class ModMetaBlock extends ModBlock {
    // public static final PropertyEnum<EnumTypes> TYPE = PropertyEnum.create(LibMisc.BLOCK_TYPE, EnumTypes.class);

    public ModMetaBlock(String name) {
        super(name);

        // this.setDefaultState(blockState.getBaseState().withProperty(TYPE, EnumTypes.DEFAULT_VALUE));
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, getTypeEnum());
    }

    // return TYPE;
    protected abstract IProperty<?> getTypeEnum();

    // return state.getValue(TYPE).getMetadata();
    @Override
    public abstract int getMetaFromState(IBlockState state);

    // if (meta >= EnumTypes.values().length) {
    //    meta = 0;
    // }
    // return getDefaultState().withProperty(TYPE, EnumTypes.values()[meta]);
    @Override
    @Nonnull
    public abstract IBlockState getStateFromMeta(int meta);

    @Override
    public void getSubBlocks(@Nonnull Item item, CreativeTabs tab, NonNullList<ItemStack> list) {
        for (int i = 0; i < getNumOfTypes(); i++)
            list.add(new ItemStack(this, 1, i));
    }

    // return EnumTypes.values().length;
    protected abstract int getNumOfTypes();

    // return EnumTypes.values()[meta].getName();
    public abstract String getTypeName(int meta);

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    public float getBlockHardness(IBlockState state, World world, BlockPos pos) {
        return getBlockHardness(getMetaFromState(state));
    }

    // return EnumTypes.values()[meta].getHardness();
    protected abstract float getBlockHardness(int meta);

    @Override
    public float getExplosionResistance(World world, BlockPos pos, @Nullable Entity exploder, Explosion explosion) {
        return getExplosionResistance(getMetaFromState(world.getBlockState(pos)));
    }

    // return EnumTypes.values()[meta].getResistance();
    protected abstract float getExplosionResistance(int meta);

    @Override
    @Nonnull
    public Material getMaterial(IBlockState state) {
        return getMaterial(getMetaFromState(state));
    }

    // return EnumTypes.values()[meta].getMaterial();
    @Nonnull
    protected abstract Material getMaterial(int meta);

    @Override
    @Nonnull
    public MapColor getMapColor(IBlockState state) {
        return getMapColor(getMetaFromState(state));
    }

    // return return EnumTypes.values()[meta].getMapColor();
    @Nonnull
    protected abstract MapColor getMapColor(int meta);

    @Override
    public boolean usesDefaultBlockRegistry() {
        GameRegistry.register(this);
        ModMetaItemBlock itemBlock = new ModMetaItemBlock(this);
        itemBlock.setRegistryName(getBlockRegistryName());
        itemBlock.setUnlocalizedName(getBlockUnlocalizedName());
        GameRegistry.register(itemBlock);
        return false;
    }

    @Override
    public boolean usesDefaultRenderRegistry() {
        Item item = Item.getItemFromBlock(this);
        if (!(item instanceof ModMetaItemBlock)) {
            return true;
        }
        ModMetaItemBlock itemBlock = (ModMetaItemBlock) item;

        for (int i = 0; i < getNumOfTypes(); i++) {
            ModelResourceLocation mrl = new ModelResourceLocation(new ResourceLocation(getBlockRegistryName().getResourceDomain(), LibLocations.ITEMBLOCK_MODEL_FOLDER_PREFIX + getBlockRegistryName().getResourcePath() + "_" + getTypeName(i)), LibMisc.INVENTORY_VARIANT);
            ModelLoader.setCustomModelResourceLocation(itemBlock, i, mrl);
        }
        return false;
    }

    public interface IEnumType extends IStringSerializable {
        @Nonnull
        String getName();

        int getMetadata();

        float getHardness();

        float getResistance();

        @Nonnull
        Material getMaterial();

        MapColor getMapColor();
    }

    public static class ModMetaItemBlock extends ItemBlock {

        private ModMetaBlock block;

        private ModMetaItemBlock(ModMetaBlock block) {
            super(block);
            this.block = block;
            setHasSubtypes(true);
        }

        @Override
        @Nonnull
        public String getUnlocalizedName(ItemStack stack) {
            return super.getUnlocalizedName(stack) + "." + block.getTypeName(stack.getItemDamage());
        }

        @Override
        public int getMetadata(int damage) {
            return damage;
        }
    }

}
