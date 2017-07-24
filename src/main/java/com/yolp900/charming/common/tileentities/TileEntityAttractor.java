package com.yolp900.charming.common.tileentities;

import com.yolp900.charming.api.CharmingAPI;
import com.yolp900.charming.api.tiles.IInvertible;
import com.yolp900.charming.client.particle.ModParticles;
import com.yolp900.charming.common.network.MessageParticle;
import com.yolp900.charming.common.network.NetworkHandler;
import com.yolp900.charming.config.ModConfig;
import com.yolp900.charming.reference.LibMisc;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class TileEntityAttractor extends ModTileEntity implements ITickable, IInvertible {
    private Random random = new Random();
    private boolean inverted;

    public TileEntityAttractor() {
        markDirty();
        update();
    }

    @Override
    public void update() {
        if (world == null) return;
        if (isOn()) {
            float range = ModConfig.ATTRACTOR_RANGE.getValue();
            List<EntityItem> entityItems = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(getPos().getX() - range, getPos().getY(), getPos().getZ() - range, getPos().getX() + range + 1, getPos().getY() + 3, getPos().getZ() + range + 1));
            if (entityItems.size() > 0) {
                for (EntityItem entityItem : entityItems) {
                    if (!entityItem.getItem().isEmpty() && !CharmingAPI.Attractor.isItemStackBlacklisted(entityItem.getItem())) {
                        double x = getPos().getX() + 0.5 - entityItem.posX;
                        double y = getPos().getY() + 0.5 - entityItem.posY;
                        double z = getPos().getZ() + 0.5 - entityItem.posZ;
                        double mag = Math.sqrt(x*x + y*y + z*z);
                        if (mag > 1) {
                            x *= 1 / mag;
                            y *= 1 / mag;
                            z *= 1 / mag;
                        }

                        entityItem.motionX = x * 0.1;
                        entityItem.motionY = y * 0.1;
                        entityItem.motionZ = z * 0.1;

                        if (!world.isRemote && random.nextDouble() < 0.75) {
                            NetworkHandler.sendToAllAround(new MessageParticle(ModParticles.Particles.Levitator, entityItem.posX - (entityItem.width / 2) + (random.nextDouble() * entityItem.width), entityItem.posY - (entityItem.height / 2) + (random.nextDouble() * entityItem.height), entityItem.posZ - (entityItem.width / 2) + (random.nextDouble() * entityItem.width), random.nextDouble() + 0.25, (random.nextDouble() / 4) + 0.5, (random.nextDouble() / 4) + 0.5, (random.nextDouble() / 4) + 0.5, -entityItem.motionX, -entityItem.motionY, -entityItem.motionZ), world.provider.getDimension(), entityItem.posX, entityItem.posY, entityItem.posZ, 16);
                        }
                    }
                }
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
        if (isInverted()) {
            return !powered();
        }
        return powered();
    }

    private boolean powered() {
        return world.isBlockPowered(getPos()) || world.isBlockPowered(getPos().up()) || world.isBlockIndirectlyGettingPowered(getPos()) != 0;
    }

    private void sendUpdates() {
        world.markBlockRangeForRenderUpdate(pos, pos);
        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        world.scheduleBlockUpdate(pos, this.getBlockType(), 0, 0);
        markDirty();
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
