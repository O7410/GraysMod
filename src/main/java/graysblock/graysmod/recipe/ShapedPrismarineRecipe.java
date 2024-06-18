package graysblock.graysmod.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import graysblock.graysmod.block.GraysModBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RawShapedRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class ShapedPrismarineRecipe implements PrismarineCraftingRecipe {
    final RawShapedRecipe raw;
    final ItemStack result;
    final String group;
    final CraftingRecipeCategory category;
    final boolean showNotification;

    public ShapedPrismarineRecipe(String group, CraftingRecipeCategory category, RawShapedRecipe raw, ItemStack result, boolean showNotification) {
        this.group = group;
        this.category = category;
        this.raw = raw;
        this.result = result;
        this.showNotification = showNotification;
    }

    public ShapedPrismarineRecipe(String group, CraftingRecipeCategory category, RawShapedRecipe raw, ItemStack result) {
        this(group, category, raw, result, true);
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

    public boolean matches(CraftingRecipeInput craftingRecipeInput, World world) {
        return this.raw.matches(craftingRecipeInput);
    }

    public ItemStack craft(CraftingRecipeInput craftingRecipeInput, RegistryWrapper.WrapperLookup wrapperLookup) {
        return this.getResult(wrapperLookup).copy();
    }

    @Override
    public boolean isEmpty() {
        DefaultedList<Ingredient> defaultedList = this.getIngredients();
        return defaultedList.isEmpty()
                || defaultedList.stream().filter(ingredient -> !ingredient.isEmpty()).anyMatch(ingredient -> ingredient.getMatchingStacks().length == 0);
    }

    public static class Serializer implements RecipeSerializer<ShapedPrismarineRecipe> {
        public static final MapCodec<ShapedPrismarineRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
                                CraftingRecipeCategory.CODEC.fieldOf("category").orElse(CraftingRecipeCategory.MISC).forGetter(recipe -> recipe.category),
                                RawShapedRecipe.CODEC.forGetter(recipe -> recipe.raw),
                                ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
                                Codec.BOOL.optionalFieldOf("show_notification", Boolean.TRUE).forGetter(recipe -> recipe.showNotification)
                        )
                        .apply(instance, ShapedPrismarineRecipe::new)
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
            String string = buf.readString();
            CraftingRecipeCategory craftingRecipeCategory = buf.readEnumConstant(CraftingRecipeCategory.class);
            RawShapedRecipe rawShapedRecipe = RawShapedRecipe.PACKET_CODEC.decode(buf);
            ItemStack itemStack = ItemStack.PACKET_CODEC.decode(buf);
            boolean bl = buf.readBoolean();
            return new ShapedPrismarineRecipe(string, craftingRecipeCategory, rawShapedRecipe, itemStack, bl);
        }

        private static void write(RegistryByteBuf buf, ShapedPrismarineRecipe recipe) {
            buf.writeString(recipe.group);
            buf.writeEnumConstant(recipe.category);
            RawShapedRecipe.PACKET_CODEC.encode(buf, recipe.raw);
            ItemStack.PACKET_CODEC.encode(buf, recipe.result);
            buf.writeBoolean(recipe.showNotification);
        }
    }
}
