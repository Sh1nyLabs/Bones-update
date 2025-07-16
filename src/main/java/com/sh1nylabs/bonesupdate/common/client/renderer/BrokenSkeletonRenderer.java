package com.sh1nylabs.bonesupdate.common.client.renderer;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.client.models.BrokenSkeletonModel;
import com.sh1nylabs.bonesupdate.common.client.render_states.BrokenSkeletonRenderState;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.BrokenSkeleton;
import com.sh1nylabs.bonesupdate.registerer.BonesRegistry;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

import java.util.Map;

public class BrokenSkeletonRenderer extends MobRenderer<BrokenSkeleton, BrokenSkeletonRenderState, BrokenSkeletonModel<BrokenSkeletonRenderState>>{

    private static final Map<String, ResourceLocation> LOCATION_BY_SKELETON = Util.make(Maps.newHashMap(), (map) -> {
        map.put(EntityType.SKELETON.toString(), ResourceLocation.withDefaultNamespace("textures/entity/skeleton/skeleton.png"));
        map.put(EntityType.STRAY.toString(), ResourceLocation.withDefaultNamespace("textures/entity/skeleton/stray.png"));
        map.put(EntityType.WITHER_SKELETON.toString(), ResourceLocation.withDefaultNamespace("textures/entity/skeleton/wither_skeleton.png"));
        map.put(EntityType.BOGGED.toString(), ResourceLocation.withDefaultNamespace("textures/entity/skeleton/bogged.png"));
        map.put(BonesRegistry.HAUNTER_SKELETON.type().toString(), ResourceLocation.fromNamespaceAndPath(BonesUpdate.MODID,"textures/entity/haunter_skeleton.png"));
        map.put(BonesRegistry.KNIGHT_SKELETON.type().toString(), ResourceLocation.fromNamespaceAndPath(BonesUpdate.MODID,"textures/entity/knight_skeleton.png"));
    });

    public BrokenSkeletonRenderer(EntityRendererProvider.Context context) {
        super(context,new BrokenSkeletonModel<>(context.bakeLayer(BonesRegistry.BROKEN_SKELETON.modelLayerLocation())), 0.5f);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemRenderer()) {
            public void render(
                    PoseStack p_116330_, MultiBufferSource p_116331_, int p_116332_, BrokenSkeletonRenderState skeleton, float p_116334_, float p_116335_
            ) {
                if (skeleton.skeletonType == BonesRegistry.HAUNTER_SKELETON.type()) {
                    super.render(p_116330_, p_116331_, p_116332_, skeleton, p_116334_, p_116335_);
                }
            }
        });
    }

    @Override
    public BrokenSkeletonRenderState createRenderState() {
        return new BrokenSkeletonRenderState();
    }

    @Override
    public void extractRenderState(BrokenSkeleton brokenSkeleton, BrokenSkeletonRenderState brokenSkeletonRenderState, float value) {
        super.extractRenderState(brokenSkeleton, brokenSkeletonRenderState, value);
        brokenSkeletonRenderState.skeletonType = brokenSkeleton.getSkeletonType();
        brokenSkeletonRenderState.boggedIsSheared = brokenSkeleton.boggedIsSheared();
    }

    @Override
    public ResourceLocation getTextureLocation(BrokenSkeletonRenderState skeleton) {
        return LOCATION_BY_SKELETON.getOrDefault(skeleton.skeletonType.toString(), ResourceLocation.withDefaultNamespace("textures/entity/skeleton/skeleton.png"));
    }
}