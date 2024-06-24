package graysblock.graysmod.screen;

import graysblock.graysmod.block.GraysModBlocks;
import graysblock.graysmod.recipe.GraysModRecipeTypes;
import graysblock.graysmod.recipe.PrismarineCraftingRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.Optional;

// currently ScreenHandler instead of AbstractRecipeScreenHandler<CraftingRecipeInput, PrismarineCraftingRecipe> because recipe category thing
//public class PrismarineWorkbenchScreenHandler extends ScreenHandler {
public class PrismarineWorkbenchScreenHandler extends AbstractRecipeScreenHandler<CraftingRecipeInput, Recipe<CraftingRecipeInput>> {
    public static final int RESULT_ID = 0;
    private static final int INPUT_START = 1;
    private static final int INPUT_END = 10;
    private static final int INVENTORY_START = 10;
    private static final int INVENTORY_END = 37;
    private static final int HOTBAR_START = 37;
    private static final int HOTBAR_END = 46;

    private final RecipeInputInventory input = new CraftingInventory(this, 3, 3);
    private final CraftingResultInventory result = new CraftingResultInventory();
    private final ScreenHandlerContext context;
    private final PlayerEntity player;
    private boolean filling;

    public PrismarineWorkbenchScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public PrismarineWorkbenchScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(GraysModScreenHandlerTypes.PRISMARINE_WORKBENCH, syncId);
        this.context = context;
        this.player = playerInventory.player;

