package com.sh1nylabs.bonesupdate.common.client.models;

/* Java class written by sh1nylabs' team, using Blockbench 4.7.4. All rights reserved. */

import com.sh1nylabs.bonesupdate.common.client.render_states.KnightSkeletonRenderState;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class KnightSkeletonModel extends HumanoidModel<KnightSkeletonRenderState> implements ArmedModel {

	public KnightSkeletonModel(ModelPart root) {
		super(root);
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

		head.clearChild("hat");
		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public void setupAnim(KnightSkeletonRenderState knightState) {
		if (knightState.isDashing) { // Set a different dash pose

			this.rightLeg.yRot = 0.005F;
			this.leftLeg.yRot = -0.005F;
			this.rightLeg.zRot = 0.005F;
			this.leftLeg.zRot = -0.005F;

			this.body.xRot = 12.5F * Mth.PI/180F;
			this.body.yRot = -17.5F * Mth.PI/180F;

			this.rightArm.x = -5.0F;
			this.rightArm.y = 2.0F;
			this.rightArm.z = -4.0F;

			this.rightArm.xRot = -157.29F * Mth.PI/180F;
			this.rightArm.yRot = 12.0F * Mth.PI/180F;
			this.rightArm.zRot = 141.6F * Mth.PI/180F;

			this.leftArm.x = 4.0F;
			this.leftArm.z = -1.0F;

			this.leftArm.xRot = -27.4F * Mth.PI/180F;
			this.leftArm.yRot = -0.6F * Mth.PI/180F;
			this.leftArm.zRot = 14.9F * Mth.PI/180F;

			this.head.x = 0.5F;
			this.head.y = 0.5F;
			this.head.z = -3.0F;
			this.head.xRot = 14.0F * Mth.PI/180F;
			this.head.yRot = -13.0F * Mth.PI/180F;
			this.head.zRot = 1.6F * Mth.PI/180F;

			this.rightLeg.xRot = -12.5F * Mth.PI/180F;
			this.leftLeg.xRot = 10.0F * Mth.PI/180F;


		} else
		{
			super.setupAnim(knightState);
		}
	}
}