package graysblock.graysmod.client.render.entity;

import graysblock.graysmod.GraysMod;
import graysblock.graysmod.client.render.entity.model.CluckshroomEntityModel;
import graysblock.graysmod.client.render.entity.model.GraysModEntityModelLayers;
import graysblock.graysmod.entity.passive.CluckshroomEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class CluckshroomEntityRenderer extends MobEntityRenderer<CluckshroomEntity, CluckshroomEntityModel<CluckshroomEntity>> {
    private static final Identifier TEXTURE = Identifier.of(GraysMod.MOD_ID, "textures/entity/cluckshroom.png");

    public CluckshroomEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new CluckshroomEntityModel<>(context.getPart(GraysModEntityModelLayers.CLUCKSHROOM)), 0.3f);
    }

    public Identifier getTexture(CluckshroomEntity entity) {
        return TEXTURE;
    }

    protected float getAnimationProgress(CluckshroomEntity cluckshroomEntity, float f) {
        float g = MathHelper.lerp(f, cluckshroomEntity.prevFlapProgress, cluckshroomEntity.flapProgress);
        float h = MathHelper.lerp(f, cluckshroomEntity.prevMaxWingDeviation, cluckshroomEntity.maxWingDeviation);
        return (MathHelper.sin(g) + 1.0F) * h;
    }
}
