package com.sh1nylabs.bonesupdate.common.client.renderer;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.client.models.MinionModel;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.Minion;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class MinionRenderer extends MobRenderer<Minion, MinionModel> {

    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(BonesUpdate.MODID,"textures/entity/minion.png");

    public MinionRenderer(EntityRendererProvider.Context context) {
        super(context,new MinionModel(context.bakeLayer(MinionModel.LAYER_LOCATION)), 0.5f);
        this.addLayer(new ItemInHandLayer<>( this, context.getItemInHandRenderer()));
    }

    public ResourceLocation getTextureLocation(Minion entity) {
        return TEXTURE;
    }
}
