package com.sh1nylabs.bonesupdate.common.client.models;

/* Java class written by sh1nylabs' team, using Blockbench 4.7.4. All rights reserved. */

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.HaunterSkeleton;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import org.joml.Quaternionf;

public class HaunterSkeletonModel extends EntityModel<HaunterSkeleton> implements ArmedModel {

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BonesUpdate.MODID, "haunterskeletonmodel"), "main");
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart left_arm;
	private final ModelPart right_arm;
	private final ModelPart right_hand;
	private final ModelPart left_leg;
	private final ModelPart right_leg;

	public HaunterSkeletonModel(ModelPart root) {
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.left_arm = root.getChild("left_arm");
		this.right_arm = root.getChild("right_arm");
		this.right_hand = root.getChild("right_hand");
		this.left_leg = root.getChild("left_leg");
		this.right_leg = root.getChild("right_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(0.0F, -1.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(47, 7).mirror().addBox(-0.001F, -2.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(36, 3).mirror().addBox(1.5F, 0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(36, 3).addBox(0.0F, 8.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offset(4.0F, 1.0F, 0.0F));

		PartDefinition left_spine2_r1 = left_arm.addOrReplaceChild("left_spine2_r1", CubeListBuilder.create().texOffs(35, 7).mirror().addBox(-0.5F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.0F, -1.5F, 1.5F, 0.0F, -0.3927F, -0.6981F));

		PartDefinition left_spine1_r1 = left_arm.addOrReplaceChild("left_spine1_r1", CubeListBuilder.create().texOffs(35, 7).mirror().addBox(-0.5F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.0F, -1.5F, -1.5F, 0.0F, 0.3927F, -0.6981F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-2.0F, -1.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(47, 7).addBox(-2.999F, -2.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(47, 3).mirror().addBox(-2.0F, 8.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(47, 3).addBox(-3.5F, 0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 1.0F, 0.0F));

		PartDefinition right_spine2_r1 = right_arm.addOrReplaceChild("right_spine2_r1", CubeListBuilder.create().texOffs(35, 7).addBox(-1.5F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -1.5F, 1.5F, 0.0F, 0.3927F, 0.6981F));

		PartDefinition right_spine1_r1 = right_arm.addOrReplaceChild("right_spine1_r1", CubeListBuilder.create().texOffs(35, 7).addBox(-1.5F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -1.5F, -1.5F, 0.0F, -0.3927F, 0.6981F));

		PartDefinition right_hand = partdefinition.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(33, 19).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 10.5F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-1.0F, 0.0F, -1.1F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 12.0F, 0.1F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, 0.0F, -1.1F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.1F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public void setupAnim(HaunterSkeleton entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		//boolean broken = entity.isBroken();
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
		float period = 0.667F;

		this.right_hand.yRot = 0.0F;
		this.right_arm.yRot = 0.0F;
		this.left_arm.yRot = 0.0F;

		this.right_arm.xRot = Mth.cos(limbSwing * period + (float)Math.PI) * limbSwingAmount / f;
		setupAttackAnimation(entity, ageInTicks);
		this.right_hand.x = -5.0F;
		this.right_hand.y = 10.0F * Mth.cos(this.right_arm.xRot);
		this.right_hand.z = 10.0F * Mth.sin(this.right_arm.xRot);
		this.right_hand.xRot = 0.1F * Mth.cos(limbSwing * period + (float)Math.PI) * limbSwingAmount / f;


		this.left_arm.xRot = Mth.cos(limbSwing * period) * limbSwingAmount / f;

		this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
		if (flag) {
			this.head.xRot = (-(float)Math.PI / 4F);
		} else {
			this.head.xRot = headPitch * ((float)Math.PI / 180F);
		}

		this.right_leg.xRot = Mth.cos(limbSwing * period) * 1.4F * limbSwingAmount / f;
		this.left_leg.xRot = Mth.cos(limbSwing * period + (float)Math.PI) * 1.4F * limbSwingAmount / f;

	}

	protected void setupAttackAnimation(HaunterSkeleton entity, float ageInTicks) {
		if (this.attackTime > 0.0F) {
			float f = this.attackTime;
			this.body.yRot = Mth.cos((float)Math.PI/2F - Mth.sqrt(f) * ((float)Math.PI)) * 0.2F;

			this.right_arm.yRot += this.body.yRot;
			this.right_hand.yRot += this.body.yRot;
			this.left_arm.yRot += this.body.yRot;

			f = 1.0F - this.attackTime;
			f *= f;
			f *= f;
			f = 1.0F - f;

			float f2 = 0.3F - 1.1F * Mth.sin(f * (float)Math.PI);
			right_arm.xRot += f2;
		} else {
			this.body.yRot=0;
		}
	}

	@Override
	public void translateToHand(HumanoidArm arm, PoseStack stack) {
		ModelPart armPart = this.right_hand;
		stack.translate((armPart.x+1.0F) / 16.0F, (armPart.y-9.5F) / 16.0F, (armPart.z+1.5F) / 16.0F);
		if (armPart.xRot != 0.0F || armPart.yRot != 0.0F || armPart.zRot != 0.0F) {
			stack.mulPose((new Quaternionf()).rotationZYX(armPart.zRot, armPart.yRot, armPart.xRot));
		}

		if (armPart.xScale != 1.0F || armPart.yScale != 1.0F || armPart.zScale != 1.0F) {
			stack.scale(0.85F,0.85F,0.85F);
		}
	}


	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		left_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		right_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		right_hand.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		left_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		right_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}
}