package graysblock.graysmod.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = {"net/minecraft/screen/GrindstoneScreenHandler$2", "net/minecraft/screen/GrindstoneScreenHandler$3"})
public class GrindstoneScreenHandlerSlotsMixin {

    @Inject(method = "canInsert", at = @At("RETURN"), cancellable = true)
    private void modifyGrindstoneSlots(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if(stack.isOf(Items.ENCHANTED_GOLDEN_APPLE)) {
            cir.setReturnValue(true);
        }
    }
}
