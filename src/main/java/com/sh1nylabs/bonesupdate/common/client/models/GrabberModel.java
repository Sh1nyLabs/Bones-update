package com.sh1nylabs.bonesupdate.common.client.models;

/* Java class written by sh1nylabs' team, using Blockbench 4.7.4. All rights reserved. */

import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.client.render_states.GrabberRenderState;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class GrabberModel extends HumanoidModel<GrabberRenderState> implements ArmedModel {

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BonesUpdate.MODID, "grabber"), "main");

	public GrabberModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(36, 2).addBox(-3.5F, -7.001F, -4.0F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(9, 16).addBox(-5.0F, -11.0F, -2.5F, 10.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 18).mirror().addBox(0.201F, -1.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offset(5.0F, 7.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 18).addBox(-2.0F, -1.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offset(-5.0F, 7.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(3, 2).mirror().addBox(-1.5F, 0.0F, -1.6F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.5F, 16.0F, 0.1F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(3, 2).addBox(-1.5F, 0.0F, -1.6F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 16.0F, 0.1F));

		PartDefinition pocket = partdefinition.addOrReplaceChild("pocket", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition cube_r1 = pocket.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(52, 23).addBox(-2.0F, -2.0F, -1.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -1.0F, -2.0F, 0.0F, 0.0F, -0.1745F));

		partdefinition.clearChild("hat");
		head.clearChild("hat");
		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public void setupAnim(GrabberRenderState grabberState) {
		super.setupAnim(grabberState);

		if (grabberState.celebrating) {
			this.rightArm.xRot = -2.0F + Mth.cos(grabberState.ageInTicks * 0.15F * (float)Math.PI) * 0.35F;
			this.leftArm.xRot = -2.0F - Mth.cos(grabberState.ageInTicks * 0.15F * (float)Math.PI) * 0.35F;
			this.head.zRot = Mth.cos(grabberState.ageInTicks * 0.15F * (float)Math.PI) * 0.35F;
		}
	}
}