package graysblock.graysmod.client;

import graysblock.graysmod.block.GraysModBlocks;
import graysblock.graysmod.client.gui.screen.GraysModScreenHandlerTypes;
import graysblock.graysmod.client.gui.screen.ingame.KilnScreen;
import graysblock.graysmod.client.gui.screen.ingame.PrismarineWorkbenchScreen;
import graysblock.graysmod.client.item.GraysModModelPredicates;
import graysblock.graysmod.client.render.entity.BoulderingZombieEntityRenderer;
import graysblock.graysmod.client.render.entity.CluckshroomEntityRenderer;
import graysblock.graysmod.client.render.entity.feature.MakeshiftWingsFeatureRenderer;
import graysblock.graysmod.client.render.entity.model.GraysModEntityModelLayers;
import graysblock.graysmod.entity.GraysModEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.ArmorStandEntityModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;

public class GraysModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        GraysModEntityModelLayers.registerModdedEntityModelLayers();
        GraysModModelPredicates.init();

        registerEntityRenderers();
        registerScreens();
        registerBlockRenderLayers();

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register(((EntityType<? extends LivingEntity> entityType, LivingEntityRenderer<?, ?> entityRenderer, LivingEntityFeatureRendererRegistrationCallback.RegistrationHelper registrationHelper, EntityRendererFactory.Context context) -> {
            if(entityRenderer.getModel() instanceof PlayerEntityModel || entityRenderer.getModel() instanceof BipedEntityModel || entityRenderer.getModel() instanceof ArmorStandEntityModel) {
                registrationHelper.register(new MakeshiftWingsFeatureRenderer<>(entityRenderer, context.getModelLoader()));
            }
        }));
    }
    private void registerEntityRenderers() {
        EntityRendererRegistry.register(GraysModEntityTypes.BALL_OF_REPULSION_GEL, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(GraysModEntityTypes.CLUCKSHROOM, CluckshroomEntityRenderer::new);
        EntityRendererRegistry.register(GraysModEntityTypes.BOULDERING_ZOMBIE, BoulderingZombieEntityRenderer::new);
    }

    private void registerScreens() {
        HandledScreens.register(GraysModScreenHandlerTypes.KILN_SCREEN_HANDLER, KilnScreen::new);
        HandledScreens.register(GraysModScreenHandlerTypes.PRISMARINE_WORKBENCH_SCREEN_HANDLER, PrismarineWorkbenchScreen::new);
    }

    private void registerBlockRenderLayers() {
        BlockRenderLayerMap.INSTANCE.putBlock(GraysModBlocks.REPULSION_GEL, RenderLayer.getCutout());
    }
}
