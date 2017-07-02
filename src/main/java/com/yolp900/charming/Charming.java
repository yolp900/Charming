package com.yolp900.charming;

import com.yolp900.charming.client.gui.ModGuiFactory;
import com.yolp900.charming.common.CommonProxy;
import com.yolp900.charming.common.items.ItemTransmutationWand;
import com.yolp900.charming.common.items.ModItems;
import com.yolp900.charming.reference.LibLocations;
import com.yolp900.charming.reference.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

@Mod (modid = Reference.MOD_ID, version = Reference.MOD_VERSION, name = Reference.MOD_NAME, dependencies = Reference.MOD_DEPENDENCIES, guiFactory = LibLocations.GUI_FACTORY)
public class Charming {

    @Mod.Instance
    public static Charming instance;

    @SidedProxy (serverSide = LibLocations.COMMON_PROXY, clientSide = LibLocations.CLIENT_PROXY)
    public static CommonProxy proxy;

    public static CreativeTabs creativeTab = new ModCreativeTab();

    public static Logger logger = LogManager.getLogger(Reference.MOD_NAME);

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    private static class ModCreativeTab extends CreativeTabs {
        ModCreativeTab() {
            super(Reference.MOD_ID);
        }

        @Override
        @Nonnull
        public ItemStack getTabIconItem() {
            return new ItemStack(ModItems.TransmutationWand, 1, ItemTransmutationWand.EnumTypes.Iron.ordinal());
        }
    }

}
