package com.kwpugh.mining_dims.blocks;

import com.kwpugh.mining_dims.init.MiningDimsRegistry;

public class MiningBlock extends BasePortalBlock {
    public MiningBlock(Properties properties) {
        super(properties, MiningDimsRegistry.MININGDIMS_WORLD_KEY);
    }
}
