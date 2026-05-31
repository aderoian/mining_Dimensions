package com.kwpugh.mining_dims.init;

import com.kwpugh.mining_dims.MiningDims;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockInit {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MiningDims.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MiningDims.MOD_ID);

    public static final DeferredBlock<Block> MINING_PORTAL_BLOCK = registerPortalBlock("mining_portal_block");
    public static final DeferredBlock<Block> HUNTING_PORTAL_BLOCK = registerPortalBlock("hunting_portal_block");
    public static final DeferredBlock<Block> CAVING_PORTAL_BLOCK = registerPortalBlock("caving_portal_block");
    public static final DeferredBlock<Block> NETHERING_PORTAL_BLOCK = registerPortalBlock("nethering_portal_block");
    public static final DeferredBlock<Block> CLIMBING_PORTAL_BLOCK = registerPortalBlock("climbing_portal_block");

    public static final DeferredItem<BlockItem> MINING_PORTAL_BLOCK_ITEM = registerBlockItem(MINING_PORTAL_BLOCK);
    public static final DeferredItem<BlockItem> HUNTING_PORTAL_BLOCK_ITEM = registerBlockItem(HUNTING_PORTAL_BLOCK);
    public static final DeferredItem<BlockItem> CAVING_PORTAL_BLOCK_ITEM = registerBlockItem(CAVING_PORTAL_BLOCK);
    public static final DeferredItem<BlockItem> NETHERING_PORTAL_BLOCK_ITEM = registerBlockItem(NETHERING_PORTAL_BLOCK);
    public static final DeferredItem<BlockItem> CLIMBING_PORTAL_BLOCK_ITEM = registerBlockItem(CLIMBING_PORTAL_BLOCK);

    private static DeferredBlock<Block> registerPortalBlock(String name) {
        return BLOCKS.register(name, () -> new Block(BlockBehaviour.Properties.of().strength(2.0F, 2.0F)));
    }

    private static DeferredItem<BlockItem> registerBlockItem(DeferredBlock<Block> block) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
    }
}
