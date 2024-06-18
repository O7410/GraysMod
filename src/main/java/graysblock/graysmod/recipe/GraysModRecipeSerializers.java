package graysblock.graysmod.recipe;

import graysblock.graysmod.GraysMod;
import net.minecraft.recipe.CookingRecipeSerializer;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class GraysModRecipeSerializers {

    public static RecipeSerializer<FiringRecipe> FIRING = Registry.register(
            Registries.RECIPE_SERIALIZER,
            Identifier.of(GraysMod.MOD_ID, "firing"),
            new CookingRecipeSerializer<>(FiringRecipe::new, 100));

    public static RecipeSerializer<ShapedPrismarineRecipe> SHAPED_PRISMARINE = Registry.register(
            Registries.RECIPE_SERIALIZER,
            Identifier.of(GraysMod.MOD_ID, "shaped_prismarine"),
            new ShapedPrismarineRecipe.Serializer()
    );

    public static RecipeSerializer<ShapelessPrismarineRecipe> SHAPELESS_PRISMARINE = Registry.register(
            Registries.RECIPE_SERIALIZER,
            Identifier.of(GraysMod.MOD_ID, "shapeless_prismarine"),
            new ShapelessPrismarineRecipe.Serializer()
    );

    public static void registerModdedRecipeSerializers() {
        GraysMod.LOGGER.info("Registering GraysMod Recipe Serializers...");
    }
}
