package com.sh1nylabs.bonesupdate.common.client.renderer;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.common.client.models.MinionModel;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.Minion;
import com.sh1nylabs.bonesupdate.registerer.BonesRegistry;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.ResourceLocation;

public class MinionRenderer extends MobRenderer<Minion, HumanoidRenderState, MinionModel> {

    public MinionRenderer(EntityRendererProvider.Context context) {
        super(context, new MinionModel(context.bakeLayer(BonesRegistry.MINION.modelLayerLocation())), 0.5f);
        this.addLayer(new ItemInHandLayer<>( this));
    }

    @Override
    public HumanoidRenderState createRenderState() {
        return new HumanoidRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(HumanoidRenderState entity) {
        return BonesRegistry.MINION.textureLocation();
    }
}
