package graysblock.graysmod.data;

import graysblock.graysmod.block.GraysModBlocks;
import graysblock.graysmod.entity.GraysModEntityTypes;
import graysblock.graysmod.item.GraysModItemGroups;
import graysblock.graysmod.item.GraysModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class GraysModLangGenerator extends FabricLanguageProvider {

    public GraysModLangGenerator(FabricDataOutput dataGenerator, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataGenerator, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(GraysModItems.BALL_OF_REPULSION_GEL, "Ball of Repulsion Gel");
        translationBuilder.add(GraysModItems.BOULDERING_ZOMBIE_SPAWN_EGG, "Bouldering Zombie Spawn Egg");
        translationBuilder.add(GraysModItems.ECHO_VIAL, "Echo Vial");
        translationBuilder.add(GraysModItems.CLUCKSHROOM_SPAWN_EGG, "Cluckshroom Spawn Egg");
        translationBuilder.add(GraysModItems.HOT_POCKET, "Hot Pocket");
        translationBuilder.add(GraysModItems.MAKESHIFT_WINGS, "Makeshift Wings");
        translationBuilder.add(GraysModItems.PIGLIN_WAR_AXE, "Piglin War Axe");
        translationBuilder.add(GraysModItems.PRISMARINE_AXE, "Prismarine Axe");
        translationBuilder.add(GraysModItems.PRISMARINE_HOE, "Prismarine Hoe");
        translationBuilder.add(GraysModItems.PRISMARINE_PICKAXE, "Prismarine Pickaxe");
        translationBuilder.add(GraysModItems.PRISMARINE_SHOVEL, "Prismarine Shovel");
        translationBuilder.add(GraysModItems.PRISMARINE_SWORD, "Prismarine Sword");
        translationBuilder.add(GraysModItems.STAFF_OF_REGROWTH, "Staff of Regrowth");
        translationBuilder.add(GraysModItems.STRIDER_BOOTS, "Strider Boots");
        translationBuilder.add(GraysModItems.STRIDER_SCALE, "Strider Scale");
        translationBuilder.add(GraysModItems.TURTLE_SHELL, "Turtle Shell");
        translationBuilder.add(GraysModItems.TURTLE_SHOES, "Turtle Shoes");
        translationBuilder.add(GraysModItems.TURTLE_TROUSERS, "Turtle Trousers");
        translationBuilder.add(GraysModItems.VIAL_OF_RECOLLECTION, "Vial of Recollection");
        translationBuilder.add(GraysModItems.WIND_BOLT, "Wind Bolt");

        translationBuilder.add(GraysModBlocks.KILN, "Kiln");
        translationBuilder.add(GraysModBlocks.PRISMARINE_WORKBENCH, "Prismarine Workbench");
        translationBuilder.add(GraysModBlocks.REPULSION_GEL, "Repulsion Gel");

        translationBuilder.add(GraysModItemGroups.GRAYSMOD, "GraysMod");

        translationBuilder.add("effect.graysmod.magma_speed", "Magma Speed");

        translationBuilder.add(GraysModEntityTypes.BALL_OF_REPULSION_GEL, "Ball of Repulsion Gel");
        translationBuilder.add(GraysModEntityTypes.BOULDERING_ZOMBIE, "Bouldering Zombie");
        translationBuilder.add(GraysModEntityTypes.CLUCKSHROOM, "Cluckshroom");
        translationBuilder.add(GraysModEntityTypes.WIND_BOLT, "Wind Bolt");

        translationBuilder.add("container.kiln", "Kiln");
        translationBuilder.add("gui.recipebook.toggleRecipes.fireable", "Showing Fireable");

        translationBuilder.add("advancements.graysmod.root.title", "GraysMod");
        translationBuilder.add("advancements.graysmod.root.description", "Reimagining your gameplay one feature at a time");
        translationBuilder.add("advancements.graysmod.pure_absurdity.title", "Pure Absurdity");
        translationBuilder.add("advancements.graysmod.pure_absurdity.description", "Use a Grindstone to disenchant an Enchanted Golden Apple, like a moron");
        translationBuilder.add("advancements.graysmod.icarus.title", "Icarus");
        translationBuilder.add("advancements.graysmod.icarus.description", "Take to the skies with Makeshift Wings");
        translationBuilder.add("advancements.graysmod.so_scute.title", "So Scute!");
        translationBuilder.add("advancements.graysmod.so_scute.description", "Assemble a full set of turtle armor");
        translationBuilder.add("advancements.graysmod.hot_pockets.title", "Hot Pockets!");
        translationBuilder.add("advancements.graysmod.hot_pockets.description", "Consume a Hot Pocket. Be careful not to burn your mouth");
        translationBuilder.add("advancements.graysmod.irresistibly_hot.title", "Irresistibly Hot");
        translationBuilder.add("advancements.graysmod.irresistibly_hot.description", "Burn your mouth on a Hot Pocket");

        translationBuilder.add("subtitles.entity.ball_of_gel.throw", "Ball of Gel flies");
        translationBuilder.add("subtitles.entity.bouldering_zombie.ambient_climb", "Bouldering Zombie climbs");
        translationBuilder.add("subtitles.entity.wind_bolt.wind_burst", "Wind Bolt bursts");
        translationBuilder.add("subtitles.item.vial.dispose", "Vial breaks");
    }
}
