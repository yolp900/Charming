package com.yolp900.charming.common.blocks;

import com.yolp900.charming.client.particle.ModParticles;
import com.yolp900.charming.common.blocks.base.ModBlock;
import com.yolp900.charming.common.network.MessageParticle;
import com.yolp900.charming.common.network.MessageSound;
import com.yolp900.charming.common.network.NetworkHandler;
import com.yolp900.charming.config.ModConfig;
import com.yolp900.charming.reference.LibBlocks;
import com.yolp900.charming.util.SoundHandler;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class BlockElevator extends ModBlock {

    public BlockElevator() {
        super(LibBlocks.ELEVATOR, 3F, 7F, Material.WOOD);
    }

    @Override
    public boolean usesDefaultBlockRegistry() {
        return true;
    }

    @Override
    public boolean usesDefaultRenderRegistry() {
        return true;
    }

    public static class ElevatorHandler {

        @SubscribeEvent
        public void onPlayerJump(LivingEvent.LivingJumpEvent event) { //TODO: Fix "Player moved wrongly!" bug.
            EntityLivingBase entityLivingBase = event.getEntityLiving();
            if (entityLivingBase != null && entityLivingBase instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entityLivingBase;
                World world = player.getEntityWorld();
                BlockPos pos = player.getPosition();
                if (world.getBlockState(pos.down()).equals(ModBlocks.Elevator.getDefaultState())) {
                    for (int i = 1; i < (ModConfig.LEVITATOR_ELEVATOR_RANGE.getValue() + 1); i++) {
                        if (world.getBlockState(pos.up(i)).equals(ModBlocks.Elevator.getDefaultState()) && isBlockEmpty(world, pos.up(i + 1)) && isBlockEmpty(world, pos.up(i + 2))) {
                            BlockPos destElevator = pos.up(i);
                            teleportPlayerTo(world, player, destElevator.getX() + 0.5, destElevator.getY() + 1.1, destElevator.getZ() + 0.5);
                            player.setJumping(false);
                            break;
                        }
                    }
                }
            }
        }

        @SubscribeEvent
        public void onPlayerSneak(TickEvent.PlayerTickEvent event) { //TODO: Fix "Player moved wrongly!" spam.
            EntityPlayer player = event.player;
            if (player != null) {
                World world = player.getEntityWorld();
                BlockPos pos = player.getPosition();
                if (player.isSneaking() && world.getBlockState(pos.down()).equals(ModBlocks.Elevator.getDefaultState())) {
                    for (int i = 1; i < (ModConfig.LEVITATOR_ELEVATOR_RANGE.getValue() + 1); i++) {
                        if (world.getBlockState(pos.down(i + 1)).equals(ModBlocks.Elevator.getDefaultState()) && isBlockEmpty(world, pos.down(i)) && isBlockEmpty(world, pos.down(i - 1))) {
                            BlockPos destElevator = pos.down(i);
                            teleportPlayerTo(world, player, destElevator.getX() + 0.5, destElevator.getY() + 0.1, destElevator.getZ() + 0.5);
                            player.setSneaking(false);
                            break;
                        }
                    }
                }
            }
        }
    }

    private static void teleportPlayerTo(World world, EntityPlayer player, double xDest, double yDest, double zDest) {
        if (player instanceof EntityPlayerMP) {
            EntityPlayerMP playerMP = (EntityPlayerMP) player;
            if (playerMP.connection.getNetworkManager().isChannelOpen() && playerMP.world == world && !playerMP.isPlayerSleeping()) {
                playerMP.setPositionAndUpdate(xDest, yDest, zDest);
                playerMP.fallDistance = 0.0F;
            }
        } else {
            player.setPositionAndUpdate(xDest, yDest, zDest);
            player.fallDistance = 0.0F;
        }
        if (!world.isRemote) {
            NetworkHandler.sendToAllAround(new MessageSound(SoundHandler.ModSounds.Elevator, xDest, yDest, zDest, 1, 1), player.dimension, xDest, yDest, zDest, 16);
            Random rand = new Random();
            int flag = rand.nextInt(16) + 24;
            for (int i = 0; i < flag; ++i) {
                ModParticles.Particles particle;
                if (rand.nextBoolean()) {
                    particle = ModParticles.Particles.Levitate;
                } else {
                    particle = ModParticles.Particles.Levitator;
                }
                MessageParticle message = new MessageParticle(particle, xDest, yDest + rand.nextDouble() * (player.height / 2), zDest, rand.nextGaussian() + 0.25, (rand.nextGaussian() / 4) + 0.5, (rand.nextGaussian() / 4) + 0.5, (rand.nextGaussian() / 4) + 0.5, rand.nextGaussian(), 0, rand.nextGaussian());
                NetworkHandler.sendToAllAround(message, player.dimension, xDest, yDest, zDest, 16);
            }
        }
    }

    private static boolean isBlockEmpty(World world, BlockPos pos) {
        return world.getBlockState(pos).getMaterial() == Material.AIR || !world.getBlockState(pos).getMaterial().blocksMovement();
    }

}
