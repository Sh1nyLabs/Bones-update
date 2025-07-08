package com.sh1nylabs.bonesupdate.common.client.models;

/* Java class written by sh1nylabs' team, using Blockbench 4.7.4. All rights reserved. */

import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.entities.necromancy.Necromancer;
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
import org.joml.Quaternionf;

public class NecromancerModel extends EntityModel<Necromancer> implements ArmedModel {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BonesUpdate.MODID, "necromancermodel"), "main");
	private final ModelPart head;
	private final ModelPart hat;
	private final ModelPart nose;
	private final ModelPart body;
	private final ModelPart left_arm;
	private final ModelPart right_arm;
	private final ModelPart left_leg;
	private final ModelPart right_leg;

	public NecromancerModel(ModelPart root) {
		this.head = root.getChild("head");
		this.hat = root.getChild("hat");
		this.nose = root.getChild("nose");
		this.body = root.getChild("body");
		this.left_arm = root.getChild("left_arm");
		this.right_arm = root.getChild("right_arm");
		this.left_leg = root.getChild("left_leg");
		this.right_leg = root.getChild("right_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(29, 19).addBox(-4.0F, -10.0F, -5.0F, 8.0F, 6.0F, 9.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

		PartDefinition horns = hat.addOrReplaceChild("horns", CubeListBuilder.create().texOffs(48, 34).addBox(2.0F, -0.75F, -6.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(-0.2F))
		.texOffs(37, 36).addBox(-4.0F, -0.75F, -6.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, -10.0F, -4.0F, -0.1745F, 0.0F, 0.0F));

		PartDefinition nose = partdefinition.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(31, 46).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 0).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(32, 0).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 12.0F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		hat.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		nose.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		left_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		right_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		left_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		right_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}

	@Override
	public void setupAnim(Necromancer necromancer, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		boolean flag = necromancer.getFallFlyingTicks() > 4;
		this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
		if (flag) {
			this.head.xRot = (-(float)Math.PI / 4F);

		} else {
			this.head.xRot = headPitch * ((float)Math.PI / 180F);

		}

		this.body.yRot = 0.0F;
		float f = 1.0F;
		if (flag) {
			f = (float)necromancer.getDeltaMovement().lengthSqr();
			f /= 0.2F;
			f *= f * f;
		}

		if (f < 1.0F) {
			f = 1.0F;
		}

		float period=0.667F; //0.6662F
		this.right_arm.xRot = Mth.cos(limbSwing * period + (float)Math.PI) * limbSwingAmount / f;
		this.left_arm.xRot = Mth.cos(limbSwing * period) * limbSwingAmount / f;
		this.right_arm.zRot = 0.0F;
		this.left_arm.zRot = 0.0F;
		this.right_leg.xRot = Mth.cos(limbSwing * period) * 1.4F * limbSwingAmount / f;
		this.left_leg.xRot = Mth.cos(limbSwing * period + (float)Math.PI) * 1.4F * limbSwingAmount / f;
		this.right_leg.yRot = 0.005F;
		this.left_leg.yRot = -0.005F;
		this.right_leg.zRot = 0.005F;
		this.left_leg.zRot = -0.005F;

		this.right_arm.yRot = 0.0F;
		this.left_arm.yRot = 0.0F;

		this.nose.xRot = this.head.xRot;
		this.nose.yRot = this.head.yRot;

		this.hat.xRot = this.head.xRot;
		this.hat.yRot = this.head.yRot;
		this.hat.zRot = this.head.zRot;

		if (necromancer.isCastingSpell()) {
			this.right_arm.xRot=-3F*Mth.PI / 5F + 0.25F* Mth.cos(0.45F * necromancer.tickCount);
		}
	}

	@Override
	public void translateToHand(HumanoidArm arm, PoseStack stack) {
		ModelPart armPart= (arm == HumanoidArm.LEFT) ? this.left_arm : this.right_arm;
		stack.translate(armPart.x / 16.0F, armPart.y/16.0F, armPart.z / 16.0F);
		if (armPart.xRot != 0.0F || armPart.yRot != 0.0F || armPart.zRot != 0.0F) {
			stack.mulPose((new Quaternionf()).rotationZYX(armPart.zRot, armPart.yRot, armPart.xRot));
		}
		stack.scale(0.85F,0.85F,0.85F);
	}
}