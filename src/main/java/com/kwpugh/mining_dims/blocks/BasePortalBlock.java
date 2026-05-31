package com.kwpugh.mining_dims.blocks;

import com.kwpugh.mining_dims.blocks.entities.PortalBlockEntity;
import com.kwpugh.mining_dims.util.TeleporterUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class BasePortalBlock extends Block implements EntityBlock {
    private final ResourceKey<Level> dimensionKey;

    protected BasePortalBlock(Properties properties, ResourceKey<Level> dimensionKey) {
        super(properties);
        this.dimensionKey = dimensionKey;
    }

    public ResourceKey<Level> getDimensionKey() {
        return dimensionKey;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PortalBlockEntity(pos, state);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        TeleporterUtil.movePlayerOnPortalBlockUse(dimensionKey, level, player, pos, state);
        return InteractionResult.CONSUME;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.mining_dims.portal_block.tip1").withStyle(ChatFormatting.GREEN));
        tooltip.add(Component.translatable("item.mining_dims.portal_block.tip2").withStyle(ChatFormatting.GREEN));
        tooltip.add(Component.translatable("item.mining_dims.portal_block.tip3").withStyle(ChatFormatting.GRAY));
    }
}
