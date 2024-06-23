package graysblock.graysmod.data;

import graysblock.graysmod.block.GraysModBlocks;
import graysblock.graysmod.registry.tag.GraysModBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class GraysModBlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public GraysModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    public void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(GraysModBlockTags.NEEDS_PRISMARINE_TOOL)
                .add(Blocks.DIAMOND_ORE)
                .add(Blocks.DEEPSLATE_DIAMOND_ORE)
                .add(Blocks.DIAMOND_BLOCK);

        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(GraysModBlocks.KILN);

        getOrCreateTagBuilder(GraysModBlockTags.INCORRECT_FOR_PRISMARINE_TOOL)
                .forceAddTag(BlockTags.NEEDS_DIAMOND_TOOL);
    }
}
