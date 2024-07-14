package net.Gmaj7.doAsInteresting.daiEntities.model;// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.Gmaj7.doAsInteresting.DoAsInteresting;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class IronShootEntityModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(DoAsInteresting.MODID, "iron_shoot_entity"), "main");
	private final ModelPart bone;

	public IronShootEntityModel(ModelPart root) {
		this.bone = root.getChild("bone");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 3).addBox(-1.0F, -21.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-1.0F, -16.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(6, 1).addBox(2.0F, -19.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 6).addBox(-3.0F, -19.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(11, 4).addBox(-1.0F, -19.0F, 2.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(10, 0).addBox(-1.0F, -19.0F, -3.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 14).addBox(1.0F, -19.0F, -2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 14).addBox(1.0F, -19.0F, 1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 14).addBox(-2.0F, -19.0F, 1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 13).addBox(-2.0F, -19.0F, -2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(11, 12).addBox(-1.0F, -20.0F, -2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 9).addBox(-1.0F, -20.0F, 1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(5, 12).addBox(-1.0F, -17.0F, 1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(11, 7).addBox(-1.0F, -17.0F, -2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 10).addBox(-2.0F, -17.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(8, 9).addBox(1.0F, -17.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(4, 8).addBox(1.0F, -20.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(6, 5).addBox(-2.0F, -20.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}