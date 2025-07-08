package com.sh1nylabs.bonesupdate.common.client.models;

/* Java class written by sh1nylabs' team, using Blockbench 4.7.4. All rights reserved. */

import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.Minion;
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

public class MinionModel extends EntityModel<Minion> implements ArmedModel {

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BonesUpdate.MODID, "minion_model"), "main");
	private final ModelPart hat;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart right_leg;
	private final ModelPart left_leg;
	private final ModelPart left_arm;
	private final ModelPart right_arm;

	public MinionModel(ModelPart root) {
		this.hat = root.getChild("hat");
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.right_leg = root.getChild("right_leg");
		this.left_leg = root.getChild("left_leg");
		this.left_arm = root.getChild("left_arm");
		this.right_arm = root.getChild("right_arm");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(5, 20).addBox(-2.0F, -4.002F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 13.0F, 0.0F));
		partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -4.002F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(13, 0).addBox(-0.5F, -0.5F, -2.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 13.0F, 0.0F));
		partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(4, 9).addBox(-2.0F, 0.999F, -1.0F, 4.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 13.0F, 0.0F));
		partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 17).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 19.0F, 0.0F));
		partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 9).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 19.0F, 0.0F));
		partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(16, 9).addBox(0.001F, 0.5F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 13.5F, 0.0F));
		partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(4, 17).addBox(-1.0001F, 0.5F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 13.5F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}
	@Override
	public void setupAnim(Minion entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		boolean flag = entity.getFallFlyingTicks() > 4;
		this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
		if (flag) {
			this.head.xRot = (-(float)Math.PI / 4F);
		} else {
			this.head.xRot = headPitch * ((float)Math.PI / 180F);
		}

		this.body.yRot = 0.0F;
		this.right_arm.z = 0.0F;
		this.right_arm.x = 3.0F;
		this.left_arm.z = 0.0F;
		this.left_arm.x = -3.0F;
		float f = 1.0F;
		if (flag) {
			f = (float)entity.getDeltaMovement().lengthSqr();
			f /= 0.2F;
			f *= f * f;
		}

		if (f < 1.0F) {
			f = 1.0F;
		}

		float period=0.95F; //0.6662F
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

		if (entity.isAggressive()) {
			this.right_arm.xRot = -3F*Mth.PI/5F;
			this.right_arm.yRot = 0.1F;
		}


		this.setupAttackAnimation(entity, ageInTicks);

	}

	protected void setupAttackAnimation(Minion entity, float ageInTicks) {
		if (!(this.attackTime <= 0.0F)) {
			HumanoidArm humanoidarm = this.getAttackArm(entity);
			ModelPart modelpart = this.getArm(humanoidarm);
			float f = this.attackTime;
			this.body.yRot = Mth.sin(Mth.sqrt(f) * ((float)Math.PI * 2F)) * 0.2F;
			if (humanoidarm == HumanoidArm.LEFT) {
				this.body.yRot *= -1.0F;
			}

			this.right_arm.yRot += this.body.yRot;
			this.left_arm.yRot += this.body.yRot;
			this.left_arm.xRot += this.body.yRot;
			f = 1.0F - this.attackTime;
			f *= f;
			f *= f;
			f = 1.0F - f;
			float f1 = Mth.sin(f * (float)Math.PI);
			float f2 = Mth.sin(this.attackTime * (float)Math.PI) * -(this.head.xRot - 0.7F) * 0.75F;
			modelpart.xRot -= f1 * 1.2F + f2;
			modelpart.yRot += this.body.yRot * 2.0F;
			modelpart.zRot += Mth.sin(this.attackTime * (float)Math.PI) * -0.4F;
		}
	}

	private HumanoidArm getAttackArm(Minion entity) {
		HumanoidArm humanoidarm = entity.getMainArm();
		return entity.swingingArm == InteractionHand.MAIN_HAND ? humanoidarm : humanoidarm.getOpposite();
	}

	private ModelPart getArm(HumanoidArm arm) {
		return arm == HumanoidArm.LEFT ? this.left_arm : this.right_arm;
	}

	@Override
	public void translateToHand(HumanoidArm arm, PoseStack stack) {
		ModelPart armPart= (arm == HumanoidArm.LEFT) ? this.left_arm : this.right_arm;
		stack.translate(armPart.x / 16.0F, armPart.y/16.0F, armPart.z / 16.0F);
		if (armPart.xRot != 0.0F || armPart.yRot != 0.0F || armPart.zRot != 0.0F) {
			stack.mulPose((new Quaternionf()).rotationZYX(armPart.zRot, armPart.yRot, armPart.xRot));
		}
		stack.scale(0.45F,0.45F,0.45F);
	}


	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		hat.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		right_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		left_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		left_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		right_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}
}