package babydontherdme.entity;

import babydontherdme.Entrypoint;
import babydontherdme.entity.animal.HighlandCowEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

public class ModEntityType<T extends Entity> {

    public final static EntityType<HighlandCowEntity> HIGHLAND_COW;

    @SuppressWarnings("SameParameterValue")
    private static <T extends Entity> EntityType<T> register(String id, EntityType<T> type) {
        return Registry.register(Registry.ENTITY_TYPE, Entrypoint.identify(id), type);
    }
    static{
        HIGHLAND_COW = register("highland_cow", FabricEntityTypeBuilder.create(SpawnGroup.CREATURE,HighlandCowEntity::new).dimensions(new EntityDimensions(0.9F, 1.4F, true)).build());
    }
    @SuppressWarnings("all")
    public static void initialize(){
        Entrypoint.LOGGER.info("Registering Entities.");
        FabricDefaultAttributeRegistry.register(HIGHLAND_COW, HighlandCowEntity.createCowAttributes());
    }
}
