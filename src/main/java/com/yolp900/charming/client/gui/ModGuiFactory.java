package com.yolp900.charming.client.gui;

import com.yolp900.charming.config.ModConfig;
import com.yolp900.charming.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ModGuiFactory implements IModGuiFactory {

    @Override
    public void initialize(Minecraft minecraftInstance) {

    }

    @Override
    public boolean hasConfigGui() {
        return true;
    }

    @Override
    public GuiScreen createConfigGui(GuiScreen parentScreen) {
        return new ConfigGui(parentScreen);
    }

    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return ConfigGui.class;
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

    @Nullable
    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
        return null;
    }

    public static class ConfigGui extends GuiConfig {

        ConfigGui(GuiScreen parentScreen) {
            super(parentScreen, getConfigCategories(), Reference.MOD_ID, false, false, Reference.MOD_NAME);
        }

        private static List<IConfigElement> getConfigCategories() {

            List<IConfigElement> categories = new ArrayList<>();

            for (String category : ModConfig.configuration.getCategoryNames()) {
                ConfigCategory cc = ModConfig.configuration.getCategory(category);
                if (cc.isChild()) continue;
                ConfigElement ce = new ConfigElement(cc);
                categories.add(ce);
            }
            return categories;
        }

    }
}
