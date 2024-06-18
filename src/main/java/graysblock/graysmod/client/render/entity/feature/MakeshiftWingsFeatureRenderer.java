package graysblock.graysmod.client.render.entity.feature;

import graysblock.graysmod.GraysMod;
import graysblock.graysmod.client.render.entity.model.GraysModEntityModelLayers;
import graysblock.graysmod.client.render.entity.model.MakeshiftWingsEntityModel;
import graysblock.graysmod.item.GraysModItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.ElytraEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MakeshiftWingsFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    private static final Identifier WINGS_TEXTURE = Identifier.of(GraysMod.MOD_ID, "textures/entity/makeshift_wings.png");
    private final MakeshiftWingsEntityModel<T> makeshift_wings;

    public MakeshiftWingsFeatureRenderer(FeatureRendererContext<T, M> context, EntityModelLoader loader) {
        super(context);
        this.makeshift_wings = new MakeshiftWingsEntityModel<>(loader.getModelPart(GraysModEntityModelLayers.MAKESHIFT_WINGS));
    }

    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
        ItemStack itemStack = livingEntity.getEquippedStack(EquipmentSlot.CHEST);
        if(itemStack.isOf(GraysModItems.MAKESHIFT_WINGS)) {
            matrixStack.push();
            matrixStack.translate(0.0F, 0.0F, 0.125F);
            this.getContextModel().copyStateTo(this.makeshift_wings);
            this.makeshift_wings.setAngles(livingEntity, f, g, j, k ,l);
            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(
                    vertexConsumerProvider, RenderLayer.getArmorCutoutNoCull(WINGS_TEXTURE), itemStack.hasGlint()
            );
            this.makeshift_wings.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV);
            matrixStack.pop();
        }
    }
}
