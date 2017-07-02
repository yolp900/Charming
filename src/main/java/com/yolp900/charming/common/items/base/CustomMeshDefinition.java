package com.yolp900.charming.common.items.base;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;

import javax.annotation.Nonnull;

public class CustomMeshDefinition implements ItemMeshDefinition {

    private ModItemWithCustomMeshDefinition item;

    public CustomMeshDefinition(ModItemWithCustomMeshDefinition item) {
        this.item = item;
    }

    public static void registerRender(ModItemWithCustomMeshDefinition item) {
        ModelResourceLocation[] modelResourceLocations = new ModelResourceLocation[item.getMeshDefinitions().length];
        for (int i = 0; i < item.getMeshDefinitions().length; i++) {
            modelResourceLocations[i] = item.getMeshDefinitions()[i].getModeResourceLocation(item);
        }
        ModelBakery.registerItemVariants(item, modelResourceLocations);
        ModelLoader.setCustomMeshDefinition(item, new CustomMeshDefinition(item));
    }

    @Override
    @Nonnull
    public ModelResourceLocation getModelLocation(@Nonnull ItemStack stack) {
        for (int i = 0; i < item.getMeshDefinitions().length; i++) {
            if (item.getMeshDefinitions()[i].getMeshDefinitionCondition(stack)) {
                return item.getMeshDefinitions()[i].getModeResourceLocation(stack.getItem());
            }
        }
        return new ModelResourceLocation(item.getItemRegistryName().toString());
    }

}
