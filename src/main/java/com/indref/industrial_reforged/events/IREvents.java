package com.indref.industrial_reforged.events;

import com.indref.industrial_reforged.IndustrialReforged;
import com.indref.industrial_reforged.api.capabilities.IRCapabilities;
import com.indref.industrial_reforged.api.capabilities.energy.storage.EnergyWrapper;
import com.indref.industrial_reforged.api.capabilities.heat.storage.HeatWrapper;
import com.indref.industrial_reforged.api.items.MultiBarItem;
import com.indref.industrial_reforged.api.items.SimpleFluidItem;
import com.indref.industrial_reforged.api.items.container.IEnergyItem;
import com.indref.industrial_reforged.api.items.container.IFluidItem;
import com.indref.industrial_reforged.api.items.container.IHeatItem;
import com.indref.industrial_reforged.client.hud.ScannerInfoOverlay;
import com.indref.industrial_reforged.client.renderer.blocks.CastingBasinRenderer;
import com.indref.industrial_reforged.client.renderer.items.CrucibleProgressRenderer;
import com.indref.industrial_reforged.client.renderer.items.MultiBarRenderer;
import com.indref.industrial_reforged.networking.*;
import com.indref.industrial_reforged.networking.data.*;
import com.indref.industrial_reforged.registries.IRBlockEntityTypes;
import com.indref.industrial_reforged.registries.IRItems;
import com.indref.industrial_reforged.registries.IRMenuTypes;
import com.indref.industrial_reforged.registries.items.misc.BlueprintItem;
import com.indref.industrial_reforged.registries.items.storage.BatteryItem;
import com.indref.industrial_reforged.registries.items.tools.NanoSaberItem;
import com.indref.industrial_reforged.registries.items.tools.TapeMeasureItem;
import com.indref.industrial_reforged.registries.items.tools.ThermometerItem;
import com.indref.industrial_reforged.registries.screen.*;
import com.indref.industrial_reforged.util.ItemUtils;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;
import org.lwjgl.glfw.GLFW;

