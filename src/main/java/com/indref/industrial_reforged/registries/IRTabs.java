package com.indref.industrial_reforged.registries;

import com.indref.industrial_reforged.IndustrialReforged;
import com.indref.industrial_reforged.api.items.container.IEnergyItem;
import com.indref.industrial_reforged.api.items.container.IFluidItem;
import com.indref.industrial_reforged.networking.IRPackets;
import com.indref.industrial_reforged.networking.packets.S2CFluidSync;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class IRTabs {
    /**
     * Variable used for registering and storing all item groups under the "indref" mod-id
     */
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, IndustrialReforged.MODID);

    /**
     * Default Item Group for all indref items
     */
    public static final Supplier<CreativeModeTab> ITEMS = CREATIVE_TABS.register("items", () -> CreativeModeTab.builder()
            .title(Component.translatable("creative_tab.indref.items"))
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .icon(() -> new ItemStack(IRItems.HAMMER.get()))
            .displayItems((parameters, output) -> {
                // Tools
                addItem(output, IRItems.WRENCH);
                addItem(output, IRItems.HAMMER);
                addItem(output, IRItems.TREE_TAP);
                addPoweredItem(output, IRItems.SCANNER);
                addPoweredItem(output, IRItems.ELECTRIC_WRENCH);
                addPoweredItem(output, IRItems.ELECTRIC_HOE);
                addPoweredItem(output, IRItems.ELECTRIC_TREE_TAP);
                addItem(output, IRItems.TAPE_MEASURE);

                // Storage items
                addItem(output, IRItems.TOOLBOX);
                addItem(output, IRItems.LUNCH_BAG);
                addItem(output, IRItems.SOAP_WATER_BUCKET);
                addVariantForAllFluids(output, IRItems.FLUID_CELL);

                // armor
                addItem(output, IRItems.HAZMAT_BOOTS);
                addItem(output, IRItems.HAZMAT_LEGGINGS);
                addItem(output, IRItems.HAZMAT_CHESTPLATE);
                addItem(output, IRItems.HAZMAT_HELMET);

                // reactor
                addItem(output, IRItems.URANIUM_FUEL_ROD);

                // misc and crafting items
                addItem(output, IRItems.FERTILIZER);
                addItem(output, IRItems.RUBBER_SHEET);
                addItem(output, IRItems.STICKY_RESIN);
                addItem(output, IRItems.RAW_BAUXITE);
                addItem(output, IRItems.RAW_CHROMIUM);
                addItem(output, IRItems.RAW_IRIDIUM);
                addItem(output, IRItems.RAW_LEAD);
                addItem(output, IRItems.RAW_NICKEL);
                addItem(output, IRItems.RAW_TIN);
                addItem(output, IRItems.RAW_URANIUM);

                addItem(output, IRItems.ALUMINUM_INGOT);
                addItem(output, IRItems.CHROMIUM_INGOT);
                addItem(output, IRItems.IRIDIUM_INGOT);
                addItem(output, IRItems.LEAD_INGOT);
                addItem(output, IRItems.NICKEL_INGOT);
                addItem(output, IRItems.TITANIUM_INGOT);
                addItem(output, IRItems.URANIUM_INGOT);
                addItem(output, IRItems.TIN_INGOT);
            }).build());
    public static final Supplier<CreativeModeTab> BLOCKS = CREATIVE_TABS.register("blocks", () -> CreativeModeTab.builder()
            .title(Component.translatable("creative_tab.indref.blocks"))
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .icon(() -> new ItemStack(IRBlocks.BASIC_MACHINE_FRAME.get()))
            .displayItems((parameters, output) -> {
                // misc blocks
                addBlock(output, IRBlocks.CENTRIFUGE);
                addBlock(output, IRBlocks.BASIC_MACHINE_FRAME);
                addBlock(output, IRBlocks.DRAIN);
                addBlock(output, IRBlocks.CRAFTING_STATION);
                addBlock(output, IRBlocks.TIN_CABLE);
                addItem(output, IRItems.MINING_PIPE);
                addBlock(output, IRBlocks.COIL);
                addBlock(output, IRBlocks.TERRACOTTA_SLAB);
                addBlock(output, IRBlocks.TERRACOTTA_BRICK);
                addBlock(output, IRBlocks.CERAMIC_FAUCET);
                addBlock(output, IRBlocks.REFRACTORY_BRICK);
                addBlock(output, IRBlocks.REFRACTORY_STONE);

                // Ores
                addBlock(output, IRBlocks.BAUXITE_ORE);
                addBlock(output, IRBlocks.DEEPSLATE_BAUXITE_ORE);
                addBlock(output, IRBlocks.CHROMIUM_ORE);
                addBlock(output, IRBlocks.DEEPSLATE_CHROMIUM_ORE);
                addBlock(output, IRBlocks.IRIDIUM_ORE);
                addBlock(output, IRBlocks.DEEPSLATE_IRIDIUM_ORE);
                addBlock(output, IRBlocks.LEAD_ORE);
                addBlock(output, IRBlocks.DEEPSLATE_LEAD_ORE);
                addBlock(output, IRBlocks.NICKEL_ORE);
                addBlock(output, IRBlocks.DEEPSLATE_NICKEL_ORE);
                addBlock(output, IRBlocks.TIN_ORE);
                addBlock(output, IRBlocks.DEEPSLATE_TIN_ORE);
                addBlock(output, IRBlocks.URANIUM_ORE);
                addBlock(output, IRBlocks.DEEPSLATE_URANIUM_ORE);

                addBlock(output, IRBlocks.RUBBER_TREE_BUTTON);
                addBlock(output, IRBlocks.RUBBER_TREE_DOOR);
                addBlock(output, IRBlocks.RUBBER_TREE_FENCE);
                addBlock(output, IRBlocks.RUBBER_TREE_FENCE_GATE);
                addBlock(output, IRBlocks.RUBBER_TREE_LEAVES);
                addBlock(output, IRBlocks.RUBBER_TREE_LOG);
                addBlock(output, IRBlocks.RUBBER_TREE_PLANKS);
                addBlock(output, IRBlocks.RUBBER_TREE_PRESSURE_PLATE);
                // addBlock(output, IRBlocks.RUBBER_TREE_SAPLING);
                addBlock(output, IRBlocks.RUBBER_TREE_SLAB);
                addBlock(output, IRBlocks.RUBBER_TREE_STAIRS);
                addBlock(output, IRBlocks.RUBBER_TREE_TRAPDOOR);
                addBlock(output, IRBlocks.RUBBER_TREE_WOOD);
                addBlock(output, IRBlocks.STRIPPED_RUBBER_TREE_LOG);
                addBlock(output, IRBlocks.STRIPPED_RUBBER_TREE_WOOD);
            }).build());

    private static void addItem(CreativeModeTab.Output output, Supplier<Item> item) {
        output.accept(item.get());
    }

    public static void addPoweredItem(CreativeModeTab.Output output, Supplier<Item> item) {
        output.accept(item.get());
        ItemStack stack = new ItemStack(item.get());
        IEnergyItem energyItem = (IEnergyItem) stack.getItem();
        energyItem.setEnergyStored(stack, energyItem.getEnergyCapacity());

        output.accept(stack);
    }

    public static void addVariantForAllFluids(CreativeModeTab.Output output, Supplier<Item> item) {
        // Add base item
        output.accept(item.get());
        Set<Map.Entry<ResourceKey<Fluid>, Fluid>> fluids = BuiltInRegistries.FLUID.entrySet();
        IndustrialReforged.LOGGER.info(fluids.toString());
        for (Map.Entry<ResourceKey<Fluid>, Fluid> fluid : fluids) {
            ItemStack stack = new ItemStack(item.get());
            if (!fluid.getValue().equals(Fluids.EMPTY) && fluid.getValue().isSource(fluid.getValue().defaultFluidState())) {
                if (item.get() instanceof IFluidItem fluidContainerItem)
                    fluidContainerItem.tryFillFluid(fluid.getValue(), 1000, stack);
                IRPackets.sendToClients(new S2CFluidSync(fluid.getValue(), 1000, stack));
                IndustrialReforged.LOGGER.info("Registering fluid cell: "+fluid.getValue());
                output.accept(stack);
            }
        }
    }

    /**
     * Add a new item to a creative tab
     * @param output Specify the creative tab
     * @param block Specify the item to add
     */
    private static void addBlock(CreativeModeTab.Output output, Supplier<Block> block) {
        output.accept(block.get());
    }
}
