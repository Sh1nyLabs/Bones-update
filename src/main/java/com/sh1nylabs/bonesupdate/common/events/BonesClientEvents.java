package com.sh1nylabs.bonesupdate.common.events;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.client.models.*;
import com.sh1nylabs.bonesupdate.common.client.renderer.*;
import com.sh1nylabs.bonesupdate.common.particle.ParticleSoul;
import com.sh1nylabs.bonesupdate.init.BonesParticles;
import com.sh1nylabs.bonesupdate.common.particle.ParticleBar;
import com.sh1nylabs.bonesupdate.init.BonesEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BonesUpdate.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BonesClientEvents {

    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(BonesEntities.MINION.get(), MinionRenderer::new);
        event.registerEntityRenderer(BonesEntities.NECROMANCER.get(), NecromancerRenderer::new);
        event.registerEntityRenderer(BonesEntities.REAPER.get(), ReaperRenderer::new);
        event.registerEntityRenderer(BonesEntities.KNIGHT_SKELETON.get(), KnightSkeletonRenderer::new);
        event.registerEntityRenderer(BonesEntities.HAUNTER_SKELETON.get(), HaunterSkeletonRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(MinionModel.LAYER_LOCATION,MinionModel::createBodyLayer);
        event.registerLayerDefinition(NecromancerModel.LAYER_LOCATION,NecromancerModel::createBodyLayer);
        event.registerLayerDefinition(ReaperModel.LAYER_LOCATION,ReaperModel::createBodyLayer);
        event.registerLayerDefinition(KnightSkeletonModel.LAYER_LOCATION, KnightSkeletonModel::createBodyLayer);
        event.registerLayerDefinition(HaunterSkeletonModel.LAYER_LOCATION, HaunterSkeletonModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(BonesParticles.PURPLE_BAR.get(),  ParticleBar.Provider::new);
        event.registerSpriteSet(BonesParticles.PURPLE_SOUL.get(),  ParticleSoul.Provider::new);
    }
}
