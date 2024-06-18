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

    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockPos blockPos2 = blockPos.offset(context.getSide());
        if (useOnFertilizable(world, context.getPlayer(), context.getStack(), context.getBlockPos())) {
            if (!world.isClient) {
                context.getPlayer().emitGameEvent(GameEvent.ITEM_INTERACT_FINISH);
                world.syncWorldEvent(WorldEvents.BONE_MEAL_USED, blockPos, 15);
            }

            return ActionResult.success(world.isClient);
        } else {
            BlockState blockState = world.getBlockState(blockPos);
            boolean bl = blockState.isSideSolidFullSquare(world, blockPos, context.getSide());
            if (bl && useOnGround(world, context.getPlayer(), context.getStack(), blockPos2, context.getSide())) {
                if (!world.isClient) {
                    context.getPlayer().emitGameEvent(GameEvent.ITEM_INTERACT_FINISH);
                    world.syncWorldEvent(WorldEvents.BONE_MEAL_USED, blockPos2, 15);
                }

                return ActionResult.success(world.isClient);
            }

            else {
                return ActionResult.PASS;
            }
        }
    }

    public static boolean useOnFertilizable(World world, LivingEntity user, ItemStack stack, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        Block var5 = blockState.getBlock();
        if (var5 instanceof Fertilizable fertilizable && user instanceof PlayerEntity playerEntity) {
            if (fertilizable.isFertilizable(world, pos, blockState)) {
                if (world instanceof ServerWorld) {
                    if (fertilizable.canGrow(world, world.random, pos, blockState)) {
                        fertilizable.grow((ServerWorld)world, world.random, pos, blockState);
                    }

                    EquipmentSlot equipmentSlot = stack.equals(playerEntity.getEquippedStack(EquipmentSlot.OFFHAND)) ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
                    stack.damage(1, user, equipmentSlot);
                }

                return true;
            }
        }

        return false;
    }

    public static boolean useOnGround(World world, LivingEntity user, ItemStack stack, BlockPos blockPos, @Nullable Direction facing) {
        if (world.getBlockState(blockPos).isOf(Blocks.WATER) && world.getFluidState(blockPos).getLevel() == 8 && user instanceof PlayerEntity playerEntity) {
            if (!(world instanceof ServerWorld)) {
                return true;
            } else {
                Random random = world.getRandom();

                label78:
                for(int i = 0; i < 128; ++i) {
                    BlockPos blockPos2 = blockPos;
                    BlockState blockState = Blocks.SEAGRASS.getDefaultState();

                    for(int j = 0; j < i / 16; ++j) {
                        blockPos2 = blockPos2.add(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
                        if (world.getBlockState(blockPos2).isFullCube(world, blockPos2)) {
                            continue label78;
                        }
                    }

                    RegistryEntry<Biome> registryEntry = world.getBiome(blockPos2);
                    if (registryEntry.isIn(BiomeTags.PRODUCES_CORALS_FROM_BONEMEAL)) {
                        if (i == 0 && facing != null && facing.getAxis().isHorizontal()) {
                            blockState = (BlockState) Registries.BLOCK.getRandomEntry(BlockTags.WALL_CORALS, world.random).map((blockEntry) -> {
                                return ((Block)blockEntry.value()).getDefaultState();
                            }).orElse(blockState);
                            if (blockState.contains(DeadCoralWallFanBlock.FACING)) {
                                blockState = (BlockState)blockState.with(DeadCoralWallFanBlock.FACING, facing);
                            }
                        } else if (random.nextInt(4) == 0) {
                            blockState = (BlockState)Registries.BLOCK.getRandomEntry(BlockTags.UNDERWATER_BONEMEALS, world.random).map((blockEntry) -> {
                                return ((Block)blockEntry.value()).getDefaultState();
                            }).orElse(blockState);
                        }
                    }

                    if (blockState.isIn(BlockTags.WALL_CORALS, (state) -> {
                        return state.contains(DeadCoralWallFanBlock.FACING);
                    })) {
                        for(int k = 0; !blockState.canPlaceAt(world, blockPos2) && k < 4; ++k) {
                            blockState = (BlockState)blockState.with(DeadCoralWallFanBlock.FACING, Direction.Type.HORIZONTAL.random(random));
                        }
                    }

                    if (blockState.canPlaceAt(world, blockPos2)) {
                        BlockState blockState2 = world.getBlockState(blockPos2);
                        if (blockState2.isOf(Blocks.WATER) && world.getFluidState(blockPos2).getLevel() == 8) {
                            world.setBlockState(blockPos2, blockState, Block.NOTIFY_ALL);
                        } else if (blockState2.isOf(Blocks.SEAGRASS) && random.nextInt(10) == 0) {
                            ((Fertilizable)Blocks.SEAGRASS).grow((ServerWorld)world, random, blockPos2, blockState2);
                        }
                    }
                }

                EquipmentSlot equipmentSlot = stack.equals(playerEntity.getEquippedStack(EquipmentSlot.OFFHAND)) ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
                stack.damage(1, user, equipmentSlot);
                return true;
            }
        } else {
            return false;
        }
    }
}
