package graysblock.graysmod.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import graysblock.graysmod.entity.effect.GraysModStatusEffects;
import graysblock.graysmod.item.GraysModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow public abstract boolean hasStatusEffect(RegistryEntry<StatusEffect> effect);
    @Shadow @Nullable public abstract StatusEffectInstance getStatusEffect(RegistryEntry<StatusEffect> effect);

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @ModifyExpressionValue(method = "travel", at = {@At(value = "CONSTANT", args = "doubleValue=0.5D", ordinal = 0),
            @At(value = "CONSTANT", args = "doubleValue=0.5D", ordinal = 1),
            @At(value = "CONSTANT", args = "doubleValue=0.5D", ordinal = 2)})
    private double modifyLavaSpeed(double original) {
        boolean hasMagmaSpeed = this.hasStatusEffect(GraysModStatusEffects.MAGMA_SPEED);
        if(hasMagmaSpeed) {
            int i = this.getStatusEffect(GraysModStatusEffects.MAGMA_SPEED).getAmplifier() + 1;
            return original + (0.07F * i);
        } else {
            return original;
        }
    }

    @ModifyReturnValue(method = "canWalkOnFluid", at = @At("RETURN"))
    private boolean striderBootsActivation(boolean original, FluidState state) {
        ItemStack itemStack = getEquippedStack(EquipmentSlot.FEET);
        boolean hasStriderBootsOn = itemStack.isOf(GraysModItems.STRIDER_BOOTS);
        if(hasStriderBootsOn) {
            return state.isIn(FluidTags.LAVA);
        } else {
            return original;
        }
    }
}
