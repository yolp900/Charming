package com.yolp900.charming.api.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IConvertible {

    void convertState(World world, BlockPos pos, EntityPlayer player);

}
