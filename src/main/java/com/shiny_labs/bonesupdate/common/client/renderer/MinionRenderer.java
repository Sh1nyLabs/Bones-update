package com.shiny_labs.bonesupdate.common.client.renderer;

import com.shiny_labs.bonesupdate.BonesUpdate;
import com.shiny_labs.bonesupdate.common.client.models.MinionModel;
import com.shiny_labs.bonesupdate.common.entities.custom_skeletons.Minion;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class MinionRenderer extends MobRenderer<Minion, MinionModel> {

    public static final ResourceLocation TEXTURE=new ResourceLocation(BonesUpdate.MODID,"textures/entity/minion.png");

    public MinionRenderer(EntityRendererProvider.Context context) {
        super(context,new MinionModel(context.bakeLayer(MinionModel.LAYER_LOCATION)), 0.5f);
        this.addLayer(new ItemInHandLayer<>( this, context.getItemInHandRenderer()));
    }

    public ResourceLocation getTextureLocation(Minion entity) {
        return TEXTURE;
    }
}
