package com.yolp900.charming.common.items.base;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;

import javax.annotation.Nonnull;

public class CustomMeshDefinition implements ItemMeshDefinition {

    private ModItem item;

    public CustomMeshDefinition(ModItem item) {
        this.item = item;
    }

    public static void registerRender(ModItem item) {
        IHasMeshDefinition hasMeshDefinition = (IHasMeshDefinition) item;
        ModelResourceLocation[] modelResourceLocations = new ModelResourceLocation[hasMeshDefinition.getMeshDefinitions().length];
        for (int i = 0; i < hasMeshDefinition.getMeshDefinitions().length; i++) {
            modelResourceLocations[i] = hasMeshDefinition.getMeshDefinitions()[i].getModeResourceLocation(item);
        }
        ModelBakery.registerItemVariants(item, modelResourceLocations);
        ModelLoader.setCustomMeshDefinition(item, new CustomMeshDefinition(item));
    }

    @Override
    @Nonnull
    public ModelResourceLocation getModelLocation(@Nonnull ItemStack stack) {
        IHasMeshDefinition hasMeshDefinition = (IHasMeshDefinition) item;
        for (int i = 0; i < hasMeshDefinition.getMeshDefinitions().length; i++) {
            if (hasMeshDefinition.getMeshDefinitions()[i].getMeshDefinitionCondition(stack)) {
                return hasMeshDefinition.getMeshDefinitions()[i].getModeResourceLocation(stack.getItem());
            }
        }
        return new ModelResourceLocation(item.getItemRegistryName().toString());
    }

}
