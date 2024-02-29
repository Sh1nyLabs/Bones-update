package com.sh1nylabs.bonesupdate.common.client.renderer;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.client.models.BrokenSkeletonModel;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.BrokenSkeleton;
import net.minecraft.Util;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.Markings;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;

import java.util.List;
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
    private static final Map<BrokenSkeleton.SkeletonVariants, ResourceLocation> LOCATION_BY_VARIANT = Util.make(Maps.newEnumMap(BrokenSkeleton.SkeletonVariants.class), (map) -> {
        map.put(BrokenSkeleton.SkeletonVariants.NONE, null);
        map.put(BrokenSkeleton.SkeletonVariants.SKELETON, new ResourceLocation("textures/entity/skeleton/skeleton.png"));
        map.put(BrokenSkeleton.SkeletonVariants.STRAY, new ResourceLocation("textures/entity/skeleton/stray.png"));
        map.put(BrokenSkeleton.SkeletonVariants.WITHER_SKELETON, new ResourceLocation("textures/entity/skeleton/wither_skeleton.png"));
    });

    public BrokenSkeletonVariantLayer(RenderLayerParent<BrokenSkeleton, BrokenSkeletonModel<BrokenSkeleton>> skeleton) {
        super(skeleton);
    }

    public void render(PoseStack p_117058_, MultiBufferSource p_117059_, int p_117060_, BrokenSkeleton skeleton, float p_117062_, float p_117063_, float p_117064_, float p_117065_, float p_117066_, float p_117067_) {
        ResourceLocation resourcelocation = LOCATION_BY_VARIANT.get(skeleton.getVariant());
        if (resourcelocation != null && !skeleton.isInvisible()) {
            VertexConsumer vertexconsumer = p_117059_.getBuffer(RenderType.entityTranslucent(resourcelocation));
            this.getParentModel().renderToBuffer(p_117058_, vertexconsumer, p_117060_, LivingEntityRenderer.getOverlayCoords(skeleton, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}