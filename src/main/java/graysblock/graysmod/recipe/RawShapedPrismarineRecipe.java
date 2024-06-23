package graysblock.graysmod.recipe;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.chars.CharArraySet;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.util.Util;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.dynamic.Codecs;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class RawShapedPrismarineRecipe {
    private static final int MAX_WIDTH_AND_HEIGHT = 3;
    public static final MapCodec<RawShapedPrismarineRecipe> CODEC = RawShapedPrismarineRecipe.Data.CODEC.flatXmap(
            RawShapedPrismarineRecipe::fromData, recipe -> recipe.data.map(DataResult::success).orElseGet(() -> DataResult.error(() -> "Cannot encode unpacked recipe")));
    public static final PacketCodec<RegistryByteBuf, RawShapedPrismarineRecipe> PACKET_CODEC = PacketCodec.of(RawShapedPrismarineRecipe::writeToBuf, RawShapedPrismarineRecipe::readFromBuf);
    private final int width;
    private final int height;
    private final DefaultedList<Ingredient> ingredients;
    private final Optional<Data> data;
    private final int ingredientCount;
    private final boolean symmetrical;

    public RawShapedPrismarineRecipe(int width, int height, DefaultedList<Ingredient> ingredients, Optional<RawShapedPrismarineRecipe.Data> data) {
        this.width = width;
        this.height = height;
        this.ingredients = ingredients;
        this.data = data;
        int nonEmptyIngredients = 0;
        for (Ingredient ingredient : ingredients) {
            if (ingredient.isEmpty()) continue;
            ++nonEmptyIngredients;
        }
        this.ingredientCount = nonEmptyIngredients;
        this.symmetrical = Util.isSymmetrical(width, height, ingredients);
    }

    public static RawShapedPrismarineRecipe create(Map<Character, Ingredient> key, String ... pattern) {
        return RawShapedPrismarineRecipe.create(key, List.of(pattern));
    }

    public static RawShapedPrismarineRecipe create(Map<Character, Ingredient> key, List<String> pattern) {
        RawShapedPrismarineRecipe.Data data = new RawShapedPrismarineRecipe.Data(key, pattern);
        return RawShapedPrismarineRecipe.fromData(data).getOrThrow();
    }

    private static DataResult<RawShapedPrismarineRecipe> fromData(RawShapedPrismarineRecipe.Data data) {
        String[] pattern = RawShapedPrismarineRecipe.removePadding(data.pattern);
        int width = pattern[0].length();
        int height = pattern.length;
        DefaultedList<Ingredient> ingredients = DefaultedList.ofSize(width * height, Ingredient.EMPTY);
        CharArraySet charSet = new CharArraySet(data.key.keySet());
        for (int rowIndex = 0; rowIndex < pattern.length; ++rowIndex) {
            String row = pattern[rowIndex];
            for (int columnIndex = 0; columnIndex < row.length(); ++columnIndex) {
                char character = row.charAt(columnIndex);
                Ingredient ingredient = character == ' ' ? Ingredient.EMPTY : data.key.get(character);
                if (ingredient == null) {
                    return DataResult.error(() -> "Pattern references symbol '" + character + "' but it's not defined in the key");
                }
                charSet.remove(character);
                ingredients.set(columnIndex + width * rowIndex, ingredient);
            }
        }
        if (!charSet.isEmpty()) {
            return DataResult.error(() -> "Key defines symbols that aren't used in pattern: " + charSet);
        }
        return DataResult.success(new RawShapedPrismarineRecipe(width, height, ingredients, Optional.of(data)));
    }

    @VisibleForTesting
    static String[] removePadding(List<String> pattern) {
        int firstSymbolIndexInAllPattern = Integer.MAX_VALUE;
        int lastSymbolIndexInAllPattern = 0;
        int emptyLinesFromTheTop = 0;
        int emptyLinesFromTheBottom = 0;
        for (int rowIndex = 0; rowIndex < pattern.size(); ++rowIndex) {
            String row = pattern.get(rowIndex);
            firstSymbolIndexInAllPattern = Math.min(firstSymbolIndexInAllPattern, findFirstSymbol(row));
            int lastSymbolIndexInThisRow = findLastSymbol(row);
            lastSymbolIndexInAllPattern = Math.max(lastSymbolIndexInAllPattern, lastSymbolIndexInThisRow); // make sure lastSymbolIndexInAllPattern doesn't go down
            if (lastSymbolIndexInThisRow < 0) { // line is empty
                if (emptyLinesFromTheTop == rowIndex) { // didn't have a non-empty line yet
                    ++emptyLinesFromTheTop;
                }
                ++emptyLinesFromTheBottom;
                continue;
            }
            emptyLinesFromTheBottom = 0;
        }
        if (pattern.size() == emptyLinesFromTheBottom) {
            return new String[0];
        }
        String[] newPattern = new String[pattern.size() - emptyLinesFromTheBottom - emptyLinesFromTheTop];
        for (int newRowIndex = 0; newRowIndex < newPattern.length; ++newRowIndex) {
            newPattern[newRowIndex] = pattern.get(newRowIndex + emptyLinesFromTheTop).substring(firstSymbolIndexInAllPattern, lastSymbolIndexInAllPattern + 1);
        }
        return newPattern;
    }

    private static int findFirstSymbol(String line) {
        int i = 0;
        while (i < line.length() && line.charAt(i) == ' ') {
            ++i;
        }
        return i;
    }

    private static int findLastSymbol(String line) {
        int i = line.length() - 1;
        while (i >= 0 && line.charAt(i) == ' ') {
            --i;
        }
        return i;
    }

    public boolean matches(CraftingRecipeInput input) {
        if (input.getStackCount() != this.ingredientCount) {
            return false;
        }
        if (input.getWidth() == this.width && input.getHeight() == this.height) {
            if (!this.symmetrical && this.matches(input, true)) {
                return true;
            }
            if (this.matches(input, false)) {
                return true;
            }
        }
        return false;
    }

    private boolean matches(CraftingRecipeInput input, boolean mirrored) {
        for (int i = 0; i < this.height; ++i) {
            for (int j = 0; j < this.width; ++j) {
                Ingredient ingredient = mirrored ? this.ingredients.get(this.width - j - 1 + i * this.width) : this.ingredients.get(j + i * this.width);
                if (ingredient.test(input.getStackInSlot(j, i))) continue;
                return false;
            }
        }
        return true;
    }

    private void writeToBuf(RegistryByteBuf buf) {
        buf.writeVarInt(this.width);
        buf.writeVarInt(this.height);
        for (Ingredient ingredient : this.ingredients) {
            Ingredient.PACKET_CODEC.encode(buf, ingredient);
        }
    }

    private static RawShapedPrismarineRecipe readFromBuf(RegistryByteBuf buf) {
        int width = buf.readVarInt();
        int height = buf.readVarInt();
        DefaultedList<Ingredient> ingredients = DefaultedList.ofSize(width * height, Ingredient.EMPTY);
        ingredients.replaceAll(ingredient -> Ingredient.PACKET_CODEC.decode(buf));
        return new RawShapedPrismarineRecipe(width, height, ingredients, Optional.empty());
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public DefaultedList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public record Data(Map<Character, Ingredient> key, List<String> pattern) {
        private static final Codec<List<String>> PATTERN_CODEC = Codec.STRING.listOf().comapFlatMap(pattern -> {
            if (pattern.size() > MAX_WIDTH_AND_HEIGHT) {
                return DataResult.error(() -> "Invalid pattern: too many rows, 3 is maximum");
            }
            if (pattern.isEmpty()) {
                return DataResult.error(() -> "Invalid pattern: empty pattern not allowed");
            }
            int i = pattern.getFirst().length();
            for (String string : pattern) {
                if (string.length() > 3) {
                    return DataResult.error(() -> "Invalid pattern: too many columns, 3 is maximum");
                }
                if (i == string.length()) continue;
                return DataResult.error(() -> "Invalid pattern: each row must be the same width");
            }
            return DataResult.success(pattern);
        }, Function.identity());
        private static final Codec<Character> KEY_ENTRY_CODEC = Codec.STRING.comapFlatMap(keyEntry -> {
            if (keyEntry.length() != 1) {
                return DataResult.error(() -> "Invalid key entry: '" + keyEntry + "' is an invalid symbol (must be 1 character only).");
            }
            if (" ".equals(keyEntry)) {
                return DataResult.error(() -> "Invalid key entry: ' ' is a reserved symbol.");
            }
            return DataResult.success(keyEntry.charAt(0));
        }, String::valueOf);
        public static final MapCodec<RawShapedPrismarineRecipe.Data> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codecs.strictUnboundedMap(KEY_ENTRY_CODEC, Ingredient.DISALLOW_EMPTY_CODEC).fieldOf("key").forGetter(data -> data.key),
                PATTERN_CODEC.fieldOf("pattern").forGetter(data -> data.pattern)
        ).apply(instance, RawShapedPrismarineRecipe.Data::new));
    }
}
