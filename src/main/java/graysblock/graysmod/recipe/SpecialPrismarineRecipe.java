package graysblock.graysmod.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.RegistryWrapper;

public abstract class SpecialPrismarineRecipe implements PrismarineCraftingRecipe {
    private final CraftingRecipeCategory category;

    public SpecialPrismarineRecipe(CraftingRecipeCategory category) {
        this.category = category;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return ItemStack.EMPTY;
    }

    @Override
    public CraftingRecipeCategory getCategory() {
        return this.category;
    }
}
