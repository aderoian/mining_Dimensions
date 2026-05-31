package com.kwpugh.mining_dims.mixin;

import com.kwpugh.mining_dims.config.MiningDimsConfig;
import com.kwpugh.mining_dims.init.MiningDimsRegistry;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSkeleton.class)
public abstract class AbstractSkeletonEntityMixin extends Monster {
    private AbstractSkeletonEntityMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "populateDefaultEquipmentSlots", at = @At("TAIL"))
    private void miningDimsInitEquipment(net.minecraft.util.RandomSource random, DifficultyInstance difficulty, CallbackInfo ci) {
        if (level().dimension().equals(MiningDimsRegistry.MININGDIMS_WORLD_KEY2) && MiningDimsConfig.GENERAL.enableZombieGear.get()) {
            setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
            setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
            setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
            setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.IRON_LEGGINGS));
            setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.IRON_BOOTS));
        }
    }
}
