package babydontherdme.sound;


import babydontherdme.Entrypoint;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;

public class ModSoundEvents {
    public static final SoundEvent MUSIC_DISC_SHEEP = ModSoundEvents.register("music_disc_sheep");
    public static final SoundEvent WHISTLE = ModSoundEvents.register("whistle");

    private static SoundEvent register(String id) {
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(Entrypoint.identify(id)));
    }
    public static void initialize(){
        Entrypoint.LOGGER.info("Registering SoundEvents.");
    }
}
