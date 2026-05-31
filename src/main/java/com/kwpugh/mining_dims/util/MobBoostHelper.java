package com.kwpugh.mining_dims.util;

import com.kwpugh.mining_dims.MiningDims;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;

public final class MobBoostHelper {
    private MobBoostHelper() {
    }

    public static void addBonus(Mob mob, Holder<Attribute> attribute, ResourceLocation id, double amount) {
        AttributeInstance instance = mob.getAttribute(attribute);
        if (instance != null) {
            instance.addPermanentModifier(new AttributeModifier(id, amount, AttributeModifier.Operation.ADD_VALUE));
        }
    }

    public static void applyZombieBoosts(Mob mob) {
        addBonus(mob, Attributes.MAX_HEALTH, MiningDims.id("zombie_health"), com.kwpugh.mining_dims.config.MiningDimsConfig.GENERAL.zombieMaxHealth.get());
        addBonus(mob, Attributes.ATTACK_DAMAGE, MiningDims.id("zombie_attack"), com.kwpugh.mining_dims.config.MiningDimsConfig.GENERAL.zombieAttackDamageBonus.get());
        addBonus(mob, Attributes.ARMOR, MiningDims.id("zombie_armor"), com.kwpugh.mining_dims.config.MiningDimsConfig.GENERAL.zombieArmorBonus.get());
        addBonus(mob, Attributes.MOVEMENT_SPEED, MiningDims.id("zombie_speed"), com.kwpugh.mining_dims.config.MiningDimsConfig.GENERAL.zombieMovementBonus.get());
    }

    public static void applyPiglinBoosts(Mob mob) {
        addBonus(mob, Attributes.MAX_HEALTH, MiningDims.id("piglin_health"), com.kwpugh.mining_dims.config.MiningDimsConfig.GENERAL.piglinMaxHealth.get());
        addBonus(mob, Attributes.ATTACK_DAMAGE, MiningDims.id("piglin_attack"), com.kwpugh.mining_dims.config.MiningDimsConfig.GENERAL.piglinAttackDamageBonus.get());
        addBonus(mob, Attributes.ARMOR, MiningDims.id("piglin_armor"), com.kwpugh.mining_dims.config.MiningDimsConfig.GENERAL.piglinArmorBonus.get());
        addBonus(mob, Attributes.MOVEMENT_SPEED, MiningDims.id("piglin_speed"), com.kwpugh.mining_dims.config.MiningDimsConfig.GENERAL.piglinMovementBonus.get());
    }

    public static void applyPiglinBruteBoosts(Mob mob) {
        addBonus(mob, Attributes.MAX_HEALTH, MiningDims.id("brute_health"), com.kwpugh.mining_dims.config.MiningDimsConfig.GENERAL.piglinBruteMaxHealth.get());
        addBonus(mob, Attributes.ATTACK_DAMAGE, MiningDims.id("brute_attack"), com.kwpugh.mining_dims.config.MiningDimsConfig.GENERAL.piglinBruteAttackDamageBonus.get());
        addBonus(mob, Attributes.ARMOR, MiningDims.id("brute_armor"), com.kwpugh.mining_dims.config.MiningDimsConfig.GENERAL.piglinBruteArmorBonus.get());
        addBonus(mob, Attributes.MOVEMENT_SPEED, MiningDims.id("brute_speed"), com.kwpugh.mining_dims.config.MiningDimsConfig.GENERAL.piglinBruteMovementBonus.get());
    }

    public static void applyWitherSkeletonBoosts(Mob mob) {
        addBonus(mob, Attributes.MAX_HEALTH, MiningDims.id("wither_health"), com.kwpugh.mining_dims.config.MiningDimsConfig.GENERAL.witherSkeletonMaxHealth.get());
        addBonus(mob, Attributes.ATTACK_DAMAGE, MiningDims.id("wither_attack"), com.kwpugh.mining_dims.config.MiningDimsConfig.GENERAL.witherSkeletonDamageBonus.get());
        addBonus(mob, Attributes.ARMOR, MiningDims.id("wither_armor"), com.kwpugh.mining_dims.config.MiningDimsConfig.GENERAL.witherSkeletonArmorBonus.get());
        addBonus(mob, Attributes.MOVEMENT_SPEED, MiningDims.id("wither_speed"), com.kwpugh.mining_dims.config.MiningDimsConfig.GENERAL.witherSkeletonMovementBonus.get());
    }
}
