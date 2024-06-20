package graysblock.graysmod.entity.projectile;

import graysblock.graysmod.entity.GraysModEntityTypes;
import graysblock.graysmod.item.GraysModItems;
import graysblock.graysmod.sound.GraysModSoundEvents;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.explosion.AdvancedExplosionBehavior;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;

public class WindBoltEntity extends PersistentProjectileEntity {
    private static final ExplosionBehavior EXPLOSION_BEHAVIOR = new AdvancedExplosionBehavior(
            true, false, Optional.of(2.44F), Registries.BLOCK.getEntryList(BlockTags.BLOCKS_WIND_CHARGE_EXPLOSIONS).map(Function.identity())
    );

    public WindBoltEntity(EntityType<? extends WindBoltEntity> entityType, World world) {
        super(entityType, world);
    }

    public WindBoltEntity(World world, LivingEntity owner, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(GraysModEntityTypes.WIND_BOLT, owner, world, stack, shotFrom);
    }

    public WindBoltEntity(World world, double x, double y, double z, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(GraysModEntityTypes.WIND_BOLT, x, y, z, world, stack, shotFrom);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if(!this.getWorld().isClient) {
            Entity damageSource = this.getOwner();
            LivingEntity livingEntity = damageSource instanceof LivingEntity livingEntity1 ? livingEntity1 : null;
            Entity entity = entityHitResult.getEntity();
            if(livingEntity != null) {
                livingEntity.onAttacking(entity);
            }

            DamageSource damageSource1 = this.getDamageSources().arrow(this, livingEntity);
            if(entity.damage(damageSource1, 1.0F) && entity instanceof LivingEntity livingEntity2) {
                EnchantmentHelper.onTargetDamaged((ServerWorld) this.getWorld(), livingEntity2, damageSource1);
            }

            this.createExplosion(this.getPos());
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if(!this.getWorld().isClient) {
            Vec3i vec3i = blockHitResult.getSide().getVector();
            Vec3d vec3d = Vec3d.of(vec3i).multiply(0.25, 0.25, 0.25);
            Vec3d vec3d2 = blockHitResult.getPos().add(vec3d);
            this.createExplosion(vec3d2);
            this.discard();
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if(!this.getWorld().isClient) {
            this.discard();
        }
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return new ItemStack(GraysModItems.WIND_BOLT);
    }

    protected void createExplosion(Vec3d pos) {
        this.getWorld()
                .createExplosion(
                        this,
                        null,
                        EXPLOSION_BEHAVIOR,
                        pos.getX(),
                        pos.getY(),
                        pos.getZ(),
                        2.4F,
                        false,
                        World.ExplosionSourceType.TRIGGER,
                        ParticleTypes.GUST_EMITTER_SMALL,
                        ParticleTypes.GUST_EMITTER_LARGE,
                        GraysModSoundEvents.ENTITY_WIND_BOLT_WIND_BURST
                );
    }
}
