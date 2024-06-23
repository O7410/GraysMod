package graysblock.graysmod.recipe;

import graysblock.graysmod.block.GraysModBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.CookingRecipeCategory;

public class FiringRecipe extends AbstractCookingRecipe {

    public FiringRecipe(String group, CookingRecipeCategory category, Ingredient input, ItemStack output, float experience, int cookingTime) {
        super(GraysModRecipeTypes.FIRING, group, category, input, output, experience, cookingTime);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(GraysModBlocks.KILN);
    }

    @Override
    public RecipeSerializer<FiringRecipe> getSerializer() {
        return GraysModRecipeSerializers.FIRING;
    }

}
