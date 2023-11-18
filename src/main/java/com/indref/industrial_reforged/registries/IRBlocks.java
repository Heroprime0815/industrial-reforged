package com.indref.industrial_reforged.registries;

import com.indref.industrial_reforged.IndustrialReforged;
import com.indref.industrial_reforged.registries.blocks.CableBlock;
import com.indref.industrial_reforged.api.tiers.EnergyTiers;
import com.indref.industrial_reforged.registries.blocks.*;
import com.indref.industrial_reforged.registries.blocks.machines.PrimitiveForgeBlock;
import com.indref.industrial_reforged.registries.blocks.machines.SimplePressBlock;
import com.indref.industrial_reforged.registries.blocks.misc.MiningPipeBlock;
import com.indref.industrial_reforged.registries.blocks.trees.RubberTreeLeavesBlock;
import com.indref.industrial_reforged.registries.blocks.trees.RubberTreeLogBlock;
import com.indref.industrial_reforged.registries.blocks.trees.RubberTreeResinHoleBlock;
import com.indref.industrial_reforged.test.EnergyTestBlock;
import com.indref.industrial_reforged.test.HeatTestBlock;
import com.indref.industrial_reforged.test.TestMultiblockController;
import com.indref.industrial_reforged.test.TestMultiblockPart;
import com.indref.industrial_reforged.test.energy.TestGeneratorBlock;
import com.indref.industrial_reforged.worldgen.RubberTreeGrower;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;

import java.util.function.Supplier;

