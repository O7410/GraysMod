package graysblock.graysmod.client.item;

import graysblock.graysmod.item.GraysModItems;
import graysblock.graysmod.item.MakeshiftWingsItem;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ChargedProjectilesComponent;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class GraysModModelPredicates {

    public static void init() {
        ModelPredicateProviderRegistry.register(GraysModItems.MAKESHIFT_WINGS, Identifier.ofVanilla("broken"), (stack, world, entity, seed) ->
                MakeshiftWingsItem.isUsable(stack) ? 0.0F : 1.0F);

        ModelPredicateProviderRegistry.register(Items.CROSSBOW, Identifier.ofVanilla("wind_bolt"), (stack, world, entity, seed) -> {
            ChargedProjectilesComponent chargedProjectilesComponent = stack.get(DataComponentTypes.CHARGED_PROJECTILES);
            return chargedProjectilesComponent != null && chargedProjectilesComponent.contains(GraysModItems.WIND_BOLT) ? 1.0F : 0.0F;
        });
    }
}
