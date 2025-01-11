package net.Gmaj7.funny_world.daiEntities.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.Gmaj7.funny_world.FunnyWorld;
import net.Gmaj7.funny_world.daiEntities.custom.IronShootEntity;
import net.Gmaj7.funny_world.daiEntities.custom.MahjongEntity;
import net.Gmaj7.funny_world.daiEntities.model.MahjongEntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class MahjongEntityRenderer extends EntityRenderer<MahjongEntity> {

    private final MahjongEntityModel mahjongEntityModel;
    public MahjongEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.mahjongEntityModel = new MahjongEntityModel(pContext.bakeLayer(MahjongEntityModel.LAYER_LOCATION));
    }

    @Override
    public ResourceLocation getTextureLocation(MahjongEntity pEntity) {
        return ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "textures/entity/mahjong_entity.png");
    }

    @Override
    public void render(MahjongEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
        pPoseStack.pushPose();
        pPoseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.yRotO, pEntity.getYRot())));
        pPoseStack.mulPose(Axis.XN.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot())));
        VertexConsumer buffer = pBuffer.getBuffer(this.mahjongEntityModel.renderType(this.getTextureLocation(pEntity)));
        this.mahjongEntityModel.renderToBuffer(pPoseStack, buffer, pPackedLight, OverlayTexture.NO_OVERLAY);
        pPoseStack.popPose();
    }
}
