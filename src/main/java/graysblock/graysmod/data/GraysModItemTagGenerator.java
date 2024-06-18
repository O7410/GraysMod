package graysblock.graysmod.data;

import graysblock.graysmod.item.GraysModItems;
import graysblock.graysmod.registry.tag.GraysModBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class GraysModItemTagGenerator extends FabricTagProvider.ItemTagProvider {

    public GraysModItemTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ItemTags.CHEST_ARMOR)
                .add(GraysModItems.TURTLE_SHELL);
        getOrCreateTagBuilder(ItemTags.LEG_ARMOR)
                .add(GraysModItems.TURTLE_TROUSERS);
        getOrCreateTagBuilder(ItemTags.FOOT_ARMOR)
                .add(GraysModItems.TURTLE_SHOES)
                .add(GraysModItems.STRIDER_BOOTS);

        getOrCreateTagBuilder(ItemTags.SWORDS)
                .add(GraysModItems.PIGLIN_WAR_AXE)
                .add(GraysModItems.PRISMARINE_SWORD);

        getOrCreateTagBuilder(ItemTags.PICKAXES)
                .add(GraysModItems.PRISMARINE_PICKAXE);

        getOrCreateTagBuilder(ItemTags.AXES)
                .add(GraysModItems.PRISMARINE_AXE);

        getOrCreateTagBuilder(ItemTags.SHOVELS)
                .add(GraysModItems.PRISMARINE_SHOVEL);

        getOrCreateTagBuilder(ItemTags.HOES)
                .add(GraysModItems.PRISMARINE_HOE);
    }
}
