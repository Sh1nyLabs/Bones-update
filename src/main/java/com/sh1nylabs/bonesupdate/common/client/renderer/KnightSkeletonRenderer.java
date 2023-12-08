package com.sh1nylabs.bonesupdate.common.client.renderer;

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

    public static final ResourceLocation TEXTURE=new ResourceLocation(BonesUpdate.MODID,"textures/entity/knight_skeleton.png");

    public KnightSkeletonRenderer(EntityRendererProvider.Context context) {
        super(context,new KnightSkeletonModel(context.bakeLayer(KnightSkeletonModel.LAYER_LOCATION)), 0.5f);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()) {
            public void render(PoseStack poseStack, MultiBufferSource source, int p_116354_, KnightSkeleton knightSkeleton, float p_116356_, float p_116357_, float p_116358_, float p_116359_, float p_116360_, float p_116361_) {
                if (!knightSkeleton.isBroken()) {
                    super.render(poseStack, source, p_116354_, knightSkeleton, p_116356_, p_116357_, p_116358_, p_116359_, p_116360_, p_116361_);
                }
            }
        });
    }

    public ResourceLocation getTextureLocation(KnightSkeleton entity) {
        return TEXTURE;
    }
}
