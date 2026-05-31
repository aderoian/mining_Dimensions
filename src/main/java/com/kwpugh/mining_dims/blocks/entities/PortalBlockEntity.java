package com.kwpugh.mining_dims.blocks.entities;

import com.kwpugh.mining_dims.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PortalBlockEntity extends BlockEntity {
    public PortalBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.PORTAL_BLOCK_BE.get(), pos, state);
    }
}
