package com.sh1nylabs.bonesupdate.common.client.renderer;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.common.client.models.NecromancerModel;
import com.sh1nylabs.bonesupdate.common.client.render_states.NecromancerRenderState;
import com.sh1nylabs.bonesupdate.common.entities.necromancy.Necromancer;
import com.sh1nylabs.bonesupdate.registerer.BonesRegistry;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractIllager;

public class NecromancerRenderer extends IllagerRenderer<Necromancer, NecromancerRenderState> {

    public NecromancerRenderer(EntityRendererProvider.Context context) {
        super(context,new NecromancerModel<>(context.bakeLayer(BonesRegistry.NECROMANCER.modelLayerLocation())), 0.5f);
        this.addLayer(new ItemInHandLayer<>( this));
    }

    @Override
    public NecromancerRenderState createRenderState() {
        return new NecromancerRenderState();
    }

    @Override
    public void extractRenderState(Necromancer necromancer, NecromancerRenderState necromancerRenderState, float value) {
        super.extractRenderState(necromancer, necromancerRenderState, value);
        necromancerRenderState.armPose = AbstractIllager.IllagerArmPose.NEUTRAL;
        necromancerRenderState.isCastingSpell = necromancer.isCastingSpell();
    }

    public ResourceLocation getTextureLocation(NecromancerRenderState entity) {
    return BonesRegistry.NECROMANCER.textureLocation();
    }

}
