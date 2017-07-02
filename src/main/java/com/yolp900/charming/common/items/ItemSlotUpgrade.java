package com.yolp900.charming.common.items;

import com.yolp900.charming.common.items.base.ModItemWithCustomMeshDefinition;
import com.yolp900.charming.reference.LibItems;
import com.yolp900.charming.reference.LibMisc;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemSlotUpgrade extends ModItemWithCustomMeshDefinition {

    public ItemSlotUpgrade() {
        super(LibItems.SLOT_UPGRADE);
        this.setMaxStackSize(8);
    }

    @Override
    public IHandlesMeshDefinitions[] getMeshDefinitions() {
        return EnumMeshTypes.values();
    }

    public enum EnumMeshTypes implements IHandlesMeshDefinitions {
        One, Two, Three, Four, Five, Six, Seven, Eight; // Very Creative Indeed

        @Override
        public boolean getMeshDefinitionCondition(ItemStack stack) {
            return stack.getCount() == ordinal() + 1;
        }

        @Override
        public ModelResourceLocation getModeResourceLocation(Item item) {
            if (item.getRegistryName() == null) return null;
            return new ModelResourceLocation(new ResourceLocation(item.getRegistryName().getResourceDomain(), item.getRegistryName().getResourcePath() + (ordinal() + 1)), LibMisc.INVENTORY_VARIANT);
        }
    }

}
