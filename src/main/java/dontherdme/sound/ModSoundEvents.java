package dontherdme.sound;


import dontherdme.Entrypoint;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;

public class ModSoundEvents {
    public static final SoundEvent MUSIC_DISC_SHEEP = ModSoundEvents.register("music_disc_sheep");

    private static SoundEvent register(String id) {
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(Entrypoint.identify(id)));
    }
    public static void initialize(){
        Entrypoint.LOGGER.info("Registering SoundEvents.");
    }
}
