package graysblock.graysmod.recipe;

import graysblock.graysmod.GraysMod;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class GraysModRecipeTypes {

    public static RecipeType<FiringRecipe> FIRING = Registry.register(Registries.RECIPE_TYPE,
            Identifier.of(GraysMod.MOD_ID, "firing"), new RecipeType<FiringRecipe>() {});

    public static RecipeType<PrismarineCraftingRecipe> PRISMARINE_CRAFTING = Registry.register(Registries.RECIPE_TYPE,
            Identifier.of(GraysMod.MOD_ID, "prismarine_crafting"), new RecipeType<PrismarineCraftingRecipe>() {});

    public static void registerModdedRecipeTypes() {
        GraysMod.LOGGER.info("Registering GraysMod Recipe Types...");
    }
}