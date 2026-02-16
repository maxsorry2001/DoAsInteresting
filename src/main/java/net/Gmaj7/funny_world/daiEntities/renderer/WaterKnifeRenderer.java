package net.Gmaj7.funny_world.daiEntities.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.Gmaj7.funny_world.FunnyWorld;
import net.Gmaj7.funny_world.daiEntities.custom.WaterKnife;
import net.Gmaj7.funny_world.daiEntities.model.WaterKnifeModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class WaterKnifeRenderer extends EntityRenderer<WaterKnife> {

    private final WaterKnifeModel waterKnifeModel;
    public WaterKnifeRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.waterKnifeModel = new WaterKnifeModel(pContext.bakeLayer(WaterKnifeModel.LAYER_LOCATION));
    }

    @Override
    public ResourceLocation getTextureLocation(WaterKnife pEntity) {
        return ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "textures/entity/water_knife.png");
    }

    @Override
    public void render(WaterKnife pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
        pPoseStack.pushPose();
        pPoseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.yRotO, pEntity.getYRot())));
        pPoseStack.mulPose(Axis.XN.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot())));
        VertexConsumer buffer = pBuffer.getBuffer(this.waterKnifeModel.renderType(this.getTextureLocation(pEntity)));
        this.waterKnifeModel.renderToBuffer(pPoseStack, buffer, pPackedLight, OverlayTexture.NO_OVERLAY);
        pPoseStack.popPose();
    }
}
