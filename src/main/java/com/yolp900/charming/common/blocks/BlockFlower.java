package com.yolp900.charming.common.blocks;

import com.yolp900.charming.common.blocks.base.IMetaBlock;
import com.yolp900.charming.common.blocks.base.ModBlockBush;
import com.yolp900.charming.reference.LibBlocks;
import com.yolp900.charming.reference.LibLocations;
import com.yolp900.charming.reference.LibMisc;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockFlower extends ModBlockBush implements IMetaBlock {
    public static final PropertyEnum<EnumTypes> TYPE = PropertyEnum.create(LibMisc.BLOCK_TYPE, EnumTypes.class);

    public BlockFlower() {
        super(LibBlocks.FLOWER);
        this.setUnlocalizedName(getBlockUnlocalizedName());
        this.setRegistryName(getBlockRegistryName());
        this.setCreativeTab(getBlockCreativeTab());
        this.setSoundType(SoundType.PLANT);

        this.setDefaultState(blockState.getBaseState().withProperty(TYPE, EnumTypes.DesertRose));
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, TYPE);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        if (state.getBlock() != this) return 0;
        return state.getValue(TYPE).getMetadata();
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        if (meta >= getTypes().length) {
            meta = 0;
        }
        return getDefaultState().withProperty(TYPE, EnumTypes.values()[meta]);
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
    @Nonnull
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumTypes.values()[getMetaFromState(world.getBlockState(pos))].getPlantType(world, pos);
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {
        return EnumTypes.values()[getMetaFromState(state)].canSustainBlock(state);
    }

    @Override
    public IEnumType[] getTypes() {
        return EnumTypes.values();
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

    public enum EnumTypes implements IEnumType {
        DesertRose {
            @Override
            public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
                return EnumPlantType.Desert;
            }

            @Override
            public boolean canSustainBlock(IBlockState state) {
                return state == Blocks.SAND;
            }
        };

        public abstract EnumPlantType getPlantType(IBlockAccess world, BlockPos pos);

        public abstract boolean canSustainBlock(IBlockState state);

        @Nonnull
        @Override
        public String getName() {
            return name().toLowerCase();
        }

        @Override
        public int getMetadata() {
            return ordinal();
        }

        @Override
        public float getHardness() {
            return 0;
        }

        @Override
        public float getResistance() {
            return 0;
        }

        @Nonnull
        @Override
        public Material getMaterial() {
            return Material.PLANTS;
        }

        @Nonnull
        @Override
        public MapColor getMapColor() {
            return getMaterial().getMaterialMapColor();
        }
    }

}
