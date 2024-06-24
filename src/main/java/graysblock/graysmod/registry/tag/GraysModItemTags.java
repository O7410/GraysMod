package graysblock.graysmod.registry.tag;

import graysblock.graysmod.GraysMod;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class GraysModItemTags {

    public static final TagKey<Item> VIALS = of("vials");

    private static TagKey<Item> of(String id) {
        return TagKey.of(RegistryKeys.ITEM, Identifier.of(GraysMod.MOD_ID, id));
    }
}
