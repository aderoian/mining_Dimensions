package com.kwpugh.mining_dims.mixin;

import com.kwpugh.mining_dims.config.MiningDimsConfig;
import com.kwpugh.mining_dims.init.MiningDimsRegistry;
import com.kwpugh.mining_dims.util.MobBoostHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Zombie.class)
public abstract class ZombieEntityMixin extends Monster {
    private ZombieEntityMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "populateDefaultEquipmentSlots", at = @At("TAIL"))
    private void miningdimsInitEquipment(net.minecraft.util.RandomSource random, DifficultyInstance difficulty, CallbackInfo ci) {
        if (level().dimension().equals(MiningDimsRegistry.MININGDIMS_WORLD_KEY2) && MiningDimsConfig.GENERAL.enableZombieGear.get()) {
            setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_SWORD));
            setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.DIAMOND_HELMET));
            setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.DIAMOND_CHESTPLATE));
            setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.DIAMOND_LEGGINGS));
            setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.DIAMOND_BOOTS));
        }
    }

    @Inject(method = "hurt", at = @At("HEAD"))
    private void miningdimsReflectProjectile(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (!level().isClientSide() && level().dimension().equals(MiningDimsRegistry.MININGDIMS_WORLD_KEY2)) {
            if (source.getEntity() instanceof Player && source.is(DamageTypeTags.IS_PROJECTILE)) {
                source.getEntity().hurt(level().damageSources().generic(), 5.0F);
            }
        }
    }

    @Inject(method = "finalizeSpawn", at = @At("TAIL"))
    private void miningdimsFinalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, net.minecraft.world.entity.MobSpawnType reason,
                                         net.minecraft.world.entity.SpawnGroupData spawnData, CallbackInfoReturnable<net.minecraft.world.entity.SpawnGroupData> cir) {
        if (level().dimension().equals(MiningDimsRegistry.MININGDIMS_WORLD_KEY2)) {
            MobBoostHelper.applyZombieBoosts(this);
        }
    }
}
