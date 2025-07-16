package com.sh1nylabs.bonesupdate.common.client.renderer;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.client.models.GrabberModel;
import com.sh1nylabs.bonesupdate.common.client.render_states.GrabberRenderState;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.Grabber;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class GrabberRenderer extends MobRenderer<Grabber, GrabberRenderState, GrabberModel> {

    public static final ResourceLocation GRABBER_TEXTURE = ResourceLocation.fromNamespaceAndPath(BonesUpdate.MODID,"textures/entity/grabber.png");

    public GrabberRenderer(EntityRendererProvider.Context context) {
        super(context,new GrabberModel(context.bakeLayer(GrabberModel.LAYER_LOCATION)), 0.5f);
        this.addLayer(new GrabberPocketItemLayer( this, context.getItemRenderer()));
        this.addLayer(new ItemInHandLayer<>( this, context.getItemRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(GrabberRenderState grabberState) {
        return GRABBER_TEXTURE;
    }

    @Override
    public GrabberRenderState createRenderState() {
        return new GrabberRenderState();
    }

    @Override
    public void extractRenderState(Grabber grabber, GrabberRenderState grabberRenderState, float value) {
        super.extractRenderState(grabber, grabberRenderState, value);
        grabberRenderState.pocketItem = grabber.getPocketItem();
        grabberRenderState.celebrating = grabber.isCelebratingNewItem();
        grabberRenderState.pocketItemModel = itemRenderer.resolveItemModel(grabberRenderState.pocketItem, grabber, ItemDisplayContext.GROUND);
    }
}

class GrabberPocketItemLayer extends RenderLayer<GrabberRenderState, GrabberModel> {
    private final ItemRenderer itemRenderer;

    public GrabberPocketItemLayer(RenderLayerParent<GrabberRenderState, GrabberModel> layerParent, ItemRenderer renderer) {
        super(layerParent);
        this.itemRenderer = renderer;
    }

    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int p_116899_, GrabberRenderState grabberRenderState, float p_116901_, float p_116902_) {
        ItemStack itemstack = grabberRenderState.pocketItem;
        BakedModel bakedmodel = grabberRenderState.pocketItemModel;
        if (bakedmodel != null && itemstack != null) {
            poseStack.pushPose();
            poseStack.translate(0.10F, 0.9F, -0.15F);
            poseStack.mulPose(Axis.ZP.rotation(Mth.PI * 10.0F / 11.0F));
            poseStack.scale(0.57F, 0.57F, 1.0F);

            this.itemRenderer.render(itemstack, ItemDisplayContext.GROUND, false, poseStack, bufferSource, p_116899_, OverlayTexture.NO_OVERLAY, bakedmodel);
            poseStack.popPose();
        }
    }
}
