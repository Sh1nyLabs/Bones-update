package com.sh1nylabs.bonesupdate.common.client.renderer;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.client.models.ReaperModel;
import com.sh1nylabs.bonesupdate.common.entities.necromancy.Reaper;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ReaperRenderer extends MobRenderer<Reaper, ReaperModel>{

    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(BonesUpdate.MODID,"textures/entity/reaper.png");

    public ReaperRenderer(EntityRendererProvider.Context context) {
        super(context,new ReaperModel(context.bakeLayer(ReaperModel.LAYER_LOCATION)), 0.5f);
    }

    public ResourceLocation getTextureLocation(Reaper entity) {
    return TEXTURE;
    }

}
