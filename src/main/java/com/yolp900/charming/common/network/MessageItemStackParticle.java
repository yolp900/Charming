package com.yolp900.charming.common.network;

import com.yolp900.charming.Charming;
import com.yolp900.charming.client.particle.ModParticle;
import com.yolp900.charming.client.particle.ModParticles;
import com.yolp900.charming.client.particle.ParticleItemStack;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class MessageItemStackParticle extends MessageParticle {
    protected ItemStack stack;

    public MessageItemStackParticle() {

    }

    public MessageItemStackParticle(ItemStack stack, double x, double y, double z) {
        super(ModParticles.Particles.Transmutation, x, y, z);
        this.stack = stack;
    }

    @Override
    public void handleClientSide(MessageParticle message, EntityPlayer player) {
        if (player != null && message instanceof MessageItemStackParticle) {
            MessageItemStackParticle stackMessage = (MessageItemStackParticle) message;
            ItemStack stack = stackMessage.stack;
            World world = player.getEntityWorld();
            if (!doParticle(world)) return;
            double x = stackMessage.x;
            double y = stackMessage.y;
            double z = stackMessage.z;
            double size = stackMessage.size;
            double red = stackMessage.red;
            double green = stackMessage.green;
            double blue = stackMessage.blue;
            ModParticle particle = new ParticleItemStack(stack, world, x, y, z, size, red, green, blue);
            double motionX = stackMessage.motionX;
            double motionY = stackMessage.motionY;
            double motionZ = stackMessage.motionZ;
            particle.setMotion(motionX, motionY, motionZ);
            Charming.proxy.particle(particle);
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        stack = ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
        ByteBufUtils.writeItemStack(buf, stack);
    }
}
