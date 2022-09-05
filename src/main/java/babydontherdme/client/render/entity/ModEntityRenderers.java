package babydontherdme.client.render.entity;

import babydontherdme.entity.ModEntityType;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class ModEntityRenderers {
    public static void initialize() {
        EntityRendererRegistry.register(ModEntityType.HIGHLAND_COW, HighlandCowEntityRenderer::new);
    }
}
