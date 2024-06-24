package graysblock.graysmod.item.vials;

import graysblock.graysmod.sound.GraysModSoundEvents;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class VialOfRecollectionItem extends Item {

    public VialOfRecollectionItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof ServerPlayerEntity serverPlayer) {
            Criteria.CONSUME_ITEM.trigger(serverPlayer, stack);

            BlockPos spawnPoint = serverPlayer.getSpawnPointPosition();
            if (spawnPoint == null) {
                return stack;
            }

            serverPlayer.fallDistance = 0;
            serverPlayer.teleport(
                    ((ServerWorld) world).getServer().getWorld(serverPlayer.getSpawnPointDimension()),
                    spawnPoint.getX(), spawnPoint.getY(), spawnPoint.getZ(), serverPlayer.getYaw(), serverPlayer.getPitch()
            );

            serverPlayer.incrementStat(Stats.USED.getOrCreateStat(this));
            stack.decrementUnlessCreative(1, user);
        }

        if (!world.isClient) {
            world.playSound(null, user.getBlockPos(), GraysModSoundEvents.ITEM_VIAL_DISPOSE, user.getSoundCategory(), 1.0F, 1.0F);
        }

        return stack;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 32;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }
}
