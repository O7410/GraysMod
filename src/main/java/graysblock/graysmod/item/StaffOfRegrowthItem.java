package graysblock.graysmod.item;

import net.minecraft.block.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class StaffOfRegrowthItem extends Item {

    public StaffOfRegrowthItem(Item.Settings settings) {
        super(settings.maxDamage(1024));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockPos clickedOnPos = blockPos.offset(context.getSide());
        if (useOnFertilizable(world, context.getPlayer(), context.getStack(), context.getHand(), context.getBlockPos())) {
            if (!world.isClient) {
                context.getPlayer().emitGameEvent(GameEvent.ITEM_INTERACT_FINISH);
                world.syncWorldEvent(WorldEvents.BONE_MEAL_USED, blockPos, 15);
            }

            return ActionResult.success(world.isClient);
        } else {
            BlockState blockState = world.getBlockState(blockPos);
            boolean sideSolidFullSquare = blockState.isSideSolidFullSquare(world, blockPos, context.getSide());
            if (sideSolidFullSquare && useOnGround(world, context.getPlayer(), context.getStack(), context.getHand(), clickedOnPos, context.getSide())) {
                if (!world.isClient) {
                    context.getPlayer().emitGameEvent(GameEvent.ITEM_INTERACT_FINISH);
                    world.syncWorldEvent(WorldEvents.BONE_MEAL_USED, clickedOnPos, 15);
                }

                return ActionResult.success(world.isClient);
            } else {
                return ActionResult.PASS;
            }
        }
    }

    public static boolean useOnFertilizable(World world, PlayerEntity user, ItemStack stack, Hand hand, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        if (block instanceof Fertilizable fertilizable && fertilizable.isFertilizable(world, pos, blockState)) {
            if (!(world instanceof ServerWorld serverWorld)) {
                return true;
            }
            if (fertilizable.canGrow(world, world.random, pos, blockState)) {
                fertilizable.grow(serverWorld, world.random, pos, blockState);
            }
            stack.damage(1, user, hand == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
        }

        return false;
    }

    public static boolean useOnGround(World world, LivingEntity user, ItemStack stack, Hand hand, BlockPos blockPos, @Nullable Direction facing) {
        if (!world.getBlockState(blockPos).isOf(Blocks.WATER) || world.getFluidState(blockPos).getLevel() != 8 || !(user instanceof PlayerEntity playerEntity)) {
            return false;
        }
        if (!(world instanceof ServerWorld serverWorld)) {
            return true;
        }
        Random random = world.getRandom();

        label78:
        for (int i = 0; i < 128; ++i) {
            BlockPos posToPlace = blockPos;
            BlockState stateToPlace = Blocks.SEAGRASS.getDefaultState();

            for (int j = 0; j < i / 16; ++j) {
                posToPlace = posToPlace.add(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
                if (world.getBlockState(posToPlace).isFullCube(world, posToPlace)) {
                    continue label78;
                }
            }

            RegistryEntry<Biome> biomeEntry = world.getBiome(posToPlace);
            if (biomeEntry.isIn(BiomeTags.PRODUCES_CORALS_FROM_BONEMEAL)) {
                if (i == 0 && facing != null && facing.getAxis().isHorizontal()) {
                    stateToPlace = Registries.BLOCK.getRandomEntry(BlockTags.WALL_CORALS, world.random)
                            .map(blockEntry -> blockEntry.value().getDefaultState())
                            .orElse(stateToPlace);
                    if (stateToPlace.contains(DeadCoralWallFanBlock.FACING)) {
                        stateToPlace = stateToPlace.with(DeadCoralWallFanBlock.FACING, facing);
                    }
                } else if (random.nextInt(4) == 0) {
                    stateToPlace = Registries.BLOCK.getRandomEntry(BlockTags.UNDERWATER_BONEMEALS, world.random)
                            .map(blockEntry -> blockEntry.value().getDefaultState())
                            .orElse(stateToPlace);
                }
            }

            if (stateToPlace.isIn(BlockTags.WALL_CORALS) && stateToPlace.contains(DeadCoralWallFanBlock.FACING)) {
                for (int canPlaceTry = 0; !stateToPlace.canPlaceAt(world, posToPlace) && canPlaceTry < 4; ++canPlaceTry) {
                    stateToPlace = stateToPlace.with(DeadCoralWallFanBlock.FACING, Direction.Type.HORIZONTAL.random(random));
                }
            }

            if (stateToPlace.canPlaceAt(world, posToPlace)) {
                BlockState stateInPos = world.getBlockState(posToPlace);
                if (stateInPos.isOf(Blocks.WATER) && world.getFluidState(posToPlace).getLevel() == 8) {
                    world.setBlockState(posToPlace, stateToPlace, Block.NOTIFY_ALL);
                } else if (stateInPos.isOf(Blocks.SEAGRASS) && random.nextInt(10) == 0) {
                    ((Fertilizable)Blocks.SEAGRASS).grow(serverWorld, random, posToPlace, stateInPos);
                }
            }
        }
        EquipmentSlot equipmentSlot = hand == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
        stack.damage(1, user, equipmentSlot);
        return true;
    }
}
