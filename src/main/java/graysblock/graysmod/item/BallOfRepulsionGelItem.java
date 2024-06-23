package graysblock.graysmod.item;

import graysblock.graysmod.entity.projectile.thrown.BallOfRepulsionGelEntity;
import graysblock.graysmod.sound.GraysModSoundEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BallOfRepulsionGelItem extends Item {

    public BallOfRepulsionGelItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(
                null,
                user.getX(),
                user.getY(),
                user.getZ(),
                GraysModSoundEvents.ENTITY_BALL_OF_GEL_THROW,
                SoundCategory.PLAYERS,
                0.5F,
                0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F)
        );

        if(!world.isClient) {
            BallOfRepulsionGelEntity repulsionGelEntity = new BallOfRepulsionGelEntity(world, user);
            repulsionGelEntity.setItem(itemStack);
            repulsionGelEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
            world.spawnEntity(repulsionGelEntity);

            if(!user.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}
