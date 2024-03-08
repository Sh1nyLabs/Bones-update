package com.sh1nylabs.bonesupdate.common.client.renderer;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.client.models.BrokenSkeletonModel;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.BrokenSkeleton;
import com.sh1nylabs.bonesupdate.init.BonesEntities;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import java.util.Map;

public class BrokenSkeletonRenderer extends MobRenderer<BrokenSkeleton, BrokenSkeletonModel<BrokenSkeleton>>{

    public static final ResourceLocation BASIS_TEXTURE = new ResourceLocation(BonesUpdate.MODID,"textures/entity/broken_skeleton.png");
    public BrokenSkeletonRenderer(EntityRendererProvider.Context context) {
        super(context,new BrokenSkeletonModel<>(context.bakeLayer(BrokenSkeletonModel.LAYER_LOCATION)), 0.5f);
        this.addLayer(new BrokenSkeletonVariantLayer(this));
    }

    public ResourceLocation getTextureLocation(BrokenSkeleton entity) {
    return BASIS_TEXTURE;
    }

}


@OnlyIn(Dist.CLIENT)
class BrokenSkeletonVariantLayer extends RenderLayer<BrokenSkeleton, BrokenSkeletonModel<BrokenSkeleton>> {
    private static final Map<String, ResourceLocation> LOCATION_BY_SKELETON = Util.make(Maps.newHashMap(), (map) -> {
        map.put(EntityType.SKELETON.toString(), new ResourceLocation("textures/entity/skeleton/skeleton.png"));
        map.put(EntityType.STRAY.toString(), new ResourceLocation("textures/entity/skeleton/stray.png"));
        map.put(EntityType.WITHER_SKELETON.toString(), new ResourceLocation("textures/entity/skeleton/wither_skeleton.png"));
        map.put(BonesEntities.HAUNTER_SKELETON.get().toString(), new ResourceLocation(BonesUpdate.MODID,"textures/entity/haunter_skeleton.png"));
        map.put(BonesEntities.KNIGHT_SKELETON.get().toString(), new ResourceLocation(BonesUpdate.MODID,"textures/entity/knight_skeleton.png"));
    });
    public BrokenSkeletonVariantLayer(RenderLayerParent<BrokenSkeleton, BrokenSkeletonModel<BrokenSkeleton>> skeleton) {
        super(skeleton);
    }

    public void render(PoseStack p_117058_, MultiBufferSource p_117059_, int p_117060_, BrokenSkeleton skeleton, float p_117062_, float p_117063_, float p_117064_, float p_117065_, float p_117066_, float p_117067_) {
        ResourceLocation resourcelocation = LOCATION_BY_SKELETON.get(skeleton.getSkeletonTypeString());
        if (resourcelocation != null && !skeleton.isInvisible()) {
            VertexConsumer vertexconsumer = p_117059_.getBuffer(RenderType.entityTranslucent(resourcelocation));
            this.getParentModel().renderToBuffer(p_117058_, vertexconsumer, p_117060_, LivingEntityRenderer.getOverlayCoords(skeleton, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}