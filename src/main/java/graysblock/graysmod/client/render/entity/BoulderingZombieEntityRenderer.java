package graysblock.graysmod.client.render.entity;

import graysblock.graysmod.GraysMod;
import graysblock.graysmod.client.render.entity.model.GraysModEntityModelLayers;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ZombieEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;

public class BoulderingZombieEntityRenderer extends ZombieEntityRenderer {
    private static final Identifier TEXTURE = Identifier.of(GraysMod.MOD_ID, "textures/entity/zombie/bouldering_zombie.png");

    public BoulderingZombieEntityRenderer(EntityRendererFactory.Context context) {
        super(context, GraysModEntityModelLayers.BOULDERING_ZOMBIE, EntityModelLayers.ZOMBIE_INNER_ARMOR, EntityModelLayers.ZOMBIE_OUTER_ARMOR);
    }

    public Identifier getTexture(ZombieEntity zombieEntity) {
        return TEXTURE;
    }
}
