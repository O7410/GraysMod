package graysblock.graysmod.item;

import graysblock.graysmod.GraysMod;
import graysblock.graysmod.block.GraysModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.*;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class GraysModItemGroups {

    public static final RegistryKey<ItemGroup> GRAYSMOD = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(GraysMod.MOD_ID, "graysmod"));

    public static void registerModdedItemGroups() {
        Registry.register(Registries.ITEM_GROUP, GRAYSMOD, FabricItemGroup.builder().displayName(Text.translatable("itemGroup.graysmod"))
                .icon(() -> new ItemStack(GraysModItems.STAFF_OF_REGROWTH)).entries((displayContext, entries) -> {
                    entries.add(GraysModItems.HOT_POCKET);
                    entries.add(GraysModItems.TURTLE_SHELL);
                    entries.add(GraysModItems.TURTLE_TROUSERS);
                    entries.add(GraysModItems.TURTLE_SHOES);
                    entries.add(GraysModItems.PRISMARINE_SWORD);
                    entries.add(GraysModItems.PRISMARINE_PICKAXE);
                    entries.add(GraysModItems.PRISMARINE_AXE);
                    entries.add(GraysModItems.PRISMARINE_SHOVEL);
                    entries.add(GraysModItems.PRISMARINE_HOE);
                    entries.add(GraysModItems.PIGLIN_WAR_AXE);
                    entries.add(GraysModItems.STRIDER_SCALE);
                    entries.add(GraysModItems.STRIDER_BOOTS);
                    entries.add(GraysModItems.STAFF_OF_REGROWTH);
                    entries.add(GraysModItems.WIND_BOLT);
                    entries.add(GraysModItems.BOULDERING_ZOMBIE_SPAWN_EGG);
                    entries.add(GraysModItems.CLUCKSHROOM_SPAWN_EGG);
                    entries.add(GraysModBlocks.KILN);
                    entries.add(GraysModBlocks.PRISMARINE_WORKBENCH);
                    entries.add(GraysModItems.MAKESHIFT_WINGS);
                    entries.add(GraysModItems.BALL_OF_REPULSION_GEL);
                    entries.add(GraysModItems.ECHO_VIAL);
                    entries.add(GraysModItems.VIAL_OF_RECOLLECTION);
                }).build());

        GraysMod.LOGGER.info("Registering GraysMod Item Groups...");
    }
}
