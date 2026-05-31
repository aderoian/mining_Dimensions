package com.kwpugh.mining_dims.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class BaseTeleporter extends Item {
    public BaseTeleporter(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        return stack.copy();
    }
}
