package net.Gmaj7.doAsInteresting.daiEntities.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.Gmaj7.doAsInteresting.DoAsInteresting;
import net.Gmaj7.doAsInteresting.daiEntities.custom.IronShootEntity;
import net.Gmaj7.doAsInteresting.daiEntities.model.IronShootEntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class IronShootEntityRenderer extends EntityRenderer<IronShootEntity> {

    private final IronShootEntityModel ironShootEntityModel;
    public IronShootEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.ironShootEntityModel = new IronShootEntityModel(pContext.bakeLayer(IronShootEntityModel.LAYER_LOCATION));
    }

    @Override
    public ResourceLocation getTextureLocation(IronShootEntity pEntity) {
        return ResourceLocation.fromNamespaceAndPath(DoAsInteresting.MODID, "textures/entity/iron_shoot_entity.png");
    }

    @Override
    public void render(IronShootEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
        pPoseStack.pushPose();
        VertexConsumer buffer = pBuffer.getBuffer(this.ironShootEntityModel.renderType(this.getTextureLocation(pEntity)));
        this.ironShootEntityModel.renderToBuffer(pPoseStack, buffer, pPackedLight, OverlayTexture.NO_OVERLAY, 88263371);
        pPoseStack.popPose();
    }
}
