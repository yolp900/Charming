package com.yolp900.charming.common.blocks.base;

import com.yolp900.charming.Charming;
import com.yolp900.charming.common.blocks.ModBlocks;
import com.yolp900.charming.reference.LibLocations;
import com.yolp900.charming.reference.LibMisc;
import com.yolp900.charming.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class ModBlock extends Block implements IModBlock {
    private String name;
    private float hardness;
    private float resistance;
    private Material material;
    private MapColor mapColor;

    public ModBlock(String name) {
        super(Material.ROCK);
        this.name = name;
        ModBlocks.modBlocks.add(this);

        if (this instanceof IMetaBlock) {
            IMetaBlock iMetaBlock = (IMetaBlock) this;
            this.setDefaultState(blockState.getBaseState().withProperty(iMetaBlock.getTypeEnum(), iMetaBlock.getDefaultState()));
        }
    }

    public ModBlock(String name, float hardness, float resistance, Material material, MapColor mapColor) {
       this(name);
       this.hardness = hardness;
       this.resistance = resistance;
       this.material = material;
       this.mapColor = mapColor;
    }

    public ModBlock(String name, float hardness, float resistance, Material material) {
        this(name, hardness, resistance, material, material.getMaterialMapColor());
    }

    public String getName() {
        return name;
    }

    @Override
    public String getBlockUnlocalizedName() {
        return Reference.MOD_PREFIX + getName();
    }

    @Nonnull
    @Override
    public ResourceLocation getBlockRegistryName() {
        return new ResourceLocation(Reference.MOD_ID, getName());
    }

    @Override
    public CreativeTabs getBlockCreativeTab() {
        return Charming.creativeTab;
    }

    @Override
    public float getBlockHardness(IBlockState state, World world, BlockPos pos) {
        if (this instanceof IMetaBlock) {
            IMetaBlock iMetaBlock = (IMetaBlock) this;
            return iMetaBlock.getTypes()[getMetaFromState(state)].getHardness();
        }
        return hardness;
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, @Nullable Entity exploder, Explosion explosion) {
        if (this instanceof IMetaBlock) {
            IMetaBlock iMetaBlock = (IMetaBlock) this;
            return iMetaBlock.getTypes()[getMetaFromState(world.getBlockState(pos))].getResistance();
        }
        return resistance;
    }

    @Override
    @Nonnull
    public Material getMaterial(IBlockState state) {
        if (this instanceof IMetaBlock) {
            IMetaBlock iMetaBlock = (IMetaBlock) this;
            return iMetaBlock.getTypes()[getMetaFromState(state)].getMaterial();
        }
        return material;
    }

    @Override
    @Nonnull
    public MapColor getMapColor(IBlockState state) {
        if (this instanceof IMetaBlock) {
            IMetaBlock iMetaBlock = (IMetaBlock) this;
            return iMetaBlock.getTypes()[getMetaFromState(state)].getMapColor();
        }
        return mapColor;
    }

    @Override
    @Nonnull
    public BlockStateContainer getBlockState() {
        if (this instanceof IMetaBlock) {
            IMetaBlock iMetaBlock = (IMetaBlock) this;
            return new BlockStateContainer(this, iMetaBlock.getTypeEnum());
        }
        return super.getBlockState();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        if (this instanceof IMetaBlock) {
            IMetaBlock iMetaBlock = (IMetaBlock) this;
            for (int i = 0; i < iMetaBlock.getTypes().length; i++) {
                Object obj = state.getValue(iMetaBlock.getTypeEnum());
                if (obj == iMetaBlock.getTypes()[i]) return i;
            }
        }
        return super.getMetaFromState(state);
    }

    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        if (this instanceof IMetaBlock) {
            IMetaBlock iMetaBlock = (IMetaBlock) this;
            if (meta >= iMetaBlock.getTypes().length) {
                meta = 0;
            }
            return getDefaultState().withProperty(iMetaBlock.getTypeEnum(), iMetaBlock.getTypes()[meta]);
        }
        return super.getStateFromMeta(meta);
    }

    @Override
    public void getSubBlocks(@Nonnull Item item, CreativeTabs tab, NonNullList<ItemStack> list) {
        if (this instanceof IMetaBlock) {
            IMetaBlock iMetaBlock = (IMetaBlock) this;
            for (int i = 0; i < iMetaBlock.getTypes().length; i++) {
                list.add(new ItemStack(this, 1, i));
            }
        } else {
            super.getSubBlocks(item, tab, list);
        }
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    public boolean usesDefaultBlockRegistry() {
        if (this instanceof IMetaBlock) {
            GameRegistry.register(this);
            IMetaBlock.ModMetaItemBlock itemBlock = new IMetaBlock.ModMetaItemBlock(this);
            itemBlock.setRegistryName(getBlockRegistryName());
            itemBlock.setUnlocalizedName(getBlockUnlocalizedName());
            GameRegistry.register(itemBlock);
            return false;
        }
        return true;
    }

    @Override
    public boolean usesDefaultRenderRegistry() {
        if (this instanceof IMetaBlock) {
            IMetaBlock iMetaBlock = (IMetaBlock) this;
            Item item = Item.getItemFromBlock(this);
            if (!(item instanceof ModMetaBlock.ModMetaItemBlock)) {
                return true;
            }
            ModMetaBlock.ModMetaItemBlock itemBlock = (ModMetaBlock.ModMetaItemBlock) item;

            for (int i = 0; i < iMetaBlock.getTypes().length; i++) {
                ModelResourceLocation mrl = new ModelResourceLocation(new ResourceLocation(getBlockRegistryName().getResourceDomain(), LibLocations.ITEMBLOCK_MODEL_FOLDER_PREFIX + getBlockRegistryName().getResourcePath() + "_" + iMetaBlock.getTypes()[i].getName()), LibMisc.INVENTORY_VARIANT);
                ModelLoader.setCustomModelResourceLocation(itemBlock, i, mrl);
            }
            return false;
        }
        return true;
    }

}
