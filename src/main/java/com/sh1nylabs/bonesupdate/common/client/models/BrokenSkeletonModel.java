package com.sh1nylabs.bonesupdate.common.client.models;

/* Java class written by sh1nylabs' team, using Blockbench 4.7.4. All rights reserved. */

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.BrokenSkeleton;
import com.sh1nylabs.bonesupdate.registerer.BonesRegistry;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HumanoidArm;
import org.joml.Quaternionf;

public class BrokenSkeletonModel<T extends BrokenSkeleton> extends EntityModel<T> implements ArmedModel {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BonesUpdate.MODID, "brokenskeletonmodel"), "main");
	private final ModelPart broken_state;
	private final ModelPart haunter_parts;
	private final ModelPart right_hand;
	private final ModelPart mushrooms;

	public BrokenSkeletonModel(ModelPart root) {
		this.broken_state = root.getChild("broken_state");
		this.haunter_parts = root.getChild("haunter_parts");
		this.right_hand = root.getChild("right_hand");
		this.mushrooms = root.getChild("mushrooms");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition broken_state = partdefinition.addOrReplaceChild("broken_state", CubeListBuilder.create().texOffs(0, 0).addBox(1.0F, -7.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 23.0F, 0.0F));

		PartDefinition body_r1 = broken_state.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(16, 16).addBox(0.0F, -10.0F, -4.5F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, 0.0F, -3.0F, -1.1961F, 0.2291F, -0.5236F));

		PartDefinition haunter_parts = partdefinition.addOrReplaceChild("haunter_parts", CubeListBuilder.create().texOffs(47, 7).mirror().addBox(1.999F, -4.0F, -8.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(36, 3).mirror().addBox(3.5F, -1.5F, -7.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition left_spine2_r1 = haunter_parts.addOrReplaceChild("left_spine2_r1", CubeListBuilder.create().texOffs(35, 7).mirror().addBox(-0.5F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, -3.5F, -4.5F, 0.0F, -0.3927F, -0.6981F));

		PartDefinition left_spine1_r1 = haunter_parts.addOrReplaceChild("left_spine1_r1", CubeListBuilder.create().texOffs(35, 7).mirror().addBox(-0.5F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, -3.5F, -7.5F, 0.0F, 0.3927F, -0.6981F));

		PartDefinition left_bracelet_r1 = haunter_parts.addOrReplaceChild("left_bracelet_r1", CubeListBuilder.create().texOffs(47, 3).addBox(4.0F, -1.0F, -4.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-10.0F, 0.0F, 0.0F, 0.0F, -0.48F, 0.0F));

		PartDefinition right_hand = partdefinition.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(33, 19).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 23.5F, 6.0F));

		PartDefinition mushrooms = partdefinition.addOrReplaceChild("mushrooms", CubeListBuilder.create().texOffs(50, 16).addBox(0.0F, -35.0F, 3.0F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(50, 10).addBox(3.0F, -35.0F, 0.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(50, 22).addBox(-6.0F, -35.0F, -3.0F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(50, 16).addBox(-3.0F, -35.0F, -6.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(50, 28).addBox(-5.0F, -28.0F, 5.0F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(50, 22).addBox(-2.0F, -28.0F, 2.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 48.0F, 0.0F, 0.0F, -0.3927F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public void setupAnim(BrokenSkeleton entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if (entity.getSkeletonType() != BonesRegistry.HAUNTER_SKELETON.type()) {
			this.haunter_parts.visible = false;
		}
		this.mushrooms.visible = entity.getSkeletonType() == EntityType.BOGGED && !entity.boggedIsSheared();
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
		broken_state.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		haunter_parts.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		right_hand.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		mushrooms.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}
}