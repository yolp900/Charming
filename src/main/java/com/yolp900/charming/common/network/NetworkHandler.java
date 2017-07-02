package com.yolp900.charming.common.network;

import com.yolp900.charming.reference.Reference;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {

    public static SimpleNetworkWrapper instance;

    private static int i;

    public static void init() {
        instance = new SimpleNetworkWrapper(Reference.MOD_ID);
        i = 0;
        registerMessage(MessageSound.class, Side.CLIENT);
        registerMessage(MessageParticle.class, Side.CLIENT);
    }

    @SuppressWarnings ("unchecked")
    private static void registerMessage(Class message, Side side) {
        instance.registerMessage(message, message, i, side);
        i++;
    }

    public static void sendToServer(IMessage message) {
        instance.sendToServer(message);
    }

    public static void sendTo(IMessage message, EntityPlayerMP player) {
        instance.sendTo(message, player);
    }

    public static void sendToAllAround(IMessage message, int dimension, double x, double y, double z, double range) {
        instance.sendToAllAround(message, new NetworkRegistry.TargetPoint(dimension, x, y, z, range));
    }

}
