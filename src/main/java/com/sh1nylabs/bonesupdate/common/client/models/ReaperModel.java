package com.sh1nylabs.bonesupdate.common.client.models;

/* Java class written by sh1nylabs' team, using Blockbench 4.7.4. All rights reserved. */

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.entities.necromancy.Reaper;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ReaperModel extends EntityModel<Reaper> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BonesUpdate.MODID, "reapermodel"), "main");
	private final ModelPart head;
	private final ModelPart headwear;
	private final ModelPart body;
	private final ModelPart armor;
	private final ModelPart right_arm;
	private final ModelPart left_arm;

	public ReaperModel(ModelPart root) {
		this.head = root.getChild("head");
		this.headwear = root.getChild("headwear");
		this.body = root.getChild("body");
		this.armor = root.getChild("armor");
		this.right_arm = root.getChild("right_arm");
		this.left_arm = root.getChild("left_arm");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.0F, 0.0F));
		partdefinition.addOrReplaceChild("headwear", CubeListBuilder.create().texOffs(26, 25).addBox(-4.0F, -8.0F, -5.0F, 8.0F, 8.0F, 9.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 9.0F, 0.0F));
		partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.0F, 0.0F));
		partdefinition.addOrReplaceChild("armor", CubeListBuilder.create().texOffs(1, 46).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 14.0F, 4.0F, new CubeDeformation(0.4F)), PartPose.offset(0.0F, 9.0F, 0.0F));
		partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(24, 16).addBox(-2.4001F, -1.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(35, -4).addBox(-1.0F, 5.0F, -1.0F, 0.0F, 7.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 10.0F, 0.0F));
		partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(24, 16).addBox(0.4001F, -1.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(35, -4).addBox(2.0F, 5.0F, -1.0F, 0.0F, 7.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 10.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Reaper reaper, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		boolean flag = reaper.getFallFlyingTicks() > 4;
		this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
		if (flag) {
			this.head.xRot = (-(float)Math.PI / 4F);

		} else {
			this.head.xRot = headPitch * ((float)Math.PI / 180F);

		}

		this.body.yRot = 0.0F;
		float f = 1.0F;
		if (flag) {
			f = (float) reaper.getDeltaMovement().lengthSqr();
			f /= 0.2F;
			f *= f * f;
		}

		if (f < 1.0F) {
			f = 1.0F;
		}

		float offset = Mth.cos(ageInTicks/25.0F) * 1.1F; /* Used to 'float' in the air */

		this.head.y = 9.0F + offset;
		this.headwear.y = 9.0F + offset;
		this.body.y = 9.0F +  offset;
		this.armor.y = 9.0F +  offset;
		this.right_arm.y = 10.0F +  offset;
		this.left_arm.y = 10.0F +  offset;

		this.body.xRot = 0.6F * (Mth.sqrt(limbSwingAmount) + 0.2F + 0.4F * Mth.cos(ageInTicks/10.0F));
		this.armor.xRot = this.body.xRot;
		this.right_arm.xRot = 0.6F * this.body.xRot;
		this.left_arm.xRot = 0.6F * this.body.xRot;
		this.right_arm.zRot = 0.0F;
		this.left_arm.zRot = 0.0F;

		this.headwear.xRot = this.head.xRot;
		this.headwear.yRot = this.head.yRot;
		this.headwear.zRot = this.head.zRot;
		this.setupAttackAnimation(reaper, ageInTicks);
	}

	private void setupAttackAnimation(Reaper reaper, float ageInTicks) {
		if (this.attackTime > 0.0F) {
			this.right_arm.xRot = -1.1F * (1.0F - Mth.cos(attackTime * Mth.PI));
			this.right_arm.zRot = -0.8F;
			this.left_arm.xRot = -1.1F * (1.0F - Mth.cos(attackTime * Mth.PI));
			this.left_arm.zRot = 0.8F;
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		headwear.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		armor.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		right_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		left_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}
}