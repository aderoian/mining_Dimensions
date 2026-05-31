package com.kwpugh.mining_dims.event;

import com.kwpugh.mining_dims.config.MiningDimsConfig;
import com.kwpugh.mining_dims.init.EnchantmentInit;
import com.kwpugh.mining_dims.init.MiningDimsRegistry;
import com.kwpugh.mining_dims.items.BaseTeleporter;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

public class MiningDimsEvents {
    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        if (event.getRight().is(Items.WHITE_BED) && event.getLeft().getItem() instanceof BaseTeleporter) {
            event.getPlayer().level().registryAccess().registryOrThrow(Registries.ENCHANTMENT)
                    .getHolder(EnchantmentInit.RETURNING).ifPresent(holder -> {
                        ItemStack result = event.getLeft().copy();
                        result.enchant(holder, 1);
                        event.setOutput(result);
                        event.setCost(30);
                        event.setMaterialCost(0);
                    });
        }
    }

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (event.getEntity().level().isClientSide()) {
            return;
        }
        if (!(event.getSource().getEntity() instanceof Player)) {
            return;
        }
        if (!event.getEntity().level().dimension().equals(MiningDimsRegistry.MININGDIMS_WORLD_KEY2)) {
            return;
        }
        if (event.getEntity().level().getRandom().nextDouble() >= MiningDimsConfig.GENERAL.gearDropChance.get()) {
            return;
        }

        var victim = event.getEntity();
        for (EquipmentSlot slot : new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND,
                EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
            ItemStack equipped = victim.getItemBySlot(slot);
            if (!equipped.isEmpty()) {
                ItemEntity drop = new ItemEntity(victim.level(), victim.getX(), victim.getY(), victim.getZ(), equipped.copy());
                event.getDrops().add(drop);
            }
        }
    }
}
