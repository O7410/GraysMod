package graysblock.graysmod.mixin;

import graysblock.graysmod.entity.effect.GraysModStatusEffects;
import graysblock.graysmod.registry.tag.GraysModBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "collidesWithStateAtPos", at = @At("HEAD"), cancellable = true)
    private void shadowCollisionChange(BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if((Entity) (Object) this instanceof PlayerEntity player) {
            if(player instanceof ServerPlayerEntity serverPlayer) {
                if(serverPlayer.hasStatusEffect(GraysModStatusEffects.SHADOW_FORM) && state.isIn(GraysModBlockTags.SHADOW_FORM_PASSES_THROUGH)) {
                    cir.setReturnValue(false);
                }
            }
        }
    }
}
