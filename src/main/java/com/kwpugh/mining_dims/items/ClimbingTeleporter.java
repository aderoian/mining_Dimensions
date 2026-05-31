package com.kwpugh.mining_dims.items;

import com.kwpugh.mining_dims.init.MiningDimsRegistry;
import com.kwpugh.mining_dims.util.TeleporterUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class ClimbingTeleporter extends BaseTeleporter {
    private static final ResourceKey<Level> DIM_KEY = MiningDimsRegistry.MININGDIMS_WORLD_KEY5;

    public ClimbingTeleporter(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        return TeleporterUtil.movePlayerOnPortableUse(DIM_KEY, world, player, hand);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.mining_dims.teleporter.desc").withStyle(ChatFormatting.GREEN));
    }
}
