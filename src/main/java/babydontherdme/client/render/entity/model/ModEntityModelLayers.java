package babydontherdme.client.render.entity.model;

import babydontherdme.Entrypoint;
import net.minecraft.client.render.entity.model.EntityModelLayer;

public class ModEntityModelLayers {
    public static final EntityModelLayer HIGHLAND_COW_LAYER;
    static{
        HIGHLAND_COW_LAYER = registerMain("highland_cow");
    }
    @SuppressWarnings("SameParameterValue")
    private static EntityModelLayer registerMain(String id){
        return new EntityModelLayer((Entrypoint.identify(id)),"main");
    }
}
