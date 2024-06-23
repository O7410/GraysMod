package graysblock.graysmod;

import com.chocohead.mm.api.ClassTinkerers;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class GraysModEarlyRiser implements Runnable {
    @Override
    public void run() {
        GraysMod.LOGGER.info("Early Riser");
        ClassTinkerers.enumBuilder("net.minecraft.recipe.book.RecipeBookCategory")
                .addEnum("PRISMARINE_CRAFTING")
                .build();
        ClassTinkerers.enumBuilder("net.minecraft.client.recipebook.RecipeBookGroup", ItemStack[].class)
                .addEnum("PRISMARINE_CRAFTING_SEARCH", () -> new Object[] {new ItemStack[] {new ItemStack(Items.COMPASS)}})
                .addEnum("PRISMARINE_CRAFTING_BUILDING_BLOCKS", () -> new Object[] {new ItemStack[] {new ItemStack(Blocks.BRICKS)}})
                .addEnum("PRISMARINE_CRAFTING_REDSTONE", () -> new Object[] {new ItemStack[] {new ItemStack(Items.REDSTONE)}})
                .addEnum("PRISMARINE_CRAFTING_EQUIPMENT", () -> new Object[] {new ItemStack[] {new ItemStack(Items.IRON_AXE), new ItemStack(Items.GOLDEN_SWORD)}})
                .addEnum("PRISMARINE_CRAFTING_MISC", () -> new Object[] {new ItemStack[] {new ItemStack(Items.LAVA_BUCKET), new ItemStack(Items.APPLE)}})
                .build();

    }
}
