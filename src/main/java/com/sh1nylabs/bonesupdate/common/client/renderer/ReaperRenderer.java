package com.sh1nylabs.bonesupdate.common.client.renderer;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.common.client.models.ReaperModel;
import com.sh1nylabs.bonesupdate.common.client.render_states.ReaperRenderState;
import com.sh1nylabs.bonesupdate.common.entities.necromancy.Reaper;
import com.sh1nylabs.bonesupdate.registerer.BonesRegistry;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ReaperRenderer extends MobRenderer<Reaper, ReaperRenderState, ReaperModel>{

    public ReaperRenderer(EntityRendererProvider.Context context) {
        super(context,new ReaperModel(context.bakeLayer(BonesRegistry.REAPER.modelLayerLocation())), 0.5f);
    }

    @Override
    public ReaperRenderState createRenderState() {
        return new ReaperRenderState();
    }

    @Override
    public void extractRenderState(Reaper reaper, ReaperRenderState reaperRenderState, float value) {
        super.extractRenderState(reaper, reaperRenderState, value);
        reaperRenderState.attackTime = reaper.getAttackAnim(value);
        reaperRenderState.walkAnimationSinceLastStop = reaper.walkAnimation.isMoving() ? reaperRenderState.walkAnimationPos - reaperRenderState.previousWalkAnimationPos
                                                       : Math.max(reaperRenderState.walkAnimationSinceLastStop / 1.05F, 0.01F);
        reaperRenderState.previousWalkAnimationPos = reaperRenderState.walkAnimationPos;
    }

    public ResourceLocation getTextureLocation(ReaperRenderState entity) {
    return BonesRegistry.REAPER.textureLocation();
    }

}
