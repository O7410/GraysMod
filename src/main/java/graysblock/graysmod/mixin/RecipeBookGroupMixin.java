package graysblock.graysmod.mixin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.recipe.book.RecipeBookCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(RecipeBookGroup.class)
public class RecipeBookGroupMixin {


    @Unique private static final List<RecipeBookGroup> PRISMARINE_CRAFTING_GROUPS = ImmutableList.of(
            RecipeBookGroup.valueOf("PRISMARINE_CRAFTING_SEARCH"),
            RecipeBookGroup.valueOf("PRISMARINE_CRAFTING_EQUIPMENT"),
            RecipeBookGroup.valueOf("PRISMARINE_CRAFTING_BUILDING_BLOCKS"),
            RecipeBookGroup.valueOf("PRISMARINE_CRAFTING_MISC"),
            RecipeBookGroup.valueOf("PRISMARINE_CRAFTING_REDSTONE")
//            RecipeBookGroup.CRAFTING_SEARCH,
//            RecipeBookGroup.CRAFTING_EQUIPMENT,
//            RecipeBookGroup.CRAFTING_BUILDING_BLOCKS,
//            RecipeBookGroup.CRAFTING_MISC
//            RecipeBookGroup.CRAFTING_REDSTONE,
    );


    @Inject(method = "getGroups", at = @At("HEAD"), cancellable = true)
    private static void getGroupsForDiamondCrafting(RecipeBookCategory category, CallbackInfoReturnable<List<RecipeBookGroup>> cir) {
        if (category == RecipeBookCategory.valueOf("PRISMARINE_CRAFTING")) {
            cir.setReturnValue(PRISMARINE_CRAFTING_GROUPS);
        }
    }

    @ModifyExpressionValue(method = "<clinit>", at = @At(value = "INVOKE", target =
            "Lcom/google/common/collect/ImmutableMap;of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;" +
                    "Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Lcom/google/common/collect/ImmutableMap;"), remap = false)
    private static ImmutableMap<RecipeBookGroup, List<RecipeBookGroup>> addArgumentsToMap(ImmutableMap<RecipeBookGroup, List<RecipeBookGroup>> original) {
        ImmutableMap.Builder<RecipeBookGroup, List<RecipeBookGroup>> builder = ImmutableMap.<RecipeBookGroup, List<RecipeBookGroup>>builder()
                .putAll(original);
        builder.put(RecipeBookGroup.valueOf("PRISMARINE_CRAFTING_SEARCH"), ImmutableList.of(
                RecipeBookGroup.valueOf("PRISMARINE_CRAFTING_EQUIPMENT"),
                RecipeBookGroup.valueOf("PRISMARINE_CRAFTING_BUILDING_BLOCKS"),
                RecipeBookGroup.valueOf("PRISMARINE_CRAFTING_MISC"),
                RecipeBookGroup.valueOf("PRISMARINE_CRAFTING_REDSTONE"),
                RecipeBookGroup.CRAFTING_EQUIPMENT,
                RecipeBookGroup.CRAFTING_BUILDING_BLOCKS,
                RecipeBookGroup.CRAFTING_MISC,
                RecipeBookGroup.CRAFTING_REDSTONE
        ));
        return builder.build();
    }
}
