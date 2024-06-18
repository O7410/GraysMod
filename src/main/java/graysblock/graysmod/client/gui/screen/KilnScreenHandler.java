package graysblock.graysmod.client.gui.screen;

import graysblock.graysmod.recipe.GraysModRecipeTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.*;

public class KilnScreenHandler extends AbstractFurnaceScreenHandler {

    public KilnScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(GraysModScreenHandlerTypes.KILN_SCREEN_HANDLER, GraysModRecipeTypes.FIRING, RecipeBookCategory.FURNACE, syncId, playerInventory);
    }

    public KilnScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(GraysModScreenHandlerTypes.KILN_SCREEN_HANDLER, GraysModRecipeTypes.FIRING, RecipeBookCategory.FURNACE, syncId, playerInventory, inventory, propertyDelegate);
    }
}
