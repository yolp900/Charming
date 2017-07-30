package com.yolp900.charming.common.tileentities;

import com.yolp900.charming.api.CharmingAPI;
import com.yolp900.charming.api.tiles.IInvertible;
import com.yolp900.charming.client.particle.ModParticles;
import com.yolp900.charming.common.network.MessageParticle;
import com.yolp900.charming.common.network.NetworkHandler;
import com.yolp900.charming.config.ModConfig;
import com.yolp900.charming.reference.LibMisc;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class TileEntityLevitator extends ModTileEntity implements ITickable, IInvertible {
    private Random random = new Random();
    private boolean inverted;

    public TileEntityLevitator() {
        markDirty();
        update();
    }

    @Override
    public void update() {
        if (world == null) return;
        if (isOn()) {
            List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(getPos().getX(), getPos().getY() + 1, getPos().getZ(), getPos().getX() + 1, getPos().getY() + 1 + ModConfig.LEVITATOR_RANGE.getValue(), getPos().getZ() + 1));
            if (entities.size() > 0) {
                for (Entity entity : entities) {
                    if (entity instanceof EntityPlayer) {
                        EntityPlayer liftedPlayer = (EntityPlayer) entity;
                        liftedPlayer.fallDistance = 0;
                        if (liftedPlayer.isSneaking()) {
                            if (liftedPlayer.motionY > 0) {
                                liftedPlayer.motionY -= 0.1;
                            } else {
                                if (liftedPlayer.motionY < -0.35) {
                                    liftedPlayer.motionY = -0.35;
                                }
                            }
                        } else {
                            if (!liftedPlayer.capabilities.isCreativeMode) {
                                if (liftedPlayer.motionY < 0.35) {
                                    liftedPlayer.motionY += 0.1;
                                }
                            }
                        }
                    } else if (entity.canBePushed()) {
                        if (!CharmingAPI.Levitator.isEntityBlacklisted(entity.getClass())) {
                            if (entity.motionY < 0.35) {
                                entity.motionY += 0.1;
                            }
                        }
                    } else if (entity instanceof EntityItem) {
                        if (!CharmingAPI.Levitator.isItemStackBlacklisted(((EntityItem) entity).getItem())) {
                            if (entity.motionY < 0.35) {
                                entity.motionY += 0.1;
                            }
                        }
                    }
                    if (entity.motionY > 0) {
                        if (!world.isRemote && random.nextDouble() < 0.75) {
                            NetworkHandler.sendToAllAround(new MessageParticle(ModParticles.Particles.Levitator, entity.posX - (entity.width / 2) + (random.nextDouble() * entity.width), entity.posY, entity.posZ - (entity.width / 2) + (random.nextDouble() * entity.width), random.nextDouble() + 0.25, (random.nextDouble() / 4) + 0.5, (random.nextDouble() / 4) + 0.5, (random.nextDouble() / 4) + 0.5, 0, -entity.motionY, 0), world.provider.getDimension(), entity.posX, entity.posY, entity.posZ, 16);
                        }
                    }
                }
            }
            double i = random.nextDouble();
            if (!world.isRemote && i < 0.15) {
                NetworkHandler.sendToAllAround(new MessageParticle(ModParticles.Particles.Levitate, getPos().getX() + random.nextDouble(), getPos().getY() + 1.01, getPos().getZ() + random.nextDouble(), random.nextDouble() + 0.25, (random.nextDouble() / 4) + 0.5, (random.nextDouble() / 4) + 0.5, (random.nextDouble() / 4) + 0.5, 0, 0.1, 0), world.provider.getDimension(), getPos().getX() + 0.5, getPos().getY() + 1.05, getPos().getZ() + 0.5, 16);
            }
        }
    }

    @Override
    public boolean isInverted() {
        return inverted;
    }

    @Override
    public void setInverted(boolean inverted) {
        this.inverted = inverted;
        sendUpdates();
    }

    public boolean isOn() {
        if (inverted) {
            return !powered();
        }
        return powered();
    }

    private boolean powered() {
        return world.isBlockPowered(getPos()) || world.isBlockPowered(getPos().up()) || world.isBlockIndirectlyGettingPowered(getPos()) != 0;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.inverted = tag.getBoolean(LibMisc.INVERTED);
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setBoolean(LibMisc.INVERTED, inverted);

        return tag;
    }

}
