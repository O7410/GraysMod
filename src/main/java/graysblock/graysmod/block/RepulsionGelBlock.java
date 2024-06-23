package graysblock.graysmod.block;

import com.google.common.collect.Maps;
import com.mojang.serialization.MapCodec;
import graysblock.graysmod.GraysMod;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.Map;

public class RepulsionGelBlock extends GelBlock {
    public static final MapCodec<RepulsionGelBlock> CODEC = createCodec(RepulsionGelBlock::new);

    public RepulsionGelBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState());
    }

    public MapCodec<RepulsionGelBlock> getCodec() {
        return CODEC;
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (entity.bypassesLandingEffects()) {
            super.onLandedUpon(world, state, pos, entity, fallDistance);
        } else {
            entity.handleFallDamage(fallDistance, 0.0F, world.getDamageSources().fall());
        }
    }

    @Override
    public void onEntityLand(BlockView world, Entity entity) {
//        GraysMod.LOGGER.info("onEntityLand!");
        if (entity.bypassesLandingEffects() || !(entity instanceof LivingEntity) && !(entity instanceof ItemEntity)) {
            super.onEntityLand(world, entity);
        } else {
            GraysMod.LOGGER.info("onEntityLand!");
            Vec3d velocity = entity.getVelocity();

            if (velocity.y < 0) {
                double speed = entity instanceof LivingEntity ? 1.0D : 0.8D;
                entity.setVelocity(velocity.x, -velocity.y * speed, velocity.z);
                entity.fallDistance = 0;
                if (entity instanceof ItemEntity) {
                    entity.setOnGround(false);
                }
            } else {
                super.onEntityLand(world, entity);
            }
        }
    }

    private static final Box UP_BOX = new Box(0, 11/16d, 0, 1, 1, 1);
    private static final Box DOWN_BOX = new Box(0, 0, 0, 1, 5/16d, 1);
    private static final Box EAST_BOX = new Box(0, 0, 0, 5/16d, 1, 1);
    private static final Box WEST_BOX = new Box(11/16d, 0, 0, 1, 1, 1);
    private static final Box SOUTH_BOX = new Box(0, 0, 0, 1, 1, 5/16d);
    private static final Box NORTH_BOX = new Box(0, 0, 11/16d, 1, 1, 1);
    private static final Map<Direction, Box> COLLISION_SHAPES_FOR_DIRECTIONS = Util.make(Maps.newEnumMap(Direction.class), shapes -> {
        shapes.put(Direction.NORTH, SOUTH_BOX);
        shapes.put(Direction.EAST, WEST_BOX);
        shapes.put(Direction.SOUTH, NORTH_BOX);
        shapes.put(Direction.WEST, EAST_BOX);
        shapes.put(Direction.UP, UP_BOX);
        shapes.put(Direction.DOWN, DOWN_BOX);
    });

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (/*!world.isClient() || */entity.bypassesLandingEffects()) return;
        checkCollisionsAndBounce(state, pos, entity);
//        entity.refreshPositionAndAngles(entityPos.x, entityPos.y, entityPos.z, entity.getYaw(), entity.getPitch());

    }

    public static void checkCollisionsAndBounce(BlockState state, BlockPos pos, Entity entity) {
        Direction[] directions = Arrays.stream(getDirections(state))
                .filter(direction -> {
                    VoxelShape shape = getShapeForDirection(direction);
//                    Box box = COLLISION_SHAPES_FOR_DIRECTIONS.get(direction);
                    return shape.getBoundingBox().offset(pos).intersects(entity.getBoundingBox());
                }).toArray(Direction[]::new);

        if (directions.length == 0) {
            return;
        }

//        Vec3d velocity = entity.getVelocity();
        Vec3d velocity = entity.getPos().subtract(entity.prevX, entity.prevY, entity.prevZ);
        for (Direction direction : directions) {
            double componentAlongAxis = Math.abs(entity.getVelocity().getComponentAlongAxis(direction.getAxis()));
            if (componentAlongAxis > 0.01) {
                velocity = velocity.withAxis(direction.getAxis(), componentAlongAxis * direction.getDirection().getOpposite().offset() * 1.2);
            }
        }
        entity.setVelocity(velocity);
    }
}
