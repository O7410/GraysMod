package graysblock.graysmod.data;

import graysblock.graysmod.data.client.GraysModModelGenerator;
import graysblock.graysmod.data.server.advancement.GraysModAdvancementGenerator;
import graysblock.graysmod.data.server.advancement.GraysModAdvancementProviders;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Util;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

public class GraysModDataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture = CompletableFuture.supplyAsync(
				BuiltinRegistries::createWrapperLookup, Util.getMainWorkerExecutor()
		);

		pack.addProvider(GraysModModelGenerator::new);
		pack.addProvider(GraysModItemTagGenerator::new);
		pack.addProvider(GraysModBlockTagGenerator::new);
		pack.addProvider(GraysModEntityTypeTagGenerator::new);
		pack.addProvider(GraysModLangGenerator::new);
		pack.addProvider(GraysModRecipeGenerator::new);
		pack.addProvider(toFactory(GraysModAdvancementProviders::createGraysModProvider, completableFuture));
	}

	private static <T extends DataProvider> DataProvider.Factory<T> toFactory(
			BiFunction<DataOutput, CompletableFuture<RegistryWrapper.WrapperLookup>, T> baseFactory,
			CompletableFuture<RegistryWrapper.WrapperLookup> registryLookupFuture
	) {
		return output -> (T)baseFactory.apply(output, registryLookupFuture);
	}
}
