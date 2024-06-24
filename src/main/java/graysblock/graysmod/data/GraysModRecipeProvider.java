package graysblock.graysmod.data;

import graysblock.graysmod.block.GraysModBlocks;
import graysblock.graysmod.data.server.recipe.FiringRecipeJsonBuilder;
import graysblock.graysmod.data.server.recipe.ShapedPrismarineRecipeJsonBuilder;
import graysblock.graysmod.data.server.recipe.ShapelessPrismarineRecipeJsonBuilder;
import graysblock.graysmod.item.GraysModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

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
                .criterion(hasItem(Items.TURTLE_SCUTE), conditionsFromItem(Items.TURTLE_SCUTE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, GraysModItems.TURTLE_TROUSERS)
                .pattern("SSS")
                .pattern("S S")
                .pattern("S S")
                .input('S', Items.TURTLE_SCUTE)
                .criterion(hasItem(Items.TURTLE_SCUTE), conditionsFromItem(Items.TURTLE_SCUTE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, GraysModItems.TURTLE_SHOES)
                .pattern("S S")
                .pattern("S S")
                .input('S', Items.TURTLE_SCUTE)
                .criterion(hasItem(Items.TURTLE_SCUTE), conditionsFromItem(Items.TURTLE_SCUTE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, GraysModItems.STRIDER_BOOTS)
                .pattern("S S")
                .pattern("S S")
                .input('S', GraysModItems.STRIDER_SCALE)
                .criterion(hasItem(GraysModItems.STRIDER_SCALE), conditionsFromItem(GraysModItems.STRIDER_SCALE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, GraysModBlocks.KILN)
                .pattern("SSS")
                .pattern("BFB")
                .pattern("BBB")
                .input('S', Items.SMOOTH_BASALT)
                .input('B', Items.BRICKS)
                .input('F', Items.FURNACE)
                .criterion(hasItem(Items.BRICKS), conditionsFromItem(Items.BRICKS))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, GraysModItems.MAKESHIFT_WINGS)
                .pattern("HLH")
                .pattern("FHF")
                .pattern("F F")
                .input('H', Items.HONEYCOMB)
                .input('L', Items.LEATHER)
                .input('F', Items.FEATHER)
                .criterion(hasItem(Items.HONEYCOMB), conditionsFromItem(Items.HONEYCOMB))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, GraysModItems.WIND_BOLT, 4)
                .pattern("C")
                .pattern("B")
                .pattern("F")
                .input('C', Items.COPPER_INGOT)
                .input('B', Items.BREEZE_ROD)
                .input('F', Items.FEATHER)
                .criterion(hasItem(Items.BREEZE_ROD), conditionsFromItem(Items.BREEZE_ROD))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, GraysModBlocks.PRISMARINE_WORKBENCH)
                .pattern("PPP")
                .pattern("SCS")
                .pattern("SSS")
                .input('P', GraysModItems.PEARL)
                .input('S', Items.PRISMARINE_SHARD)
                .input('C', Items.CRAFTING_TABLE)
                .criterion(hasItem(Items.PRISMARINE_SHARD), conditionsFromItem(Items.PRISMARINE_SHARD))
                .offerTo(exporter);

        offerPrismarineRecipes(exporter);
        offerKilnRecipes(exporter);
    }

    private void offerKilnRecipe(RecipeExporter exporter, Ingredient input, RecipeCategory category, Item output, float experience, int cookingTime) {
        String outputName = Registries.ITEM.getId(output).getPath();

        FiringRecipeJsonBuilder.createFiring(input, category, output, experience, cookingTime)
                .criterion(hasItem(output), conditionsFromItem(output))
                .offerTo(exporter, Identifier.of(outputName + "_from_firing"));
    }

    private void offerKilnRecipes(RecipeExporter exporter) {
        offerKilnRecipe(exporter, Ingredient.fromTag(ItemTags.SMELTS_TO_GLASS), RecipeCategory.BUILDING_BLOCKS, Blocks.GLASS.asItem(), 0.1F, 100);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.WHITE_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.WHITE_GLAZED_TERRACOTTA.asItem(), 0.1F, 100);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.LIGHT_GRAY_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA.asItem(), 0.1F, 100);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.GRAY_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.GRAY_GLAZED_TERRACOTTA.asItem(), 0.1F, 100);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.BLACK_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.BLACK_GLAZED_TERRACOTTA.asItem(), 0.1F, 100);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.BROWN_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.BROWN_GLAZED_TERRACOTTA.asItem(), 0.1F, 100);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.RED_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.RED_GLAZED_TERRACOTTA.asItem(), 0.1F, 100);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.ORANGE_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.ORANGE_GLAZED_TERRACOTTA.asItem(), 0.1F, 100);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.YELLOW_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.YELLOW_GLAZED_TERRACOTTA.asItem(), 0.1F, 100);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.LIME_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.LIME_GLAZED_TERRACOTTA.asItem(), 0.1F, 100);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.GREEN_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.GREEN_GLAZED_TERRACOTTA.asItem(), 0.1F, 100);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.CYAN_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.CYAN_GLAZED_TERRACOTTA.asItem(), 0.1F, 100);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.LIGHT_BLUE_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA.asItem(), 0.1F, 100);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.BLUE_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.BLUE_GLAZED_TERRACOTTA.asItem(), 0.1F, 100);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.PURPLE_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.PURPLE_GLAZED_TERRACOTTA.asItem(), 0.1F, 100);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.MAGENTA_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.MAGENTA_GLAZED_TERRACOTTA.asItem(), 0.1F, 100);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.PINK_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.PINK_GLAZED_TERRACOTTA.asItem(), 0.1F, 100);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.DEEPSLATE_TILES), RecipeCategory.BUILDING_BLOCKS, Blocks.CRACKED_DEEPSLATE_TILES.asItem(), 0.1F, 100);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.DEEPSLATE_BRICKS), RecipeCategory.BUILDING_BLOCKS, Blocks.CRACKED_DEEPSLATE_BRICKS.asItem(), 0.1F, 100);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.POLISHED_BLACKSTONE), RecipeCategory.BUILDING_BLOCKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS.asItem(), 0.1F, 100);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.STONE_BRICKS), RecipeCategory.BUILDING_BLOCKS, Blocks.CRACKED_STONE_BRICKS.asItem(), 0.1F, 100);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.NETHER_BRICKS), RecipeCategory.BUILDING_BLOCKS, Blocks.CRACKED_NETHER_BRICKS.asItem(), 0.1F, 100);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.RED_SANDSTONE), RecipeCategory.BUILDING_BLOCKS, Blocks.SMOOTH_RED_SANDSTONE.asItem(), 0.1F, 100);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.SANDSTONE), RecipeCategory.BUILDING_BLOCKS, Blocks.SMOOTH_SANDSTONE.asItem(), 0.1F, 100);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.QUARTZ_BLOCK), RecipeCategory.BUILDING_BLOCKS, Blocks.SMOOTH_QUARTZ.asItem(), 0.1F, 100);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.STONE), RecipeCategory.BUILDING_BLOCKS, Blocks.SMOOTH_STONE.asItem(), 0.1F, 100);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.BASALT), RecipeCategory.BUILDING_BLOCKS, Blocks.SMOOTH_BASALT.asItem(), 0.1F, 100);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.COBBLESTONE), RecipeCategory.BUILDING_BLOCKS, Blocks.STONE.asItem(), 0.1F, 100);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.COBBLED_DEEPSLATE), RecipeCategory.BUILDING_BLOCKS, Blocks.DEEPSLATE.asItem(), 0.1F, 100);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.CLAY), RecipeCategory.BUILDING_BLOCKS, Blocks.TERRACOTTA.asItem(), 0.1F, 100);
        offerKilnRecipe(exporter, Ingredient.ofItems(Items.CLAY_BALL), RecipeCategory.MISC, Items.BRICK, 0.3F, 100);
        offerKilnRecipe(exporter, Ingredient.ofItems(Blocks.NETHERRACK), RecipeCategory.MISC, Items.NETHER_BRICK, 0.1F, 100);
    }

    private void offerPrismarineRecipes(RecipeExporter exporter) {
        ShapedPrismarineRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Blocks.DARK_PRISMARINE)
                .input('S', Items.PRISMARINE_SHARD)
                .input('I', Items.BLACK_DYE)
                .pattern("SSS")
                .pattern("SIS")
                .pattern("SSS")
                .criterion(hasItem(Items.PRISMARINE_SHARD), conditionsFromItem(Items.PRISMARINE_SHARD))
                .offerTo(exporter);

        offer2x2CompactingPrismarineRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, Blocks.PRISMARINE, Items.PRISMARINE_SHARD);
        offerCompactingPrismarineRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, Blocks.PRISMARINE_BRICKS, Items.PRISMARINE_SHARD);

        ShapedPrismarineRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Blocks.SEA_LANTERN)
                .input('S', Items.PRISMARINE_SHARD)
                .input('C', Items.PRISMARINE_CRYSTALS)
                .pattern("SCS")
                .pattern("CCC")
                .pattern("SCS")
                .criterion(hasItem(Items.PRISMARINE_CRYSTALS), conditionsFromItem(Items.PRISMARINE_CRYSTALS))
                .offerTo(exporter);

        ShapedPrismarineRecipeJsonBuilder.create(RecipeCategory.COMBAT, GraysModItems.PRISMARINE_SWORD)
                .pattern("P")
                .pattern("P")
                .pattern("S")
                .input('P', Items.PRISMARINE_SHARD)
                .input('S', Items.STICK)
                .criterion(hasItem(Items.PRISMARINE_SHARD), conditionsFromItem(Items.PRISMARINE_SHARD))
                .offerTo(exporter);

        ShapedPrismarineRecipeJsonBuilder.create(RecipeCategory.TOOLS, GraysModItems.PRISMARINE_PICKAXE)
                .pattern("PPP")
                .pattern(" S ")
                .pattern(" S ")
                .input('P', Items.PRISMARINE_SHARD)
                .input('S', Items.STICK)
                .criterion(hasItem(Items.PRISMARINE_SHARD), conditionsFromItem(Items.PRISMARINE_SHARD))
                .offerTo(exporter);

        ShapedPrismarineRecipeJsonBuilder.create(RecipeCategory.TOOLS, GraysModItems.PRISMARINE_AXE)
                .pattern("PP")
                .pattern("PS")
                .pattern(" S")
                .input('P', Items.PRISMARINE_SHARD)
                .input('S', Items.STICK)
                .criterion(hasItem(Items.PRISMARINE_SHARD), conditionsFromItem(Items.PRISMARINE_SHARD))
                .offerTo(exporter);

        ShapedPrismarineRecipeJsonBuilder.create(RecipeCategory.TOOLS, GraysModItems.PRISMARINE_SHOVEL)
                .pattern("P")
                .pattern("S")
                .pattern("S")
                .input('P', Items.PRISMARINE_SHARD)
                .input('S', Items.STICK)
                .criterion(hasItem(Items.PRISMARINE_SHARD), conditionsFromItem(Items.PRISMARINE_SHARD))
                .offerTo(exporter);

        ShapedPrismarineRecipeJsonBuilder.create(RecipeCategory.TOOLS, GraysModItems.PRISMARINE_HOE)
                .pattern("PP")
                .pattern(" S")
                .pattern(" S")
                .input('P', Items.PRISMARINE_SHARD)
                .input('S', Items.STICK)
                .criterion(hasItem(Items.PRISMARINE_SHARD), conditionsFromItem(Items.PRISMARINE_SHARD))
                .offerTo(exporter);

        ShapedPrismarineRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.DIAMOND_AXE)
                .input('#', Items.STICK)
                .input('X', Items.DIAMOND)
                .pattern("XX")
                .pattern("X#")
                .pattern(" #")
                .criterion(hasItem(Items.DIAMOND), conditionsFromItem(Items.DIAMOND))
                .offerTo(exporter);

        ShapedPrismarineRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.DIAMOND_BOOTS)
                .input('X', Items.DIAMOND)
                .pattern("X X")
                .pattern("X X")
                .criterion(hasItem(Items.DIAMOND), conditionsFromItem(Items.DIAMOND))
                .offerTo(exporter);

        ShapedPrismarineRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.DIAMOND_CHESTPLATE)
                .input('X', Items.DIAMOND)
                .pattern("X X")
                .pattern("XXX")
                .pattern("XXX")
                .criterion(hasItem(Items.DIAMOND), conditionsFromItem(Items.DIAMOND))
                .offerTo(exporter);

        ShapedPrismarineRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.DIAMOND_HELMET)
                .input('X', Items.DIAMOND)
                .pattern("XXX")
                .pattern("X X")
                .criterion(hasItem(Items.DIAMOND), conditionsFromItem(Items.DIAMOND))
                .offerTo(exporter);

        ShapedPrismarineRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.DIAMOND_HOE)
                .input('#', Items.STICK)
                .input('X', Items.DIAMOND)
                .pattern("XX")
                .pattern(" #")
                .pattern(" #")
                .criterion(hasItem(Items.DIAMOND), conditionsFromItem(Items.DIAMOND))
                .offerTo(exporter);

        ShapedPrismarineRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.DIAMOND_LEGGINGS)
                .input('X', Items.DIAMOND)
                .pattern("XXX")
                .pattern("X X")
                .pattern("X X")
                .criterion(hasItem(Items.DIAMOND), conditionsFromItem(Items.DIAMOND))
                .offerTo(exporter);

        ShapedPrismarineRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.DIAMOND_PICKAXE)
                .input('#', Items.STICK)
                .input('X', Items.DIAMOND)
                .pattern("XXX")
                .pattern(" # ")
                .pattern(" # ")
                .criterion(hasItem(Items.DIAMOND), conditionsFromItem(Items.DIAMOND))
                .offerTo(exporter);

        ShapedPrismarineRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.DIAMOND_SHOVEL)
                .input('#', Items.STICK)
                .input('X', Items.DIAMOND)
                .pattern("X")
                .pattern("#")
                .pattern("#")
                .criterion(hasItem(Items.DIAMOND), conditionsFromItem(Items.DIAMOND))
                .offerTo(exporter);

        ShapedPrismarineRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.DIAMOND_SWORD)
                .input('#', Items.STICK)
                .input('X', Items.DIAMOND)
                .pattern("X")
                .pattern("X")
                .pattern("#")
                .criterion(hasItem(Items.DIAMOND), conditionsFromItem(Items.DIAMOND))
                .offerTo(exporter);

        offerReversibleCompactingPrismarineRecipes(exporter, RecipeCategory.MISC, Items.DIAMOND, RecipeCategory.BUILDING_BLOCKS, Items.DIAMOND_BLOCK);
    }

    public static void offerReversibleCompactingPrismarineRecipes(RecipeExporter exporter, RecipeCategory reverseCategory, ItemConvertible baseItem, RecipeCategory compactingCategory, ItemConvertible compactItem) {
        offerReversibleCompactingPrismarineRecipes(exporter, reverseCategory, baseItem, compactingCategory, compactItem, getRecipeName(compactItem), null, getRecipeName(baseItem), null);
    }

    public static void offerReversibleCompactingPrismarineRecipes(
            RecipeExporter exporter,
            RecipeCategory reverseCategory,
            ItemConvertible baseItem,
            RecipeCategory compactingCategory,
            ItemConvertible compactItem,
            String compactingId,
            @Nullable String compactingGroup,
            String reverseId,
            @Nullable String reverseGroup
    ) {
        ShapelessPrismarineRecipeJsonBuilder.create(reverseCategory, baseItem, 9)
                .input(compactItem)
                .group(reverseGroup)
                .criterion(hasItem(compactItem), conditionsFromItem(compactItem))
                .offerTo(exporter, Identifier.of(reverseId));
        ShapedPrismarineRecipeJsonBuilder.create(compactingCategory, compactItem)
                .input('#', baseItem)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .group(compactingGroup)
                .criterion(hasItem(baseItem), conditionsFromItem(baseItem))
                .offerTo(exporter, Identifier.of(compactingId));
    }

    public static void offer2x2CompactingPrismarineRecipe(RecipeExporter exporter, RecipeCategory category, ItemConvertible output, ItemConvertible input) {
        ShapedPrismarineRecipeJsonBuilder.create(category, output, 1)
                .input('#', input)
                .pattern("##")
                .pattern("##")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter);
    }

    public static void offerCompactingPrismarineRecipe(RecipeExporter exporter, RecipeCategory category, ItemConvertible output, ItemConvertible input) {
        offerCompactingPrismarineRecipe(exporter, category, output, input, hasItem(input));
    }

    public static void offerCompactingPrismarineRecipe(RecipeExporter exporter, RecipeCategory category, ItemConvertible output, ItemConvertible input, String criterionName) {
        ShapelessPrismarineRecipeJsonBuilder.create(category, output)
                .input(input, 9)
                .criterion(criterionName, conditionsFromItem(input))
                .offerTo(exporter);
    }
}
