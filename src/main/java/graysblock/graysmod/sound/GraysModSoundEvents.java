package graysblock.graysmod.sound;

import graysblock.graysmod.GraysMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class GraysModSoundEvents {
    public static final SoundEvent ENTITY_BALL_OF_GEL_THROW = registerSoundEvents("entity.ball_of_gel.throw");
    public static final SoundEvent ENTITY_CLUCKSHROOM_SHEAR = registerSoundEvents("entity.cluckshroom.shear");
    public static final SoundEvent ENTITY_BOULDERING_ZOMBIE_AMBIENT_CLIMB = registerSoundEvents("entity.bouldering_zombie.ambient_climb");
    public static final RegistryEntry.Reference<SoundEvent> ENTITY_WIND_BOLT_WIND_BURST = registerSoundEventReferences("entity.wind_bolt.wind_burst");

    private static SoundEvent registerSoundEvents(String name) {
        Identifier id = Identifier.of(GraysMod.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    private static RegistryEntry.Reference<SoundEvent> registerSoundEventReferences(String name) {
        Identifier id = Identifier.of(GraysMod.MOD_ID, name);
        return registerSoundEventReferences(id, id);
    }

    private static RegistryEntry.Reference<SoundEvent> registerSoundEventReferences(Identifier id, Identifier soundId) {
        return Registry.registerReference(Registries.SOUND_EVENT, id, SoundEvent.of(soundId));
    }

    public static void registerModdedSounds() {
        GraysMod.LOGGER.info("Registering GraysMod Sound Events...");
    }
}
