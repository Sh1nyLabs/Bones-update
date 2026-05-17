package com.sh1nylabs.bonesupdate.common.client.render_states;

/* Java class written by sh1nylabs' team. All rights reserved. */

import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GrabberRenderState extends HumanoidRenderState {
    public ItemStackRenderState pocketItem = new ItemStackRenderState();
    public boolean celebrating;
}
