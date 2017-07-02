package com.yolp900.charming.client;

import com.yolp900.charming.client.particle.ModParticle;
import com.yolp900.charming.client.render.BlockRenderHandler;
import com.yolp900.charming.client.render.ItemRenderHandler;
import com.yolp900.charming.common.CommonProxy;
import com.yolp900.charming.common.events.ModEvents;
import com.yolp900.charming.util.SoundHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        BlockRenderHandler.registerBlockRenders();
        ItemRenderHandler.registerItemRenders();
        ModEvents.registerClientEvents();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @Override
    public Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }

    @Override
    public EntityPlayer getClientPlayer() {
        return getMinecraft().player;
    }

    @Override
    public void particle(ModParticle particle) {
        getMinecraft().effectRenderer.addEffect(particle);
    }

    @Override
    public void playSound(String sound, World world, EntityPlayer player, double x, double y, double z, double volume, double pitch) {
        SoundHandler.ModSounds event = SoundHandler.ModSounds.valueOf(sound);
        world.playSound(player, x, y, z, event.getSoundEvent(), event.getCategory(), (float) volume, (float) pitch);
    }

    @Override
    public void playSound(String sound, World world, double x, double y, double z, double volume, double pitch, boolean distanceDelay) {
        SoundHandler.ModSounds event = SoundHandler.ModSounds.valueOf(sound);
        world.playSound(x, y, z, event.getSoundEvent(), event.getCategory(), (float) volume, (float) pitch, distanceDelay);
    }

}
