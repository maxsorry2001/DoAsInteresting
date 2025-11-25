package net.Gmaj7.funny_world.daiEntities.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.Gmaj7.funny_world.FunnyWorld;
import net.Gmaj7.funny_world.daiEntities.custom.WaterBomb;
import net.Gmaj7.funny_world.daiEntities.model.WaterBombModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class WaterBombRenderer extends EntityRenderer<WaterBomb> {

    private final WaterBombModel waterBombModel;
    public WaterBombRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.waterBombModel = new WaterBombModel(pContext.bakeLayer(WaterBombModel.LAYER_LOCATION));
    }

    @Override
    public ResourceLocation getTextureLocation(WaterBomb pEntity) {
        return ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "textures/entity/water_bomb.png");
    }

    @Override
    public void render(WaterBomb pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
        pPoseStack.pushPose();
        this.waterBombModel.setupAnim(pEntity, 0, 0, pEntity.tickCount, 0, 0);
        VertexConsumer buffer = pBuffer.getBuffer(this.waterBombModel.renderType(this.getTextureLocation(pEntity)));
        this.waterBombModel.renderToBuffer(pPoseStack, buffer, pPackedLight, OverlayTexture.NO_OVERLAY);
        pPoseStack.popPose();
    }
}
