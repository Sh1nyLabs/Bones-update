package com.sh1nylabs.bonesupdate.common.client.renderer;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.client.models.KnightSkeletonModel;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.KnightSkeleton;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class KnightSkeletonRenderer extends MobRenderer<KnightSkeleton, KnightSkeletonModel> {

    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(BonesUpdate.MODID,"textures/entity/knight_skeleton.png");

    public KnightSkeletonRenderer(EntityRendererProvider.Context context) {
        super(context,new KnightSkeletonModel(context.bakeLayer(KnightSkeletonModel.LAYER_LOCATION)), 0.5f);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
    }

    public ResourceLocation getTextureLocation(KnightSkeleton entity) {
        return TEXTURE;
    }
}
