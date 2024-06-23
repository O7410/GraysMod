package graysblock.graysmod.block;

import com.mojang.serialization.MapCodec;
import graysblock.graysmod.block.entity.GraysModBlockEntityTypes;
import graysblock.graysmod.block.entity.KilnBlockEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class KilnBlock extends AbstractFurnaceBlock {
    public static final MapCodec<KilnBlock> CODEC = createCodec(KilnBlock::new);

    public KilnBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public MapCodec<KilnBlock> getCodec() {
        return CODEC;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new KilnBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(world, type, GraysModBlockEntityTypes.KILN_BLOCK_ENTITY);
    }

    @Override
    protected void openScreen(World world, BlockPos pos, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof KilnBlockEntity kilnBlockEntity) {
            player.openHandledScreen(kilnBlockEntity);
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(LIT)) {

            double centerX = (double) pos.getX() + 0.5;
            double centerY = pos.getY();
            double centerZ = (double) pos.getZ() + 0.5;
            if (random.nextDouble() < 0.1) {
                world.playSound(centerX, centerY, centerZ, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction = state.get(FACING);
            Direction.Axis axis = direction.getAxis();
            double backFrontOffset = random.nextDouble() * 0.6 - 0.3;
            double verticalOffset = random.nextDouble() * 9.0 / 16.0;

            double xOffset = axis == Direction.Axis.X ? direction.getOffsetX() * 0.52 : backFrontOffset;
            double zOffset = axis == Direction.Axis.Z ? direction.getOffsetZ() * 0.52 : backFrontOffset;
            world.addParticle(ParticleTypes.SMOKE, centerX + xOffset, centerY + verticalOffset, centerZ + zOffset, 0.0, 0.0, 0.0);
        }
    }
}
