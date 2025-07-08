package com.sh1nylabs.bonesupdate.common.events;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.client.models.*;
import com.sh1nylabs.bonesupdate.common.client.renderer.*;
import com.sh1nylabs.bonesupdate.common.particle.ParticleSoul;
import com.sh1nylabs.bonesupdate.init.BonesParticles;
import com.sh1nylabs.bonesupdate.common.particle.ParticleBar;
import com.sh1nylabs.bonesupdate.init.BonesEntities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.bus.api.SubscribeEvent;

@EventBusSubscriber(modid = BonesUpdate.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BonesClientEvents {

    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(BonesEntities.GRABBER.get(), GrabberRenderer::new);
        event.registerEntityRenderer(BonesEntities.MINION.get(), MinionRenderer::new);
        event.registerEntityRenderer(BonesEntities.NECROMANCER.get(), NecromancerRenderer::new);
        event.registerEntityRenderer(BonesEntities.REAPER.get(), ReaperRenderer::new);
        event.registerEntityRenderer(BonesEntities.KNIGHT_SKELETON.get(), KnightSkeletonRenderer::new);
        event.registerEntityRenderer(BonesEntities.HAUNTER_SKELETON.get(), HaunterSkeletonRenderer::new);
        event.registerEntityRenderer(BonesEntities.BROKEN_SKELETON.get(), BrokenSkeletonRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(GrabberModel.LAYER_LOCATION,GrabberModel::createBodyLayer);
        event.registerLayerDefinition(MinionModel.LAYER_LOCATION,MinionModel::createBodyLayer);
        event.registerLayerDefinition(NecromancerModel.LAYER_LOCATION,NecromancerModel::createBodyLayer);
        event.registerLayerDefinition(ReaperModel.LAYER_LOCATION,ReaperModel::createBodyLayer);
        event.registerLayerDefinition(KnightSkeletonModel.LAYER_LOCATION, KnightSkeletonModel::createBodyLayer);
        event.registerLayerDefinition(HaunterSkeletonModel.LAYER_LOCATION, HaunterSkeletonModel::createBodyLayer);
        event.registerLayerDefinition(BrokenSkeletonModel.LAYER_LOCATION, BrokenSkeletonModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(BonesParticles.PURPLE_BAR.get(),  ParticleBar.Provider::new);
        event.registerSpriteSet(BonesParticles.PURPLE_SOUL.get(),  ParticleSoul.Provider::new);
    }
}
