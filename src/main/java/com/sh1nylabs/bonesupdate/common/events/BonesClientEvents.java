package com.sh1nylabs.bonesupdate.common.events;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.client.models.*;
import com.sh1nylabs.bonesupdate.common.client.renderer.*;
import com.sh1nylabs.bonesupdate.common.particle.ParticleSoul;
import com.sh1nylabs.bonesupdate.common.particle.ParticleBar;
import com.sh1nylabs.bonesupdate.registerer.BonesRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BonesUpdate.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BonesClientEvents {

    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(BonesRegistry.GRABBER.type(), GrabberRenderer::new);
        event.registerEntityRenderer(BonesRegistry.MINION.type(), MinionRenderer::new);
        event.registerEntityRenderer(BonesRegistry.NECROMANCER.type(), NecromancerRenderer::new);
        event.registerEntityRenderer(BonesRegistry.REAPER.type(), ReaperRenderer::new);
        event.registerEntityRenderer(BonesRegistry.KNIGHT_SKELETON.type(), KnightSkeletonRenderer::new);
        event.registerEntityRenderer(BonesRegistry.HAUNTER_SKELETON.type(), HaunterSkeletonRenderer::new);
        event.registerEntityRenderer(BonesRegistry.BROKEN_SKELETON.type(), BrokenSkeletonRenderer::new);
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
        event.registerSpriteSet(BonesRegistry.PURPLE_BAR.particle(),  ParticleBar.Provider::new);
        event.registerSpriteSet(BonesRegistry.PURPLE_SOUL.particle(),  ParticleSoul.Provider::new);
    }
}
