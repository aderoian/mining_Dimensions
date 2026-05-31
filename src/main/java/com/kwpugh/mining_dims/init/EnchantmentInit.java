package com.kwpugh.mining_dims.init;

import com.kwpugh.mining_dims.MiningDims;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentInit {
    public static final ResourceKey<Enchantment> RETURNING = ResourceKey.create(Registries.ENCHANTMENT, MiningDims.id("returning"));
}
