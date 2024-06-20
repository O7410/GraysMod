package graysblock.graysmod.data;

import graysblock.graysmod.entity.GraysModEntityTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.EntityTypeTags;

import java.util.concurrent.CompletableFuture;

public class GraysModEntityTypeTagGenerator extends FabricTagProvider.EntityTypeTagProvider {

    public GraysModEntityTypeTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    public void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(EntityTypeTags.FALL_DAMAGE_IMMUNE)
                .add(GraysModEntityTypes.CLUCKSHROOM);

        getOrCreateTagBuilder(EntityTypeTags.ZOMBIES)
                .add(GraysModEntityTypes.BOULDERING_ZOMBIE);

        getOrCreateTagBuilder(EntityTypeTags.ARROWS)
                .add(GraysModEntityTypes.WIND_BOLT);
    }
}
