package net.Gmaj7.funny_world.daiEntities.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.Gmaj7.funny_world.FunnyWorld;
import net.Gmaj7.funny_world.daiEntities.custom.ThunderBallEntity;
import net.Gmaj7.funny_world.daiEntities.model.ThunderBallModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class ThunderBallRender extends EntityRenderer<ThunderBallEntity> {
    private final ThunderBallModel thunderBallModel;
    public ThunderBallRender(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.thunderBallModel = new ThunderBallModel(pContext.bakeLayer(ThunderBallModel.LAYER_LOCATION));
    }

    @Override
    public ResourceLocation getTextureLocation(ThunderBallEntity pEntity) {
        return ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "textures/entity/thunder_ball.png");
    }@Override
    public void render(ThunderBallEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
        pPoseStack.pushPose();
        VertexConsumer buffer = pBuffer.getBuffer(this.thunderBallModel.renderType(this.getTextureLocation(pEntity)));
        this.thunderBallModel.renderToBuffer(pPoseStack, buffer, pPackedLight, OverlayTexture.NO_OVERLAY);
        pPoseStack.popPose();
    }
}
