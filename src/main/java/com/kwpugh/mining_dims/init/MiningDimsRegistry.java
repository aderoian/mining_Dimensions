package com.kwpugh.mining_dims.init;

import com.kwpugh.mining_dims.MiningDims;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class MiningDimsRegistry {
    public static final ResourceKey<Level> MININGDIMS_WORLD_KEY = ResourceKey.create(Registries.DIMENSION, MiningDims.MOD_DIMENSION_ID);
    public static final ResourceKey<Level> MININGDIMS_WORLD_KEY2 = ResourceKey.create(Registries.DIMENSION, MiningDims.MOD_DIMENSION2_ID);
    public static final ResourceKey<Level> MININGDIMS_WORLD_KEY3 = ResourceKey.create(Registries.DIMENSION, MiningDims.MOD_DIMENSION3_ID);
    public static final ResourceKey<Level> MININGDIMS_WORLD_KEY4 = ResourceKey.create(Registries.DIMENSION, MiningDims.MOD_DIMENSION4_ID);
    public static final ResourceKey<Level> MININGDIMS_WORLD_KEY5 = ResourceKey.create(Registries.DIMENSION, MiningDims.MOD_DIMENSION5_ID);
    public static final ResourceKey<Level> MININGDIMS_WORLD_KEY6 = ResourceKey.create(Registries.DIMENSION, MiningDims.MOD_DIMENSION6_ID);
}
