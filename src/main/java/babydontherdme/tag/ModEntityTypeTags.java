package babydontherdme.tag;

import babydontherdme.BabyDontHerdMe;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ModEntityTypeTags {
    public static final TagKey<EntityType<?>> HERDABLE = TagKey.of(RegistryKeys.ENTITY_TYPE, BabyDontHerdMe.identify("herdable"));
    public static void initialize() {
        BabyDontHerdMe.LOGGER.info("Registering Entity Tags");
    }
}
