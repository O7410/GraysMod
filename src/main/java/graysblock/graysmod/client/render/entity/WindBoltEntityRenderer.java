package graysblock.graysmod.client.render.entity;

import graysblock.graysmod.GraysMod;
import graysblock.graysmod.entity.custom.WindBoltEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class WindBoltEntityRenderer extends ProjectileEntityRenderer<WindBoltEntity> {
    public static final Identifier TEXTURE = Identifier.of(GraysMod.MOD_ID, "textures/entity/projectiles/wind_bolt.png");

    public WindBoltEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    public Identifier getTexture(WindBoltEntity windBoltEntity) {
        return TEXTURE;
    }
}
