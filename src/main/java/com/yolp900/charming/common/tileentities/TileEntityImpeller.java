package com.yolp900.charming.common.tileentities;

import com.yolp900.charming.api.tiles.IInvertible;
import com.yolp900.charming.config.ModConfig;
import com.yolp900.charming.reference.LibMisc;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class TileEntityImpeller extends ModTileEntity implements ITickable, IInvertible {
    private Random random = new Random();
    private boolean inverted;

    public TileEntityImpeller() {
        markDirty();
        update();
    }

    @Override
    public void update() {
        if (world == null) return;
        if (isOn()) {
            float range = ModConfig.ATTRACTOR_IMPELLER_RANGE.getValue();
            List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(getPos().getX() - range, getPos().getY(), getPos().getZ() - range, getPos().getX() + range, getPos().getY() + 2, getPos().getZ() + range));
            if (entities.size() > 0) {
                for (Entity entity : entities) {

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
        world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
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