public class IREvents {
    @Mod.EventBusSubscriber(modid = IndustrialReforged.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientBus {
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll(new ResourceLocation(IndustrialReforged.MODID, "scanner_info_overlay"), ScannerInfoOverlay.HUD_SCANNER_INFO);
        }

        @SubscribeEvent
        public static void onClientSetup(RegisterMenuScreensEvent event) {
            event.register(IRMenuTypes.FIREBOX_MENU.get(), FireBoxScreen::new);
            event.register(IRMenuTypes.CRUCIBLE_MENU.get(), CrucibleScreen::new);
            event.register(IRMenuTypes.CENTRIFUGE_MENU.get(), CentrifugeScreen::new);
            event.register(IRMenuTypes.BLAST_FURNACE_MENU.get(), BlastFurnaceScreen::new);
            event.register(IRMenuTypes.CRAFTING_STATION_MENU.get(), CraftingStationScreen::new);
        }

        @SubscribeEvent
        public static void itemDecorationRender(RegisterItemDecorationsEvent event) {
            for (Item item : BuiltInRegistries.ITEM) {
                if (item instanceof MultiBarItem)
                    event.register(item, new MultiBarRenderer(item));
                event.register(item, new CrucibleProgressRenderer());
            }
        }

        @SubscribeEvent
        public static void registerItemColor(RegisterColorHandlersEvent.Item event) {
            event.register(new SimpleFluidItem.Colors(), IRItems.FLUID_CELL.get());
        }

        @SubscribeEvent
        public static void onFMLClientSetupEvent(final FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                ItemProperties.register(IRItems.TAPE_MEASURE.get(), new ResourceLocation(IndustrialReforged.MODID, TapeMeasureItem.EXTENDED_KEY),
                        (stack, level, living, id) -> TapeMeasureItem.isExtended(stack));
                ItemProperties.register(IRItems.NANO_SABER.get(), new ResourceLocation(IndustrialReforged.MODID, ItemUtils.ACTIVE_KEY),
                        (stack, level, living, id) -> NanoSaberItem.isActive(stack));
                ItemProperties.register(IRItems.BLUEPRINT.get(), new ResourceLocation(IndustrialReforged.MODID, BlueprintItem.HAS_RECIPE_KEY),
                        (stack, level, living, id) -> BlueprintItem.hasRecipe(stack));
                ItemProperties.register(IRItems.THERMOMETER.get(), new ResourceLocation(IndustrialReforged.MODID, ThermometerItem.DISPLAY_TEMPERATURE_KEY),
                        (stack, level, living, id) -> ThermometerItem.getTemperature(stack));
                for (Item item : BuiltInRegistries.ITEM) {
                    if (item instanceof BatteryItem) {
                        ItemProperties.register(item, new ResourceLocation(IndustrialReforged.MODID, BatteryItem.ENERGY_STAGE_KEY),
                                (stack, level, living, id) -> BatteryItem.getEnergyStage(stack));
                    }
                }
            });
        }

        public static final Lazy<KeyMapping> JETPACK_TOGGLE = Lazy.of(() -> new KeyMapping(
                "key.indref.toggle_jetpack",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_J,
                "key.categories.indref"
        ));
        public static final Lazy<KeyMapping> JETPACK_ASCEND = Lazy.of(() -> new KeyMapping(
                "key.indref.ascend_jetpack",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_SPACE,
                "key.categories.indref"
        ));

        @SubscribeEvent
        public static void registerBindings(RegisterKeyMappingsEvent event) {
            event.register(JETPACK_TOGGLE.get());
            event.register(JETPACK_ASCEND.get());
        }

        @SubscribeEvent
        public static void registerBERenderer(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(IRBlockEntityTypes.CASTING_BASIN.get(),
                    CastingBasinRenderer::new);
        }
    }

    @Mod.EventBusSubscriber(modid = IndustrialReforged.MODID, value = Dist.CLIENT)
    public static class Client {
        @SubscribeEvent
        public static void appendTooltips(ItemTooltipEvent event) {
            CompoundTag tag = event.getItemStack().getOrCreateTag();
            if (tag.getBoolean(CrucibleProgressRenderer.IS_MELTING_KEY))
                event.getToolTip().add(Component.translatable("*.desc.melting_progress")
                        .append(": ")
                        .append(String.valueOf(tag.getInt("barwidth")))
                        .append("/10")
                        .withStyle(ChatFormatting.GRAY));
        }
    }

    @Mod.EventBusSubscriber(modid = IndustrialReforged.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ServerBus {
        @SubscribeEvent
        public static void registerCapabilities(RegisterCapabilitiesEvent event) {
            for (Item item : BuiltInRegistries.ITEM) {
                if (item instanceof IEnergyItem)
                    event.registerItem(IRCapabilities.EnergyStorage.ITEM, (stack, ctx) -> new EnergyWrapper.Item(stack), item);

                if (item instanceof IHeatItem)
                    event.registerItem(IRCapabilities.HeatStorage.ITEM, (stack, ctx) -> new HeatWrapper.Item(stack), item);

                if (item instanceof IFluidItem fluidItem)
                    event.registerItem(Capabilities.FluidHandler.ITEM, (stack, ctx) -> new FluidHandlerItemStack(stack, fluidItem.getFluidCapacity()), item);
            }

            // Register all your block entity capabilities manually
            event.registerBlockEntity(IRCapabilities.EnergyStorage.BLOCK, IRBlockEntityTypes.TEST_GEN.get(), (blockEntity, ctx) -> new EnergyWrapper.Block(blockEntity));
            event.registerBlockEntity(IRCapabilities.EnergyStorage.BLOCK, IRBlockEntityTypes.BASIC_GENERATOR.get(), (blockEntity, ctx) -> new EnergyWrapper.Block(blockEntity));
            event.registerBlockEntity(IRCapabilities.EnergyStorage.BLOCK, IRBlockEntityTypes.TEST_BLOCK.get(), (blockEntity, ctx) -> new EnergyWrapper.Block(blockEntity));
            event.registerBlockEntity(IRCapabilities.EnergyStorage.BLOCK, IRBlockEntityTypes.CENTRIFUGE.get(), (blockEntity, ctx) -> new EnergyWrapper.Block(blockEntity));

            event.registerBlockEntity(IRCapabilities.HeatStorage.BLOCK, IRBlockEntityTypes.FIREBOX.get(), (blockEntity, ctx) -> new HeatWrapper.Block(blockEntity));

            event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, IRBlockEntityTypes.FIREBOX.get(), (blockEntity, ctx) -> blockEntity.getItemHandler());
            event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, IRBlockEntityTypes.CRUCIBLE.get(), (blockEntity, ctx) -> blockEntity.getItemHandler());
            event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, IRBlockEntityTypes.CASTING_BASIN.get(), (blockEntity, ctx) -> blockEntity.getItemHandler());
            event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, IRBlockEntityTypes.CRAFTING_STATION.get(), (blockEntity, ctx) -> blockEntity.getItemHandler());

            event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, IRBlockEntityTypes.CRUCIBLE.get(), (blockEntity, ctx) -> blockEntity.getFluidTank());
            event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, IRBlockEntityTypes.DRAIN.get(), (blockEntity, ctx) -> blockEntity.getFluidTank());
            event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, IRBlockEntityTypes.FAUCET.get(), (blockEntity, ctx) -> blockEntity.getFluidTank());
            event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, IRBlockEntityTypes.CASTING_BASIN.get(), (blockEntity, ctx) -> blockEntity.getFluidTank());
        }

        @SubscribeEvent
        public static void registerPayloads(final RegisterPayloadHandlerEvent event) {
            final IPayloadRegistrar registrar = event.registrar(IndustrialReforged.MODID);
            registrar.play(ArmorActivitySyncData.ID, ArmorActivitySyncData::new, handler -> handler
                    .server(ArmorActivityPayload.getInstance()::handleData));
            registrar.play(ItemNbtSyncData.ID, ItemNbtSyncData::new, handler -> handler
                    .server(NbtPayload.getInstance()::handleData));
            registrar.play(ItemActivitySyncData.ID, ItemActivitySyncData::new, handler -> handler
                    .server(ItemActivityPayload.getInstance()::handleData));
            registrar.play(CastingDurationSyncData.ID, CastingDurationSyncData::new, handler -> handler
                    .client(CastingDurationPayload.getInstance()::handleData));
            registrar.play(CastingGhostItemSyncData.ID, CastingGhostItemSyncData::new, handler -> handler
                    .client(CastingGhostItemPayload.getInstance()::handleData));
        }
    }
}
