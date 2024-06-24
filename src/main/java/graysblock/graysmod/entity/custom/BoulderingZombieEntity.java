package graysblock.graysmod.entity.custom;

import graysblock.graysmod.entity.ai.BoulderingZombieNavigation;
import graysblock.graysmod.sound.GraysModSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;

public class BoulderingZombieEntity extends ZombieEntity {
    private static final TrackedData<Byte> BOULDERING_ZOMBIE_FLAGS = DataTracker.registerData(BoulderingZombieEntity.class, TrackedDataHandlerRegistry.BYTE);

    public BoulderingZombieEntity(EntityType<? extends BoulderingZombieEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (isClimbing()) {
            return GraysModSoundEvents.ENTITY_BOULDERING_ZOMBIE_AMBIENT_CLIMB;
        }
        return super.getAmbientSound();
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new BoulderingZombieNavigation(this, world);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(BOULDERING_ZOMBIE_FLAGS, (byte) 0);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient) {
            this.setClimbingWall(this.horizontalCollision);
        }
    }

    @Override
    public boolean isClimbing() {
        return this.isClimbingWall();
    }

    public boolean isClimbingWall() {
        return (this.dataTracker.get(BOULDERING_ZOMBIE_FLAGS) & 1) != 0;
    }

    public void setClimbingWall(boolean climbing) {
        byte flags = this.dataTracker.get(BOULDERING_ZOMBIE_FLAGS);
        if (climbing) {
            flags = (byte) (flags | 1);
        } else {
            flags &= -2;
        }

        this.dataTracker.set(BOULDERING_ZOMBIE_FLAGS, flags);
    }
}
