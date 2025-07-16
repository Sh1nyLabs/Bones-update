package com.sh1nylabs.bonesupdate.common.client.render_states;

/* Java class written by sh1nylabs' team. All rights reserved. */

import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class GrabberRenderState extends HumanoidRenderState {
    public ItemStack pocketItem;
    @Nullable
    public BakedModel pocketItemModel;
    public boolean celebrating;
}
