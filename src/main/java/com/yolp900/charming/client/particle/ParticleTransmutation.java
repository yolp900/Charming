package com.yolp900.charming.client.particle;

import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ParticleTransmutation extends ModParticle {
    private double initX;
    private double initZ;
    private float partialTicks;
    private double spinSpeed;
    private double spinSize;

    protected ParticleTransmutation(ResourceLocation texture, World world, double x, double y, double z, double size, double red, double green, double blue) {
        super(texture, world, x, y, z, size, red, green, blue);
        this.initX = x;
        this.initZ = z;
        this.partialTicks = 0;
        setMaxAge(rand.nextInt(20) + 20);
        this.spinSpeed = 0.04 + rand.nextDouble() / 5;
        this.spinSize = 1 + rand.nextDouble() * 3;
    }

    @Override
    public void renderParticle(VertexBuffer buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
        this.partialTicks += spinSpeed;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        this.posX = initX + Math.sin(partialTicks) / spinSize;
        this.posZ = initZ + Math.cos(partialTicks) / spinSize;
    }
}
