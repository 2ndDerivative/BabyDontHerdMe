package babydontherdme.block.jukebox;

import babydontherdme.BabyDontHerdMe;
import net.minecraft.block.jukebox.JukeboxSong;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class ModJukeboxSongs {
    public static final RegistryKey<JukeboxSong> SHEEP = RegistryKey.of(RegistryKeys.JUKEBOX_SONG, BabyDontHerdMe.identify("sheep"));

    public static void initialize() {
    }
}