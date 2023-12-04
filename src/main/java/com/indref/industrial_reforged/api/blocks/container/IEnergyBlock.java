package com.indref.industrial_reforged.api.blocks.container;

import com.indref.industrial_reforged.api.blocks.IScannable;
import com.indref.industrial_reforged.capabilities.IRCapabilities;
import com.indref.industrial_reforged.capabilities.energy.storage.IEnergyStorage;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

/**
 * Interface for implementing Blocks that store EU
 */
public interface IEnergyBlock extends IScannable {

    default void setEnergyStored(BlockEntity blockEntity, int value) {
        int prev = getEnergyStored(blockEntity);
        if (prev == value) return;

        IEnergyStorage energyStorage = blockEntity.getCapability(IRCapabilities.ENERGY).orElseThrow(NullPointerException::new);
        energyStorage.setEnergyStored(value);
        onEnergyChanged();
    }

    default int getEnergyStored(BlockEntity blockEntity) {
        IEnergyStorage energyStorage = blockEntity.getCapability(IRCapabilities.ENERGY).orElseThrow(NullPointerException::new);
        return energyStorage.getEnergyStored();
    }

    int getEnergyCapacity();

    default void onEnergyChanged() {
    }

    default boolean tryDrainEnergy(BlockEntity blockEntity, int value) {
        if (getEnergyStored(blockEntity) - value >= 0) {
            setEnergyStored(blockEntity, getEnergyStored(blockEntity) - value);
            return true;
        }
        return false;
    }

    default boolean tryFillEnergy(BlockEntity blockEntity, int value) {
        if (getEnergyStored(blockEntity) + value <= getEnergyCapacity()) {
            setEnergyStored(blockEntity, getEnergyStored(blockEntity) + value);
            return true;
        } else {
            setEnergyStored(blockEntity, getEnergyCapacity());
        }
        return false;
    }

    @Override
    default List<Component> displayText(BlockState scannedBlock, BlockPos scannedBlockPos, Level level) {
        IEnergyBlock energyBlock = null;
        BlockEntity blockEntity = level.getBlockEntity(scannedBlockPos);
        if (blockEntity instanceof IEnergyBlock energyBlock1)
            energyBlock = energyBlock1;

        return List.of(
                scannedBlock.getBlock().getName().withStyle(ChatFormatting.WHITE),
                MutableComponent.create(ComponentContents.EMPTY).withStyle(ChatFormatting.WHITE)
                        .append(Component.translatable("scanner_info.energy_block.energy_ratio"))
                        .append(Component.literal(String.format("%d/%d", energyBlock.getEnergyStored(blockEntity), energyBlock.getEnergyCapacity()))
                                .append(Component.literal(","))
                        ));
    }
}
