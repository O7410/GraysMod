package graysblock.graysmod.data.server.recipe;

import graysblock.graysmod.recipe.ShapelessPrismarineRecipe;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class ShapelessPrismarineRecipeJsonBuilder implements CraftingRecipeJsonBuilder {
    private final RecipeCategory category;
    private final Item output;
    private final int count;
    private final DefaultedList<Ingredient> inputs = DefaultedList.of();
    private final Map<String, AdvancementCriterion<?>> advancementBuilder = new LinkedHashMap<>();
    @Nullable
    private String group;

    public ShapelessPrismarineRecipeJsonBuilder(RecipeCategory category, ItemConvertible output, int count) {
        this.category = category;
        this.output = output.asItem();
        this.count = count;
    }

    public static ShapelessPrismarineRecipeJsonBuilder create(RecipeCategory category, ItemConvertible output) {
        return new ShapelessPrismarineRecipeJsonBuilder(category, output, 1);
    }

    public static ShapelessPrismarineRecipeJsonBuilder create(RecipeCategory category, ItemConvertible output, int count) {
        return new ShapelessPrismarineRecipeJsonBuilder(category, output, count);
    }

    public ShapelessPrismarineRecipeJsonBuilder input(TagKey<Item> tag) {
        return this.input(Ingredient.fromTag(tag));
    }

    public ShapelessPrismarineRecipeJsonBuilder input(ItemConvertible itemProvider) {
        return this.input(itemProvider, 1);
    }

    public ShapelessPrismarineRecipeJsonBuilder input(ItemConvertible itemProvider, int size) {
        for(int i = 0; i < size; ++i) {
            this.input(Ingredient.ofItems(itemProvider));
        }

        return this;
    }

    public ShapelessPrismarineRecipeJsonBuilder input(Ingredient ingredient) {
        return this.input(ingredient, 1);
    }

    public ShapelessPrismarineRecipeJsonBuilder input(Ingredient ingredient, int size) {
        for(int i = 0; i < size; ++i) {
            this.inputs.add(ingredient);
        }

        return this;
    }

    @Override
    public ShapelessPrismarineRecipeJsonBuilder criterion(String string, AdvancementCriterion<?> advancementCriterion) {
        this.advancementBuilder.put(string, advancementCriterion);
        return this;
    }

    @Override
    public ShapelessPrismarineRecipeJsonBuilder group(@Nullable String string) {
        this.group = string;
        return this;
    }

    @Override
    public Item getOutputItem() {
        return this.output;
    }

    @Override
    public void offerTo(RecipeExporter exporter, Identifier recipeId) {
        this.validate(recipeId);
        Advancement.Builder builder = exporter.getAdvancementBuilder()
                .criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId))
                .criteriaMerger(AdvancementRequirements.CriterionMerger.OR);
        this.advancementBuilder.forEach(builder::criterion);
        ShapelessPrismarineRecipe shapelessPrismarineRecipe = new ShapelessPrismarineRecipe(
                Objects.requireNonNullElse(this.group, ""),
                CraftingRecipeJsonBuilder.toCraftingCategory(this.category),
                new ItemStack(this.output, this.count),
                this.inputs
        );
        exporter.accept(recipeId, shapelessPrismarineRecipe, builder.build(recipeId.withPrefixedPath("recipes/" + this.category.getName() + "/")));
    }

    private void validate(Identifier recipeId) {
        if(this.advancementBuilder.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + recipeId);
        }
    }
}
