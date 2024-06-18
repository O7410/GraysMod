package graysblock.graysmod.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import graysblock.graysmod.item.GraysModItems;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.feature.CapeFeatureRenderer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CapeFeatureRenderer.class)
public abstract class CapeFeatureRendererMixin {

    @WrapOperation(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/network/AbstractClientPlayerEntity;FFFFFF)V",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;isPartVisible(Lnet/minecraft/entity/player/PlayerModelPart;)Z"))
    private boolean hideCape(AbstractClientPlayerEntity instance, PlayerModelPart playerModelPart, Operation<Boolean> original) {
        ItemStack itemStack = instance.getEquippedStack(EquipmentSlot.CHEST);
        return original.call(instance, playerModelPart) && !itemStack.isOf(GraysModItems.MAKESHIFT_WINGS);
    }
}
