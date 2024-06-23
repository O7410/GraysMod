package graysblock.graysmod.mixin;

import com.google.common.collect.ImmutableMap;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.datafixers.util.Pair;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.book.RecipeBookOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RecipeBookOptions.class)
public class RecipeBookOptionsMixin {

    @ModifyExpressionValue(method = "<clinit>", at = @At(value = "INVOKE", target =
            "Lcom/google/common/collect/ImmutableMap;of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;" +
                    "Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Lcom/google/common/collect/ImmutableMap;"), remap = false)
    private static ImmutableMap<RecipeBookCategory, Pair<String, String>> addArgumentsToMap(ImmutableMap<RecipeBookCategory, Pair<String, String>> original) {
        ImmutableMap.Builder<RecipeBookCategory, Pair<String, String>> builder = ImmutableMap.<RecipeBookCategory, Pair<String, String>>builder()
                .putAll(original);
        builder.put(RecipeBookCategory.valueOf("PRISMARINE_CRAFTING"), Pair.of("isPrismarineCraftingGuiOpen", "isPrismarineCraftingFilteringCraftable"));
        return builder.build();
    }
}