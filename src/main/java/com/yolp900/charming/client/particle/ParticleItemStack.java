package com.yolp900.charming.client.particle;

import com.yolp900.charming.Charming;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ParticleItemStack extends ModParticle {
    protected ItemStack stack;

    public ParticleItemStack(ItemStack stack, World world, double x, double y, double z, double size, double red, double green, double blue) {
        super(ModParticles.Particles.Transmutation.getParticleTexture(), world, x, y, z, size, red, green, blue);
        this.stack = stack;
    }

    @Override
    public void renderParticle(VertexBuffer buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        // Render Nothing!
    }

    @Override
    public void onUpdate() {
        if (particleAge++ >= particleMaxAge) setExpired();

        Charming.logger.info("AAA: " + stack.getDisplayName());

        Charming.proxy.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.FIXED); //TODO Not Rendering
    }
}
