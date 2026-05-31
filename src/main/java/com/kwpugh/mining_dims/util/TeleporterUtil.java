package com.kwpugh.mining_dims.util;

import com.kwpugh.mining_dims.components.LocationData;
import com.kwpugh.mining_dims.config.MiningDimsConfig;
import com.kwpugh.mining_dims.init.EnchantmentInit;
import com.kwpugh.mining_dims.init.MiningDimsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TeleporterUtil {
    private static final int[] X_OFFSETS = {0, 0, 2, -2, -2, 4, 4, -4, -4};
    private static final int[] Z_OFFSETS = {0, 0, 2, -2, 2, -2, 4, -4, 4};

    public static InteractionResultHolder<ItemStack> movePlayerOnPortableUse(ResourceKey<Level> dimKey, Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (world.isClientSide) {
            return InteractionResultHolder.success(stack);
        }

        if (player.isShiftKeyDown()) {
            handleReturnToBed(world, player);
            return InteractionResultHolder.success(stack);
        }

        attemptDimensionTravel(dimKey, world, player, null, null, true);
        return InteractionResultHolder.success(stack);
    }

    public static InteractionResultHolder<ItemStack> movePlayer(ResourceKey<Level> dimKey, Level world, Player player, InteractionHand hand) {
        return movePlayerOnPortableUse(dimKey, world, player, hand);
    }

    public static void movePlayerOnPortalBlockUse(ResourceKey<Level> dimKey, Level world, Player player, BlockPos portalBlockPos, BlockState portalBlockState) {
        if (world.isClientSide || player.isShiftKeyDown()) {
            return;
        }
        attemptDimensionTravel(dimKey, world, player, portalBlockPos, portalBlockState, false);
    }

    public static void movePlayerOnTeleportPadUse(Level world, Player player, BlockPos padPos, LocationData location) {
        if (world.isClientSide || !(player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        ServerLevel serverWorld = (ServerLevel) world;
        ServerLevel targetWorld = serverWorld.getServer().getLevel(location.dimension());
        if (targetWorld == null) {
            player.displayClientMessage(Component.translatable("item.mining_dims.spatial_tool7"), true);
            return;
        }

        BlockPos targetPos = location.blockPos();
        teleportToPosition(targetWorld, serverPlayer, player, targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5);
    }

    private static void handleReturnToBed(Level world, Player player) {
        var enchantmentRegistry = world.registryAccess().registryOrThrow(Registries.ENCHANTMENT);
        var returningHolder = enchantmentRegistry.getHolder(EnchantmentInit.RETURNING);

        if (returningHolder.isEmpty()
                || EnchantmentHelper.getItemEnchantmentLevel(returningHolder.get(), player.getItemBySlot(EquipmentSlot.MAINHAND)) <= 0) {
            return;
        }

        ServerLevel overworld = ((ServerLevel) world).getServer().getLevel(Level.OVERWORLD);
        if (!(player instanceof ServerPlayer serverPlayer) || overworld == null) {
            return;
        }

        if (serverPlayer.getRespawnPosition() != null) {
            BlockPos bedLoc = serverPlayer.getRespawnPosition();
            serverPlayer.stopRiding();
            serverPlayer.teleportTo(overworld, bedLoc.getX() + 0.5F, bedLoc.getY(), bedLoc.getZ() + 0.5F,
                    serverPlayer.getYRot(), serverPlayer.getXRot());
            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.NEUTRAL, 0.5F,
                    0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
            player.displayClientMessage(Component.translatable("item.mining_dims.teleporter4"), true);
        } else {
            player.displayClientMessage(Component.translatable("item.mining_dims.teleporter5"), true);
        }
    }

    private static void attemptDimensionTravel(ResourceKey<Level> dimKey, Level world, Player player,
                                               BlockPos portalBlockPos, BlockState portalBlockState, boolean portable) {
        ServerLevel serverWorld = (ServerLevel) world;
        ServerLevel overWorld = serverWorld.getServer().getLevel(Level.OVERWORLD);
        ServerLevel targetWorld = serverWorld.getServer().getLevel(dimKey);
        if (overWorld == null || targetWorld == null || !(player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        ResourceKey<Level> currentWorldKey = world.dimension();
        ServerLevel destWorld = currentWorldKey.equals(dimKey) ? overWorld : targetWorld;
        ResourceKey<Level> destKey = destWorld.dimension();

        int heightMax = getHeightMax(destKey);
        int heightMin = getHeightMin(destKey);

        serverPlayer.displayClientMessage(Component.translatable("item.mining_dims.portal_block.tip5"), true);

        BlockPos existingPortal = null;
        if (!portable && portalBlockPos != null && portalBlockState != null) {
            existingPortal = checkForPortalBlock(destWorld, portalBlockPos, portalBlockState, serverPlayer);
            if (existingPortal != null) {
                teleportDirectToPortalBlock(destWorld, serverPlayer, player, existingPortal);
                return;
            }
        }

        BlockPos referencePos = portalBlockPos != null ? portalBlockPos : player.blockPosition();

        if (destKey.equals(MiningDimsRegistry.MININGDIMS_WORLD_KEY6)) {
            teleportToSkyDimension(destWorld, serverPlayer, player, referencePos, portalBlockState, existingPortal != null);
            return;
        }

        for (int attempt = 0; attempt < X_OFFSETS.length; attempt++) {
            if (attempt == 0) {
                serverPlayer.displayClientMessage(Component.translatable("item.mining_dims.teleporter1"), true);
            } else {
                serverPlayer.displayClientMessage(Component.translatable("item.mining_dims.teleporter2"), true);
            }

            int x = referencePos.getX() + X_OFFSETS[attempt];
            int z = referencePos.getZ() + Z_OFFSETS[attempt];
            int y = heightMax;

            while (y > heightMin) {
                y--;
                BlockPos groundPos = new BlockPos(x, y - 2, z);
                BlockState groundState = destWorld.getBlockState(groundPos);
                boolean isAir = groundState.isAir();
                boolean isBedrock = groundState.is(Blocks.BEDROCK);
                boolean isLava = groundState.is(Blocks.LAVA) || groundState.is(Blocks.MAGMA_BLOCK);
                boolean canFit = (y - 2) > heightMin;

                if (!isAir && !isBedrock && !isLava && canFit) {
                    if (groundState.getFluidState().is(FluidTags.WATER)) {
                        destWorld.setBlock(groundPos, Blocks.STONE.defaultBlockState(), 3);
                    }

                    BlockPos legPos = new BlockPos(x, y - 1, z);
                    BlockPos headPos = new BlockPos(x, y, z);
                    if (destWorld.getBlockState(legPos).isAir() && destWorld.getBlockState(headPos).isAir()) {
                        if (!portable && portalBlockState != null) {
                            destWorld.setBlock(groundPos, portalBlockState, 3);
                        }
                        teleportToPosition(destWorld, serverPlayer, player, x + 0.5, y, z + 0.5);
                        return;
                    }
                }
            }
        }

        serverPlayer.displayClientMessage(Component.translatable("item.mining_dims.teleporter3"), true);
    }

    private static void teleportToSkyDimension(ServerLevel destWorld, ServerPlayer serverPlayer, Player player,
                                                BlockPos referencePos, BlockState portalBlockState, boolean existingPortalFound) {
        int x = referencePos.getX();
        int z = referencePos.getZ();
        BlockPos portalPos = new BlockPos(x, 63, z);
        BlockPos plankPos = new BlockPos(x, 62, z);

        if (!existingPortalFound) {
            if (portalBlockState != null) {
                destWorld.setBlock(portalPos, portalBlockState, 3);
                destWorld.setBlock(plankPos, Blocks.OAK_PLANKS.defaultBlockState(), 3);
            } else if (destWorld.getBlockState(portalPos).is(BlockTags.REPLACEABLE)) {
                destWorld.setBlock(plankPos, Blocks.OAK_PLANKS.defaultBlockState(), 3);
            }
        }

        teleportToPosition(destWorld, serverPlayer, player, x + 0.5, 64, z + 0.5);
    }

    private static BlockPos checkForPortalBlock(ServerLevel destWorld, BlockPos portalBlockPos, BlockState portalBlockState, ServerPlayer player) {
        Vec3 searchPos = new Vec3(portalBlockPos.getX(), portalBlockPos.getY(), portalBlockPos.getZ());
        List<BlockPos> chunkCenters = MiningDimsConfig.GENERAL.enableExtendedSearchRange.get()
                ? ChunkPosUtil.getSurroundingTwentyFiveChunkCenters(destWorld, searchPos)
                : ChunkPosUtil.getSurroundingNineChunkCenters(destWorld, searchPos);

        Set<BlockPos> blockEntityPositions = new HashSet<>();
        for (BlockPos center : chunkCenters) {
            LevelChunk chunk = destWorld.getChunk(center.getX() >> 4, center.getZ() >> 4);
            blockEntityPositions.addAll(chunk.getBlockEntities().keySet());
        }

        player.displayClientMessage(Component.translatable("item.mining_dims.portal_block.tip4"), true);

        BlockPos found = null;
        for (BlockPos pos : blockEntityPositions) {
            BlockState state = destWorld.getBlockState(pos);
            if (state.getBlock() == portalBlockState.getBlock()) {
                found = pos;
            }
        }
        return found;
    }

    private static void teleportDirectToPortalBlock(ServerLevel destWorld, ServerPlayer serverPlayer, Player player, BlockPos portalPos) {
        teleportToPosition(destWorld, serverPlayer, player,
                portalPos.getX() + 0.5, portalPos.getY() + 1, portalPos.getZ() + 0.5);
    }

    private static void teleportToPosition(ServerLevel destWorld, ServerPlayer serverPlayer, Player player,
                                           double x, double y, double z) {
        serverPlayer.stopRiding();
        Vec3 destVec = new Vec3(x, y, z);
        DimensionTransition transition = new DimensionTransition(
                destWorld, destVec, Vec3.ZERO, player.getYRot(), player.getXRot(),
                false, DimensionTransition.DO_NOTHING);
        serverPlayer.changeDimension(transition);
        serverPlayer.resetFallDistance();
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ENDERMAN_TELEPORT, SoundSource.NEUTRAL, 0.5F,
                0.4F / (player.level().getRandom().nextFloat() * 0.4F + 0.8F));
    }

    private static int getHeightMax(ResourceKey<Level> destKey) {
        if (destKey.equals(MiningDimsRegistry.MININGDIMS_WORLD_KEY4)) {
            return 100;
        }
        return 200;
    }

    private static int getHeightMin(ResourceKey<Level> destKey) {
        if (destKey.equals(MiningDimsRegistry.MININGDIMS_WORLD_KEY4)) {
            return 40;
        }
        return 20;
    }
}
