package com.indref.industrial_reforged.api.multiblocks;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IMultiblock {
    // Returns the block that is the controller
    Block getController();

    /**
     * Return the layout of the multi
     * <br>[<br>0, 0, 0,
     * <br>0, 1, 0
     * <br>0, 0, 0<br>]
     */
    List<List<Integer>> getLayout();

    /**
     * Use this method to assign the numbers from getLayout() to a block
     */
    Map<Integer, Block> getDefinition();

    /**
     * !!! IMPORTANT: override this if your
     * multi-block's layers are not quadratic
     * <br>
     * First Integer is the x direction ([0, 0, 0...)
     * Second Integer is the z direction
     * <br>[<br>0,...
     * <br>                                0,...
     * <br>                                0,...
     * <br>]
     */
    default List<Pair<Integer, Integer>> getWidths() {
        List<Pair<Integer, Integer>> widths = new ArrayList<>();
        for (List<Integer> list : getLayout()) {
            int width = (int) Math.sqrt(list.size());
            widths.add(Pair.of(width, width));
        }
        return widths;
    }
}
