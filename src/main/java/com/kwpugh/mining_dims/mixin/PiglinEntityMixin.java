package com.kwpugh.mining_dims.mixin;

import com.kwpugh.mining_dims.config.MiningDimsConfig;
import com.kwpugh.mining_dims.init.MiningDimsRegistry;
import com.kwpugh.mining_dims.util.MobBoostHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Piglin.class)
public abstract class PiglinEntityMixin extends AbstractPiglin {
    public PiglinEntityMixin(EntityType<? extends AbstractPiglin> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "populateDefaultEquipmentSlots", at = @At("TAIL"))
    private void miningDimsInitEquipment(net.minecraft.util.RandomSource random, DifficultyInstance difficulty, CallbackInfo ci) {
        if (level().dimension().equals(MiningDimsRegistry.MININGDIMS_WORLD_KEY2) && MiningDimsConfig.GENERAL.enablePiglinGear.get()) {
            setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
            setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.GOLDEN_HELMET));
            setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.GOLDEN_CHESTPLATE));
            setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.GOLDEN_LEGGINGS));
            setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.GOLDEN_BOOTS));
        }
    }

    @Inject(method = "finalizeSpawn", at = @At("TAIL"))
    private void miningdimsFinalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, net.minecraft.world.entity.MobSpawnType reason,
                                         net.minecraft.world.entity.SpawnGroupData spawnData, CallbackInfoReturnable<net.minecraft.world.entity.SpawnGroupData> cir) {
        if (level().dimension().equals(MiningDimsRegistry.MININGDIMS_WORLD_KEY2)) {
            MobBoostHelper.applyPiglinBoosts(this);
        }
    }
}
