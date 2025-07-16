package com.sh1nylabs.bonesupdate.common.client.models;

/* Java class written by sh1nylabs' team, using Blockbench 4.7.4. All rights reserved. */

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import org.joml.Quaternionf;

public class HaunterSkeletonModel extends HumanoidModel<HumanoidRenderState> implements ArmedModel {

	private final ModelPart rightHand;

	public HaunterSkeletonModel(ModelPart root) {
		super(root);
		this.rightHand = root.getChild("right_hand");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
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

		head.clearChild("hat");
		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public void setupAnim(HumanoidRenderState humanoidState)
	{
		super.setupAnim(humanoidState);
		this.rightHand.x = -5.0F - 10.0F * Mth.sin(this.rightArm.zRot);
		this.rightHand.y = 10.0F * Mth.cos(this.rightArm.xRot);
		this.rightHand.z = 10.0F * Mth.sin(this.rightArm.xRot);
		this.rightHand.xRot = 0.1F * Mth.cos(humanoidState.walkAnimationPos * 0.6662F + (float) Math.PI) * 2.0F * humanoidState.walkAnimationSpeed * 0.5F / humanoidState.speedValue;
		this.rightHand.yRot = rightArm.yRot;
	}

	@Override
	protected void setupAttackAnimation(HumanoidRenderState humanoidState, float ageInTicks) {
		if (humanoidState.attackTime > 0.0F) {
			float f = humanoidState.attackTime;
			this.body.yRot = Mth.cos((float)Math.PI/2F - Mth.sqrt(f) * ((float)Math.PI)) * 0.2F;

			this.rightArm.yRot += this.body.yRot;
			this.rightHand.yRot += this.body.yRot;
			this.rightArm.yRot += this.body.yRot;

			f = 1.0F - humanoidState.attackTime;
			f *= f;
			f *= f;
			f = 1.0F - f;

			float f2 = 0.3F - 1.1F * Mth.sin(f * (float)Math.PI);
			rightArm.xRot += f2;
		} else {
			this.body.yRot=0;
		}
	}

	@Override
	public void translateToHand(HumanoidArm arm, PoseStack stack) {
		ModelPart armPart = this.rightHand;
		stack.translate((armPart.x) / 16.0F, (armPart.y-9.5F) / 16.0F, (armPart.z) / 16.0F);
		if (armPart.xRot != 0.0F || armPart.yRot != 0.0F || armPart.zRot != 0.0F) {
			stack.mulPose((new Quaternionf()).rotationZYX(armPart.zRot, armPart.yRot, armPart.xRot));
		}

		if (armPart.xScale != 1.0F || armPart.yScale != 1.0F || armPart.zScale != 1.0F) {
			stack.scale(0.85F,0.85F,0.85F);
		}
	}
}