package com.yolp900.charming.common.events;

import com.yolp900.charming.client.particle.ModParticles;
import com.yolp900.charming.common.blocks.BlockElevator;
import com.yolp900.charming.common.blocks.ModBlocks;
import com.yolp900.charming.common.crafting.InteractionHandler;
import com.yolp900.charming.config.ModConfig;
import net.minecraftforge.common.MinecraftForge;

public class ModEvents {

    public static void registerEvents() {
        registerEvent(new ModConfig());
        registerEvent(new InteractionHandler());
        registerEvent(new BlockElevator.ElevatorHandler());
    }

    public static void registerClientEvents() {
        registerEvent(new ModParticles());
    }

    private static void registerEvent(Object eventHandler) {
        MinecraftForge.EVENT_BUS.register(eventHandler);
    }

    private static void registerTerrainGenEvent(Object eventHandler) {
        MinecraftForge.TERRAIN_GEN_BUS.register(eventHandler);
    }

}
