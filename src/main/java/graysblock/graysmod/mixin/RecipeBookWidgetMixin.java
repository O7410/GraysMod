package graysblock.graysmod.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import graysblock.graysmod.client.gui.screen.ingame.PrismarineWorkbenchScreen;
import graysblock.graysmod.recipe.PrismarineCraftingRecipe;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(RecipeBookWidget.class)
public abstract class RecipeBookWidgetMixin {

    @Shadow protected MinecraftClient client;
    @Shadow @Final private RecipeMatcher recipeFinder;
    @Shadow protected AbstractRecipeScreenHandler<?, ?> craftingScreenHandler;
    @Shadow private ClientRecipeBook recipeBook;

    @Inject(method = "refreshResults", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/recipebook/RecipeBookResults;setResults(Ljava/util/List;Z)V"))
    private void onRefreshResults(boolean resetCurrentPage, CallbackInfo ci, @Local(/*name = "list2", */index = 3) List<RecipeResultCollection> recipes) {
        if (this.client.currentScreen instanceof PrismarineWorkbenchScreen prismarineWorkbenchScreen &&
                prismarineWorkbenchScreen.isShowingOnlyPrismarineRecipes()) {
            recipes.replaceAll(resultCollection -> {
                List<RecipeEntry<?>> newRecipes = new ArrayList<>(resultCollection.getAllRecipes());
                newRecipes.removeIf(recipeEntry -> !(recipeEntry.value() instanceof PrismarineCraftingRecipe));
                RecipeResultCollection newResultCollection = new RecipeResultCollection(resultCollection.getRegistryManager(), newRecipes);
                newResultCollection.computeCraftables(this.recipeFinder, this.craftingScreenHandler.getCraftingWidth(), this.craftingScreenHandler.getCraftingHeight(), this.recipeBook);
                return newResultCollection;
            });
            recipes.removeIf(resultCollection -> resultCollection.getAllRecipes().isEmpty());
        }
    }
}
