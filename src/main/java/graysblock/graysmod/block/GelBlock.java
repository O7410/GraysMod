package graysblock.graysmod.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public abstract class GelBlock extends Block {
    protected static final Direction[] DIRECTIONS = Direction.values();
    private static final VoxelShape UP_SHAPE = Block.createCuboidShape(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape DOWN_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
    private static final VoxelShape EAST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
    private static final VoxelShape WEST_SHAPE = Block.createCuboidShape(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
    private static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
    private static final Map<Direction, BooleanProperty> FACING_PROPERTIES = ConnectingBlock.FACING_PROPERTIES;
    private static final Map<Direction, VoxelShape> SHAPES_FOR_DIRECTIONS = Util.make(Maps.newEnumMap(Direction.class), shapes -> {
        shapes.put(Direction.NORTH, SOUTH_SHAPE);
        shapes.put(Direction.EAST, WEST_SHAPE);
        shapes.put(Direction.SOUTH, NORTH_SHAPE);
        shapes.put(Direction.WEST, EAST_SHAPE);
        shapes.put(Direction.UP, UP_SHAPE);
        shapes.put(Direction.DOWN, DOWN_SHAPE);
    });

    private final ImmutableMap<BlockState, VoxelShape> shapesByState;
    private final boolean hasAllHorizontalDirections;
    private final boolean canMirrorX;
    private final boolean canMirrorZ;

    public GelBlock(Settings settings) {
        super(settings);
        this.setDefaultState(withAllDirections(this.getStateManager().getDefaultState(), false));
        this.shapesByState = this.getShapesForStates(GelBlock::getShapeForState);
        this.hasAllHorizontalDirections = Direction.Type.HORIZONTAL
                .stream()
                .allMatch(this::canHaveDirection);
        this.canMirrorX = Direction.Type.HORIZONTAL
                .stream()
                .filter(Direction.Axis.X)
                .filter(this::canHaveDirection)
                .count() % 2L == 0L;
        this.canMirrorZ = Direction.Type.HORIZONTAL
                .stream()
                .filter(Direction.Axis.Z)
                .filter(this::canHaveDirection)
                .count() % 2L == 0L;
    }

    public static Direction[] getPlaceableSides(BlockView blockView, BlockPos blockPos) {
        return Arrays.stream(DIRECTIONS)
                .filter(direction -> canPlaceOnSide(blockView, blockPos, direction))
                .toArray(Direction[]::new);
    }

    public static Direction[] getDirections(BlockState state) {
        return Arrays.stream(DIRECTIONS)
                .filter(direction -> state.get(FACING_PROPERTIES.get(direction)))
                .toArray(Direction[]::new);
    }

    private static boolean canPlaceOnSide(BlockView blockView, BlockPos blockPos, Direction direction) {
        BlockState neighborState = blockView.getBlockState(blockPos.offset(direction));
        return neighborState.isSideSolidFullSquare(blockView, blockPos.offset(direction), direction.getOpposite());
    }

    public static boolean hasDirection(BlockState state, Direction direction) {
        BooleanProperty booleanProperty = getProperty(direction);
        return state.contains(booleanProperty) && state.get(booleanProperty);
    }

    private static BlockState disableDirection(BlockState state, BooleanProperty direction) {
        BlockState blockState = state.with(direction, false);
        return hasAnyDirection(blockState) ? blockState : Blocks.AIR.getDefaultState();
    }

    public static BooleanProperty getProperty(Direction direction) {
        return FACING_PROPERTIES.get(direction);
    }

    private static BlockState withAllDirections(BlockState blockState, boolean value) {

        for (BooleanProperty booleanProperty : FACING_PROPERTIES.values()) {
            if (blockState.contains(booleanProperty)) {
                blockState = blockState.with(booleanProperty, value);
            }
        }

        return blockState;
    }

    protected static VoxelShape getShapeForDirection(Direction direction) {
        return SHAPES_FOR_DIRECTIONS.get(direction);
    }

    private static VoxelShape getShapeForState(BlockState state) {
        VoxelShape voxelShape = VoxelShapes.empty();

        for (Direction direction : DIRECTIONS) {
            if (hasDirection(state, direction)) {
                voxelShape = VoxelShapes.union(voxelShape, SHAPES_FOR_DIRECTIONS.get(direction));
            }
        }

        return voxelShape.isEmpty() ? VoxelShapes.fullCube() : voxelShape;
    }

    protected static boolean hasAnyDirection(BlockState state) {
        return Arrays.stream(DIRECTIONS).anyMatch(direction -> hasDirection(state, direction));
    }

    private static boolean isNotFullBlock(BlockState state) {
        return Arrays.stream(DIRECTIONS).anyMatch(direction -> !hasDirection(state, direction));
    }

    @Override
    protected abstract MapCodec<? extends GelBlock> getCodec();

    protected boolean canHaveDirection(Direction direction) {
        return true;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {

        for (Direction direction : DIRECTIONS) {
            if (this.canHaveDirection(direction)) {
                builder.add(getProperty(direction));
            }
        }

    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!hasAnyDirection(state)) {
            return Blocks.AIR.getDefaultState();
        }
        return hasDirection(state, direction) && !canPlaceOnSide(world, pos, direction) ? disableDirection(state, getProperty(direction)) : state;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.shapesByState.get(state);
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {

        for (Direction direction : DIRECTIONS) {
            if (hasDirection(state, direction)) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected boolean canReplace(BlockState state, ItemPlacementContext context) {
        return isNotFullBlock(state);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        BlockState state = blockState.isOf(this) ? blockState : this.getDefaultState();
        if (!canPlaceOnSide(world, blockPos, ctx.getSide().getOpposite())) {
            return null;
        }
        return state.with(getProperty(ctx.getSide().getOpposite()), true);
    }

    public BlockState withDirections(BlockState state, Direction[] directions) {
        for (Direction direction : directions) {
            state = state.with(GelBlock.getProperty(direction), true);
        }
        return state;
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        if (!this.hasAllHorizontalDirections) {
            return state;
        }
        return this.mirror(state, rotation::rotate);
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        if (mirror == BlockMirror.FRONT_BACK && !this.canMirrorX) {
            return state;
        }
        if (mirror == BlockMirror.LEFT_RIGHT && !this.canMirrorZ) {
            return state;
        }
        Objects.requireNonNull(mirror);
        return this.mirror(state, mirror::apply);
    }

    private BlockState mirror(BlockState state, Function<Direction, Direction> mirror) {
        BlockState blockState = state;

        for (Direction direction : DIRECTIONS) {
            if (this.canHaveDirection(direction)) {
                blockState = blockState.with(getProperty(mirror.apply(direction)), state.get(getProperty(direction)));
            }
        }

        return blockState;
    }
}
