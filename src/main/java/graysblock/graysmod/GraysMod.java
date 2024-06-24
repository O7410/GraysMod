package graysblock.graysmod;

import graysblock.graysmod.block.GraysModBlocks;
import graysblock.graysmod.block.entity.GraysModBlockEntityTypes;
import graysblock.graysmod.screen.GraysModScreenHandlerTypes;
import graysblock.graysmod.entity.GraysModEntityTypes;
import graysblock.graysmod.effect.GraysModStatusEffects;
import graysblock.graysmod.entity.custom.BoulderingZombieEntity;
import graysblock.graysmod.entity.custom.CluckshroomEntity;
import graysblock.graysmod.item.GraysModItemGroups;
import graysblock.graysmod.item.GraysModItems;
import graysblock.graysmod.recipe.GraysModRecipeSerializers;
import graysblock.graysmod.recipe.GraysModRecipeTypes;
import graysblock.graysmod.sound.GraysModSoundEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GraysMod implements ModInitializer {
    public static final String MOD_ID = "graysmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {

        LOGGER.info("Drink your food and eat your water!");
        GraysModItems.registerModdedItems();
        GraysModBlocks.registerModdedBlocks();
        GraysModBlockEntityTypes.registerModdedBlockEntities();
        GraysModScreenHandlerTypes.registerModdedScreenHandlers();
        GraysModRecipeTypes.registerModdedRecipeTypes();
        GraysModRecipeSerializers.registerModdedRecipeSerializers();
        GraysModItemGroups.registerModdedItemGroups();
        GraysModSoundEvents.registerModdedSounds();
        GraysModStatusEffects.registerModdedStatusEffects();

        FabricDefaultAttributeRegistry.register(GraysModEntityTypes.CLUCKSHROOM, CluckshroomEntity.createCluckshroomAttributes());
        FabricDefaultAttributeRegistry.register(GraysModEntityTypes.BOULDERING_ZOMBIE, BoulderingZombieEntity.createZombieAttributes());

    }

	/*
	 * TODO: Fix "Shadow" status effect to let you pass through blocks in the "SHADOW_FORM_PASSES_THROUGH" tag.
	 */
}