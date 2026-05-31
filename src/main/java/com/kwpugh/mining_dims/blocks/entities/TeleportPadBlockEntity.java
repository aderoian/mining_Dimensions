package com.kwpugh.mining_dims.blocks.entities;

import com.kwpugh.mining_dims.components.LocationData;
import com.kwpugh.mining_dims.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class TeleportPadBlockEntity extends BlockEntity {
    @Nullable
    private LocationData location;

    public TeleportPadBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.TELEPORT_PAD_BE.get(), pos, state);
    }

    @Nullable
    public LocationData getLocation() {
        return location;
    }

    public void setLocation(@Nullable LocationData location) {
        this.location = location;
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (location != null) {
            CompoundTag locTag = new CompoundTag();
            locTag.putString("dimension", location.dimension().location().toString());
            locTag.putInt("x", location.blockPos().getX());
            locTag.putInt("y", location.blockPos().getY());
            locTag.putInt("z", location.blockPos().getZ());
            tag.put("location", locTag);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("location")) {
            CompoundTag locTag = tag.getCompound("location");
            location = LocationData.builder()
                    .dimension(net.minecraft.resources.ResourceKey.create(
                            net.minecraft.core.registries.Registries.DIMENSION,
                            net.minecraft.resources.ResourceLocation.parse(locTag.getString("dimension"))))
                    .blockPos(new BlockPos(locTag.getInt("x"), locTag.getInt("y"), locTag.getInt("z")))
                    .build();
        } else {
            location = null;
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }
}
