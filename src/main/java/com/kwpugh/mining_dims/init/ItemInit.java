package com.kwpugh.mining_dims.init;

import com.kwpugh.mining_dims.MiningDims;
import com.kwpugh.mining_dims.items.*;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemInit {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MiningDims.MOD_ID);

    public static final DeferredItem<MiningTeleporter> MINING_TELEPORTER = ITEMS.register("mining_teleporter",
            () -> new MiningTeleporter(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<CavingTeleporter> CAVING_TELEPORTER = ITEMS.register("caving_teleporter",
            () -> new CavingTeleporter(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<ClimbingTeleporter> CLIMBING_TELEPORTER = ITEMS.register("climbing_teleporter",
            () -> new ClimbingTeleporter(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<NetheringTeleporter> NETHERING_TELEPORTER = ITEMS.register("nethering_teleporter",
            () -> new NetheringTeleporter(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<HuntingTeleporter> HUNTING_TELEPORTER = ITEMS.register("hunting_teleporter",
            () -> new HuntingTeleporter(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<SkyTeleporter> SKY_TELEPORTER = ITEMS.register("sky_teleporter",
            () -> new SkyTeleporter(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> DIAMOND_NUGGET = ITEMS.register("diamond_nugget",
            () -> new Item(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> NETHERITE_FRAGMENT = ITEMS.register("netherite_fragment",
            () -> new Item(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<SpatialCore> SPATIAL_CORE = ITEMS.register("spatial_core",
            () -> new SpatialCore(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<SpatialCaptureItem> SPATIAL_TOOL = ITEMS.register("spatial_tool",
            () -> new SpatialCaptureItem(new Item.Properties().stacksTo(1)));
}
