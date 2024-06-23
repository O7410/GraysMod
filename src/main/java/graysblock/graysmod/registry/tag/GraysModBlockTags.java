package graysblock.graysmod.registry.tag;

import graysblock.graysmod.GraysMod;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class GraysModBlockTags {

    public static final TagKey<Block> NEEDS_PRISMARINE_TOOL = of("needs_prismarine_tool");
    public static final TagKey<Block> INCORRECT_FOR_PRISMARINE_TOOL = of("incorrect_for_prismarine_tool");

    private static TagKey<Block> of(String id) {
        return TagKey.of(RegistryKeys.BLOCK, Identifier.of(GraysMod.MOD_ID, id));
    }
}
