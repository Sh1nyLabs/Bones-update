package com.sh1nylabs.bonesupdate.common.client.renderer;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.common.client.models.HaunterSkeletonModel;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.HaunterSkeleton;
import com.sh1nylabs.bonesupdate.registerer.BonesRegistry;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.ResourceLocation;

public class HaunterSkeletonRenderer extends HumanoidMobRenderer<HaunterSkeleton, HumanoidRenderState, HaunterSkeletonModel> {
    public HaunterSkeletonRenderer(EntityRendererProvider.Context context) {
        super(context, new HaunterSkeletonModel(context.bakeLayer(BonesRegistry.HAUNTER_SKELETON.modelLayerLocation())), 0.5f);
        this.addLayer(new ItemInHandLayer<>(this));
    }

    @Override
    public HumanoidRenderState createRenderState() {
        return new HumanoidRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(HumanoidRenderState entity) {
        return BonesRegistry.HAUNTER_SKELETON.textureLocation();
    }

}
