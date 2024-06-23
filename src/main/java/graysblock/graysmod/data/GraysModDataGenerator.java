package graysblock.graysmod.data;

import graysblock.graysmod.data.client.GraysModModelGenerator;
import graysblock.graysmod.data.server.advancement.GraysModAdvancementProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class GraysModDataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(GraysModModelGenerator::new);
		pack.addProvider(GraysModItemTagProvider::new);
		pack.addProvider(GraysModBlockTagProvider::new);
		pack.addProvider(GraysModEntityTypeTagProvider::new);
		pack.addProvider(GraysModLanguageProvider::new);
		pack.addProvider(GraysModRecipeProvider::new);
		pack.addProvider(GraysModAdvancementProvider::new);
	}
}
