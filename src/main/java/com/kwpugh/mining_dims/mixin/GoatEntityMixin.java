package com.kwpugh.mining_dims.mixin;

import com.kwpugh.mining_dims.init.MiningDimsRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Goat.class)
public abstract class GoatEntityMixin extends Animal {
    public GoatEntityMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "ageBoundaryReached", at = @At("TAIL"))
    private void miningDimsAgeBoundaryReached(CallbackInfo ci) {
        if (level().dimension().equals(MiningDimsRegistry.MININGDIMS_WORLD_KEY5)) {
            getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(6.0D);
        }
    }
}
