package com.sh1nylabs.bonesupdate.common.client.renderer;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.mojang.blaze3d.vertex.PoseStack;
import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.client.models.GrabberModel;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.Grabber;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;

public class GrabberRenderer extends MobRenderer<Grabber, GrabberModel> {

    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(BonesUpdate.MODID,"textures/entity/grabber.png");

    public GrabberRenderer(EntityRendererProvider.Context context) {
        super(context,new GrabberModel(context.bakeLayer(GrabberModel.LAYER_LOCATION)), 0.5f);
        this.addLayer(new GrabberPocketItemLayer( this, context.getItemInHandRenderer()));
        this.addLayer(new ItemInHandLayer<>( this, context.getItemInHandRenderer()));

    }

    @Override
    public ResourceLocation getTextureLocation(Grabber grabber) {
        return TEXTURE;
    }
}

class GrabberPocketItemLayer extends RenderLayer<Grabber, GrabberModel> {
    private final ItemInHandRenderer itemInHandRenderer;

    public GrabberPocketItemLayer(RenderLayerParent<Grabber, GrabberModel> layerParent, ItemInHandRenderer renderer) {
        super(layerParent);
        this.itemInHandRenderer = renderer;
    }

    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int p_116899_, Grabber grabber, float p_116901_, float p_116902_, float p_116903_, float p_116904_, float p_116905_, float p_116906_) {
        ItemStack itemstack = grabber.getPocketItem();
        if (itemstack!=null) {
            poseStack.pushPose();

            poseStack.translate(0.10F, 0.9F, -0.15F);
            poseStack.scale(0.47F, 0.47F, 1.0F);
            poseStack.mulPose((new Quaternionf()).rotationZYX(Mth.PI * 10.0F / 11.0F, 0.0F, 0.0F));

            this.itemInHandRenderer.renderItem(grabber, itemstack, ItemDisplayContext.GROUND, false, poseStack, bufferSource, p_116899_);
            poseStack.popPose();
        }
    }
}
