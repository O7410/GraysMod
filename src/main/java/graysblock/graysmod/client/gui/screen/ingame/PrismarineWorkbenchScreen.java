package graysblock.graysmod.client.gui.screen.ingame;

import graysblock.graysmod.GraysMod;
import graysblock.graysmod.client.gui.screen.PrismarineWorkbenchScreenHandler;
import graysblock.graysmod.mixin.RecipeBookWidgetInvoker;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.gui.widget.ToggleButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class PrismarineWorkbenchScreen extends HandledScreen<PrismarineWorkbenchScreenHandler> implements RecipeBookProvider {
    private static final Identifier TEXTURE = Identifier.of(GraysMod.MOD_ID, "textures/gui/container/prismarine_workbench.png");

    private static final Text TOGGLE_ALL_RECIPES_TEXT = Text.literal("Showing All");
    private static final Text TOGGLE_PRISMARINE_RECIPES_TEXT = Text.literal("Showing Only Prismarine Recipes");
    private static final ButtonTextures PRISMARINE_ONLY_FILTER_BUTTON_TEXTURES = new ButtonTextures(
            Identifier.ofVanilla("recipe_book/filter_enabled"),
            Identifier.ofVanilla("recipe_book/filter_disabled"),
            Identifier.ofVanilla("recipe_book/filter_enabled_highlighted"),
            Identifier.ofVanilla("recipe_book/filter_disabled_highlighted"));
//    private static final ButtonTextures PRISMARINE_ONLY_FILTER_BUTTON_TEXTURES = new ButtonTextures(
//            Identifier.of(GraysMod.MOD_ID, "recipe_book/prismarine_only_filter_enabled"),
//            Identifier.of(GraysMod.MOD_ID, "recipe_book/prismarine_only_filter_disabled"),
//            Identifier.of(GraysMod.MOD_ID, "recipe_book/prismarine_only_filter_enabled_highlighted"),
//            Identifier.of(GraysMod.MOD_ID, "recipe_book/prismarine_only_filter_disabled_highlighted"));


    protected ToggleButtonWidget toggleOnlyPrismarineRecipesButton;
    private final RecipeBookWidget recipeBook = new RecipeBookWidget();
    private boolean narrow;

    public PrismarineWorkbenchScreen(PrismarineWorkbenchScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();

        this.toggleOnlyPrismarineRecipesButton = new ToggleButtonWidget((this.width - 147) / 2 + 24, (this.height - 166) / 2 + 137, 27, 17, false);

        this.narrow = this.width < 379;
        this.recipeBook.initialize(this.width, this.height, this.client, this.narrow, this.handler);
        this.x = this.recipeBook.findLeftEdge(this.width, this.backgroundWidth);
        this.addDrawableChild(new TexturedButtonWidget(this.x + 5, this.height / 2 - 49, 20, 18, RecipeBookWidget.BUTTON_TEXTURES, button -> {
            this.recipeBook.toggleOpen();
            this.toggleOnlyPrismarineRecipesButton.visible = this.recipeBook.isOpen();
            this.x = this.recipeBook.findLeftEdge(this.width, this.backgroundWidth);
            button.setPosition(this.x + 5, this.height / 2 - 49);
        }));

        this.updateTogglePrismarineRecipesTooltip();
        this.toggleOnlyPrismarineRecipesButton.setTextures(PRISMARINE_ONLY_FILTER_BUTTON_TEXTURES);
        this.toggleOnlyPrismarineRecipesButton.visible = this.recipeBook.isOpen();

        this.addDrawableChild(this.toggleOnlyPrismarineRecipesButton);

        this.addSelectableChild(this.recipeBook);
        this.titleX = 29;
    }

    public boolean isShowingOnlyPrismarineRecipes() {
        return this.toggleOnlyPrismarineRecipesButton.isToggled();
    }

    private void updateTogglePrismarineRecipesTooltip() {
        this.toggleOnlyPrismarineRecipesButton.setTooltip(this.toggleOnlyPrismarineRecipesButton.isToggled() ? Tooltip.of(TOGGLE_PRISMARINE_RECIPES_TEXT) : Tooltip.of(TOGGLE_ALL_RECIPES_TEXT));
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int i = this.x;
        int j = (this.height - this.backgroundHeight) / 2;
        context.drawTexture(TEXTURE, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }


    @Override
    public void handledScreenTick() {
        super.handledScreenTick();
        this.recipeBook.update();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (this.recipeBook.isOpen() && this.narrow) {
            this.renderBackground(context, mouseX, mouseY, delta);
            this.recipeBook.render(context, mouseX, mouseY, delta);
            this.toggleOnlyPrismarineRecipesButton.render(context, mouseX, mouseY, delta);
        } else {
            super.render(context, mouseX, mouseY, delta);
            this.recipeBook.render(context, mouseX, mouseY, delta);
            this.toggleOnlyPrismarineRecipesButton.render(context, mouseX, mouseY, delta);
            this.recipeBook.drawGhostSlots(context, this.x, this.y, true, delta);
        }
        this.drawMouseoverTooltip(context, mouseX, mouseY);
        this.recipeBook.drawTooltip(context, this.x, this.y, mouseX, mouseY);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.recipeBook.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        if (this.recipeBook.charTyped(chr, modifiers)) {
            return true;
        }
        return super.charTyped(chr, modifiers);
    }

    @Override
    protected boolean isPointWithinBounds(int x, int y, int width, int height, double pointX, double pointY) {
        return (!this.narrow || !this.recipeBook.isOpen()) && super.isPointWithinBounds(x, y, width, height, pointX, pointY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.recipeBook.mouseClicked(mouseX, mouseY, button)) {
            this.setFocused(this.recipeBook);
            return true;
        }
        if (this.toggleOnlyPrismarineRecipesButton.mouseClicked(mouseX, mouseY, button)) {
            this.toggleOnlyPrismarineRecipesButton.setToggled(!this.toggleOnlyPrismarineRecipesButton.isToggled());
            this.updateTogglePrismarineRecipesTooltip();
            (((RecipeBookWidgetInvoker) this.recipeBook)).invokeRefreshResults(false);
            this.setFocused(this.recipeBook);
            return true;
        }
        if (this.narrow && this.recipeBook.isOpen()) {
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected boolean isClickOutsideBounds(double mouseX, double mouseY, int left, int top, int button) {
        boolean isOutsideBounds = mouseX < (double) left || mouseY < (double) top || mouseX >= (double) (left + this.backgroundWidth) || mouseY >= (double) (top + this.backgroundHeight);
        return this.recipeBook.isClickOutsideBounds(mouseX, mouseY, this.x, this.y, this.backgroundWidth, this.backgroundHeight, button) && isOutsideBounds;
    }

    @Override
    protected void onMouseClick(Slot slot, int slotId, int button, SlotActionType actionType) {
        super.onMouseClick(slot, slotId, button, actionType);
        this.recipeBook.slotClicked(slot);
    }

    @Override
    public void refreshRecipeBook() {
        this.recipeBook.refresh();
    }

    @Override
    public RecipeBookWidget getRecipeBookWidget() {
        return this.recipeBook;
    }
}
