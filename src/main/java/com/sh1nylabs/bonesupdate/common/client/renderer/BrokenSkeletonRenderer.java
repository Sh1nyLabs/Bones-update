package com.sh1nylabs.bonesupdate.common.client.renderer;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.sh1nylabs.bonesupdate.common.client.models.BrokenSkeletonModel;
import com.sh1nylabs.bonesupdate.common.client.render_states.BrokenSkeletonRenderState;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.BrokenSkeleton;
import com.sh1nylabs.bonesupdate.registerer.BUModIdentifier;
import com.sh1nylabs.bonesupdate.registerer.BonesRegistry;
import net.minecraft.util.Util;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;

import java.util.Map;

import static com.sh1nylabs.bonesupdate.common.client.renderer.BUModelLayerLocation.getLayerLocation;

public class BrokenSkeletonRenderer extends MobRenderer<BrokenSkeleton, BrokenSkeletonRenderState, BrokenSkeletonModel<BrokenSkeletonRenderState>>{

    private static final Map<String, Identifier> LOCATION_BY_SKELETON = Util.make(Maps.newHashMap(), (map) -> {
        map.put(EntityType.SKELETON.toString(), BUModIdentifier.fromModNamespace("textures/entity/broken_skeleton/skeleton.png"));
        map.put(EntityType.STRAY.toString(), BUModIdentifier.fromModNamespace("textures/entity/broken_skeleton/stray.png"));
        map.put(EntityType.WITHER_SKELETON.toString(), BUModIdentifier.fromModNamespace("textures/entity/broken_skeleton/wither_skeleton.png"));
        map.put(EntityType.BOGGED.toString(), BUModIdentifier.fromModNamespace("textures/entity/broken_skeleton/bogged.png"));
        map.put(EntityType.PARCHED.toString(), BUModIdentifier.fromModNamespace("textures/entity/broken_skeleton/parched.png"));
        map.put(BonesRegistry.HAUNTER_SKELETON.type().toString(), BUModIdentifier.fromModNamespace("textures/entity/broken_skeleton/haunter_skeleton.png"));
        map.put(BonesRegistry.KNIGHT_SKELETON.type().toString(), BUModIdentifier.fromModNamespace("textures/entity/broken_skeleton/knight_skeleton.png"));
    });

    public BrokenSkeletonRenderer(EntityRendererProvider.Context context) {
        super(context,new BrokenSkeletonModel<>(context.bakeLayer(getLayerLocation(BonesRegistry.BROKEN_SKELETON))), 0.5f);
        this.addLayer(new ItemInHandLayer<BrokenSkeletonRenderState, BrokenSkeletonModel<BrokenSkeletonRenderState>>(this) {
            public void submit(
                    PoseStack poseStack, SubmitNodeCollector nodeCollector, int i1, BrokenSkeletonRenderState skeleton, float f1, float f2
            ) {
                if (skeleton.skeletonType == BonesRegistry.HAUNTER_SKELETON.type()) {
                    super.submit(poseStack, nodeCollector, i1, skeleton, f1, f2);
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
    public Identifier getTextureLocation(BrokenSkeletonRenderState skeleton) {
        return LOCATION_BY_SKELETON.getOrDefault(skeleton.skeletonType.toString(), BUModIdentifier.fromModNamespace("textures/entity/broken_skeleton/skeleton.png"));
    }
}