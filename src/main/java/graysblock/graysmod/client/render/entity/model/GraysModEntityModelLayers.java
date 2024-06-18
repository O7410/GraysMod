package graysblock.graysmod.client.render.entity.model;

import graysblock.graysmod.GraysMod;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class GraysModEntityModelLayers {
    public static final Map<EntityModelLayer, Supplier<TexturedModelData>> GRAYSMOD_MODEL_LAYERS = new HashMap<>();

    public static final EntityModelLayer CLUCKSHROOM = registerEntityModelLayer("cluckshroom", CluckshroomEntityModel.getTexturedModelData());
    public static final EntityModelLayer BOULDERING_ZOMBIE = registerEntityModelLayer("bouldering_zombie", BoulderingZombieEntityModel.getTexturedModelData());
    public static final EntityModelLayer MAKESHIFT_WINGS = registerEntityModelLayer("makeshift_wings", MakeshiftWingsEntityModel.getTexturedModelData());

    public static EntityModelLayer registerEntityModelLayer(String name, TexturedModelData modelData) {
        EntityModelLayer entityModelLayer = new EntityModelLayer(Identifier.of(GraysMod.MOD_ID, name), "main");
        GRAYSMOD_MODEL_LAYERS.put(entityModelLayer, () -> modelData);
        EntityModelLayerRegistry.registerModelLayer(entityModelLayer, () -> modelData);
        return entityModelLayer;
    }

    public static void registerModdedEntityModelLayers() {
        GraysMod.LOGGER.info("Registering GraysMod Entity Models...");
    }
}
