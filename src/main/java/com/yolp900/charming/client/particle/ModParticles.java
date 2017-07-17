package com.yolp900.charming.client.particle;

import com.yolp900.charming.reference.LibLocations;
import com.yolp900.charming.reference.LibParticles;
import com.yolp900.charming.reference.Reference;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class ModParticles {

    private static TextureMap map;

    public static void registerSprite(ResourceLocation texture) {
        map.registerSprite(texture);
    }

    public static ModParticle getParticle(Particles particle, World world, double x, double y, double z, double size, double red, double green, double blue) {
        return particle.getModParticle(world, x, y, z, size, red, green, blue);
    }

    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent event) {
        map = event.getMap();

        for (Particles particle : Particles.values()) {
            particle.registerSprite();
        }

    }

    public enum Particles {
        ConstructionTableConstruction {
            @Override
            public ModParticle getModParticle(World world, double x, double y, double z, double size, double red, double green, double blue) {
                return new ModParticle(getParticleTexture(), world, x, y, z, size, red, green, blue);
            }

            @Override
            public ResourceLocation getParticleTexture() {
                int i = rand.nextInt(4) + 1;
                return new ResourceLocation(Reference.MOD_ID, LibLocations.PARTICLE_TEXTURE + LibParticles.CONSTRUCTION_TABLE_CONSTRUCTION + i);
            }

            @Override
            public void registerSprite() {
                for (int i = 1; i < 5; i++) {
                    ModParticles.registerSprite(new ResourceLocation(Reference.MOD_ID, LibLocations.PARTICLE_TEXTURE + LibParticles.CONSTRUCTION_TABLE_CONSTRUCTION + i));
                }
            }
        }, SlotUpgrade {
            @Override
            public ModParticle getModParticle(World world, double x, double y, double z, double size, double red, double green, double blue) {
                return new ModParticle(getParticleTexture(), world, x, y, z, size, red, blue, green);
            }

            @Override
            public ResourceLocation getParticleTexture() {
                return new ResourceLocation(Reference.MOD_ID, LibLocations.PARTICLE_TEXTURE + LibParticles.SLOT_UPGRADE);
            }
        }, Transmutation {
            @Override
            public ModParticle getModParticle(World world, double x, double y, double z, double size, double red, double green, double blue) {
                return new ParticleTransmutation(getParticleTexture(), world, x, y, z, size, red, green, blue);
            }

            @Override
            public ResourceLocation getParticleTexture() {
                return new ResourceLocation(Reference.MOD_ID, LibLocations.PARTICLE_TEXTURE + LibParticles.TRANSMUTATION);
            }
        }, Levitator {
            @Override
            public ModParticle getModParticle(World world, double x, double y, double z, double size, double red, double green, double blue) {
                return new ModParticle(getParticleTexture(), world, x, y, z, size, red, green, blue);
            }

            @Override
            public ResourceLocation getParticleTexture() {
                return new ResourceLocation(Reference.MOD_ID, LibLocations.PARTICLE_TEXTURE + LibParticles.LEVITATOR);
            }
        }, Levitate {
            @Override
            public ModParticle getModParticle(World world, double x, double y, double z, double size, double red, double green, double blue) {
                return new ModParticle(getParticleTexture(), world, x, y, z, size, red, green, blue);
            }

            @Override
            public ResourceLocation getParticleTexture() {
                return new ResourceLocation(Reference.MOD_ID, LibLocations.PARTICLE_TEXTURE + LibParticles.LEVITATE);
            }
        };

        Random rand = new Random();

        public abstract ModParticle getModParticle(World world, double x, double y, double z, double size, double red, double green, double blue);

        public abstract ResourceLocation getParticleTexture();

        public void registerSprite() {
            ModParticles.registerSprite(getParticleTexture());
        }
    }
}
