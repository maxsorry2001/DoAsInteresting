package net.Gmaj7.doAsInteresting.daiEntities.model;

import net.Gmaj7.doAsInteresting.DoAsInteresting;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.AbstractArrow;

public class ThunderWedgeRender extends ArrowRenderer<AbstractArrow> {
    public ThunderWedgeRender(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractArrow pEntity) {
        return new ResourceLocation(DoAsInteresting.MODID, "textures/entity/thunder_wedge.png");
    }
}
