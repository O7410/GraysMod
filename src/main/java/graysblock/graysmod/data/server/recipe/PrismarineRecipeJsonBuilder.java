package graysblock.graysmod.data.server.recipe;

import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
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

    static Identifier getItemId(ItemConvertible item) {
        return Registries.ITEM.getId(item.asItem());
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
