package com.yolp900.charming.common.items;

import com.yolp900.charming.common.items.base.CustomMeshDefinition;
import com.yolp900.charming.common.items.base.IHasMeshDefinition;
import com.yolp900.charming.common.items.base.ModItem;
import com.yolp900.charming.reference.LibItems;
import com.yolp900.charming.reference.LibMisc;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemHalfPearl extends ModItem implements IHasMeshDefinition {
    public ItemHalfPearl() {
        super(LibItems.Half_ENDER_PEARL);
        this.setMaxStackSize(2);
    }

    @Override
    public IHandlesMeshDefinitions[] getMeshDefinitions() {
        return EnumMeshTypes.values();
    }

    @Override
    public boolean usesDefaultItemRegistry() {
        return true;
    }

    @Override
    public boolean usesDefaultRenderRegistry() {
        CustomMeshDefinition.registerRender(this);
        return false;
    }

    public enum EnumMeshTypes implements IHandlesMeshDefinitions {
        Half, TwoHalves;

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
