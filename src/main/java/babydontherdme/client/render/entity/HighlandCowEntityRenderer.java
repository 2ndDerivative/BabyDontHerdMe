package babydontherdme.client.render.entity;

import babydontherdme.Entrypoint;
import babydontherdme.client.render.entity.model.HighlandCowEntityModel;
import babydontherdme.client.render.entity.model.ModEntityModelLayers;
import babydontherdme.entity.animal.HighlandCowEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class HighlandCowEntityRenderer extends MobEntityRenderer<HighlandCowEntity, HighlandCowEntityModel<HighlandCowEntity>> {
    public static final Identifier TEXTURE = Entrypoint.identify("textures/entity/cow/highland_cow.png");
    public HighlandCowEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new HighlandCowEntityModel<>(context.getPart(ModEntityModelLayers.HIGHLAND_COW_LAYER)),0.7F);
    }
    public Identifier getTexture(HighlandCowEntity entity) {
        return TEXTURE;
    }
}
