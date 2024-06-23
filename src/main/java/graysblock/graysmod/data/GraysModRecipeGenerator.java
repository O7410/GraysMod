package graysblock.graysmod.data;

import graysblock.graysmod.block.GraysModBlocks;
import graysblock.graysmod.data.server.recipe.FiringRecipeJsonBuilder;
import graysblock.graysmod.data.server.recipe.PrismarineRecipeJsonBuilder;
import graysblock.graysmod.data.server.recipe.ShapedPrismarineRecipeJsonBuilder;
import graysblock.graysmod.item.GraysModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class GraysModRecipeGenerator extends FabricRecipeProvider {

    public GraysModRecipeGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, GraysModItems.TURTLE_SHELL)
                .pattern("s s")
                .pattern("sss")
                .pattern("sss")
                .input('s', Items.TURTLE_SCUTE)
                .criterion(FabricRecipeProvider.hasItem(Items.TURTLE_SCUTE),
                        FabricRecipeProvider.conditionsFromItem(Items.TURTLE_SCUTE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, GraysModItems.TURTLE_TROUSERS)
                .pattern("sss")
                .pattern("s s")
                .pattern("s s")
                .input('s', Items.TURTLE_SCUTE)
                .criterion(FabricRecipeProvider.hasItem(Items.TURTLE_SCUTE),
                        FabricRecipeProvider.conditionsFromItem(Items.TURTLE_SCUTE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, GraysModItems.TURTLE_SHOES)
                .pattern("s s")
                .pattern("s s")
                .input('s', Items.TURTLE_SCUTE)
                .criterion(FabricRecipeProvider.hasItem(Items.TURTLE_SCUTE),
                        FabricRecipeProvider.conditionsFromItem(Items.TURTLE_SCUTE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, GraysModItems.STRIDER_BOOTS)
                .pattern("s s")
                .pattern("s s")
                .input('s', GraysModItems.STRIDER_SCALE)
                .criterion(FabricRecipeProvider.hasItem(GraysModItems.STRIDER_SCALE),
                        FabricRecipeProvider.conditionsFromItem(GraysModItems.STRIDER_SCALE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, GraysModBlocks.KILN)
                .pattern("sss")
                .pattern("bfb")
                .pattern("bbb")
                .input('s', Items.SMOOTH_BASALT)
                .input('b', Items.BRICKS)
                .input('f', Items.FURNACE)
                .criterion(FabricRecipeProvider.hasItem(Items.BRICKS),
                        FabricRecipeProvider.conditionsFromItem(Items.BRICKS))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, GraysModItems.MAKESHIFT_WINGS)
                .pattern("hlh")
                .pattern("fhf")
                .pattern("f f")
                .input('h', Items.HONEYCOMB)
                .input('l', Items.LEATHER)
                .input('f', Items.FEATHER)
                .criterion(FabricRecipeProvider.hasItem(Items.HONEYCOMB),
                        FabricRecipeProvider.conditionsFromItem(Items.HONEYCOMB))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, GraysModItems.WIND_BOLT, 4)
                .pattern("c")
                .pattern("b")
                .pattern("f")
                .input('c', Items.COPPER_INGOT)
                .input('b', Items.BREEZE_ROD)
                .input('f', Items.FEATHER)
                .criterion(FabricRecipeProvider.hasItem(Items.BREEZE_ROD),
                        FabricRecipeProvider.conditionsFromItem(Items.BREEZE_ROD))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, GraysModBlocks.PRISMARINE_WORKBENCH)
                .pattern("ppp")
                .pattern("scs")
                .pattern("sss")
                .input('p', GraysModItems.PEARL)
                .input('s', Items.PRISMARINE_SHARD)
                .input('c', Items.CRAFTING_TABLE)
                .criterion(FabricRecipeProvider.hasItem(Items.PRISMARINE_SHARD),
                        FabricRecipeProvider.conditionsFromItem(Items.PRISMARINE_SHARD))
                .offerTo(exporter);

        createPrismarineRecipes(exporter);
        createKilnRecipes(exporter);
    }

    private void createKilnRecipe(RecipeExporter exporter, Ingredient input, RecipeCategory category, Item output, float experience, int cookingTime) {
        String outputName = Registries.ITEM.getId(output).getPath();

        FiringRecipeJsonBuilder.createFiring(input, category, output, experience, cookingTime)
                .criterion(FabricRecipeProvider.hasItem(output), conditionsFromItem(output))
                .offerTo(exporter, Identifier.of(outputName + "_from_firing"));
    }

    private void createKilnRecipes(RecipeExporter exporter) {
        createKilnRecipe(exporter, Ingredient.fromTag(ItemTags.SMELTS_TO_GLASS), RecipeCategory.BUILDING_BLOCKS, Blocks.GLASS.asItem(), 0.1F, 100);
        createKilnRecipe(exporter, Ingredient.ofItems(Blocks.WHITE_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.WHITE_GLAZED_TERRACOTTA.asItem(), 0.1F, 100);
        createKilnRecipe(exporter, Ingredient.ofItems(Blocks.LIGHT_GRAY_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA.asItem(), 0.1F, 100);
        createKilnRecipe(exporter, Ingredient.ofItems(Blocks.GRAY_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.GRAY_GLAZED_TERRACOTTA.asItem(), 0.1F, 100);
        createKilnRecipe(exporter, Ingredient.ofItems(Blocks.BLACK_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.BLACK_GLAZED_TERRACOTTA.asItem(), 0.1F, 100);
        createKilnRecipe(exporter, Ingredient.ofItems(Blocks.BROWN_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.BROWN_GLAZED_TERRACOTTA.asItem(), 0.1F, 100);
        createKilnRecipe(exporter, Ingredient.ofItems(Blocks.RED_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.RED_GLAZED_TERRACOTTA.asItem(), 0.1F, 100);
        createKilnRecipe(exporter, Ingredient.ofItems(Blocks.ORANGE_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.ORANGE_GLAZED_TERRACOTTA.asItem(), 0.1F, 100);
        createKilnRecipe(exporter, Ingredient.ofItems(Blocks.YELLOW_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.YELLOW_GLAZED_TERRACOTTA.asItem(), 0.1F, 100);
        createKilnRecipe(exporter, Ingredient.ofItems(Blocks.LIME_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.LIME_GLAZED_TERRACOTTA.asItem(), 0.1F, 100);
        createKilnRecipe(exporter, Ingredient.ofItems(Blocks.GREEN_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.GREEN_GLAZED_TERRACOTTA.asItem(), 0.1F, 100);
        createKilnRecipe(exporter, Ingredient.ofItems(Blocks.CYAN_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.CYAN_GLAZED_TERRACOTTA.asItem(), 0.1F, 100);
        createKilnRecipe(exporter, Ingredient.ofItems(Blocks.LIGHT_BLUE_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA.asItem(), 0.1F, 100);
        createKilnRecipe(exporter, Ingredient.ofItems(Blocks.BLUE_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.BLUE_GLAZED_TERRACOTTA.asItem(), 0.1F, 100);
        createKilnRecipe(exporter, Ingredient.ofItems(Blocks.PURPLE_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.PURPLE_GLAZED_TERRACOTTA.asItem(), 0.1F, 100);
        createKilnRecipe(exporter, Ingredient.ofItems(Blocks.MAGENTA_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.MAGENTA_GLAZED_TERRACOTTA.asItem(), 0.1F, 100);
        createKilnRecipe(exporter, Ingredient.ofItems(Blocks.PINK_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.PINK_GLAZED_TERRACOTTA.asItem(), 0.1F, 100);
        createKilnRecipe(exporter, Ingredient.ofItems(Blocks.DEEPSLATE_TILES), RecipeCategory.BUILDING_BLOCKS, Blocks.CRACKED_DEEPSLATE_TILES.asItem(), 0.1F, 100);
        createKilnRecipe(exporter, Ingredient.ofItems(Blocks.DEEPSLATE_BRICKS), RecipeCategory.BUILDING_BLOCKS, Blocks.CRACKED_DEEPSLATE_BRICKS.asItem(), 0.1F, 100);
        createKilnRecipe(exporter, Ingredient.ofItems(Blocks.POLISHED_BLACKSTONE), RecipeCategory.BUILDING_BLOCKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS.asItem(), 0.1F, 100);
        createKilnRecipe(exporter, Ingredient.ofItems(Blocks.STONE_BRICKS), RecipeCategory.BUILDING_BLOCKS, Blocks.CRACKED_STONE_BRICKS.asItem(), 0.1F, 100);
        createKilnRecipe(exporter, Ingredient.ofItems(Blocks.NETHER_BRICKS), RecipeCategory.BUILDING_BLOCKS, Blocks.CRACKED_NETHER_BRICKS.asItem(), 0.1F, 100);
        createKilnRecipe(exporter, Ingredient.ofItems(Blocks.RED_SANDSTONE), RecipeCategory.BUILDING_BLOCKS, Blocks.SMOOTH_RED_SANDSTONE.asItem(), 0.1F, 100);
        createKilnRecipe(exporter, Ingredient.ofItems(Blocks.SANDSTONE), RecipeCategory.BUILDING_BLOCKS, Blocks.SMOOTH_SANDSTONE.asItem(), 0.1F, 100);
        createKilnRecipe(exporter, Ingredient.ofItems(Blocks.QUARTZ_BLOCK), RecipeCategory.BUILDING_BLOCKS, Blocks.SMOOTH_QUARTZ.asItem(), 0.1F, 100);
        createKilnRecipe(exporter, Ingredient.ofItems(Blocks.STONE), RecipeCategory.BUILDING_BLOCKS, Blocks.SMOOTH_STONE.asItem(), 0.1F, 100);
        createKilnRecipe(exporter, Ingredient.ofItems(Blocks.BASALT), RecipeCategory.BUILDING_BLOCKS, Blocks.SMOOTH_BASALT.asItem(), 0.1F, 100);
        createKilnRecipe(exporter, Ingredient.ofItems(Blocks.COBBLESTONE), RecipeCategory.BUILDING_BLOCKS, Blocks.STONE.asItem(), 0.1F, 100);
        createKilnRecipe(exporter, Ingredient.ofItems(Blocks.COBBLED_DEEPSLATE), RecipeCategory.BUILDING_BLOCKS, Blocks.DEEPSLATE.asItem(), 0.1F, 100);
        createKilnRecipe(exporter, Ingredient.ofItems(Blocks.CLAY), RecipeCategory.BUILDING_BLOCKS, Blocks.TERRACOTTA.asItem(), 0.1F, 100);
        createKilnRecipe(exporter, Ingredient.ofItems(Items.CLAY_BALL), RecipeCategory.MISC, Items.BRICK, 0.3F, 100);
        createKilnRecipe(exporter, Ingredient.ofItems(Blocks.NETHERRACK), RecipeCategory.MISC, Items.NETHER_BRICK, 0.1F, 100);
    }

    private void createPrismarineRecipes(RecipeExporter exporter) {
        ShapedPrismarineRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Blocks.DARK_PRISMARINE)
                .input('S', Items.PRISMARINE_SHARD)
                .input('I', Items.BLACK_DYE)
                .pattern("SSS")
                .pattern("SIS")
                .pattern("SSS")
                .criterion("has_prismarine_shard", conditionsFromItem(Items.PRISMARINE_SHARD))
                .offerTo(exporter);

        PrismarineRecipeJsonBuilder.offer2x2CompactingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, Blocks.PRISMARINE, Items.PRISMARINE_SHARD);
        PrismarineRecipeJsonBuilder.offerCompactingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, Blocks.PRISMARINE_BRICKS, Items.PRISMARINE_SHARD);

        ShapedPrismarineRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Blocks.SEA_LANTERN)
                .input('S', Items.PRISMARINE_SHARD)
                .input('C', Items.PRISMARINE_CRYSTALS)
                .pattern("SCS")
                .pattern("CCC")
                .pattern("SCS")
                .criterion("has_prismarine_crystals", conditionsFromItem(Items.PRISMARINE_CRYSTALS))
                .offerTo(exporter);

        ShapedPrismarineRecipeJsonBuilder.create(RecipeCategory.COMBAT, GraysModItems.PRISMARINE_SWORD)
                .pattern("p")
                .pattern("p")
                .pattern("s")
                .input('p', Items.PRISMARINE_SHARD)
                .input('s', Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(Items.PRISMARINE_SHARD),
                        FabricRecipeProvider.conditionsFromItem(Items.PRISMARINE_SHARD))
                .offerTo(exporter);

        ShapedPrismarineRecipeJsonBuilder.create(RecipeCategory.TOOLS, GraysModItems.PRISMARINE_PICKAXE)
                .pattern("ppp")
                .pattern(" s ")
                .pattern(" s ")
                .input('p', Items.PRISMARINE_SHARD)
                .input('s', Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(Items.PRISMARINE_SHARD),
                        FabricRecipeProvider.conditionsFromItem(Items.PRISMARINE_SHARD))
                .offerTo(exporter);

        ShapedPrismarineRecipeJsonBuilder.create(RecipeCategory.TOOLS, GraysModItems.PRISMARINE_AXE)
                .pattern("pp")
                .pattern("ps")
                .pattern(" s")
                .input('p', Items.PRISMARINE_SHARD)
                .input('s', Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(Items.PRISMARINE_SHARD),
                        FabricRecipeProvider.conditionsFromItem(Items.PRISMARINE_SHARD))
                .offerTo(exporter);

        ShapedPrismarineRecipeJsonBuilder.create(RecipeCategory.TOOLS, GraysModItems.PRISMARINE_SHOVEL)
                .pattern("p")
                .pattern("s")
                .pattern("s")
                .input('p', Items.PRISMARINE_SHARD)
                .input('s', Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(Items.PRISMARINE_SHARD),
                        FabricRecipeProvider.conditionsFromItem(Items.PRISMARINE_SHARD))
                .offerTo(exporter);

        ShapedPrismarineRecipeJsonBuilder.create(RecipeCategory.TOOLS, GraysModItems.PRISMARINE_HOE)
                .pattern("pp")
                .pattern(" s")
                .pattern(" s")
                .input('p', Items.PRISMARINE_SHARD)
                .input('s', Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(Items.PRISMARINE_SHARD),
                        FabricRecipeProvider.conditionsFromItem(Items.PRISMARINE_SHARD))
                .offerTo(exporter);

        ShapedPrismarineRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.DIAMOND_AXE)
                .input('#', Items.STICK)
                .input('X', Items.DIAMOND)
                .pattern("XX")
                .pattern("X#")
                .pattern(" #")
                .criterion("has_diamond", conditionsFromItem(Items.DIAMOND))
                .offerTo(exporter);

        ShapedPrismarineRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.DIAMOND_BOOTS)
                .input('X', Items.DIAMOND)
                .pattern("X X")
                .pattern("X X")
                .criterion("has_diamond", conditionsFromItem(Items.DIAMOND))
                .offerTo(exporter);

        ShapedPrismarineRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.DIAMOND_CHESTPLATE)
                .input('X', Items.DIAMOND)
                .pattern("X X")
                .pattern("XXX")
                .pattern("XXX")
                .criterion("has_diamond", conditionsFromItem(Items.DIAMOND))
                .offerTo(exporter);

        ShapedPrismarineRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.DIAMOND_HELMET)
                .input('X', Items.DIAMOND)
                .pattern("XXX")
                .pattern("X X")
                .criterion("has_diamond", conditionsFromItem(Items.DIAMOND))
                .offerTo(exporter);

        ShapedPrismarineRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.DIAMOND_HOE)
                .input('#', Items.STICK)
                .input('X', Items.DIAMOND)
                .pattern("XX")
                .pattern(" #")
                .pattern(" #")
                .criterion("has_diamond", conditionsFromItem(Items.DIAMOND))
                .offerTo(exporter);

        ShapedPrismarineRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.DIAMOND_LEGGINGS)
                .input('X', Items.DIAMOND)
                .pattern("XXX")
                .pattern("X X")
                .pattern("X X")
                .criterion("has_diamond", conditionsFromItem(Items.DIAMOND))
                .offerTo(exporter);

        ShapedPrismarineRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.DIAMOND_PICKAXE)
                .input('#', Items.STICK)
                .input('X', Items.DIAMOND)
                .pattern("XXX")
                .pattern(" # ")
                .pattern(" # ")
                .criterion("has_diamond", conditionsFromItem(Items.DIAMOND))
                .offerTo(exporter);

        ShapedPrismarineRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.DIAMOND_SHOVEL)
                .input('#', Items.STICK)
                .input('X', Items.DIAMOND)
                .pattern("X")
                .pattern("#")
                .pattern("#")
                .criterion("has_diamond", conditionsFromItem(Items.DIAMOND))
                .offerTo(exporter);

        ShapedPrismarineRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.DIAMOND_SWORD)
                .input('#', Items.STICK)
                .input('X', Items.DIAMOND)
                .pattern("X")
                .pattern("X")
                .pattern("#")
                .criterion("has_diamond", conditionsFromItem(Items.DIAMOND))
                .offerTo(exporter);

        PrismarineRecipeJsonBuilder.offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, Items.DIAMOND, RecipeCategory.BUILDING_BLOCKS, Items.DIAMOND_BLOCK);
    }
}
