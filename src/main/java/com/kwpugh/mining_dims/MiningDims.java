package com.kwpugh.mining_dims;

import com.kwpugh.mining_dims.config.MiningDimsConfig;
import com.kwpugh.mining_dims.init.BlockInit;
import com.kwpugh.mining_dims.init.ItemInit;
import com.kwpugh.mining_dims.util.MiningDimsGroup;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;

@Mod(MiningDims.MOD_ID)
public class MiningDims {
    public static final String MOD_ID = "mining_dims";

    public static final ResourceLocation MOD_DIMENSION_ID = id("mining_dim");
    public static final ResourceLocation MOD_DIMENSION2_ID = id("hunting_dim");
    public static final ResourceLocation MOD_DIMENSION3_ID = id("caving_dim");
    public static final ResourceLocation MOD_DIMENSION4_ID = id("nethering_dim");
    public static final ResourceLocation MOD_DIMENSION5_ID = id("climbing_dim");

    public MiningDims(IEventBus modEventBus, ModContainer modContainer) {
        BlockInit.BLOCKS.register(modEventBus);
        BlockInit.ITEMS.register(modEventBus);
        ItemInit.ITEMS.register(modEventBus);
        MiningDimsGroup.CREATIVE_TABS.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, MiningDimsConfig.SPEC);

        modEventBus.register(com.kwpugh.mining_dims.event.MiningDimsModEvents.class);
        NeoForge.EVENT_BUS.register(com.kwpugh.mining_dims.event.MiningDimsEvents.class);
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
