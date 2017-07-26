package com.yolp900.charming.common.network;

import com.google.common.collect.Lists;
import com.yolp900.charming.Charming;
import com.yolp900.charming.client.particle.ModParticle;
import com.yolp900.charming.client.particle.ModParticles;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.util.Collections;

public class MessageParticle extends MessageBase<MessageParticle> {
    protected ModParticles.Particles particle;
    protected double x, y, z;
    protected double motionX, motionY, motionZ;
    protected double size, red, green, blue;

    public MessageParticle() {

    }

    public MessageParticle(ModParticles.Particles particle, double x, double y, double z) {
        this(particle, x, y, z, 1, 1, 1, 1, 0, 0, 0);
    }

    public MessageParticle(ModParticles.Particles particle, double x, double y, double z, double motionX, double motionY, double motionZ) {
        this(particle, x, y, z, 1, 1, 1, 1, motionX, motionY, motionZ);
    }

    public MessageParticle(ModParticles.Particles particle, double x, double y, double z, double size, double red, double green, double blue) {
        this(particle, x, y, z, size, red, green, blue, 0, 0, 0);
    }

    public MessageParticle(ModParticles.Particles particle, double x, double y, double z, double size, double red, double green, double blue, double motionX, double motionY, double motionZ) {
        this.particle = particle;
        this.x = x;
        this.y = y;
        this.z = z;
        this.size = size;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
    }

    @Override
    public void handleClientSide(MessageParticle message, EntityPlayer player) {
        if (player != null) {
            ModParticles.Particles part = message.particle;
            World world = player.getEntityWorld();
            if (!doParticle(world)) return;
            double x = message.x;
            double y = message.y;
            double z = message.z;
            double size = message.size;
            double red = message.red;
            double green = message.green;
            double blue = message.blue;
            ModParticle particle = ModParticles.getParticle(part, world, x, y, z, size, red, green, blue);
            if (particle == null) return;
            double motionX = message.motionX;
            double motionY = message.motionY;
            double motionZ = message.motionZ;
            particle.setMotion(motionX, motionY, motionZ);
            Charming.proxy.particle(particle);
        }
    }

    protected boolean doParticle(World world) {
        if (!world.isRemote) return false;

        float chance = 1F;
        int particleSetting = Charming.proxy.getMinecraft().gameSettings.particleSetting;
        if (particleSetting == 1) chance = 0.6F;
        else if (particleSetting == 2) chance = 0.2F;

        return chance == 1F || Math.random() < chance;
    }

    @Override
    public void handleServerSide(MessageParticle message, EntityPlayer player) {

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        String name = ByteBufUtils.readUTF8String(buf);
        this.particle = ModParticles.Particles.valueOf(name);
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        this.size = buf.readDouble();
        this.red = buf.readDouble();
        this.green = buf.readDouble();
        this.blue = buf.readDouble();
        this.motionX = buf.readDouble();
        this.motionY = buf.readDouble();
        this.motionZ = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, particle.toString());
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeDouble(size);
        buf.writeDouble(red);
        buf.writeDouble(green);
        buf.writeDouble(blue);
        buf.writeDouble(motionX);
        buf.writeDouble(motionY);
        buf.writeDouble(motionZ);
    }
}
