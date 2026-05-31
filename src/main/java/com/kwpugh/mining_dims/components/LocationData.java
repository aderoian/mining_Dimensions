package com.kwpugh.mining_dims.components;

import com.kwpugh.mining_dims.MiningDims;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

import java.util.Optional;

public record LocationData(ResourceKey<Level> dimension, BlockPos blockPos) {
    private static final String TAG = "TeleportLocation";

    public static Optional<LocationData> fromStack(ItemStack stack) {
        CustomData customData = stack.get(net.minecraft.core.component.DataComponents.CUSTOM_DATA);
        if (customData == null) {
            return Optional.empty();
        }
        CompoundTag tag = customData.copyTag();
        if (!tag.contains(TAG)) {
            return Optional.empty();
        }
        CompoundTag locTag = tag.getCompound(TAG);
        if (!locTag.contains("dimension") || !locTag.contains("x")) {
            return Optional.empty();
        }
        ResourceLocation dimId = ResourceLocation.parse(locTag.getString("dimension"));
        ResourceKey<Level> dimension = ResourceKey.create(Registries.DIMENSION, dimId);
        BlockPos pos = new BlockPos(locTag.getInt("x"), locTag.getInt("y"), locTag.getInt("z"));
        return Optional.of(new LocationData(dimension, pos));
    }

    public static void writeToStack(ItemStack stack, LocationData location) {
        CompoundTag tag = new CompoundTag();
        tag.putString("dimension", location.dimension().location().toString());
        tag.putInt("x", location.blockPos().getX());
        tag.putInt("y", location.blockPos().getY());
        tag.putInt("z", location.blockPos().getZ());
        CompoundTag wrapper = new CompoundTag();
        wrapper.put(TAG, tag);
        stack.set(net.minecraft.core.component.DataComponents.CUSTOM_DATA, CustomData.of(wrapper));
    }

    public static void clearStack(ItemStack stack) {
        CustomData customData = stack.get(net.minecraft.core.component.DataComponents.CUSTOM_DATA);
        if (customData == null) {
            return;
        }
        CompoundTag tag = customData.copyTag();
        tag.remove(TAG);
        if (tag.isEmpty()) {
            stack.remove(net.minecraft.core.component.DataComponents.CUSTOM_DATA);
        } else {
            stack.set(net.minecraft.core.component.DataComponents.CUSTOM_DATA, CustomData.of(tag));
        }
    }

    public static boolean hasLocation(ItemStack stack) {
        return fromStack(stack).isPresent();
    }

    public String toDisplayString() {
        return dimension().location() + " @ " + blockPos().getX() + ", " + blockPos().getY() + ", " + blockPos().getZ();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private ResourceKey<Level> dimension;
        private BlockPos blockPos;

        public Builder dimension(ResourceKey<Level> dimension) {
            this.dimension = dimension;
            return this;
        }

        public Builder blockPos(BlockPos blockPos) {
            this.blockPos = blockPos;
            return this;
        }

        public LocationData build() {
            return new LocationData(dimension, blockPos);
        }
    }
}
