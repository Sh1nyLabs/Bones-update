package com.sh1nylabs.bonesupdate.common.client.models;

/* Java class written by sh1nylabs' team, using Blockbench 4.7.4. All rights reserved. */

import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.Grabber;
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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import org.joml.Quaternionf;

public class GrabberModel extends EntityModel<Grabber> implements ArmedModel {

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BonesUpdate.MODID, "grabber"), "main");
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart left_arm;
	private final ModelPart right_arm;
	private final ModelPart left_leg;
	private final ModelPart right_leg;
	private final ModelPart pocket;

	public GrabberModel(ModelPart root) {
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.left_arm = root.getChild("left_arm");
		this.right_arm = root.getChild("right_arm");
		this.left_leg = root.getChild("left_leg");
		this.right_leg = root.getChild("right_leg");
		this.pocket = root.getChild("pocket");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(36, 2).addBox(-3.5F, -7.001F, -4.0F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(9, 16).addBox(-5.0F, -11.0F, -2.5F, 10.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 18).mirror().addBox(0.201F, -1.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offset(5.0F, 7.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 18).addBox(-2.0F, -1.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offset(-5.0F, 7.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(3, 2).mirror().addBox(-1.5F, 0.0F, -1.6F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.5F, 16.0F, 0.1F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(3, 2).addBox(-1.5F, 0.0F, -1.6F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 16.0F, 0.1F));

		PartDefinition pocket = partdefinition.addOrReplaceChild("pocket", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition cube_r1 = pocket.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(52, 23).addBox(-2.0F, -2.0F, -1.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -1.0F, -2.0F, 0.0F, 0.0F, -0.1745F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public void setupAnim(Grabber grabber, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		boolean flag = grabber.getFallFlyingTicks() > 4;
		this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
		if (flag) {
			this.head.xRot = (-(float)Math.PI / 4F);
		} else {
			this.head.xRot = headPitch * ((float)Math.PI / 180F);
		}

		this.body.yRot = 0.0F;
		/**
		this.right_arm.z = 0.0F;
		this.right_arm.x = 6.0F;
		this.left_arm.z = 0.0F;
		this.left_arm.x = -6.0F;
		 */
		float f = 1.0F;
		if (flag) {
			f = (float)grabber.getDeltaMovement().lengthSqr();
			f /= 0.2F;
			f *= f * f;
		}

		if (f < 1.0F) {
			f = 1.0F;
		}

		float period=0.9F; //0.6662F
		this.right_arm.xRot = Mth.cos(limbSwing * period + (float)Math.PI) * 2.0F * limbSwingAmount * 0.6F / f;
		this.left_arm.xRot = Mth.cos(limbSwing * period) * 2.0F * limbSwingAmount * 0.9F / f;
		this.right_arm.zRot = 0.0F;
		this.left_arm.zRot = 0.0F;
		this.right_leg.xRot = Mth.cos(limbSwing * period) * 1.7F * limbSwingAmount / f;
		this.left_leg.xRot = Mth.cos(limbSwing * period + (float)Math.PI) * 1.7F * limbSwingAmount / f;
		this.right_leg.yRot = 0.005F;
		this.left_leg.yRot = -0.005F;
		this.right_leg.zRot = 0.005F;
		this.left_leg.zRot = -0.005F;

		this.right_arm.yRot = 0.0F;
		this.left_arm.yRot = 0.0F;

		if (grabber.isCelebratingNewItem()) {
			this.right_arm.xRot = -2.0F + Mth.cos(ageInTicks * 0.15F * (float)Math.PI) * 0.35F / f;
			this.left_arm.xRot = -2.0F - Mth.cos(ageInTicks * 0.15F * (float)Math.PI) * 0.35F / f;
			this.head.zRot = Mth.cos(ageInTicks * 0.15F * (float)Math.PI) * 0.35F / f;
		} else {
			this.head.zRot = 0.0F;
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		left_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		right_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		left_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		right_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		pocket.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}

	@Override
	public void translateToHand(HumanoidArm arm, PoseStack poseStack) {
		ModelPart armPart = this.right_arm;
		poseStack.translate(armPart.x / 16.0F, armPart.y/16.0F, armPart.z / 16.0F);
		if (armPart.xRot != 0.0F || armPart.yRot != 0.0F || armPart.zRot != 0.0F) {
			poseStack.mulPose((new Quaternionf()).rotationZYX(armPart.zRot, armPart.yRot, armPart.xRot));
		}
		poseStack.scale(0.7F, 0.7F, 0.7F);
	}
}