package graysblock.graysmod.block;

import graysblock.graysmod.GraysMod;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import static net.minecraft.block.Blocks.createLightLevelFromLitBlockState;

public class GraysModBlocks {

    public static final Block KILN = registerBlock("kiln", new KilnBlock(AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY).instrument(NoteBlockInstrument.BASS).requiresTool().strength(3.5F).luminance(createLightLevelFromLitBlockState(13))));
    public static final Block PRISMARINE_WORKBENCH = registerBlock("prismarine_workbench", new PrismarineWorkbenchBlock(AbstractBlock.Settings.create().mapColor(MapColor.DIAMOND_BLUE).instrument(NoteBlockInstrument.BASEDRUM).strength(1.5F, 6.0F)));
    public static final Block REPULSION_GEL = registerBlock("repulsion_gel", new RepulsionGelBlock(AbstractBlock.Settings.create().mapColor(MapColor.DIAMOND_BLUE).nonOpaque().replaceable().dropsNothing().strength(0.2F).sounds(BlockSoundGroup.SLIME).pistonBehavior(PistonBehavior.DESTROY)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(GraysMod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, Identifier.of(GraysMod.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModdedBlocks() {
        GraysMod.LOGGER.info("Registering GraysMod Blocks...");
    }
}
