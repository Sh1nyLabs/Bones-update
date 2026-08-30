package com.sh1nylabs.bonesupdate.common.client.renderer;

import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.registerer.BUEntityHelper;
import com.sh1nylabs.bonesupdate.registerer.BUModIdentifier;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.world.entity.Mob;

public class BUModelLayerLocation {

    public static ModelLayerLocation getLayerLocation(BUEntityHelper<? extends Mob> entity) {
        return new ModelLayerLocation(BUModIdentifier.fromModNamespace( entity.name() + "model"), "main");
    }
}
