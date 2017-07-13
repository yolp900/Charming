package com.yolp900.charming.common.blocks;

import com.yolp900.charming.common.blocks.base.ModBlockLog;
import com.yolp900.charming.reference.LibBlocks;

public class BlockTintedLog extends ModBlockLog {
    public BlockTintedLog() {
        super(LibBlocks.TINTED_LOG);
    }

    @Override
    public boolean usesDefaultBlockRegistry() {
        return true;
    }

    @Override
    public boolean usesDefaultRenderRegistry() {
        return true;
    }
}
