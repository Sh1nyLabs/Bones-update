package com.sh1nylabs.bonesupdate.common.client.models;

/* Java class written by sh1nylabs' team, using Blockbench 4.7.4. All rights reserved. */

import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.client.render_states.ReaperRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ReaperModel extends EntityModel<ReaperRenderState> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BonesUpdate.MODID, "reapermodel"), "main");
	private final ModelPart head;
	private final ModelPart headwear;
	private final ModelPart body;
	private final ModelPart armor;
	private final ModelPart right_arm;
	private final ModelPart left_arm;

	public ReaperModel(ModelPart root) {
		super(root);
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
	public void setupAnim(ReaperRenderState reaper) {

		boolean flag = reaper.isFallFlying;
		this.head.yRot = reaper.yRot * ((float)Math.PI / 180F);
		if (flag) {
			this.head.xRot = (-(float)Math.PI / 4F);

		} else {
			this.head.xRot = reaper.xRot * ((float)Math.PI / 180F);

		}

		this.body.yRot = 0.0F;

		float offset = Mth.cos(reaper.ageInTicks/25.0F) * 1.1F; // Used to 'float' in the air

		this.head.y = 9.0F + offset;
		this.headwear.y = 9.0F + offset;
		this.body.y = 9.0F +  offset;
		this.armor.y = 9.0F +  offset;
		this.right_arm.y = 10.0F +  offset;
		this.left_arm.y = 10.0F +  offset;

		this.body.xRot = (0.6F * ((float) Math.atan(reaper.walkAnimationSinceLastStop * 1.5F) + 0.2F + 0.4F * Mth.cos(reaper.ageInTicks/10.0F)));
		this.armor.xRot = this.body.xRot;
		this.right_arm.xRot = 0.6F * this.body.xRot;
		this.left_arm.xRot = 0.6F * this.body.xRot;
		this.right_arm.zRot = 0.0F;
		this.left_arm.zRot = 0.0F;

		this.headwear.xRot = this.head.xRot;
		this.headwear.yRot = this.head.yRot;
		this.headwear.zRot = this.head.zRot;
		this.setupAttackAnimation(reaper);
	}

	private void setupAttackAnimation(ReaperRenderState reaper) {

		if (reaper.attackTime > 0.0F) {
			this.right_arm.xRot = -1.1F * (1.0F - Mth.cos(reaper.attackTime * Mth.PI));
			this.right_arm.zRot = -0.8F;
			this.left_arm.xRot = -1.1F * (1.0F - Mth.cos(reaper.attackTime * Mth.PI));
			this.left_arm.zRot = 0.8F;
		}
	}
}