package com.kwpugh.mining_dims.init;

import com.kwpugh.mining_dims.MiningDims;
import com.kwpugh.mining_dims.blocks.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockInit {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MiningDims.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MiningDims.MOD_ID);

    private static final BlockBehaviour.Properties PORTAL_PROPERTIES = BlockBehaviour.Properties.of().strength(2.0F, 2.0F).requiresCorrectToolForDrops();

    public static final DeferredBlock<MiningBlock> MINING_PORTAL_BLOCK = BLOCKS.register("mining_portal_block",
            () -> new MiningBlock(PORTAL_PROPERTIES));
    public static final DeferredBlock<HuntingBlock> HUNTING_PORTAL_BLOCK = BLOCKS.register("hunting_portal_block",
            () -> new HuntingBlock(PORTAL_PROPERTIES));
    public static final DeferredBlock<CavingBlock> CAVING_PORTAL_BLOCK = BLOCKS.register("caving_portal_block",
            () -> new CavingBlock(PORTAL_PROPERTIES));
    public static final DeferredBlock<NetheringBlock> NETHERING_PORTAL_BLOCK = BLOCKS.register("nethering_portal_block",
            () -> new NetheringBlock(PORTAL_PROPERTIES));
    public static final DeferredBlock<ClimbingBlock> CLIMBING_PORTAL_BLOCK = BLOCKS.register("climbing_portal_block",
            () -> new ClimbingBlock(PORTAL_PROPERTIES));
    public static final DeferredBlock<SkyBlock> SKY_PORTAL_BLOCK = BLOCKS.register("sky_portal_block",
            () -> new SkyBlock(PORTAL_PROPERTIES));
    public static final DeferredBlock<TeleportPad> TELEPORT_PAD = BLOCKS.register("teleport_pad",
            () -> new TeleportPad(PORTAL_PROPERTIES));

    public static final DeferredItem<BlockItem> MINING_PORTAL_BLOCK_ITEM = registerBlockItem(MINING_PORTAL_BLOCK);
    public static final DeferredItem<BlockItem> HUNTING_PORTAL_BLOCK_ITEM = registerBlockItem(HUNTING_PORTAL_BLOCK);
    public static final DeferredItem<BlockItem> CAVING_PORTAL_BLOCK_ITEM = registerBlockItem(CAVING_PORTAL_BLOCK);
    public static final DeferredItem<BlockItem> NETHERING_PORTAL_BLOCK_ITEM = registerBlockItem(NETHERING_PORTAL_BLOCK);
    public static final DeferredItem<BlockItem> CLIMBING_PORTAL_BLOCK_ITEM = registerBlockItem(CLIMBING_PORTAL_BLOCK);
    public static final DeferredItem<BlockItem> SKY_PORTAL_BLOCK_ITEM = registerBlockItem(SKY_PORTAL_BLOCK);
    public static final DeferredItem<BlockItem> TELEPORT_PAD_ITEM = registerBlockItem(TELEPORT_PAD);

    private static <T extends net.minecraft.world.level.block.Block> DeferredItem<BlockItem> registerBlockItem(DeferredBlock<T> block) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
    }
}
