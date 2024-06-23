package graysblock.graysmod.item;

import graysblock.graysmod.GraysMod;
import graysblock.graysmod.data.server.advancement.CustomAdvancementGranters;
import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class MakeshiftWingsItem extends ElytraItem implements FabricElytraItem {

    public MakeshiftWingsItem(Item.Settings settings) {
        super(settings);
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.isOf(Items.HONEYCOMB);
    }

    @Override
    public boolean useCustomElytra(LivingEntity entity, ItemStack chestStack, boolean tickElytra) {
        if (ElytraItem.isUsable(chestStack)) {
            if (tickElytra) {
                doVanillaElytraTick(entity, chestStack);

                if (entity instanceof ServerPlayerEntity serverPlayerEntity) {
                    CustomAdvancementGranters.grantAdvancementFromId(serverPlayerEntity, Identifier.of(GraysMod.MOD_ID, "graysmod/icarus"));
                }
            }

            return true;
        }

        return false;
    }
}
