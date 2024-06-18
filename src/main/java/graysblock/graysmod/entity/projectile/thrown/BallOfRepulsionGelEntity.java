package graysblock.graysmod.entity.projectile.thrown;

import graysblock.graysmod.block.GraysModBlocks;
import graysblock.graysmod.entity.GraysModEntityTypes;
import graysblock.graysmod.item.GraysModItems;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BallOfRepulsionGelEntity extends ThrownItemEntity {

    public BallOfRepulsionGelEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public BallOfRepulsionGelEntity(World world, PlayerEntity player) {
        super(GraysModEntityTypes.BALL_OF_REPULSION_GEL, player, world);
    }

    @Override
    protected void onCollision(HitResult result) {
        super.onCollision(result);

        if(!this.getWorld().isClient) {
            BlockPos hitPosition = new BlockPos((int) result.getPos().x, (int) result.getPos().y, (int) result.getPos().z);
            for(int x = -1; x <= 1; x++) {
                for(int y = -1; y <= 1; y++) {
                    BlockPos placePosition = hitPosition.add(x, 0, y);
                    if(getWorld().getBlockState(placePosition).isAir()) {
                        getWorld().setBlockState(placePosition, GraysModBlocks.REPULSION_GEL.getDefaultState());
                    }
                }
            }

            this.discard();
        }
    }

    @Override
    protected Item getDefaultItem() {
        return GraysModItems.BALL_OF_REPULSION_GEL;
    }
}
