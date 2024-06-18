package graysblock.graysmod.data.server.advancement;

import net.minecraft.data.DataOutput;
import net.minecraft.data.server.advancement.AdvancementProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GraysModAdvancementProviders {
    public static AdvancementProvider createGraysModProvider(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookupFuture) {
        return new AdvancementProvider(
                output,
                registryLookupFuture,
                List.of(
                        new GraysModAdvancementGenerator()
                ));
    }
}
