package com.yolp900.charming.common.items.base;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class ModItemWithCustomMeshDefinition extends ModItem {

    public ModItemWithCustomMeshDefinition(String name) {
        super(name);
    }

    public abstract IHandlesMeshDefinitions[] getMeshDefinitions();

    @Override
    public void registerRender() {
        CustomMeshDefinition.registerRender(this);
    }

    public interface IHandlesMeshDefinitions {
        boolean getMeshDefinitionCondition(ItemStack stack);
        ModelResourceLocation getModeResourceLocation(Item item);
    }

}
