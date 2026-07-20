package com.sh1nylabs.bonesupdate.common.events;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.client.models.*;
import com.sh1nylabs.bonesupdate.common.client.renderer.*;
import com.sh1nylabs.bonesupdate.common.particle.ParticleSoul;
import com.sh1nylabs.bonesupdate.common.particle.ParticleBar;
import com.sh1nylabs.bonesupdate.registerer.BUEntityHelper;
import com.sh1nylabs.bonesupdate.registerer.BonesRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.bus.api.SubscribeEvent;

import static com.sh1nylabs.bonesupdate.common.client.renderer.BUModelLayerLocation.getLayerLocation;

@EventBusSubscriber(modid = BonesUpdate.MODID, value = Dist.CLIENT)
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
        event.registerLayerDefinition(getLayerLocation(BonesRegistry.GRABBER), GrabberModel::createBodyLayer);
        event.registerLayerDefinition(getLayerLocation(BonesRegistry.MINION), MinionModel::createBodyLayer);
        event.registerLayerDefinition(getLayerLocation(BonesRegistry.NECROMANCER), NecromancerModel::createBodyLayer);
        event.registerLayerDefinition(getLayerLocation(BonesRegistry.REAPER), ReaperModel::createBodyLayer);
        event.registerLayerDefinition(getLayerLocation(BonesRegistry.KNIGHT_SKELETON), KnightSkeletonModel::createBodyLayer);
        event.registerLayerDefinition(getLayerLocation(BonesRegistry.HAUNTER_SKELETON), HaunterSkeletonModel::createBodyLayer);
        event.registerLayerDefinition(getLayerLocation(BonesRegistry.BROKEN_SKELETON), BrokenSkeletonModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(BonesRegistry.PURPLE_BAR.particle(),  ParticleBar.Provider::new);
        event.registerSpriteSet(BonesRegistry.PURPLE_SOUL.particle(),  ParticleSoul.Provider::new);
    }
}
