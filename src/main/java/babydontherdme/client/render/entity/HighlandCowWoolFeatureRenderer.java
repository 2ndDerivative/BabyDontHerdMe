package babydontherdme.client.render.entity;

import babydontherdme.Entrypoint;
import babydontherdme.client.render.entity.model.HighlandCowEntityModel;
import babydontherdme.client.render.entity.model.HighlandCowWoolEntityModel;
import babydontherdme.client.render.entity.model.ModEntityModelLayers;
import babydontherdme.entity.animal.HighlandCowEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class HighlandCowWoolFeatureRenderer extends FeatureRenderer<HighlandCowEntity, HighlandCowEntityModel<HighlandCowEntity>> {
    private static final Identifier SKIN = Entrypoint.identify("textures/entity/cow/highland_cow_fur.png");
    private final HighlandCowWoolEntityModel<HighlandCowEntity> model;

    public HighlandCowWoolFeatureRenderer(FeatureRendererContext<HighlandCowEntity, HighlandCowEntityModel<HighlandCowEntity>> context, EntityModelLoader loader) {
        super(context);
        this.model = new HighlandCowWoolEntityModel<>(loader.getModelPart(ModEntityModelLayers.HIGHLAND_COW_FUR));
    }

    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, HighlandCowEntity highlandCowEntity, float f, float g, float h, float j, float k, float l) {
        if (!highlandCowEntity.isSheared()) {
            if (highlandCowEntity.isInvisible()) {
                MinecraftClient minecraftClient = MinecraftClient.getInstance();
                boolean bl = minecraftClient.hasOutline(highlandCowEntity);
                if (bl) {
                    this.getContextModel().copyStateTo(this.model);
                    this.model.animateModel(highlandCowEntity, f, g, h);
                    this.model.setAngles(highlandCowEntity, f, g, j, k, l);
                    VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getOutline(SKIN));
                    this.model.render(matrixStack, vertexConsumer, i, LivingEntityRenderer.getOverlay(highlandCowEntity, 0.0F), 0.0F, 0.0F, 0.0F, 1.0F);
                }

            } else {
                render(this.getContextModel(), this.model, SKIN, matrixStack, vertexConsumerProvider, i, highlandCowEntity, f, g, j, k, l, h, 1.0F, 1.0F, 1.0F);
            }
        }
    }
}
