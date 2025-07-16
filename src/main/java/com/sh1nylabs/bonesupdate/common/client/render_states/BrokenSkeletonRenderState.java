package com.sh1nylabs.bonesupdate.common.client.render_states;

/* Java class written by sh1nylabs' team. All rights reserved. */

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.AbstractSkeleton;

public class BrokenSkeletonRenderState extends LivingEntityRenderState {
    public EntityType<? extends AbstractSkeleton> skeletonType;
    public boolean boggedIsSheared;
}
