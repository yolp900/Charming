package com.yolp900.charming.client.render;

import com.yolp900.charming.Charming;
import com.yolp900.charming.common.tileentities.TileEntityAdjustableStorage;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class AdjustableStorageSpecialRenderer extends TileEntitySpecialRenderer<TileEntityAdjustableStorage> {

    @Override
    public void renderTileEntityAt(TileEntityAdjustableStorage tile, double x, double y, double z, float partialTicks, int destroyStage) {
        int time = tile.getTimeOnOpenState();
        int state = tile.getOpenState();
        if (tile.isOpen()) {
            if (time < 50) {
                tile.setTimeOnOpenState(time + 1);
                if (time > 0 && time % 10 == 0 && state < 5) {
                    tile.setOpenState(state + 1);
                    Charming.logger.info("AA Open state: " + tile.getOpenState());
                }
            }
        } else {
            if (time > 0) {
                tile.setTimeOnOpenState(time - 1);
                if (time < 50 && time % 10 == 0 && state >= 0) {
                    tile.setOpenState(state - 1);
                    Charming.logger.info("BB Open state: " + tile.getOpenState());
                }
            }
        }
    }

}
