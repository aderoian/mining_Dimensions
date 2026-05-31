package com.kwpugh.mining_dims.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class MiningDimsConfig {
    public static final ModConfigSpec SPEC;
    public static final General GENERAL;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        GENERAL = new General(builder);
        SPEC = builder.build();
    }

    public static class General {
        public final ModConfigSpec.IntValue extraDiamondDeepslateVeinSize;
        public final ModConfigSpec.IntValue extraDiamondDeepslateVeinsPerChunk;
        public final ModConfigSpec.IntValue extraDiamondDeepslateMaxHeight;
        public final ModConfigSpec.IntValue extraAncientDebrisVeinSize;
        public final ModConfigSpec.IntValue extraAncientDebrisVeinsPerChunk;
        public final ModConfigSpec.IntValue extraAncientDebrisMaxHeight;
        public final ModConfigSpec.DoubleValue zombieMaxHealth;
        public final ModConfigSpec.DoubleValue zombieAttackDamageBonus;
        public final ModConfigSpec.DoubleValue zombieArmorBonus;
        public final ModConfigSpec.DoubleValue zombieMovementBonus;
        public final ModConfigSpec.DoubleValue piglinMaxHealth;
        public final ModConfigSpec.DoubleValue piglinAttackDamageBonus;
        public final ModConfigSpec.DoubleValue piglinArmorBonus;
        public final ModConfigSpec.DoubleValue piglinMovementBonus;
        public final ModConfigSpec.DoubleValue piglinBruteMaxHealth;
        public final ModConfigSpec.DoubleValue piglinBruteAttackDamageBonus;
        public final ModConfigSpec.DoubleValue piglinBruteArmorBonus;
        public final ModConfigSpec.DoubleValue piglinBruteMovementBonus;
        public final ModConfigSpec.DoubleValue witherSkeletonMaxHealth;
        public final ModConfigSpec.DoubleValue witherSkeletonDamageBonus;
        public final ModConfigSpec.DoubleValue witherSkeletonArmorBonus;
        public final ModConfigSpec.DoubleValue witherSkeletonMovementBonus;
        public final ModConfigSpec.BooleanValue enableZombieGear;
        public final ModConfigSpec.BooleanValue enablePiglinGear;
        public final ModConfigSpec.BooleanValue enablePiglinBruteGear;
        public final ModConfigSpec.BooleanValue enableWitherSkeletonGear;
        public final ModConfigSpec.BooleanValue enableVexGear;
        public final ModConfigSpec.DoubleValue gearDropChance;

        public final ModConfigSpec.BooleanValue enableTeleportPad;
        public final ModConfigSpec.BooleanValue enableMessageOnSneak;
        public final ModConfigSpec.BooleanValue enableMessageOnTeleport;
        public final ModConfigSpec.BooleanValue enableExtendedSearchRange;

        General(ModConfigSpec.Builder builder) {
            builder.comment("Extra Overworld Deepslate Ores (also affects Mining, Climbing, and Caving dims)");
            extraDiamondDeepslateVeinSize = builder.defineInRange("extraDiamondDeepslateVeinSize", 3, 1, 64);
            extraDiamondDeepslateVeinsPerChunk = builder.defineInRange("extraDiamondDeepslateVeinsPerChunk", 8, 0, 64);
            extraDiamondDeepslateMaxHeight = builder.defineInRange("extraDiamondDeepslateMaxHeight", -50, -64, 320);

            builder.comment("Extra Nether Ores (also affects Nethering dim)");
            extraAncientDebrisVeinSize = builder.defineInRange("extraAncientDebrisVeinSize", 3, 1, 64);
            extraAncientDebrisVeinsPerChunk = builder.defineInRange("extraAncientDebrisVeinsPerChunk", 8, 0, 64);
            extraAncientDebrisMaxHeight = builder.defineInRange("extraAncientDebrisMaxHeight", 40, 0, 128);

            builder.comment("Hunting Dim mob attributes");
            zombieMaxHealth = builder.defineInRange("zombieMaxHealth", 40.0D, 1.0D, 1000.0D);
            zombieAttackDamageBonus = builder.defineInRange("zombieAttackDamageBonus", 6.0D, 0.0D, 1000.0D);
            zombieArmorBonus = builder.defineInRange("zombieArmorBonus", 8.0D, 0.0D, 1000.0D);
            zombieMovementBonus = builder.defineInRange("zombieMovementBonus", 0.050D, 0.0D, 1.0D);
            piglinMaxHealth = builder.defineInRange("piglinMaxHealth", 40.0D, 1.0D, 1000.0D);
            piglinAttackDamageBonus = builder.defineInRange("piglinAttackDamageBonus", 20.0D, 0.0D, 1000.0D);
            piglinArmorBonus = builder.defineInRange("piglinArmorBonus", 20.0D, 0.0D, 1000.0D);
            piglinMovementBonus = builder.defineInRange("piglinMovementBonus", 0.050D, 0.0D, 1.0D);
            piglinBruteMaxHealth = builder.defineInRange("piglinBruteMaxHealth", 60.0D, 1.0D, 1000.0D);
            piglinBruteAttackDamageBonus = builder.defineInRange("piglinBruteAttackDamageBonus", 20.0D, 0.0D, 1000.0D);
            piglinBruteArmorBonus = builder.defineInRange("piglinBruteArmorBonus", 20.0D, 0.0D, 1000.0D);
            piglinBruteMovementBonus = builder.defineInRange("piglinBruteMovementBonus", 0.0750D, 0.0D, 1.0D);
            witherSkeletonMaxHealth = builder.defineInRange("witherSkeletonMaxHealth", 80.0D, 1.0D, 1000.0D);
            witherSkeletonDamageBonus = builder.defineInRange("witherSkeletonDamageBonus", 20.0D, 0.0D, 1000.0D);
            witherSkeletonArmorBonus = builder.defineInRange("witherSkeletonArmorBonus", 20.0D, 0.0D, 1000.0D);
            witherSkeletonMovementBonus = builder.defineInRange("witherSkeletonMovementBonus", 0.0850D, 0.0D, 1.0D);

            builder.comment("Hunting Dim mob gear");
            enableZombieGear = builder.define("enableZombieGear", true);
            enablePiglinGear = builder.define("enablePiglinGear", true);
            enablePiglinBruteGear = builder.define("enablePiglinBruteGear", true);
            enableWitherSkeletonGear = builder.define("enableWitherSkeletonGear", true);
            enableVexGear = builder.define("enableVexGear", true);
            gearDropChance = builder.defineInRange("gearDropChance", 0.10, 0.0, 1.0);

            builder.comment("Teleport Pad and Portal Block settings");
            enableTeleportPad = builder.define("enableTeleportPad", true);
            enableMessageOnSneak = builder.define("enableMessageOnSneak", true);
            enableMessageOnTeleport = builder.define("enableMessageOnTeleport", true);
            enableExtendedSearchRange = builder.comment(
                    "Extended portal block search range (25 chunks) vs standard (9 chunks)")
                    .define("enableExtendedSearchRange", false);
        }
    }
}
