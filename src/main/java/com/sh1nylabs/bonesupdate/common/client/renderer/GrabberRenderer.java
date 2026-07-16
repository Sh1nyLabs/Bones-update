package com.sh1nylabs.bonesupdate.common.client.renderer;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.sh1nylabs.bonesupdate.common.client.models.GrabberModel;
import com.sh1nylabs.bonesupdate.common.client.render_states.GrabberRenderState;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.Grabber;
import com.sh1nylabs.bonesupdate.registerer.BonesRegistry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;

import static com.sh1nylabs.bonesupdate.common.client.renderer.BUModelLayerLocation.getLayerLocation;

public class GrabberRenderer extends HumanoidMobRenderer<Grabber, GrabberRenderState, GrabberModel> {

    public GrabberRenderer(EntityRendererProvider.Context context) {
        super(context, new GrabberModel(context.bakeLayer(getLayerLocation(BonesRegistry.GRABBER))), 0.5f);
        this.addLayer(new GrabberPocketItemLayer( this));
        this.addLayer(new ItemInHandLayer<>( this));
    }

    @Override
    public ResourceLocation getTextureLocation(GrabberRenderState grabberState) {
        return BonesRegistry.GRABBER.textureLocation();
    }

    @Override
    public GrabberRenderState createRenderState() {
        return new GrabberRenderState();
    }

    @Override
    public void extractRenderState(Grabber grabber, GrabberRenderState grabberRenderState, float value) {
        super.extractRenderState(grabber, grabberRenderState, value);
        itemModelResolver.updateForLiving(grabberRenderState.pocketItem, grabber.getPocketItem(), ItemDisplayContext.GROUND, grabber);
        grabberRenderState.celebrating = grabber.isCelebratingNewItem();
    }
}

class GrabberPocketItemLayer extends RenderLayer<GrabberRenderState, GrabberModel> {

    public GrabberPocketItemLayer(RenderLayerParent<GrabberRenderState, GrabberModel> layerParent) {
        super(layerParent);
    }

    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int p_116899_, GrabberRenderState grabberRenderState, float p_116901_, float p_116902_) {
        ItemStackRenderState itemStackRenderState = grabberRenderState.pocketItem;
        if (!itemStackRenderState.isEmpty()) {
            poseStack.pushPose();
            poseStack.translate(0.10F, 0.9F, -0.15F);
            poseStack.mulPose(Axis.ZP.rotation(Mth.PI * 10.0F / 11.0F));
            poseStack.scale(0.57F, 0.57F, 1.0F);

            itemStackRenderState.render(poseStack, bufferSource, p_116899_, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
        }
    }
}
