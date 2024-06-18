package graysblock.graysmod.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import graysblock.graysmod.block.GraysModBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class ShapelessPrismarineRecipe implements PrismarineCraftingRecipe {
    final String group;
    final CraftingRecipeCategory category;
    final ItemStack result;
    final DefaultedList<Ingredient> ingredients;

    public ShapelessPrismarineRecipe(String group, CraftingRecipeCategory category, ItemStack result, DefaultedList<Ingredient> ingredients) {
        this.group = group;
        this.category = category;
        this.result = result;
        this.ingredients = ingredients;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return GraysModRecipeSerializers.SHAPELESS_PRISMARINE;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(GraysModBlocks.PRISMARINE_WORKBENCH);
    }

    @Override
    public CraftingRecipeCategory getCategory() {
        return this.category;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return this.result;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public boolean matches(CraftingRecipeInput craftingRecipeInput, World world) {
        if (craftingRecipeInput.getStackCount() != this.ingredients.size()) {
            return false;
        } else {
            return craftingRecipeInput.getSize() == 1 && this.ingredients.size() == 1
                    ? ((Ingredient)this.ingredients.getFirst()).test(craftingRecipeInput.getStackInSlot(0))
                    : craftingRecipeInput.getRecipeMatcher().match(this, null);
        }
    }

    public ItemStack craft(CraftingRecipeInput craftingRecipeInput, RegistryWrapper.WrapperLookup wrapperLookup) {
        return this.result.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= this.ingredients.size();
    }

    public static class Serializer implements RecipeSerializer<ShapelessPrismarineRecipe> {
        private static final MapCodec<ShapelessPrismarineRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
                                CraftingRecipeCategory.CODEC.fieldOf("category").orElse(CraftingRecipeCategory.MISC).forGetter(recipe -> recipe.category),
                                ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
                                Ingredient.DISALLOW_EMPTY_CODEC
                                        .listOf()
                                        .fieldOf("ingredients")
                                        .flatXmap(
                                                ingredients -> {
                                                    Ingredient[] ingredients2 = (Ingredient[])ingredients.stream().filter(ingredient -> !ingredient.isEmpty()).toArray(i -> new Ingredient[i]);
                                                    if (ingredients2.length == 0) {
                                                        return DataResult.error(() -> "No ingredients for shapeless recipe");
                                                    } else {
                                                        return ingredients2.length > 9
                                                                ? DataResult.error(() -> "Too many ingredients for shapeless recipe")
                                                                : DataResult.success(DefaultedList.<Ingredient>copyOf(Ingredient.EMPTY, ingredients2));
                                                    }
                                                },
                                                DataResult::success
                                        )
                                        .forGetter(recipe -> recipe.ingredients)
                        )
                        .apply(instance, ShapelessPrismarineRecipe::new)
        );

        public static final PacketCodec<RegistryByteBuf, ShapelessPrismarineRecipe> PACKET_CODEC = PacketCodec.ofStatic(
                ShapelessPrismarineRecipe.Serializer::write, ShapelessPrismarineRecipe.Serializer::read
        );

        @Override
        public MapCodec<ShapelessPrismarineRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, ShapelessPrismarineRecipe> packetCodec() {
            return PACKET_CODEC;
        }

        private static ShapelessPrismarineRecipe read(RegistryByteBuf buf) {
            String string = buf.readString();
            CraftingRecipeCategory craftingRecipeCategory = buf.readEnumConstant(CraftingRecipeCategory.class);
            int i = buf.readVarInt();
            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i, Ingredient.EMPTY);
            defaultedList.replaceAll(empty -> Ingredient.PACKET_CODEC.decode(buf));
            ItemStack itemStack = ItemStack.PACKET_CODEC.decode(buf);
            return new ShapelessPrismarineRecipe(string, craftingRecipeCategory, itemStack, defaultedList);
        }

        private static void write(RegistryByteBuf buf, ShapelessPrismarineRecipe recipe) {
            buf.writeString(recipe.group);
            buf.writeEnumConstant(recipe.category);
            buf.writeVarInt(recipe.ingredients.size());

            for(Ingredient ingredient : recipe.ingredients) {
                Ingredient.PACKET_CODEC.encode(buf, ingredient);
            }

            ItemStack.PACKET_CODEC.encode(buf, recipe.result);
        }
    }
}
