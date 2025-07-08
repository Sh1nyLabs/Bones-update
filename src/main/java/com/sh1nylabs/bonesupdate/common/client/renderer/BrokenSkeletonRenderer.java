package com.sh1nylabs.bonesupdate.common.client.renderer;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.client.models.BrokenSkeletonModel;
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

public class BrokenSkeletonRenderer extends MobRenderer<BrokenSkeleton, BrokenSkeletonModel<BrokenSkeleton>>{
    private static final Map<String, ResourceLocation> LOCATION_BY_SKELETON = Util.make(Maps.newHashMap(), (map) -> {
        map.put(EntityType.SKELETON.toString(), new ResourceLocation("textures/entity/skeleton/skeleton.png"));
        map.put(EntityType.STRAY.toString(), new ResourceLocation("textures/entity/skeleton/stray.png"));
        map.put(EntityType.WITHER_SKELETON.toString(), new ResourceLocation("textures/entity/skeleton/wither_skeleton.png"));
        map.put(BonesRegistry.HAUNTER_SKELETON.type().toString(), new ResourceLocation(BonesUpdate.MODID,"textures/entity/haunter_skeleton.png"));
        map.put(BonesRegistry.KNIGHT_SKELETON.type().toString(), new ResourceLocation(BonesUpdate.MODID,"textures/entity/knight_skeleton.png"));
    });

    public BrokenSkeletonRenderer(EntityRendererProvider.Context context) {
        super(context,new BrokenSkeletonModel<>(context.bakeLayer(BrokenSkeletonModel.LAYER_LOCATION)), 0.5f);
        this.addLayer(new ItemInHandLayer<BrokenSkeleton, BrokenSkeletonModel<BrokenSkeleton>>(this, context.getItemInHandRenderer()) {
            public void render(PoseStack stack, MultiBufferSource bufferSource, int int1, BrokenSkeleton skeleton, float float1, float float2, float float3, float float4, float float5, float float6) {
                if (skeleton.getSkeletonType() == BonesRegistry.HAUNTER_SKELETON.type()) {
                    super.render(stack, bufferSource, int1, skeleton, float1, float2, float3, float4, float5, float6);
                }
            }
        });
    }

    public ResourceLocation getTextureLocation(BrokenSkeleton skeleton) {
        return LOCATION_BY_SKELETON.get(skeleton.getSkeletonTypeString());
    }
}