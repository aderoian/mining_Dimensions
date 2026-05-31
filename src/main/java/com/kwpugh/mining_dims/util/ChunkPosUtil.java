package com.kwpugh.mining_dims.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class ChunkPosUtil {
    public static List<BlockPos> getSurroundingNineChunkCenters(Level world, Vec3 position) {
        return getSurroundingChunkCenters(world, position, 1);
    }

    public static List<BlockPos> getSurroundingTwentyFiveChunkCenters(Level world, Vec3 position) {
        return getSurroundingChunkCenters(world, position, 2);
    }

    private static List<BlockPos> getSurroundingChunkCenters(Level world, Vec3 position, int radius) {
        int playerChunkX = (int) position.x >> 4;
        int playerChunkZ = (int) position.z >> 4;
        int topY = world.getMaxBuildHeight() - 1;
        List<BlockPos> chunkCenters = new ArrayList<>();

        for (int xOffset = -radius; xOffset <= radius; xOffset++) {
            for (int zOffset = -radius; zOffset <= radius; zOffset++) {
                int chunkX = playerChunkX + xOffset;
                int chunkZ = playerChunkZ + zOffset;
                int centerX = (chunkX << 4) + 8;
                int centerZ = (chunkZ << 4) + 8;
                chunkCenters.add(new BlockPos(centerX, topY, centerZ));
            }
        }

        return chunkCenters;
    }
}
