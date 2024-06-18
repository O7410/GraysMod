package graysblock.graysmod.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class RepulsionGelBlock extends GelBlock {
    public static final MapCodec<RepulsionGelBlock> CODEC = createCodec(RepulsionGelBlock::new);

    public MapCodec<RepulsionGelBlock> getCodec() {
        return CODEC;
    }

    public RepulsionGelBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState());
    }

    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (entity.bypassesLandingEffects()) {
            super.onLandedUpon(world, state, pos, entity, fallDistance);
        } else {
            entity.handleFallDamage(fallDistance, 0.0F, world.getDamageSources().fall());
        }
    }

    public void onEntityLand(BlockView world, Entity entity) {
        if (entity.bypassesLandingEffects() || !(entity instanceof LivingEntity) && !(entity instanceof ItemEntity)) {
            super.onEntityLand(world, entity);
        } else {
            Vec3d vec3d = entity.getVelocity();

            if(vec3d.y < 0) {
                double speed = entity instanceof LivingEntity ? 1.0D : 0.8D;
                entity.setVelocity(vec3d.x, -vec3d.y * speed, vec3d.z);
                entity.fallDistance = 0;
                if(entity instanceof ItemEntity) {
                    entity.setOnGround(false);
                }
            } else {
                super.onEntityLand(world, entity);
            }
        }
    }

    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if(!world.isClient && !entity.bypassesLandingEffects()) {
            Vec3d entityPosition = entity.getPos();
            Vec3d direction = entityPosition.subtract(pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F);

            if(direction.y < 0.9365 && direction.y >= -0.0625) {
                double velocity = entityPosition.subtract(entity.prevX, entity.prevY, entity.prevZ).length();
                Vec3d motion = entity.getVelocity();
                double absX = Math.abs(direction.x);
                double absZ = Math.abs(direction.z);
                if(absX > absZ) {
                    if(absZ < 0.495) {
                        entity.setVelocity(new Vec3d(velocity * Math.signum(direction.x), motion.y, motion.z));
                        entity.refreshPositionAndAngles(entityPosition.x, entityPosition.y, entityPosition.z, entity.getYaw(), entity.getPitch());
                        if(velocity > 0.1) {
                            world.playSound(null, pos, getSoundGroup(state).getStepSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                        }
                    }
                } else {
                    if(absZ < 0.495) {
                        entity.setVelocity(new Vec3d(motion.x, motion.y, velocity * Math.signum(direction.z)));
                        entity.refreshPositionAndAngles(entityPosition.x, entityPosition.y, entityPosition.z, entity.getYaw(), entity.getPitch());
                        if(velocity > 0.1) {
                            world.playSound(null, pos, getSoundGroup(state).getStepSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                        }
                    }
                }
            }
        }
    }
}
