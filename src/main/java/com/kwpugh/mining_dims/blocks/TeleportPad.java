package com.kwpugh.mining_dims.blocks;

import com.kwpugh.mining_dims.blocks.entities.TeleportPadBlockEntity;
import com.kwpugh.mining_dims.components.LocationData;
import com.kwpugh.mining_dims.config.MiningDimsConfig;
import com.kwpugh.mining_dims.items.SpatialCaptureItem;
import com.kwpugh.mining_dims.util.TeleporterUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import com.mojang.serialization.MapCodec;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TeleportPad extends BaseEntityBlock {
    public TeleportPad(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(TeleportPad::new);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TeleportPadBlockEntity(pos, state);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        if (!MiningDimsConfig.GENERAL.enableTeleportPad.get()) {
            player.displayClientMessage(Component.translatable("item.mining_dims.diabled_in_config"), true);
            return InteractionResult.CONSUME;
        }

        ItemStack mainHand = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (mainHand.getItem() instanceof SpatialCaptureItem) {
            if (level.getBlockEntity(pos) instanceof TeleportPadBlockEntity padEntity) {
                return SpatialCaptureItem.transferToPad(player, mainHand, padEntity);
            }
            return InteractionResult.PASS;
        }

        if (level.getBlockEntity(pos) instanceof TeleportPadBlockEntity padEntity) {
            LocationData location = padEntity.getLocation();
            if (location == null) {
                player.displayClientMessage(Component.translatable("item.mining_dims.teleport_pad.tip1"), true);
                return InteractionResult.CONSUME;
            }
            if (MiningDimsConfig.GENERAL.enableMessageOnTeleport.get()) {
                player.displayClientMessage(Component.translatable("item.mining_dims.teleport_pad.tip4", location.toDisplayString()), true);
            }
            TeleporterUtil.movePlayerOnTeleportPadUse(level, player, pos, location);
        }

        return InteractionResult.CONSUME;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.mining_dims.teleport_pad.tip1").withStyle(ChatFormatting.GREEN));
        tooltip.add(Component.translatable("item.mining_dims.teleport_pad.tip2").withStyle(ChatFormatting.GREEN));
    }
}
