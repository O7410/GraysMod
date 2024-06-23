package graysblock.graysmod.data;

import graysblock.graysmod.block.GraysModBlocks;
import graysblock.graysmod.data.server.recipe.FiringRecipeJsonBuilder;
import graysblock.graysmod.data.server.recipe.ShapedPrismarineRecipeJsonBuilder;
import graysblock.graysmod.item.GraysModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class GraysModRecipeProvider extends FabricRecipeProvider {

    public GraysModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, GraysModItems.TURTLE_SHELL)
                .pattern("S S")
                .pattern("SSS")
                .pattern("SSS")
                .input('S', Items.TURTLE_SCUTE)
                .criterion(hasItem(Items.TURTLE_SCUTE),
                        conditionsFromItem(Items.TURTLE_SCUTE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, GraysModItems.TURTLE_TROUSERS)
                .pattern("SSS")
                .pattern("S S")
                .pattern("S S")
                .input('S', Items.TURTLE_SCUTE)
                .criterion(hasItem(Items.TURTLE_SCUTE),
                        conditionsFromItem(Items.TURTLE_SCUTE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, GraysModItems.TURTLE_SHOES)
                .pattern("S S")
                .pattern("S S")
                .input('S', Items.TURTLE_SCUTE)
                .criterion(hasItem(Items.TURTLE_SCUTE),
                        conditionsFromItem(Items.TURTLE_SCUTE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, GraysModItems.STRIDER_BOOTS)
                .pattern("S S")
                .pattern("S S")
                .input('S', GraysModItems.STRIDER_SCALE)
                .criterion(hasItem(GraysModItems.STRIDER_SCALE),
                        conditionsFromItem(GraysModItems.STRIDER_SCALE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, GraysModBlocks.KILN)
                .pattern("SSS")
                .pattern("BFB")
                .pattern("BBB")
                .input('S', Items.SMOOTH_BASALT)
                .input('B', Items.BRICKS)
                .input('F', Items.FURNACE)
                .criterion(hasItem(Items.BRICKS),
                        conditionsFromItem(Items.BRICKS))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, GraysModItems.MAKESHIFT_WINGS)
                .pattern("HLH")
                .pattern("FHF")
                .pattern("F F")
                .input('H', Items.HONEYCOMB)
                .input('L', Items.LEATHER)
                .input('F', Items.FEATHER)
                .criterion(hasItem(Items.HONEYCOMB),
                        conditionsFromItem(Items.HONEYCOMB))
                .offerTo(exporter);

        ShapedPrismarineRecipeJsonBuilder.create(RecipeCategory.COMBAT, GraysModItems.PRISMARINE_SWORD)
                .pattern("P")
                .pattern("P")
                .pattern("S")
                .input('P', Items.PRISMARINE_SHARD)
                .input('S', Items.STICK)
                .criterion(hasItem(Items.PRISMARINE_SHARD),
                        conditionsFromItem(Items.PRISMARINE_SHARD))
                .offerTo(exporter);


        offerKilnRecipe(exporter, Ingredient.fromTag(ItemTags.SMELTS_TO_GLASS), RecipeCategory.BUILDING_BLOCKS, Blocks.GLASS, 0.1F, 200);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.WHITE_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.WHITE_GLAZED_TERRACOTTA, 0.1F, 200);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.LIGHT_GRAY_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA, 0.1F, 200);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.GRAY_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.GRAY_GLAZED_TERRACOTTA, 0.1F, 200);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.BLACK_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.BLACK_GLAZED_TERRACOTTA, 0.1F, 200);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.BROWN_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.BROWN_GLAZED_TERRACOTTA, 0.1F, 200);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.RED_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.RED_GLAZED_TERRACOTTA, 0.1F, 200);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.ORANGE_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.ORANGE_GLAZED_TERRACOTTA, 0.1F, 200);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.YELLOW_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.YELLOW_GLAZED_TERRACOTTA, 0.1F, 200);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.LIME_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.LIME_GLAZED_TERRACOTTA, 0.1F, 200);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.GREEN_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.GREEN_GLAZED_TERRACOTTA, 0.1F, 200);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.CYAN_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.CYAN_GLAZED_TERRACOTTA, 0.1F, 200);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.LIGHT_BLUE_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA, 0.1F, 200);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.BLUE_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.BLUE_GLAZED_TERRACOTTA, 0.1F, 200);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.PURPLE_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.PURPLE_GLAZED_TERRACOTTA, 0.1F, 200);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.MAGENTA_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.MAGENTA_GLAZED_TERRACOTTA, 0.1F, 200);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.PINK_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.PINK_GLAZED_TERRACOTTA, 0.1F, 200);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.DEEPSLATE_TILES), RecipeCategory.BUILDING_BLOCKS, Blocks.CRACKED_DEEPSLATE_TILES, 0.1F, 200);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.DEEPSLATE_BRICKS), RecipeCategory.BUILDING_BLOCKS, Blocks.CRACKED_DEEPSLATE_BRICKS, 0.1F, 200);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.POLISHED_BLACKSTONE), RecipeCategory.BUILDING_BLOCKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS, 0.1F, 200);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.STONE_BRICKS), RecipeCategory.BUILDING_BLOCKS, Blocks.CRACKED_STONE_BRICKS, 0.1F, 200);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.NETHER_BRICKS), RecipeCategory.BUILDING_BLOCKS, Blocks.CRACKED_NETHER_BRICKS, 0.1F, 200);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.RED_SANDSTONE), RecipeCategory.BUILDING_BLOCKS, Blocks.SMOOTH_RED_SANDSTONE, 0.1F, 200);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.SANDSTONE), RecipeCategory.BUILDING_BLOCKS, Blocks.SMOOTH_SANDSTONE, 0.1F, 200);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.QUARTZ_BLOCK), RecipeCategory.BUILDING_BLOCKS, Blocks.SMOOTH_QUARTZ, 0.1F, 200);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.STONE), RecipeCategory.BUILDING_BLOCKS, Blocks.SMOOTH_STONE, 0.1F, 200);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.BASALT), RecipeCategory.BUILDING_BLOCKS, Blocks.SMOOTH_BASALT, 0.1F, 200);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.COBBLESTONE), RecipeCategory.BUILDING_BLOCKS, Blocks.STONE, 0.1F, 200);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.COBBLED_DEEPSLATE), RecipeCategory.BUILDING_BLOCKS, Blocks.DEEPSLATE, 0.1F, 200);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.CLAY), RecipeCategory.BUILDING_BLOCKS, Blocks.TERRACOTTA, 0.1F, 200);
        offerKilnRecipe(exporter, Ingredient.ofItems(Items.CLAY_BALL), RecipeCategory.MISC, Items.BRICK, 0.3F, 200);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.NETHERRACK), RecipeCategory.MISC, Items.NETHER_BRICK, 0.1F, 200);
    }

    private void offerKilnRecipe(RecipeExporter exporter, Ingredient input, RecipeCategory category, ItemConvertible output, float experience, int cookingTime) {
        String outputName = Registries.ITEM.getId(output.asItem()).getPath();

        FiringRecipeJsonBuilder.createFiring(input, category, output, experience, cookingTime)
                .criterion(hasItem(output), conditionsFromItem(output))
                .offerTo(exporter, Identifier.of(outputName + "_from_firing"));
    }
}
