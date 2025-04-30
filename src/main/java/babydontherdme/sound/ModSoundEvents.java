package babydontherdme.sound;


import babydontherdme.BabyDontHerdMe;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSoundEvents {
    public static final SoundEvent MUSIC_DISC_SHEEP = ModSoundEvents.register("sheep");
    public static final SoundEvent RECALL = ModSoundEvents.register("recall");
    public static final SoundEvent GO_WHISTLE = ModSoundEvents.register("go");

    private static SoundEvent register(String raw_id) {
        Identifier id = BabyDontHerdMe.identify(raw_id);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
    public static void initialize(){
        BabyDontHerdMe.LOGGER.info("Registering SoundEvents.");
    }
}
