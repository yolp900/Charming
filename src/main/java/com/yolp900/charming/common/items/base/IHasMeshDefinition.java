package com.yolp900.charming.common.items.base;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IHasMeshDefinition {

    IHandlesMeshDefinitions[] getMeshDefinitions();

    interface IHandlesMeshDefinitions {
        boolean getMeshDefinitionCondition(ItemStack stack);

        ModelResourceLocation getModeResourceLocation(Item item);
    }

}
