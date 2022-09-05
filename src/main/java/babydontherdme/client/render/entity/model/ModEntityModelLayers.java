package babydontherdme.client.render.entity.model;

import babydontherdme.Entrypoint;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;

import javax.swing.text.html.parser.Entity;

public class ModEntityModelLayers {
    public static final EntityModelLayer HIGHLAND_COW_LAYER;
    public static final EntityModelLayer HIGHLAND_COW_FUR;
    static{
        HIGHLAND_COW_LAYER = registerMain("highland_cow");
        HIGHLAND_COW_FUR = registerMain("highland_cow_fur");
    }
    @SuppressWarnings("SameParameterValue")
    private static EntityModelLayer registerMain(String id){
        return new EntityModelLayer((Entrypoint.identify(id)),"main");
    }
    public static void initialize(){
        EntityModelLayerRegistry.registerModelLayer(HIGHLAND_COW_LAYER, HighlandCowEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(HIGHLAND_COW_FUR, HighlandCowWoolEntityModel::getTexturedModelData);
    }
}
