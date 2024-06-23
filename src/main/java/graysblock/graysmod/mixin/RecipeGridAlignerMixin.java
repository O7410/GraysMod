package graysblock.graysmod.mixin;

import graysblock.graysmod.recipe.ShapedPrismarineRecipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeGridAligner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Iterator;

@Mixin(RecipeGridAligner.class)
public interface RecipeGridAlignerMixin<T> {
    @ModifyVariable(method = "alignRecipeToGrid", at = @At("STORE"), ordinal = 4)
    private int setWidthToDiamondShapedWidth(int originalWidth, int gridWidth, int gridHeight, int gridOutputSlot, RecipeEntry<?> recipe, Iterator<T> inputs, int amount) {
        if (recipe.value() instanceof ShapedPrismarineRecipe shapedPrismarineRecipe) {
            return shapedPrismarineRecipe.getWidth();
        }
        return originalWidth;
    }

    @ModifyVariable(method = "alignRecipeToGrid", at = @At("STORE"), ordinal = 5)
    private int setWidthToDiamondShapedHeight(int originalHeight, int gridWidth, int gridHeight, int gridOutputSlot, RecipeEntry<?> recipe, Iterator<T> inputs, int amount) {
        if (recipe.value() instanceof ShapedPrismarineRecipe shapedPrismarineRecipe) {
            return shapedPrismarineRecipe.getHeight();
        }
        return originalHeight;
    }
}
