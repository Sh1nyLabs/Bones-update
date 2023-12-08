package com.shiny_labs.bonesupdate.common.client.models;
// Made with Blockbench 4.7.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.shiny_labs.bonesupdate.common.entities.custom_skeletons.KnightSkeleton;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;

public class KnightSkeletonModel extends EntityModel<KnightSkeleton> implements ArmedModel {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "knightskeletonmodel"), "main");
	private final ModelPart head;
	private final ModelPart broken_state;
	private final ModelPart body;
	private final ModelPart left_arm;
	private final ModelPart right_arm;
	private final ModelPart left_leg;
	private final ModelPart right_leg;

	public KnightSkeletonModel(ModelPart root) {
		this.head = root.getChild("head");
		this.broken_state = root.getChild("broken_state");
		this.body = root.getChild("body");
		this.left_arm = root.getChild("left_arm");
		this.right_arm = root.getChild("right_arm");
		this.left_leg = root.getChild("left_leg");
		this.right_leg = root.getChild("right_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		PartDefinition claw = head.addOrReplaceChild("claw", CubeListBuilder.create().texOffs(0, 6).addBox(-5.0F, -2.5F, -4.5F, 10.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(3, 0).addBox(-5.0F, -4.5F, -1.5F, 10.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition broken_state = partdefinition.addOrReplaceChild("broken_state", CubeListBuilder.create(), PartPose.offset(0.0F, 23.0F, 0.0F));

		PartDefinition head_r1 = broken_state.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(32, 0).addBox(1.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, -0.3927F, 0.0F));

		PartDefinition body_r1 = broken_state.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(11, 15).addBox(0.0F, -10.0F, -4.5F, 8.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, 0.0F, -3.0F, -1.1961F, 0.2291F, -0.5236F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(11, 15).addBox(-4.0F, -12.0F, -2.5F, 8.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.0F, 0.0F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(52, 17).mirror().addBox(0.0F, -2.0F, -1.5F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(4.0F, 2.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(52, 17).addBox(-3.0F, -2.0F, -1.5F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 2.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-1.0F, 0.0F, -1.1F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 12.0F, 0.1F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, 0.0F, -1.1F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.1F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public void setupAnim(KnightSkeleton entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.right_leg.yRot = 0.005F;
		this.left_leg.yRot = -0.005F;
		this.right_leg.zRot = 0.005F;
		this.left_leg.zRot = -0.005F;
		if (entity.isDashing()) { //

			this.body.xRot = 12.5F * Mth.PI/180F;
			this.body.yRot = -17.5F * Mth.PI/180F;

			this.right_arm.x = -5.0F;
			this.right_arm.y = 2.0F;
			this.right_arm.z = -4.0F;

			this.right_arm.xRot = -157.29F * Mth.PI/180F;
			this.right_arm.yRot = 12.0F * Mth.PI/180F;
			this.right_arm.zRot = 141.6F * Mth.PI/180F;

			this.left_arm.x = 4.0F;
			this.left_arm.z = -1.0F;

			this.left_arm.xRot = -4.9F * Mth.PI/180F;
			this.left_arm.yRot = -0.6F * Mth.PI/180F;
			this.left_arm.zRot = 14.9F * Mth.PI/180F;

			this.head.x = 0.5F;
			this.head.y = 0.5F;
			this.head.z = -3.0F;
			this.head.xRot = 14.0F * Mth.PI/180F;
			this.head.yRot = -13.0F * Mth.PI/180F;
			this.head.zRot = 1.6F * Mth.PI/180F;

			//this.right_leg.xRot = -12.5F * Mth.PI/180F;
			//this.left_leg.xRot = 10.0F * Mth.PI/180F;


		} else {
			//LOGGER.info(" left arm x: {}",this.left_arm.x); // x 4.0, y 2.0, z 0.0
			//LOGGER.info(" right arm x: {}",this.right_arm.x); // x -4.0, y 2.0, z 0.0

			boolean flag = entity.getFallFlyingTicks() > 4;
			float f = 1.0F;
			if (flag) {
				f = (float)entity.getDeltaMovement().lengthSqr();
				f /= 0.2F;
				f *= f * f;
			}

			if (f < 1.0F) {
				f = 1.0F;
			}
			float period=0.667F; //0.6662F

			this.right_arm.x = -4.0F;
			this.right_arm.y = 2.0F;
			this.right_arm.z = 0.0F;
			this.right_arm.xRot = Mth.cos(limbSwing * period + (float)Math.PI) * limbSwingAmount / f;
			this.right_arm.yRot = 0.0F;
			this.right_arm.zRot = 0.0F;

			this.left_arm.x = 4.0F;
			this.left_arm.z = 0.0F;
			this.left_arm.xRot = Mth.cos(limbSwing * period) * limbSwingAmount / f;
			this.left_arm.yRot = 0.0F;
			this.left_arm.zRot = 0.0F;

			this.body.xRot = 0.0F;
			this.body.yRot = 0.0F;

			this.head.x = 0.0F;
			this.head.y = 0.0F;
			this.head.z = 0.0F;

			this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
			if (flag) {
				this.head.xRot = (-(float)Math.PI / 4F);
			} else {
				this.head.xRot = headPitch * ((float)Math.PI / 180F);
			}

			this.right_leg.xRot = Mth.cos(limbSwing * period) * 1.4F * limbSwingAmount / f;
			this.left_leg.xRot = Mth.cos(limbSwing * period + (float)Math.PI) * 1.4F * limbSwingAmount / f;

		}

		boolean flag2 = entity.isBroken();
		this.head.visible=!flag2;
		this.body.visible=!flag2;
		this.left_arm.visible=!flag2;
		this.right_arm.visible=!flag2;
		this.left_leg.visible=!flag2;
		this.right_leg.visible=!flag2;

		this.broken_state.visible=flag2;
	}

	@Override
	public void translateToHand(HumanoidArm arm, PoseStack stack) {
		ModelPart armPart= (arm == HumanoidArm.LEFT) ? this.left_arm : this.right_arm;
		armPart.translateAndRotate(stack);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		broken_state.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		left_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		right_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		left_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		right_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}