package com.kwpugh.mining_dims.util;

import com.kwpugh.mining_dims.MiningDims;
import com.kwpugh.mining_dims.init.BlockInit;
import com.kwpugh.mining_dims.init.ItemInit;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MiningDimsGroup {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MiningDims.MOD_ID);

    public static final Supplier<CreativeModeTab> MINING_DIMS_TAB = CREATIVE_TABS.register("mining_dims_group",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.mining_dims.mining_dims_group"))
                    .icon(() -> new ItemStack(ItemInit.MINING_TELEPORTER.get()))
                    .displayItems((params, output) -> {
                        output.accept(BlockInit.MINING_PORTAL_BLOCK.get());
                        output.accept(BlockInit.CLIMBING_PORTAL_BLOCK.get());
                        output.accept(BlockInit.CAVING_PORTAL_BLOCK.get());
                        output.accept(BlockInit.HUNTING_PORTAL_BLOCK.get());
                        output.accept(BlockInit.NETHERING_PORTAL_BLOCK.get());
                        output.accept(BlockInit.SKY_PORTAL_BLOCK.get());
                        output.accept(BlockInit.TELEPORT_PAD.get());
                        output.accept(ItemInit.MINING_TELEPORTER.get());
                        output.accept(ItemInit.CLIMBING_TELEPORTER.get());
                        output.accept(ItemInit.CAVING_TELEPORTER.get());
                        output.accept(ItemInit.HUNTING_TELEPORTER.get());
                        output.accept(ItemInit.NETHERING_TELEPORTER.get());
                        output.accept(ItemInit.SKY_TELEPORTER.get());
                        output.accept(ItemInit.SPATIAL_CORE.get());
                        output.accept(ItemInit.SPATIAL_TOOL.get());
                        output.accept(ItemInit.DIAMOND_NUGGET.get());
                        output.accept(ItemInit.NETHERITE_FRAGMENT.get());
                    })
                    .build());
}
