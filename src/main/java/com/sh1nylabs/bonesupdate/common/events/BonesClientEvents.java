package com.sh1nylabs.bonesupdate.common.events;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.client.models.*;
import com.sh1nylabs.bonesupdate.common.client.renderer.*;
import com.sh1nylabs.bonesupdate.common.particle.ParticleSoul;
import com.sh1nylabs.bonesupdate.common.particle.ParticleBar;
import com.sh1nylabs.bonesupdate.registerer.BonesRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.bus.api.SubscribeEvent;

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
        event.registerLayerDefinition(BonesRegistry.GRABBER.modelLayerLocation(), GrabberModel::createBodyLayer);
        event.registerLayerDefinition(BonesRegistry.MINION.modelLayerLocation(), MinionModel::createBodyLayer);
        event.registerLayerDefinition(BonesRegistry.NECROMANCER.modelLayerLocation(), NecromancerModel::createBodyLayer);
        event.registerLayerDefinition(BonesRegistry.REAPER.modelLayerLocation(), ReaperModel::createBodyLayer);
        event.registerLayerDefinition(BonesRegistry.KNIGHT_SKELETON.modelLayerLocation(), KnightSkeletonModel::createBodyLayer);
        event.registerLayerDefinition(BonesRegistry.HAUNTER_SKELETON.modelLayerLocation(), HaunterSkeletonModel::createBodyLayer);
        event.registerLayerDefinition(BonesRegistry.BROKEN_SKELETON.modelLayerLocation(), BrokenSkeletonModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(BonesRegistry.PURPLE_BAR.particle(),  ParticleBar.Provider::new);
        event.registerSpriteSet(BonesRegistry.PURPLE_SOUL.particle(),  ParticleSoul.Provider::new);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        ItemBlockRenderTypes.setRenderLayer(BonesRegistry.CURSED_LANTERN.block(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BonesRegistry.PILLAGER_SK_BODY.block(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BonesRegistry.PILLAGER_SK_HEAD.block(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BonesRegistry.GUARDIAN_FOSSIL.block(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BonesRegistry.BROKEN_SKELETON_BLOCK.block(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BonesRegistry.ERODED_FOSSIL.block(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BonesRegistry.WEEPING_WILLOW_LEAVES.block(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BonesRegistry.WEEPING_WILLOW_VINES.block(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BonesRegistry.WEEPING_WILLOW_SMALL_VINES.block(), RenderType.cutout());
    }
}
