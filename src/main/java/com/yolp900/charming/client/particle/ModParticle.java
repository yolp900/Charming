package com.yolp900.charming.client.particle;

import com.yolp900.charming.Charming;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ModParticle extends Particle {

    protected ModParticle(ResourceLocation texture, World world, double x, double y, double z, double size, double red, double green, double blue) {
        super(world, x, y, z);
        TextureAtlasSprite sprite = Charming.proxy.getMinecraft().getTextureMapBlocks().getAtlasSprite(texture.toString());
        this.setParticleTexture(sprite);
        this.particleScale *= size;
        setColor(red, green, blue);
    }

    public void setColor(double red, double green, double blue) {
        this.particleRed = (float) red;
        this.particleGreen = (float) green;
        this.particleBlue = (float) blue;
    }

    public void setMotion(double motionX, double motionY, double motionZ) {
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
    }

    @Override
    public int getFXLayer() {
        return 1;
    }

    @Override
    public void onUpdate() {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;

        if (particleAge++ >= particleMaxAge) setExpired();

        posX += motionX;
        posY += motionY;
        posZ += motionZ;
    }
}
