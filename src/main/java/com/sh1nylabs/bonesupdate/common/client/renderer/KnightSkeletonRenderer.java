package com.sh1nylabs.bonesupdate.common.client.renderer;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.common.client.models.KnightSkeletonModel;
import com.sh1nylabs.bonesupdate.common.client.render_states.KnightSkeletonRenderState;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.KnightSkeleton;
import com.sh1nylabs.bonesupdate.registerer.BonesRegistry;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class KnightSkeletonRenderer extends HumanoidMobRenderer<KnightSkeleton, KnightSkeletonRenderState, KnightSkeletonModel> {


    public KnightSkeletonRenderer(EntityRendererProvider.Context context) {
        super(context,new KnightSkeletonModel(context.bakeLayer(BonesRegistry.KNIGHT_SKELETON.modelLayerLocation())), 0.5f);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemRenderer()));
    }

    @Override
    public KnightSkeletonRenderState createRenderState() {
        return new KnightSkeletonRenderState();
    }

    @Override
    public void extractRenderState(KnightSkeleton knight, KnightSkeletonRenderState knightRenderState, float value) {
        super.extractRenderState(knight, knightRenderState, value);
        knightRenderState.isDashing = knight.isDashing();
    }

    @Override
    public ResourceLocation getTextureLocation(KnightSkeletonRenderState entity) {
        return BonesRegistry.KNIGHT_SKELETON.textureLocation();
    }

}
