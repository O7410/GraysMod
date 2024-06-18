package graysblock.graysmod.mixin;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.GrindstoneScreenHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GrindstoneScreenHandler.class)
public class GrindstoneScreenHandlerMixin {
    @Shadow @Final Inventory input;
    @Shadow @Final private Inventory result;

    @Inject(method = "updateResult", at = @At("HEAD"))
    private void addGoldenAppleResult(CallbackInfo ci) {
        ItemStack firstSlot = this.input.getStack(0);
        ItemStack secondSlot = this.input.getStack(1);

        if(firstSlot.isOf(Items.ENCHANTED_GOLDEN_APPLE) && !secondSlot.isEmpty()) return;
        if(secondSlot.isOf(Items.ENCHANTED_GOLDEN_APPLE) && !firstSlot.isEmpty()) return;

        int goldenApples = firstSlot.getCount() + secondSlot.getCount();
        if(goldenApples > 0 && goldenApples <= Items.GOLDEN_APPLE.getMaxCount()) {
            this.result.setStack(0, new ItemStack(Items.GOLDEN_APPLE).copyWithCount(goldenApples));
            ((GrindstoneScreenHandler) (Object) this).sendContentUpdates();
        }
    }
}
