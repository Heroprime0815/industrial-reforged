package com.indref.industrial_reforged.api.blocks.transfer;

import com.indref.industrial_reforged.api.tiers.templates.EnergyTier;
import com.indref.industrial_reforged.capabilities.energy.network.IEnergyNets;
import com.indref.industrial_reforged.capabilities.energy.network.EnergyNet;
import com.indref.industrial_reforged.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.MalformedInputException;

public class CableBlock extends PipeBlock {
    private final EnergyTier energyTier;
    public CableBlock(Properties properties, EnergyTier energyTier) {
        super(properties);
        this.energyTier = energyTier;
    }

    @Override
    public void onPlace(BlockState p_60566_, Level level, BlockPos blockPos, BlockState p_60569_, boolean p_60570_) {
        IEnergyNets nets = Util.getEnergyNets(level);
        nets.getOrCreateNetwork(blockPos);
        Minecraft.getInstance().player.sendSystemMessage(Component.literal("Energy Net!"));
        Minecraft.getInstance().player.sendSystemMessage(Component.literal("Wth is this block?: "+p_60569_.getBlock()));
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState p_60518_, boolean p_60519_) {
        IEnergyNets nets = Util.getEnergyNets(level);
        nets.removeNetwork(blockPos);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        CableBlockEntity cable = (CableBlockEntity) level.getBlockEntity(blockPos);
        assert cable != null;
        player.sendSystemMessage(Component.literal("Energy Net: "+cable.getEnergyNet(level)));
        player.sendSystemMessage(Component.literal("Energy Nets: "+Util.getEnergyNets(level)));
        return InteractionResult.SUCCESS;
    }

    public EnergyTier getEnergyTier() {
        return this.energyTier;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CableBlockEntity(blockPos, blockState);
    }
}
