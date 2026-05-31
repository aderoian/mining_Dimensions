package com.kwpugh.mining_dims.init;

import com.kwpugh.mining_dims.MiningDims;
import com.kwpugh.mining_dims.blocks.entities.PortalBlockEntity;
import com.kwpugh.mining_dims.blocks.entities.TeleportPadBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BlockEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MiningDims.MOD_ID);

    public static final Supplier<BlockEntityType<PortalBlockEntity>> PORTAL_BLOCK_BE = BLOCK_ENTITIES.register(
            "portal_block",
            () -> BlockEntityType.Builder.of(PortalBlockEntity::new,
                    BlockInit.MINING_PORTAL_BLOCK.get(),
                    BlockInit.HUNTING_PORTAL_BLOCK.get(),
                    BlockInit.CAVING_PORTAL_BLOCK.get(),
                    BlockInit.NETHERING_PORTAL_BLOCK.get(),
                    BlockInit.CLIMBING_PORTAL_BLOCK.get(),
                    BlockInit.SKY_PORTAL_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<TeleportPadBlockEntity>> TELEPORT_PAD_BE = BLOCK_ENTITIES.register(
            "teleport_pad",
            () -> BlockEntityType.Builder.of(TeleportPadBlockEntity::new, BlockInit.TELEPORT_PAD.get()).build(null));
}
