package com.sh1nylabs.bonesupdate.common.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.client.models.HaunterSkeletonModel;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.HaunterSkeleton;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class HaunterSkeletonRenderer extends MobRenderer<HaunterSkeleton, HaunterSkeletonModel> {

    public static final ResourceLocation TEXTURE=new ResourceLocation(BonesUpdate.MODID,"textures/entity/haunter.png");

    public HaunterSkeletonRenderer(EntityRendererProvider.Context context) {
        super(context,new HaunterSkeletonModel(context.bakeLayer(HaunterSkeletonModel.LAYER_LOCATION)), 0.5f);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()) {
            public void render(PoseStack poseStack, MultiBufferSource source, int p_116354_, HaunterSkeleton haunter, float p_116356_, float p_116357_, float p_116358_, float p_116359_, float p_116360_, float p_116361_) {
                if (!haunter.isBroken()) {
                    super.render(poseStack, source, p_116354_, haunter, p_116356_, p_116357_, p_116358_, p_116359_, p_116360_, p_116361_);
                }
            }
        });
    }

    public ResourceLocation getTextureLocation(HaunterSkeleton entity) {
        return TEXTURE;
    }
}
