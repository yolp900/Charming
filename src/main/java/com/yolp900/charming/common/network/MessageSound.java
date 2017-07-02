package com.yolp900.charming.common.network;

import com.yolp900.charming.Charming;
import com.yolp900.charming.util.SoundHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class MessageSound extends MessageBase<MessageSound> {
    private String sound;
    private double x, y, z;
    private double volume, pitch;
    private boolean distanceDelay;
    private SoundTypes type;

    public MessageSound() {

    }

    public MessageSound(SoundHandler.ModSounds sound, double x, double y, double z, double volume, double pitch) {
        setParams(sound.name(), x, y, z, volume, pitch, SoundTypes.Player);
    }

    public MessageSound(SoundHandler.ModSounds sound, double x, double y, double z, double volume, double pitch, boolean distanceDelay) {
        setParams(sound.name(), x, y, z, volume, pitch, SoundTypes.Position);
        this.distanceDelay = distanceDelay;
    }

    private void setParams(String sound, double x, double y, double z, double volume, double pitch, SoundTypes type) {
        this.sound = sound;
        this.x = x;
        this.y = y;
        this.z = z;
        this.volume = volume;
        this.pitch = pitch;
        this.type = type;
    }

    @Override
    public void handleClientSide(MessageSound message, EntityPlayer player) {
        if (player != null) {
            String sound = message.sound;
            double x = message.x;
            double y = message.y;
            double z = message.z;
            double volume = message.volume;
            double pitch = message.pitch;
            SoundTypes type = message.type;
            if (type == SoundTypes.Player) {
                Charming.proxy.playSound(sound, player.getEntityWorld(), player, x, y, z, volume, pitch);
            } else if (type == SoundTypes.Position) {
                boolean distanceDelay = message.distanceDelay;
                Charming.proxy.playSound(sound, player.getEntityWorld(), x, y, z, volume, pitch, distanceDelay);
            }
        }
    }

    @Override
    public void handleServerSide(MessageSound message, EntityPlayer player) {

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.sound = ByteBufUtils.readUTF8String(buf);
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        this.volume = buf.readDouble();
        this.pitch = buf.readDouble();
        this.type = SoundTypes.valueOf(ByteBufUtils.readUTF8String(buf));
        this.distanceDelay = buf.readBoolean();

    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, sound);
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeDouble(volume);
        buf.writeDouble(pitch);
        ByteBufUtils.writeUTF8String(buf, type.name());
        buf.writeBoolean(distanceDelay);
    }

    private enum SoundTypes {
        Player, Position
    }

}
