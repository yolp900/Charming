package com.yolp900.charming.common.tileentities;

import com.yolp900.charming.api.CharmingAPI;
import com.yolp900.charming.api.tiles.IInvertible;
import com.yolp900.charming.client.particle.ModParticles;
import com.yolp900.charming.common.network.MessageParticle;
import com.yolp900.charming.common.network.NetworkHandler;
import com.yolp900.charming.config.ModConfig;
import com.yolp900.charming.reference.LibMisc;
import com.yolp900.charming.util.Vector3;
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
            List<EntityItem> entityItems = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(getPos().getX() - range, getPos().getY(), getPos().getZ() - range, getPos().getX() + range, getPos().getY() + 2, getPos().getZ() + range));
            if (entityItems.size() > 0) {
                for (EntityItem entityItem : entityItems) {
                    if (!entityItem.getItem().isEmpty() && !CharmingAPI.Attractor.isItemStackBlacklisted(entityItem.getItem())) {
                        Vector3 blockV = Vector3.fromBlockPos(getPos()).add(0.5);
                        Vector3 entityV = Vector3.fromEntity(entityItem).add(0.5);
                        Vector3 finalV = blockV.subtract(entityV);

                        if (finalV.magnitude() > 1) {
                            finalV = finalV.normalize();
                        }

                        entityItem.motionX = finalV.getX() * 0.1;
                        entityItem.motionY = finalV.getY() * 0.1;
                        entityItem.motionZ = finalV.getZ() * 0.1;

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
