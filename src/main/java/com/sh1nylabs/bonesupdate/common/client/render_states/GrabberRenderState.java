package com.sh1nylabs.bonesupdate.common.client.render_states;

/* Java class written by sh1nylabs' team. All rights reserved. */

import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class GrabberRenderState extends HumanoidRenderState {
    public ItemStackRenderState pocketItem = new ItemStackRenderState();
    public boolean celebrating;
}
