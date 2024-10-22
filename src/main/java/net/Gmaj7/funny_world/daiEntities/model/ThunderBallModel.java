package net.Gmaj7.funny_world.daiEntities.model;
// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.Gmaj7.funny_world.FunnyWorld;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class ThunderBallModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "thunderball"), "main");
	private final ModelPart bb_main;

	public ThunderBallModel(ModelPart root) {
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create()
				.texOffs(0, 0).addBox(-2.0F, -23.0F, -1.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(3, 3).addBox(-2.0F, -23.0F, 3.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(3, 3).addBox(-2.0F, -23.0F, -2.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-3.0F, -23.0F, -1.0F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(2.0F, -23.0F, -1.0F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(2.0F, -23.0F, -1.0F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, int pColor) {
		bb_main.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
	}
}