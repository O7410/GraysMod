package graysblock.graysmod.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.GrindstoneScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GrindstoneScreenHandler.class)
public class GrindstoneScreenHandlerMixin {

    @Inject(method = "getOutputStack", at = @At("HEAD"), cancellable = true)
    private void addGoldenAppleResult(ItemStack firstInput, ItemStack secondInput, CallbackInfoReturnable<ItemStack> cir) {

        if (!firstInput.isOf(Items.ENCHANTED_GOLDEN_APPLE) && !secondInput.isOf(Items.ENCHANTED_GOLDEN_APPLE)) return;
        if (firstInput.isOf(Items.ENCHANTED_GOLDEN_APPLE) && !secondInput.isEmpty() && !secondInput.isOf(Items.ENCHANTED_GOLDEN_APPLE)) return;
        if (secondInput.isOf(Items.ENCHANTED_GOLDEN_APPLE) && !firstInput.isEmpty() && !firstInput.isOf(Items.ENCHANTED_GOLDEN_APPLE)) return;

        int goldenApples = firstInput.getCount() + secondInput.getCount();
        if (goldenApples > 0 && goldenApples <= Items.GOLDEN_APPLE.getMaxCount()) {
            cir.setReturnValue(new ItemStack(Items.GOLDEN_APPLE).copyWithCount(goldenApples));
        }
    }
}
