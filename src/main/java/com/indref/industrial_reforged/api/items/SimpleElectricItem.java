package com.indref.industrial_reforged.api.items;

import com.indref.industrial_reforged.api.items.container.IEnergyContainerItem;
import com.indref.industrial_reforged.util.ItemUtils;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public abstract class SimpleElectricItem extends Item implements IEnergyContainerItem {
    public SimpleElectricItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getBarWidth(ItemStack itemStack) {
        return ItemUtils.energyForDurabilityBar(itemStack);
    }

    @Override
    public int getBarColor(ItemStack itemStack) {
        return ItemUtils.ENERGY_BAR_COLOR;
    }

    @Override
    public boolean isBarVisible(ItemStack p_150899_) {
        return true;
    }

    @Override
    public int getUseDuration(ItemStack p_41454_) {
        return 1;
    }
}