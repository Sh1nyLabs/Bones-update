package com.sh1nylabs.bonesupdate.common.client.renderer;

import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.registerer.BUEntityHelper;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;

public class BUModelLayerLocation {

    public static ModelLayerLocation getLayerLocation(BUEntityHelper<? extends Mob> entity) {
        return new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BonesUpdate.MODID, entity.name() + "model"), "main");
    }
}
