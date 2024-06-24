package graysblock.graysmod.entity;

import graysblock.graysmod.GraysMod;
import graysblock.graysmod.entity.custom.BoulderingZombieEntity;
import graysblock.graysmod.entity.custom.CluckshroomEntity;
import graysblock.graysmod.entity.custom.WindBoltEntity;
import graysblock.graysmod.entity.custom.BallOfRepulsionGelEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class GraysModEntityTypes {

    public static final EntityType<BallOfRepulsionGelEntity> BALL_OF_REPULSION_GEL = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(GraysMod.MOD_ID, "ball_of_repulsion_gel"),
            EntityType.Builder.<BallOfRepulsionGelEntity>create(BallOfRepulsionGelEntity::new, SpawnGroup.MISC)
                    .dimensions(0.25F, 0.25F)
                    .build());

    public static final EntityType<CluckshroomEntity> CLUCKSHROOM = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(GraysMod.MOD_ID, "cluckshroom"),
            EntityType.Builder.create(CluckshroomEntity::new, SpawnGroup.CREATURE)
                    .dimensions(0.4F, 0.7F)
                    .eyeHeight(0.644F)
                    .build());

    public static final EntityType<BoulderingZombieEntity> BOULDERING_ZOMBIE = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(GraysMod.MOD_ID, "bouldering_zombie"),
            EntityType.Builder.create(BoulderingZombieEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.6F, 1.95F)
                    .eyeHeight(1.74F)
                    .build());

    public static final EntityType<WindBoltEntity> WIND_BOLT = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(GraysMod.MOD_ID, "wind_bolt"),
            EntityType.Builder.<WindBoltEntity>create(WindBoltEntity::new, SpawnGroup.MISC)
                    .dimensions(0.5F, 0.5F)
                    .eyeHeight(0.13F)
                    .maxTrackingRange(4)
                    .trackingTickInterval(20)
                    .build());
}
