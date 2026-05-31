package com.kwpugh.mining_dims.items;

import com.kwpugh.mining_dims.blocks.entities.TeleportPadBlockEntity;
import com.kwpugh.mining_dims.components.LocationData;
import com.kwpugh.mining_dims.config.MiningDimsConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

public class SpatialCaptureItem extends Item {
    public SpatialCaptureItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide) {
            return InteractionResultHolder.success(stack);
        }

        if (!MiningDimsConfig.GENERAL.enableTeleportPad.get()) {
            player.displayClientMessage(Component.translatable("item.mining_dims.diabled_in_config"), true);
            return InteractionResultHolder.success(stack);
        }

        if (player.isShiftKeyDown()) {
            Vec3 look = player.getLookAngle();
            if (look.y > 0.5) {
                LocationData.clearStack(stack);
                player.displayClientMessage(Component.translatable("item.mining_dims.spatial_tool3"), true);
            } else if (MiningDimsConfig.GENERAL.enableMessageOnSneak.get()) {
                player.displayClientMessage(Component.translatable("item.mining_dims.spatial_tool6"), true);
            }
        } else if (MiningDimsConfig.GENERAL.enableMessageOnSneak.get()) {
            Optional<LocationData> existing = LocationData.fromStack(stack);
            if (existing.isPresent()) {
                player.displayClientMessage(Component.translatable("item.mining_dims.spatial_tool4", existing.get().toDisplayString()), true);
            } else {
                player.displayClientMessage(Component.translatable("item.mining_dims.spatial_tool7"), true);
            }
        }

        return InteractionResultHolder.success(stack);
    }

    @Override
    public InteractionResult useOn(net.minecraft.world.item.context.UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        ItemStack stack = context.getItemInHand();

        if (level.isClientSide || player == null) {
            return InteractionResult.SUCCESS;
        }

        if (!MiningDimsConfig.GENERAL.enableTeleportPad.get()) {
            player.displayClientMessage(Component.translatable("item.mining_dims.diabled_in_config"), true);
            return InteractionResult.CONSUME;
        }

        if (player.isShiftKeyDown() && context.getClickedFace().getStepY() > 0) {
            BlockPos capturePos = pos.above();
            LocationData location = LocationData.builder()
                    .dimension(level.dimension())
                    .blockPos(capturePos)
                    .build();
            LocationData.writeToStack(stack, location);
            player.displayClientMessage(Component.translatable("item.mining_dims.spatial_tool1", location.toDisplayString()), true);
            return InteractionResult.CONSUME;
        }

        if (level.getBlockEntity(pos) instanceof TeleportPadBlockEntity padEntity) {
            return transferToPad(player, stack, padEntity);
        }

        return InteractionResult.PASS;
    }

    public static InteractionResult transferToPad(Player player, ItemStack stack, TeleportPadBlockEntity padEntity) {
        Optional<LocationData> location = LocationData.fromStack(stack);
        if (location.isEmpty()) {
            player.displayClientMessage(Component.translatable("item.mining_dims.spatial_tool7"), true);
            return InteractionResult.CONSUME;
        }
        padEntity.setLocation(location.get());
        if (MiningDimsConfig.GENERAL.enableMessageOnTeleport.get()) {
            player.displayClientMessage(Component.translatable("item.mining_dims.spatial_tool8"), true);
            player.displayClientMessage(Component.translatable("item.mining_dims.teleport_pad.tip3", location.get().toDisplayString()), true);
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.mining_dims.spatial_tool.use").withStyle(ChatFormatting.GREEN));
        tooltip.add(Component.translatable("item.mining_dims.spatial_tool5").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("item.mining_dims.spatial_tool9").withStyle(ChatFormatting.GRAY));
        LocationData.fromStack(stack).ifPresent(location ->
                tooltip.add(Component.translatable("item.mining_dims.spatial_tool4", location.toDisplayString()).withStyle(ChatFormatting.AQUA)));
    }
}
