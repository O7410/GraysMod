package graysblock.graysmod.entity.projectile.thrown;

import graysblock.graysmod.block.GelBlock;
import graysblock.graysmod.block.GraysModBlocks;
import graysblock.graysmod.entity.GraysModEntityTypes;
import graysblock.graysmod.item.GraysModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class BallOfRepulsionGelEntity extends ThrownItemEntity {

    public BallOfRepulsionGelEntity(EntityType<? extends BallOfRepulsionGelEntity> entityType, World world) {
        super(entityType, world);
    }

    public BallOfRepulsionGelEntity(World world, PlayerEntity player) {
        super(GraysModEntityTypes.BALL_OF_REPULSION_GEL, player, world);
    }

    @Override
    protected void onCollision(HitResult result) {
        super.onCollision(result);


        if (!this.getWorld().isClient()) {
            this.discard();
            if (!(result instanceof BlockHitResult blockHitResult)) return;
            BlockPos blockPos = blockHitResult.getBlockPos().offset(blockHitResult.getSide());

            for (int xOffset = -1; xOffset <= 1; xOffset++) {
                for (int yOffset = -1; yOffset <= 1; yOffset++) {
                    for (int zOffset = -1; zOffset <= 1; zOffset++) {
                        BlockPos placePosition = blockPos.add(xOffset, yOffset, zOffset);
                        if (!this.getWorld().getBlockState(placePosition).isAir()) continue;
                        Direction[] placeableSides = GelBlock.getPlaceableSides(this.getWorld(), placePosition);
                        if (placeableSides.length == 0) continue;
                        BlockState state = GraysModBlocks.REPULSION_GEL.getDefaultState();
                        for (Direction placeableSide : placeableSides) {
                            state = state.with(GelBlock.getProperty(placeableSide), true);
                        }

                        this.getWorld().setBlockState(placePosition, state);

                    }
                }
            }
            this.getWorld().playSound(this, blockPos, SoundEvents.BLOCK_SLIME_BLOCK_PLACE, SoundCategory.BLOCKS,
                    (BlockSoundGroup.SLIME.getVolume() + 1.0f) / 2.0f,
                    BlockSoundGroup.SLIME.getPitch() * 0.8f);
        }
    }

    @Override
    protected Item getDefaultItem() {
        return GraysModItems.BALL_OF_REPULSION_GEL;
    }
}
