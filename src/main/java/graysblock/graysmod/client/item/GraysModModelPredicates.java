package graysblock.graysmod.client.item;

import graysblock.graysmod.item.GraysModItems;
import graysblock.graysmod.item.MakeshiftWingsItem;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

public class GraysModModelPredicates {

    public static void init() {
        ModelPredicateProviderRegistry.register(GraysModItems.MAKESHIFT_WINGS, Identifier.ofVanilla("broken"), (stack, world, entity, seed) ->
                MakeshiftWingsItem.isUsable(stack) ? 0.0F : 1.0F);
    }
}
