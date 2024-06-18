package graysblock.graysmod.block.entity;

import graysblock.graysmod.GraysMod;
import graysblock.graysmod.block.GraysModBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class GraysModBlockEntityTypes {

    public static final BlockEntityType<KilnBlockEntity> KILN_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(GraysMod.MOD_ID, "kiln_block_entity"),
            BlockEntityType.Builder.create(KilnBlockEntity::new, GraysModBlocks.KILN).build());


    public static void registerModdedBlockEntities() {
        GraysMod.LOGGER.info("Registering GraysMod Block Entities...");
    }
}
