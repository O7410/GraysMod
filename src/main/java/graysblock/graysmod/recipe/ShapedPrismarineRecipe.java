package graysblock.graysmod.recipe;

import com.mojang.serialization.Codec;
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

public class ShapedPrismarineRecipe implements PrismarineCraftingRecipe {
    final RawShapedPrismarineRecipe raw;
    final ItemStack result;
    final String group;
    final CraftingRecipeCategory category;
    final boolean showNotification;

    public ShapedPrismarineRecipe(String group, CraftingRecipeCategory category, RawShapedPrismarineRecipe raw, ItemStack result, boolean showNotification) {
        this.group = group;
        this.category = category;
        this.raw = raw;
        this.result = result;
        this.showNotification = showNotification;
    }

    public ShapedPrismarineRecipe(String group, CraftingRecipeCategory category, RawShapedPrismarineRecipe raw, ItemStack result) {
        this(group, category, raw, result, true);
    }

    public int getWidth() {
        return this.raw.getWidth();
    }

    public int getHeight() {
        return this.raw.getHeight();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return GraysModRecipeSerializers.SHAPED_PRISMARINE;
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
        return this.raw.getIngredients();
    }

    @Override
    public boolean showNotification() {
        return this.showNotification;
    }

    @Override
    public boolean fits(int width, int height) {
        return width >= this.raw.getWidth() && height >= this.raw.getHeight();
    }

    @Override
    public boolean matches(CraftingRecipeInput craftingRecipeInput, World world) {
        return this.raw.matches(craftingRecipeInput);
    }

    @Override
    public ItemStack craft(CraftingRecipeInput craftingRecipeInput, RegistryWrapper.WrapperLookup wrapperLookup) {
        return this.getResult(wrapperLookup).copy();
    }

    @Override
    public boolean isEmpty() {
        DefaultedList<Ingredient> ingredients = this.getIngredients();
        return ingredients.isEmpty()
                || ingredients.stream()
                .filter(ingredient -> !ingredient.isEmpty())
                .anyMatch(ingredient -> ingredient.getMatchingStacks().length == 0);
    }

    public static class Serializer implements RecipeSerializer<ShapedPrismarineRecipe> {
        public static final MapCodec<ShapedPrismarineRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                        Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
                        CraftingRecipeCategory.CODEC.fieldOf("category").orElse(CraftingRecipeCategory.MISC).forGetter(recipe -> recipe.category),
                        RawShapedPrismarineRecipe.CODEC.forGetter(recipe -> recipe.raw),
                        ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
                        Codec.BOOL.optionalFieldOf("show_notification", true).forGetter(recipe -> recipe.showNotification)
                ).apply(instance, ShapedPrismarineRecipe::new)
        );
        public static final PacketCodec<RegistryByteBuf, ShapedPrismarineRecipe> PACKET_CODEC = PacketCodec.ofStatic(
                ShapedPrismarineRecipe.Serializer::write, ShapedPrismarineRecipe.Serializer::read
        );

        @Override
        public MapCodec<ShapedPrismarineRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, ShapedPrismarineRecipe> packetCodec() {
            return PACKET_CODEC;
        }

        private static ShapedPrismarineRecipe read(RegistryByteBuf buf) {
            String group = buf.readString();
            CraftingRecipeCategory category = buf.readEnumConstant(CraftingRecipeCategory.class);
            RawShapedPrismarineRecipe raw = RawShapedPrismarineRecipe.PACKET_CODEC.decode(buf);
            ItemStack result = ItemStack.PACKET_CODEC.decode(buf);
            boolean showNotification = buf.readBoolean();
            return new ShapedPrismarineRecipe(group, category, raw, result, showNotification);
        }

        private static void write(RegistryByteBuf buf, ShapedPrismarineRecipe recipe) {
            buf.writeString(recipe.group);
            buf.writeEnumConstant(recipe.category);
            RawShapedPrismarineRecipe.PACKET_CODEC.encode(buf, recipe.raw);
            ItemStack.PACKET_CODEC.encode(buf, recipe.result);
            buf.writeBoolean(recipe.showNotification);
        }
    }
}
