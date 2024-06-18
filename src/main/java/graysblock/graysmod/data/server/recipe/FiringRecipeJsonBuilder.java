package graysblock.graysmod.data.server.recipe;

import graysblock.graysmod.recipe.FiringRecipe;
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
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.CookingRecipeCategory;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class FiringRecipeJsonBuilder implements CraftingRecipeJsonBuilder {

    private final RecipeCategory category;
    private final CookingRecipeCategory cookingCategory;
    private final Item output;
    private final Ingredient input;
    private final float experience;
    private final int cookingTime;
    private final Map<String, AdvancementCriterion<?>> criteria = new LinkedHashMap();
    private String group;
    private final AbstractCookingRecipe.RecipeFactory<?> recipeFactory;

    private FiringRecipeJsonBuilder(RecipeCategory category, CookingRecipeCategory cookingCategory, ItemConvertible output, Ingredient input, float experience, int cookingTime, AbstractCookingRecipe.RecipeFactory<?> recipeFactory) {
        this.category = category;
        this.cookingCategory = cookingCategory;
        this.output = output.asItem();
        this.input = input;
        this.experience = experience;
        this.cookingTime = cookingTime;
        this.recipeFactory = recipeFactory;
    }

    public static <T extends AbstractCookingRecipe> FiringRecipeJsonBuilder create(
            Ingredient input,
            RecipeCategory category,
            ItemConvertible output,
            float experience,
            int cookingTime,
            AbstractCookingRecipe.RecipeFactory<T> recipeFactory
    ) {
        return new FiringRecipeJsonBuilder(category, CookingRecipeCategory.BLOCKS, output, input, experience, cookingTime, recipeFactory);
    }

    public static FiringRecipeJsonBuilder createFiring(Ingredient input, RecipeCategory category, ItemConvertible output, float experience, int cookingTime) {
        return new FiringRecipeJsonBuilder(category, CookingRecipeCategory.BLOCKS, output, input, experience, cookingTime, FiringRecipe::new);
    }

    @Override
    public FiringRecipeJsonBuilder criterion(String string, AdvancementCriterion<?> advancementCriterion) {
        this.criteria.put(string, advancementCriterion);
        return this;
    }

    @Override
    public FiringRecipeJsonBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public Item getOutputItem() {
        return this.output;
    }

    @Override
    public void offerTo(RecipeExporter exporter, Identifier recipeId) {
        this.validate(recipeId);
        Advancement.Builder builder = exporter.getAdvancementBuilder().criterion("has_the_recipe",
                RecipeUnlockedCriterion.create(recipeId)).rewards(AdvancementRewards.Builder.recipe(recipeId))
                .criteriaMerger(AdvancementRequirements.CriterionMerger.OR);
        this.criteria.forEach(builder::criterion);
        AbstractCookingRecipe abstractCookingRecipe = this.recipeFactory
                        .create(Objects.requireNonNullElse(group, ""), cookingCategory, input, new ItemStack(output), experience, cookingTime);
        exporter.accept(recipeId, abstractCookingRecipe, builder.build(recipeId.withPrefixedPath("recipes/" + category.getName() + "/")));
    }

    private void validate(Identifier recipeId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + recipeId);
        }
    }
}
