package com.indref.industrial_reforged.client.renderer;

import com.indref.industrial_reforged.api.items.IMultiBarItem;
import com.indref.industrial_reforged.util.ItemUtils;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.IItemDecorator;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CrucibleProgressRenderer implements IItemDecorator {
    @Override
    public boolean render(GuiGraphics guiGraphics, Font font, ItemStack itemStack, int xOffset, int yOffset) {
        itemStack.getOrCreateTag().putBoolean("cruciblemelting", true);
        int barWidth = itemStack.getOrCreateTag().getInt("barwidth") + 5;
        int width = 1;
        int i = ItemUtils.HEAT_BAR_COLOR;
        int j = xOffset + 2;
        int k = yOffset + 13;
        guiGraphics.fill(RenderType.guiOverlay(), j, k + 2, j + 2, k - 10, -16777216);
        guiGraphics.fill(RenderType.guiOverlay(), j, k + 1, j + width, k - barWidth, i | 0xFF000000);
        return false;
    }
}
