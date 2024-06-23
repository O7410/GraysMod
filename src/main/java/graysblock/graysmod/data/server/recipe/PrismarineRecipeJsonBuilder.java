package graysblock.graysmod.data.server.recipe;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public interface PrismarineRecipeJsonBuilder {

    PrismarineRecipeJsonBuilder criterion(String name, AdvancementCriterion<?> criterion);

    PrismarineRecipeJsonBuilder group(@Nullable String group);

    Item getOutputItem();

    void offerTo(RecipeExporter exporter, Identifier recipeId);

    default void offerTo(RecipeExporter exporter) {
        this.offerTo(exporter, getItemId(this.getOutputItem()));
    }

    default void offerTo(RecipeExporter exporter, String recipePath) {
        Identifier identifier = getItemId(this.getOutputItem());
        Identifier identifier2 = Identifier.of(recipePath);
        if (identifier2.equals(identifier)) {
            throw new IllegalStateException("Recipe " + recipePath + " should remove its 'save' argument as it is equal to default one");
        } else {
            this.offerTo(exporter, identifier2);
        }
    }

    static void offer2x2CompactingRecipe(RecipeExporter exporter, RecipeCategory category, ItemConvertible output, ItemConvertible input) {
        ShapedPrismarineRecipeJsonBuilder.create(category, output, 1)
                .input('#', input)
                .pattern("##")
                .pattern("##")
                .criterion(FabricRecipeProvider.hasItem(input), FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    static void offerCompactingRecipe(RecipeExporter exporter, RecipeCategory category, ItemConvertible output, ItemConvertible input, String criterionName) {
        ShapelessPrismarineRecipeJsonBuilder.create(category, output).input(input, 9).criterion(criterionName, FabricRecipeProvider.conditionsFromItem(input)).offerTo(exporter);
    }

    static void offerCompactingRecipe(RecipeExporter exporter, RecipeCategory category, ItemConvertible output, ItemConvertible input) {
        offerCompactingRecipe(exporter, category, output, input, FabricRecipeProvider.hasItem(input));
    }

    static void offerReversibleCompactingRecipes(RecipeExporter exporter, RecipeCategory reverseCategory, ItemConvertible baseItem, RecipeCategory compactingCategory, ItemConvertible compactItem) {
        offerReversibleCompactingRecipes(exporter, reverseCategory, baseItem, compactingCategory, compactItem, getRecipeName(compactItem), null, getRecipeName(baseItem), null);
    }

    static void offerReversibleCompactingRecipes(RecipeExporter exporter, RecipeCategory reverseCategory, ItemConvertible baseItem, RecipeCategory compactingCategory, ItemConvertible compactItem, String compactingId, @Nullable String compactingGroup, String reverseId, @Nullable String reverseGroup) {
        ShapelessPrismarineRecipeJsonBuilder.create(reverseCategory, baseItem, 9)
                .input(compactItem)
                .group(reverseGroup)
                .criterion(FabricRecipeProvider.hasItem(compactItem), FabricRecipeProvider.conditionsFromItem(compactItem))
                .offerTo(exporter, Identifier.of(reverseId));

        ShapedPrismarineRecipeJsonBuilder.create(compactingCategory, compactItem)
                .input('#', baseItem)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .group(compactingGroup)
                .criterion(FabricRecipeProvider.hasItem(baseItem), FabricRecipeProvider.conditionsFromItem(baseItem))
                .offerTo(exporter, Identifier.of(compactingId));
    }

    static Identifier getItemId(ItemConvertible item) {
        return Registries.ITEM.getId(item.asItem());
    }

    static String getItemPath(ItemConvertible item) {
        return Registries.ITEM.getId(item.asItem()).getPath();
    }

    static String getRecipeName(ItemConvertible item) {
        return getItemPath(item);
    }

    static CraftingRecipeCategory toCraftingCategory(RecipeCategory category) {
        return switch(category) {
            case BUILDING_BLOCKS -> CraftingRecipeCategory.BUILDING;
            case TOOLS, COMBAT -> CraftingRecipeCategory.EQUIPMENT;
            case REDSTONE -> CraftingRecipeCategory.REDSTONE;
            default -> CraftingRecipeCategory.MISC;
        };
    }
}
