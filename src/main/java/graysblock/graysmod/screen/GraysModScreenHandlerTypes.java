package graysblock.graysmod.screen;

import graysblock.graysmod.GraysMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class GraysModScreenHandlerTypes {

    public static final ScreenHandlerType<KilnScreenHandler> KILN = Registry.register(Registries.SCREEN_HANDLER,
            Identifier.of(GraysMod.MOD_ID, "kiln"),
            new ScreenHandlerType<>(KilnScreenHandler::new, FeatureSet.empty()));

    public static final ScreenHandlerType<PrismarineWorkbenchScreenHandler> PRISMARINE_WORKBENCH = Registry.register(Registries.SCREEN_HANDLER,
            Identifier.of(GraysMod.MOD_ID, "prismarine_workbench"),
            new ScreenHandlerType<>(PrismarineWorkbenchScreenHandler::new, FeatureSet.empty()));

    public static void registerModdedScreenHandlers() {
        GraysMod.LOGGER.info("Registering GraysMod Screen Handlers...");
    }
}