        this.addSlot(new CraftingResultSlot(playerInventory.player, this.input, this.result, RESULT_ID, 124, 35) {
            @Override
            public void onTakeItem(PlayerEntity player, ItemStack stack) {
                this.onCrafted(stack);
                CraftingRecipeInput.Positioned positioned = PrismarineWorkbenchScreenHandler.this.input.createPositionedRecipeInput();
                CraftingRecipeInput craftingRecipeInput = positioned.input();
                int xOffset = positioned.left();
                int yOffset = positioned.top();
                DefaultedList<ItemStack> remainingStacks;
                if (player.getWorld().getRecipeManager().getFirstMatch(GraysModRecipeTypes.PRISMARINE_CRAFTING, PrismarineWorkbenchScreenHandler.this.input.createRecipeInput(), player.getWorld()).isPresent()) {
                    remainingStacks = player.getWorld().getRecipeManager().getRemainingStacks(GraysModRecipeTypes.PRISMARINE_CRAFTING, craftingRecipeInput, player.getWorld());
                } else {
                    remainingStacks = player.getWorld().getRecipeManager().getRemainingStacks(RecipeType.CRAFTING, craftingRecipeInput, player.getWorld());
                }
                for (int invY = 0; invY < craftingRecipeInput.getHeight(); ++invY) {
                    for (int invX = 0; invX < craftingRecipeInput.getWidth(); ++invX) {
                        int indexInInput = invX + xOffset + (invY + yOffset) * PrismarineWorkbenchScreenHandler.this.input.getWidth();
                        ItemStack stackInSlot = PrismarineWorkbenchScreenHandler.this.input.getStack(indexInInput);
                        ItemStack remainingStack = remainingStacks.get(invX + invY * craftingRecipeInput.getWidth());
                        if (!stackInSlot.isEmpty()) {
                            PrismarineWorkbenchScreenHandler.this.input.removeStack(indexInInput, 1);
                            stackInSlot = PrismarineWorkbenchScreenHandler.this.input.getStack(indexInInput);
                        }
                        if (remainingStack.isEmpty()) continue;
                        if (stackInSlot.isEmpty()) {
                            PrismarineWorkbenchScreenHandler.this.input.setStack(indexInInput, remainingStack);
                            continue;
                        }
                        if (ItemStack.areItemsAndComponentsEqual(stackInSlot, remainingStack)) {
                            remainingStack.increment(stackInSlot.getCount());
                            PrismarineWorkbenchScreenHandler.this.input.setStack(indexInInput, remainingStack);
                            continue;
                        }
                        if (playerInventory.insertStack(remainingStack)) continue;
                        PrismarineWorkbenchScreenHandler.this.player.dropItem(remainingStack, false);
                    }
                }
            }
        });

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                this.addSlot(new Slot(this.input, j + i * 3, 30 + j * 18, 17 + i * 18));
            }
        }

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    protected static void updateResult(ScreenHandler handler, World world, PlayerEntity player, RecipeInputInventory craftingInventory, CraftingResultInventory resultInventory) {
        if (!(player instanceof ServerPlayerEntity serverPlayerEntity)) return;

        CraftingRecipeInput craftingRecipeInput = craftingInventory.createRecipeInput();
        ItemStack resultStack = ItemStack.EMPTY;

        RecipeManager recipeManager = world.getRecipeManager();

        Optional<RecipeEntry<PrismarineCraftingRecipe>> firstPrismarineCrafting = recipeManager.getFirstMatch(GraysModRecipeTypes.PRISMARINE_CRAFTING, craftingRecipeInput, world);
        Optional<RecipeEntry<CraftingRecipe>> firstCrafting = recipeManager.getFirstMatch(RecipeType.CRAFTING, craftingRecipeInput, world);

        if (firstPrismarineCrafting.isPresent() || firstCrafting.isPresent()) {
            RecipeEntry<? extends Recipe<CraftingRecipeInput>> recipeEntry = firstPrismarineCrafting.isPresent() ? firstPrismarineCrafting.get() : firstCrafting.get();
            Recipe<CraftingRecipeInput> craftingRecipe = recipeEntry.value();
            if (resultInventory.shouldCraftRecipe(world, serverPlayerEntity, recipeEntry)) {
                ItemStack recipeOutput = craftingRecipe.craft(craftingRecipeInput, world.getRegistryManager());
                if (recipeOutput.isItemEnabled(world.getEnabledFeatures())) {
                    resultStack = recipeOutput;
                }
            }
        }

        resultInventory.setStack(RESULT_ID, resultStack);
        handler.setPreviousTrackedSlot(RESULT_ID, resultStack);
        serverPlayerEntity.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(handler.syncId, handler.nextRevision(), RESULT_ID, resultStack));
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        if (!this.filling) {
            this.context.run((world, pos) -> updateResult(this, world, this.player, this.input, this.result));
        }
    }

    public void onInputSlotFillStart() {
        this.filling = true;
    }

    public void onInputSlotFillFinish(RecipeEntry<Recipe<CraftingRecipeInput>> recipe) {
        this.filling = false;
        this.context.run((world, pos) -> updateResult(this, world, this.player, this.input, this.result));
    }

    public void populateRecipeFinder(RecipeMatcher finder) {
        this.input.provideRecipeInputs(finder);
    }

    public void clearCraftingSlots() {
        this.input.clear();
        this.result.clear();
    }

    public boolean matches(RecipeEntry<Recipe<CraftingRecipeInput>> recipe) {
        return recipe.value().matches(this.input.createRecipeInput(), this.player.getWorld());
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.context.run((world, pos) -> this.dropInventory(player, this.input));
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, GraysModBlocks.PRISMARINE_WORKBENCH);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slotIndex) {
        ItemStack originalStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);
        if (slot.hasStack()) {
            ItemStack stackInSlot = slot.getStack();
            originalStack = stackInSlot.copy();
            if (slotIndex == RESULT_ID) {
                this.context.run((world, pos) -> stackInSlot.getItem().onCraftByPlayer(stackInSlot, world, player));
                if (!this.insertItem(stackInSlot, INVENTORY_START, HOTBAR_END, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickTransfer(stackInSlot, originalStack);
            } else if (slotIndex >= INVENTORY_START && slotIndex < HOTBAR_END) {
                if (!this.insertItem(stackInSlot, INPUT_START, INPUT_END, false)) {
                    if (slotIndex < INVENTORY_END) {
                        if (!this.insertItem(stackInSlot, HOTBAR_START, HOTBAR_END, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.insertItem(stackInSlot, INVENTORY_START, INVENTORY_END, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.insertItem(stackInSlot, INVENTORY_START, HOTBAR_END, false)) {
                return ItemStack.EMPTY;
            }

            if (stackInSlot.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }

            if (stackInSlot.getCount() == originalStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTakeItem(player, stackInSlot);
            if (slotIndex == RESULT_ID) {
                player.dropItem(stackInSlot, false);
            }
        }

        return originalStack;
    }

    @Override
    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        return slot.inventory != this.result && super.canInsertIntoSlot(stack, slot);
    }

    @Override
    public int getCraftingResultSlotIndex() {
        return 0;
    }

    @Override
    public int getCraftingWidth() {
        return this.input.getWidth();
    }

    @Override
    public int getCraftingHeight() {
        return this.input.getHeight();
    }

    @Override
    public int getCraftingSlotCount() {
        return 10;
    }

    @Override
    public RecipeBookCategory getCategory() {
        return RecipeBookCategory.valueOf("PRISMARINE_CRAFTING"); // TODO: if you want the recipe book to work, you need a custom recipe book category with mixin
    }

    @Override
    public boolean canInsertIntoSlot(int index) {
        return index != this.getCraftingResultSlotIndex();
    }
}