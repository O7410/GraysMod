package graysblock.graysmod.mixin;

import com.google.common.collect.ImmutableMap;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import graysblock.graysmod.recipe.PrismarineCraftingRecipe;
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.recipe.RecipeEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Mixin(ClientRecipeBook.class)
public class ClientRecipeBookMixin {
    @Shadow private Map<RecipeBookGroup, List<RecipeResultCollection>> resultsByGroup;
    @Unique private static final Map<RecipeBookGroup, RecipeBookGroup> PRISMARINE_CRAFTING_TO_VANILLA = ImmutableMap.of(
                RecipeBookGroup.valueOf("PRISMARINE_CRAFTING_EQUIPMENT"), RecipeBookGroup.CRAFTING_EQUIPMENT,
                RecipeBookGroup.valueOf("PRISMARINE_CRAFTING_BUILDING_BLOCKS"), RecipeBookGroup.CRAFTING_BUILDING_BLOCKS,
                RecipeBookGroup.valueOf("PRISMARINE_CRAFTING_MISC"), RecipeBookGroup.CRAFTING_MISC,
                RecipeBookGroup.valueOf("PRISMARINE_CRAFTING_REDSTONE"), RecipeBookGroup.CRAFTING_REDSTONE
    );

    @Inject(method = "getGroupForRecipe", at = @At("HEAD"), cancellable = true)
    private static void useCustomRecipeBookGroupForDiamondRecipes(RecipeEntry<?> recipe, CallbackInfoReturnable<RecipeBookGroup> cir) {
        // TODO: add something to suppress the error messages for firing recipes
        if (recipe.value() instanceof PrismarineCraftingRecipe prismarineCraftingRecipe) {
            cir.setReturnValue(switch (prismarineCraftingRecipe.getCategory()) {
                case EQUIPMENT -> RecipeBookGroup.valueOf("PRISMARINE_CRAFTING_EQUIPMENT");
                case BUILDING -> RecipeBookGroup.valueOf("PRISMARINE_CRAFTING_BUILDING_BLOCKS");
                case MISC -> RecipeBookGroup.valueOf("PRISMARINE_CRAFTING_MISC");
                case REDSTONE -> RecipeBookGroup.valueOf("PRISMARINE_CRAFTING_REDSTONE");
//                case BUILDING -> RecipeBookGroup.CRAFTING_BUILDING_BLOCKS;
//                case EQUIPMENT -> RecipeBookGroup.CRAFTING_EQUIPMENT;
//                case REDSTONE -> RecipeBookGroup.CRAFTING_REDSTONE;
//                case MISC -> RecipeBookGroup.CRAFTING_MISC;
            });
        }
    }

    @ModifyReturnValue(method = "getResultsForGroup", at = @At("RETURN"))
    private List<RecipeResultCollection> addVanillaRecipesToRecipeGroup(List<RecipeResultCollection> original, RecipeBookGroup category) {
        if (!PRISMARINE_CRAFTING_TO_VANILLA.containsKey(category)) {
            return original;
        }
        ArrayList<RecipeResultCollection> recipeResultCollections = new ArrayList<>(original);
        recipeResultCollections.addAll(this.resultsByGroup.getOrDefault(PRISMARINE_CRAFTING_TO_VANILLA.get(category), Collections.emptyList()));
        return List.copyOf(recipeResultCollections);
    }
}
