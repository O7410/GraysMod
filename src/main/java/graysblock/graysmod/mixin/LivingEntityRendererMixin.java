package graysblock.graysmod.mixin;

import graysblock.graysmod.effect.GraysModStatusEffects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.client.render.entity.LivingEntityRenderer.getOverlay;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements FeatureRendererContext<T, M> {

    @Shadow protected M model;
//    @Shadow protected boolean isVisible(T entity) {
//        return !entity.isInvisible();
//    }
    @Shadow protected float getAnimationCounter(T entity, float tickDelta) {
        return 0.0F;
    }

    @Shadow protected abstract @Nullable RenderLayer getRenderLayer(T entity, boolean showBody, boolean translucent, boolean showOutline);

    @Shadow protected abstract boolean isVisible(T entity);

    protected LivingEntityRendererMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Inject(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target =
            "Lnet/minecraft/client/render/entity/model/EntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;III)V"))
    private void renderColorChangedModel(T livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        boolean isVisible = isVisible(livingEntity);
        boolean translucent = !isVisible && !livingEntity.isInvisibleTo(minecraftClient.player);
        boolean showOutline = minecraftClient.hasOutline(livingEntity);
        RenderLayer renderLayer = this.getRenderLayer(livingEntity, isVisible, translucent, showOutline);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(renderLayer);
        if (livingEntity.hasStatusEffect(GraysModStatusEffects.SHADOW_FORM)) {
            int q = getOverlay(livingEntity, this.getAnimationCounter(livingEntity, g));
            model.render(matrixStack, vertexConsumer, i, q, 0x3c283b);
        }
    }

}
