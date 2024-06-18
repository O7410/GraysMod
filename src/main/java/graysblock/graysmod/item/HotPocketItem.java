package graysblock.graysmod.item;

import graysblock.graysmod.data.server.advancement.CustomAdvancementGranters;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class HotPocketItem extends Item {

    public HotPocketItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if(user.getRandom().nextFloat() < 0.05F) {
            if(!user.isFireImmune()) {
                user.setOnFireFor(4.0F);
                CustomAdvancementGranters.grantIrresistiblyHotAdvancement((ServerPlayerEntity) user);
            }
        }

        return super.finishUsing(stack, world, user);
    }
}
