package babydontherdme.tag;

import babydontherdme.Entrypoint;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class ModBiomeTags {
    public static final TagKey<Biome> SPAWNS_HIGHLAND_CATTLE = of("spawns_highland_cattle");

    @SuppressWarnings("SameParameterValue")
    private static TagKey<Biome> of(String id) {return TagKey.of(Registry.BIOME_KEY, Entrypoint.identify(id));}

    public static void initialize() {
        Entrypoint.LOGGER.info("Registering Biome Tags.");
    }
}
