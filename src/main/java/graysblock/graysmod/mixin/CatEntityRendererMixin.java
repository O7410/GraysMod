package graysblock.graysmod.mixin;

import graysblock.graysmod.GraysMod;
import net.minecraft.client.render.entity.CatEntityRenderer;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CatEntityRenderer.class)
public class CatEntityRendererMixin {

    @Unique
    private static final Identifier MAX_TEXTURE = Identifier.of(GraysMod.MOD_ID, "textures/entity/cat/max.png");

    //A really good friend of mine has a cat named "Max", so I added it to the game via this mixin.  :)
    @Inject(method = "getTexture(Lnet/minecraft/entity/passive/CatEntity;)Lnet/minecraft/util/Identifier;", at = @At("RETURN"), cancellable = true)
    public void setMaxTexture(CatEntity catEntity, CallbackInfoReturnable<Identifier> cir) {
        String name = Formatting.strip(catEntity.getName().getString());
        if("Max".equals(name)) {
            cir.setReturnValue(MAX_TEXTURE);
        }
    }
}
