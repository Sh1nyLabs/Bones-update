package com.sh1nylabs.bonesupdate.common.client.models;

/* Java class written by sh1nylabs' team, using Blockbench 4.7.4. All rights reserved. */

import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.KnightSkeleton;
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

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BonesUpdate.MODID, "knightskeletonmodel"), "main");
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart left_arm;
	private final ModelPart right_arm;
	private final ModelPart left_leg;
	private final ModelPart right_leg;

	public KnightSkeletonModel(ModelPart root) {
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.left_arm = root.getChild("left_arm");
		this.right_arm = root.getChild("right_arm");
		this.left_leg = root.getChild("left_leg");
		this.right_leg = root.getChild("right_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		PartDefinition claw = head.addOrReplaceChild("claw", CubeListBuilder.create().texOffs(32, 5).addBox(-5.0F, -2.5F, -4.5F, 10.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(38, 0).addBox(-5.0F, -4.5F, -1.5F, 10.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 12.0F, 0.0F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(52, 17).mirror().addBox(0.0F, -2.0F, -1.5F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(4.0F, 2.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(52, 17).addBox(-3.0F, -2.0F, -1.5F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 2.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-1.0F, 0.0F, -1.1F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(40, 17).mirror().addBox(-1.5F, -0.001F, -1.6F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 12.0F, 0.1F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, 0.0F, -1.1F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(40, 17).addBox(-1.5F, -0.001F, -1.6F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.1F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public void setupAnim(KnightSkeleton entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.right_leg.yRot = 0.005F;
		this.left_leg.yRot = -0.005F;
		this.right_leg.zRot = 0.005F;
		this.left_leg.zRot = -0.005F;
		if (entity.isDashing()) { // Set a different dash pose

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

			this.left_arm.xRot = -27.4F * Mth.PI/180F;
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
			float period=0.667F;

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
	}

	@Override
	public void translateToHand(HumanoidArm arm, PoseStack stack) {
		ModelPart armPart= (arm == HumanoidArm.LEFT) ? this.left_arm : this.right_arm;
		armPart.translateAndRotate(stack);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		left_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		right_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		left_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		right_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}
}