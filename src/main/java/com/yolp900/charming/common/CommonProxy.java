package com.yolp900.charming.common;

import com.yolp900.charming.Charming;
import com.yolp900.charming.client.gui.GuiHandler;
import com.yolp900.charming.client.particle.ModParticle;
import com.yolp900.charming.common.blocks.ModBlocks;
import com.yolp900.charming.common.crafting.ModRecipes;
import com.yolp900.charming.common.events.ModEvents;
import com.yolp900.charming.common.items.ModItems;
import com.yolp900.charming.common.network.NetworkHandler;
import com.yolp900.charming.common.tileentities.ModTileEntities;
import com.yolp900.charming.config.ModConfig;
import com.yolp900.charming.util.OreDictHandler;
import com.yolp900.charming.util.SoundHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        ModConfig.init(event.getSuggestedConfigurationFile());

        ModBlocks.registerBlocks();
        ModItems.registerItems();
        NetworkHandler.init();
        SoundHandler.registerSounds();
        OreDictHandler.registerOreDicts();
        ModEvents.registerEvents();
    }

    public void init(FMLInitializationEvent event) {
        ModTileEntities.registerTileEntities();
        ModRecipes.registerRecipes();

        NetworkRegistry.INSTANCE.registerGuiHandler(Charming.instance, new GuiHandler());
    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    public Minecraft getMinecraft() {
        return null;
    }

    public EntityPlayer getClientPlayer() {
        return null;
    }

    public void particle(ModParticle particle) {

    }

    public void playSound(String sound, World world, EntityPlayer player, double x, double y, double z, double volume, double pitch) {

    }

    public void playSound(String sound, World world, double x, double y, double z, double volume, double pitch, boolean distanceDelay) {

    }

}
