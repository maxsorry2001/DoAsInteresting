package net.Gmaj7.funny_world.daiEntities.renderer;

import net.Gmaj7.funny_world.FunnyWorld;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.AbstractArrow;

public class MomentumArrowRenderer extends ArrowRenderer<AbstractArrow> {
    public MomentumArrowRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractArrow pEntity) {
        return ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "textures/entity/momentum_arrow.png");
    }
}
