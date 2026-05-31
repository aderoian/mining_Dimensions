package com.kwpugh.mining_dims.util;

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

import java.util.Random;

public class TeleporterUtil {
    public static InteractionResultHolder<ItemStack> movePlayer(ResourceKey<Level> dimKey, Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (world.isClientSide) {
            return InteractionResultHolder.success(stack);
        }

        var registryAccess = world.registryAccess();
        var enchantmentRegistry = registryAccess.registryOrThrow(Registries.ENCHANTMENT);
        var returningHolder = enchantmentRegistry.getHolder(EnchantmentInit.RETURNING);

        if (player.isShiftKeyDown() && returningHolder.isPresent()
                && EnchantmentHelper.getItemEnchantmentLevel(returningHolder.get(), player.getItemBySlot(EquipmentSlot.MAINHAND)) > 0) {
            ServerLevel overworld = ((ServerLevel) world).getServer().getLevel(Level.OVERWORLD);
            ServerPlayer serverPlayer = (ServerPlayer) player;

            if (serverPlayer.getRespawnPosition() != null && overworld != null) {
                BlockPos bedLoc = serverPlayer.getRespawnPosition();
                serverPlayer.stopRiding();
                serverPlayer.teleportTo(overworld, bedLoc.getX() + 0.5F, bedLoc.getY(), bedLoc.getZ() + 0.5F,
                        serverPlayer.getYRot(), serverPlayer.getXRot());
                world.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.NEUTRAL, 0.5F,
                        0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
                player.displayClientMessage(Component.translatable("item.mining_dims.teleporter4"), true);
                return InteractionResultHolder.success(stack);
            } else {
                player.displayClientMessage(Component.translatable("item.mining_dims.teleporter5"), true);
                return InteractionResultHolder.success(stack);
            }
        }

        if (!player.isShiftKeyDown()) {
            ServerLevel destWorld;
            ServerLevel overWorld = ((ServerLevel) world).getServer().getLevel(Level.OVERWORLD);
            ServerLevel targetWorld = ((ServerLevel) world).getServer().getLevel(dimKey);
            ServerPlayer serverPlayer = (ServerPlayer) player;
            ResourceKey<Level> currentWorldKey = world.dimension();

            if (currentWorldKey.equals(dimKey)) {
                destWorld = overWorld;
            } else {
                destWorld = targetWorld;
            }

            if (destWorld == null) {
                return InteractionResultHolder.success(stack);
            }

            ResourceKey<Level> destKey = destWorld.dimension();
            int heightMax = getHeightMax(destKey);
            int heightMin = getHeightMin(destKey);

            for (int i = 1; i < 6; i++) {
                if (i == 1) {
                    serverPlayer.displayClientMessage(Component.translatable("item.mining_dims.teleporter1"), true);
                } else {
                    serverPlayer.displayClientMessage(Component.translatable("item.mining_dims.teleporter2"), true);
                }

                BlockPos playerLoc = player.blockPosition();
                Random rand = new Random();
                int x = Math.round((float) playerLoc.getX()) + rand.nextInt(15) - 5;
                int y = heightMax;
                int z = Math.round((float) playerLoc.getZ()) + rand.nextInt(15) - 5;

                LevelChunk chunk = destWorld.getChunk(x >> 4, z >> 4);

                while (y > heightMin) {
                    y--;
                    BlockPos groundPos = new BlockPos(x, y - 2, z);
                    BlockState groundState = chunk.getBlockState(groundPos);
                    boolean isAir = groundState.isAir();
                    boolean isBedrock = groundState.is(Blocks.BEDROCK);
                    boolean isLava = groundState.is(Blocks.LAVA) || groundState.is(Blocks.MAGMA_BLOCK);
                    boolean canFit = (y - 2) > heightMin;

                    if (!isAir && !isBedrock && !isLava && canFit) {
                        if (groundState.getFluidState().is(FluidTags.WATER)) {
                            destWorld.setBlock(groundPos, Blocks.STONE.defaultBlockState(), 3);
                        }

                        BlockPos legPos = new BlockPos(x, y - 1, z);
                        if (chunk.getBlockState(legPos).isAir()) {
                            BlockPos headPos = new BlockPos(x, y, z);
                            if (chunk.getBlockState(headPos).isAir()) {
                                serverPlayer.stopRiding();
                                Vec3 destVec = new Vec3(x + 0.5, y, z + 0.5);
                                DimensionTransition transition = new DimensionTransition(
                                        destWorld, destVec, Vec3.ZERO, player.getYRot(), player.getXRot(),
                                        false, DimensionTransition.DO_NOTHING);
                                serverPlayer.changeDimension(transition);
                                serverPlayer.resetFallDistance();
                                world.playSound(null, player.getX(), player.getY(), player.getZ(),
                                        SoundEvents.ENDERMAN_TELEPORT, SoundSource.NEUTRAL, 0.5F,
                                        0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
                                return InteractionResultHolder.success(stack);
                            }
                        }
                    }
                }
            }

            serverPlayer.displayClientMessage(Component.translatable("item.mining_dims.teleporter3"), true);
        }

        return InteractionResultHolder.success(stack);
    }

    private static int getHeightMax(ResourceKey<Level> destKey) {
        if (destKey.equals(MiningDimsRegistry.MININGDIMS_WORLD_KEY4)) {
            return 120;
        }
        return 250;
    }

    private static int getHeightMin(ResourceKey<Level> destKey) {
        if (destKey.equals(MiningDimsRegistry.MININGDIMS_WORLD_KEY)
                || destKey.equals(MiningDimsRegistry.MININGDIMS_WORLD_KEY2)
                || destKey.equals(MiningDimsRegistry.MININGDIMS_WORLD_KEY5)) {
            return 20;
        }
        return 61;
    }
}
