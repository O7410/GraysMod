package graysblock.graysmod.data.client;

import graysblock.graysmod.block.GraysModBlocks;
import graysblock.graysmod.item.GraysModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.*;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class GraysModModelGenerator extends FabricModelProvider {

    public GraysModModelGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerCooker(GraysModBlocks.KILN, TexturedModel.ORIENTABLE_WITH_BOTTOM);
        blockStateModelGenerator.registerCubeWithCustomTextures(GraysModBlocks.PRISMARINE_WORKBENCH, Blocks.PRISMARINE, TextureMap::frontSideWithCustomBottom);
        blockStateModelGenerator.registerWallPlant(GraysModBlocks.REPULSION_GEL);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(GraysModItems.BALL_OF_REPULSION_GEL, Models.GENERATED);
        itemModelGenerator.register(GraysModItems.HOT_POCKET, Models.GENERATED);
        itemModelGenerator.register(GraysModItems.PIGLIN_WAR_AXE, Models.HANDHELD);
        itemModelGenerator.register(GraysModItems.PRISMARINE_AXE, Models.HANDHELD);
        itemModelGenerator.register(GraysModItems.PRISMARINE_HOE, Models.HANDHELD);
        itemModelGenerator.register(GraysModItems.PRISMARINE_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(GraysModItems.PRISMARINE_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(GraysModItems.PRISMARINE_SWORD, Models.HANDHELD);
        itemModelGenerator.register(GraysModItems.STAFF_OF_REGROWTH, Models.HANDHELD);
        itemModelGenerator.registerArmor((ArmorItem) GraysModItems.STRIDER_BOOTS);
        itemModelGenerator.register(GraysModItems.STRIDER_SCALE, Models.GENERATED);
        itemModelGenerator.registerArmor((ArmorItem) GraysModItems.TURTLE_SHELL);
        itemModelGenerator.registerArmor((ArmorItem) GraysModItems.TURTLE_SHOES);
        itemModelGenerator.registerArmor((ArmorItem) GraysModItems.TURTLE_TROUSERS);

        itemModelGenerator.register(GraysModItems.BOULDERING_ZOMBIE_SPAWN_EGG, new Model(Optional.of(Identifier.ofVanilla("item/template_spawn_egg")), Optional.empty()));
        itemModelGenerator.register(GraysModItems.CLUCKSHROOM_SPAWN_EGG, new Model(Optional.of(Identifier.ofVanilla("item/template_spawn_egg")), Optional.empty()));
    }
}
