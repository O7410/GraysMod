package graysblock.graysmod.recipe;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;

public interface PrismarineCraftingRecipe extends Recipe<CraftingRecipeInput> {
    @Override
    default RecipeType<?> getType() {
        return GraysModRecipeTypes.PRISMARINE_CRAFTING;
    }

    CraftingRecipeCategory getCategory();
}
