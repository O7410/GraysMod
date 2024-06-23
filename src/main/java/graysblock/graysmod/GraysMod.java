package graysblock.graysmod;

import graysblock.graysmod.block.GraysModBlocks;
import graysblock.graysmod.block.entity.GraysModBlockEntityTypes;
import graysblock.graysmod.client.gui.screen.GraysModScreenHandlerTypes;
import graysblock.graysmod.entity.GraysModEntityTypes;
import graysblock.graysmod.entity.effect.GraysModStatusEffects;
import graysblock.graysmod.entity.mob.BoulderingZombieEntity;
import graysblock.graysmod.entity.passive.CluckshroomEntity;
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

}