public class IRBlocks {
    /**
     * Variable used for registering and storing all blocks under the "indref" mod-id
     */
	public static final BlockSetType RUBBER_SET_TYPE = BlockSetType.register(new BlockSetType(IndustrialReforged.MODID + ":rubber"));
	public static final WoodType RUBBER_WOOD_TYPE = WoodType.register(new WoodType(IndustrialReforged.MODID + ":rubber", RUBBER_SET_TYPE));
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, IndustrialReforged.MODID);

    public static final RegistryObject<Block> TIN_CABLE = registerBlockAndItem("tin_cable",
            () -> new CableBlock(BlockBehaviour.Properties.of(), EnergyTiers.LOW));
    public static final RegistryObject<Block> MINING_PIPE = registerBlock("mining_pipe",
            () -> new MiningPipeBlock(BlockBehaviour.Properties.of().noOcclusion()));
    public static final RegistryObject<Block> TEST_BLOCK_ENERGY = registerBlockAndItem("test_block_energy",
            () -> new EnergyTestBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> TEST_GENERATOR = registerBlockAndItem("test_generator",
            () -> new TestGeneratorBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> REFRACTORY_BRICK = registerBlockAndItem("refractory_brick",
            () -> new RefractoryBrickBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> COIL = registerBlockAndItem("coil",
            () -> new CoilBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> TEST_BLOCK_HEAT = registerBlockAndItem("test_block_heat",
            () -> new HeatTestBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> BASIC_MACHINE_FRAME = registerBlockAndItem("basic_machine_frame",
            () -> new MachineFrameBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> PRIMITIVE_FORGE = registerBlockAndItem("primitive_forge",
            () -> new PrimitiveForgeBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> SIMPLE_PRESS = registerBlockAndItem("simple_press",
            () -> new SimplePressBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> CORN_CROP = registerBlock("corn_crop",
            () -> new CornCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission()));
    public static final RegistryObject<Block> TEST_CONTROLLER = registerBlockAndItem("test_controller",
            () -> new TestMultiblockController(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> TEST_PART = registerBlockAndItem("test_part",
            () -> new TestMultiblockPart(BlockBehaviour.Properties.of()));

    // Rubber
    public static final RegistryObject<Block> RUBBER_TREE_LOG = registerBlockAndItem("rubber_tree_log",
            RubberTreeLogBlock::new);
    public static final RegistryObject<Block> STRIPPED_RUBBER_TREE_LOG = registerBlockAndItem("stripped_rubber_tree_log",
            RubberTreeLogBlock::new);
    public static final RegistryObject<Block> RUBBER_TREE_WOOD = registerBlockAndItem("rubber_tree_wood",
            RubberTreeLogBlock::new);
    public static final RegistryObject<Block> STRIPPED_RUBBER_TREE_WOOD = registerBlockAndItem("stripped_rubber_tree_wood",
            RubberTreeLogBlock::new);
    public static final RegistryObject<Block> RUBBER_TREE_LEAVES = registerBlockAndItem("rubber_tree_leaves",
            RubberTreeLeavesBlock::new);
    public static final RegistryObject<Block> RUBBER_TREE_SAPLING = registerBlockAndItem("rubber_tree_sapling",
            () -> new SaplingBlock(new RubberTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistryObject<Block> RUBBER_TREE_RESIN_HOLE = registerBlockAndItem("rubber_tree_resin_hole",
            RubberTreeResinHoleBlock::new);
    public static final RegistryObject<Block> RUBBER_TREE_PLANKS = registerBlockAndItem("rubber_tree_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    @SuppressWarnings("deprecation")
	public static final RegistryObject<Block> RUBBER_TREE_STAIRS = registerBlockAndItem("rubber_tree_stairs",
    		() -> new StairBlock(RUBBER_TREE_PLANKS.get().defaultBlockState(),BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistryObject<Block> RUBBER_TREE_DOOR = registerBlockAndItem("rubber_tree_door",
    		() -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR), RUBBER_SET_TYPE));
    public static final RegistryObject<Block> RUBBER_TREE_PRESSURE_PLATE = registerBlockAndItem("rubber_tree_pressure_plate",
    		() -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), RUBBER_SET_TYPE));
    public static final RegistryObject<Block> RUBBER_TREE_FENCE = registerBlockAndItem("rubber_tree_fence",
    		() -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR)));
    public static final RegistryObject<Block> RUBBER_TREE_TRAPDOOR = registerBlockAndItem("rubber_tree_trapdoor",
    		() -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR), RUBBER_SET_TYPE));
    public static final RegistryObject<Block> RUBBER_TREE_FENCE_GATE = registerBlockAndItem("rubber_tree_fence_gate",
    		() -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), RUBBER_WOOD_TYPE));
    public static final RegistryObject<Block> RUBBER_TREE_BUTTON = registerBlockAndItem("rubber_tree_button",
    		() -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON), RUBBER_SET_TYPE, 30, true));
    public static final RegistryObject<Block> RUBBER_TREE_SLAB = registerBlockAndItem("rubber_tree_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));

    // Ores
    public static final RegistryObject<Block> BAUXITE_ORE = registerBlockAndItem("bauxite_ore",
            () -> new DropExperienceBlock(oreSettings(false)));
    public static final RegistryObject<Block> DEEPSLATE_BAUXITE_ORE = registerBlockAndItem("deepslate_bauxite_ore",
            () -> new DropExperienceBlock(oreSettings(true)));
    public static final RegistryObject<Block> CHROMIUM_ORE = registerBlockAndItem("chromium_ore",
            () -> new DropExperienceBlock(oreSettings(false)));
    public static final RegistryObject<Block> DEEPSLATE_CHROMIUM_ORE = registerBlockAndItem("deepslate_chromium_ore",
            () -> new DropExperienceBlock(oreSettings(true)));
    public static final RegistryObject<Block> IRIDIUM_ORE = registerBlockAndItem("iridium_ore",
            () -> new DropExperienceBlock(oreSettings(false)));
    public static final RegistryObject<Block> DEEPSLATE_IRIDIUM_ORE = registerBlockAndItem("deepslate_iridium_ore",
            () -> new DropExperienceBlock(oreSettings(true)));
    public static final RegistryObject<Block> LEAD_ORE = registerBlockAndItem("lead_ore",
            () -> new DropExperienceBlock(oreSettings(false)));
    public static final RegistryObject<Block> DEEPSLATE_LEAD_ORE = registerBlockAndItem("deepslate_lead_ore",
            () -> new DropExperienceBlock(oreSettings(true)));
    public static final RegistryObject<Block> NICKEL_ORE = registerBlockAndItem("nickel_ore",
            () -> new DropExperienceBlock(oreSettings(false)));
    public static final RegistryObject<Block> DEEPSLATE_NICKEL_ORE = registerBlockAndItem("deepslate_nickel_ore",
            () -> new DropExperienceBlock(oreSettings(true)));
    public static final RegistryObject<Block> TIN_ORE = registerBlockAndItem("tin_ore",
            () -> new DropExperienceBlock(oreSettings(false)));
    public static final RegistryObject<Block> DEEPSLATE_TIN_ORE = registerBlockAndItem("deepslate_tin_ore",
            () -> new DropExperienceBlock(oreSettings(true)));
    public static final RegistryObject<Block> URANIUM_ORE = registerBlockAndItem("uranium_ore",
            () -> new DropExperienceBlock(oreSettings(false)));
    public static final RegistryObject<Block> DEEPSLATE_URANIUM_ORE = registerBlockAndItem("deepslate_uranium_ore",
            () -> new DropExperienceBlock(oreSettings(true)));
    public static final RegistryObject<LiquidBlock> SOAP_WATER_BLOCK = BLOCKS.register("soap_water_block",
            () -> new LiquidBlock(IRFluids.SOURCE_SOAP_WATER, BlockBehaviour.Properties.copy(Blocks.WATER)));

    /**
     * Registers a new block and item
     *
     * @param name  name of the block
     * @param block the Blocks you want to add and configure using `new {@link net.minecraft.world.item.Item.Properties}()`
     * @return returns the block-registry-object that can then be used to refer to the block
     */
    private static RegistryObject<Block> registerBlockAndItem(String name, Supplier<Block> block) {
        RegistryObject<Block> toReturn = BLOCKS.register(name, block);
        registerItemFromBlock(name, toReturn);
        return toReturn;
    }

    private static RegistryObject<Block> registerBlock(String name, Supplier<Block> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> void registerItemFromBlock(String name, RegistryObject<T> block) {
        IRItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static BlockBehaviour.Properties oreSettings(boolean deepslate) {
        if (deepslate) {
            return BlockBehaviour.Properties.of().mapColor(MapColor.DEEPSLATE).strength(4.5F, 3.0F)
                    .requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE).instrument(NoteBlockInstrument.BASEDRUM);
        }
        return BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(3.0F, 3.0F)
                .requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM);
    }
}