package com.yolp900.charming.common.blocks.base;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

public interface IMetaBlock {

    PropertyEnum<EnumType> getTypeEnum();
    EnumType getDefaultState();
    EnumType[] getTypes();

    enum EnumType implements IStringSerializable {
        ;
        private float hardness;
        private float resistance;
        private Material material;
        private MapColor mapColor;

        EnumType(float hardness, float resistance, Material material) {
            this(hardness, resistance, material, material.getMaterialMapColor());
        }

        EnumType(float hardness, float resistance, Material material, MapColor mapColor) {
            this.hardness = hardness;
            this.resistance = resistance;
            this.material = material;
            this.mapColor = mapColor;
        }

        @Override
        @Nonnull
        public String getName() {
            return name();
        }

        public int getMetadata() {
            return ordinal();
        }

        public float getHardness() {
            return hardness;
        }

        public float getResistance() {
            return resistance;
        }

        @Nonnull
        public Material getMaterial() {
            return material;
        }

        public MapColor getMapColor() {
            return mapColor;
        }
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

}
