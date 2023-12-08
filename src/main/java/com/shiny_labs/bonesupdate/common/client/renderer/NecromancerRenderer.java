package com.shiny_labs.bonesupdate.common.client.renderer;

import com.shiny_labs.bonesupdate.BonesUpdate;
import com.shiny_labs.bonesupdate.common.client.models.NecromancerModel;
import com.shiny_labs.bonesupdate.common.entities.necromancy.Necromancer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class NecromancerRenderer extends MobRenderer<Necromancer, NecromancerModel>{

    public static final ResourceLocation TEXTURE=new ResourceLocation(BonesUpdate.MODID,"textures/entity/necromancer.png");

    public NecromancerRenderer(EntityRendererProvider.Context context) {
        super(context,new NecromancerModel(context.bakeLayer(NecromancerModel.LAYER_LOCATION)), 0.5f);
        this.addLayer(new ItemInHandLayer<>( this, context.getItemInHandRenderer()));
    }

    public ResourceLocation getTextureLocation(Necromancer entity) {
    return TEXTURE;
    }

}
