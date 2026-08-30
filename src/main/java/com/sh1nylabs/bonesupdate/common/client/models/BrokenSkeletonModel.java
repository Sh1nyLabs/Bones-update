package com.sh1nylabs.bonesupdate.common.client.models;

/* Java class written by sh1nylabs' team, using Blockbench 4.7.4. All rights reserved. */

import com.mojang.blaze3d.vertex.PoseStack;
import com.sh1nylabs.bonesupdate.common.client.render_states.BrokenSkeletonRenderState;
import com.sh1nylabs.bonesupdate.registerer.BonesRegistry;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HumanoidArm;
import org.joml.Quaternionf;

public class BrokenSkeletonModel<T extends BrokenSkeletonRenderState> extends EntityModel<BrokenSkeletonRenderState> implements ArmedModel<T> {
	private final ModelPart broken_state;
	private final ModelPart outer_clothes;
	private final ModelPart haunter_parts;
	private final ModelPart right_hand;
	private final ModelPart mushrooms;

	public BrokenSkeletonModel(ModelPart root) {
        super(root);
        this.broken_state = root.getChild("broken_state");
		this.outer_clothes = root.getChild("outer_clothes");
		this.haunter_parts = root.getChild("haunter_parts");
		this.right_hand = root.getChild("right_hand");
		this.mushrooms = root.getChild("mushrooms");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition broken_state = partdefinition.addOrReplaceChild("broken_state", CubeListBuilder.create().texOffs(0, 0).addBox(1.0F, -7.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 23.0F, 0.0F));

		PartDefinition body_r1 = broken_state.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(16, 16).addBox(0.0F, -10.0F, -4.5F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, 0.0F, -3.0F, -1.1961F, 0.2291F, -0.5236F));

		PartDefinition outer_clothes = partdefinition.addOrReplaceChild("outer_clothes", CubeListBuilder.create().texOffs(0, 32).addBox(1.0F, -7.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 23.0F, 0.0F));

		PartDefinition bodywear_r1 = outer_clothes.addOrReplaceChild("bodywear_r1", CubeListBuilder.create().texOffs(16, 48).addBox(0.0F, -10.0F, -4.5F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-6.0F, 0.0F, -3.0F, -1.1961F, 0.2291F, -0.5236F));

		PartDefinition haunter_parts = partdefinition.addOrReplaceChild("haunter_parts", CubeListBuilder.create().texOffs(47, 7).mirror().addBox(1.999F, -4.0F, -8.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(36, 3).mirror().addBox(3.5F, -1.5F, -7.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition left_spine2_r1 = haunter_parts.addOrReplaceChild("left_spine2_r1", CubeListBuilder.create().texOffs(35, 7).mirror().addBox(-0.5F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, -3.5F, -4.5F, 0.0F, -0.3927F, -0.6981F));

		PartDefinition left_spine1_r1 = haunter_parts.addOrReplaceChild("left_spine1_r1", CubeListBuilder.create().texOffs(35, 7).mirror().addBox(-0.5F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, -3.5F, -7.5F, 0.0F, 0.3927F, -0.6981F));

		PartDefinition left_bracelet_r1 = haunter_parts.addOrReplaceChild("left_bracelet_r1", CubeListBuilder.create().texOffs(47, 3).addBox(4.0F, -1.0F, -4.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-10.0F, 0.0F, 0.0F, 0.0F, -0.48F, 0.0F));

		PartDefinition right_hand = partdefinition.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(32, 0).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 23.5F, 6.0F));

		PartDefinition mushrooms = partdefinition.addOrReplaceChild("mushrooms", CubeListBuilder.create().texOffs(50, 0).addBox(0.0F, -35.0F, 3.0F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(50, 0).addBox(3.0F, -35.0F, 0.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(50, 0).addBox(-6.0F, -35.0F, -3.0F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(50, 0).addBox(-3.0F, -35.0F, -6.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(50, 0).addBox(-5.0F, -28.0F, 5.0F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(50, 0).addBox(-2.0F, -28.0F, 2.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 48.0F, 0.0F, 0.0F, -0.3927F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(BrokenSkeletonRenderState entity) {
		this.haunter_parts.visible = entity.skeletonType == BonesRegistry.HAUNTER_SKELETON.type();
		this.mushrooms.visible = entity.skeletonType == EntityType.BOGGED && !entity.boggedIsSheared;
		this.outer_clothes.visible = entity.skeletonType == EntityType.PARCHED || entity.skeletonType == EntityType.BOGGED;

		super.setupAnim(entity);
	}

	@Override
	public void translateToHand(BrokenSkeletonRenderState renderState, HumanoidArm arm, PoseStack stack) {
		ModelPart armPart = this.right_hand;
		stack.translate((armPart.x+1.0F) / 16.0F, (armPart.y-9.5F) / 16.0F, (armPart.z+1.5F) / 16.0F);
		if (armPart.xRot != 0.0F || armPart.yRot != 0.0F || armPart.zRot != 0.0F) {
			stack.mulPose((new Quaternionf()).rotationZYX(armPart.zRot, armPart.yRot, armPart.xRot));
		}

		if (armPart.xScale != 1.0F || armPart.yScale != 1.0F || armPart.zScale != 1.0F) {
			stack.scale(0.85F,0.85F,0.85F);
		}
	}
}
