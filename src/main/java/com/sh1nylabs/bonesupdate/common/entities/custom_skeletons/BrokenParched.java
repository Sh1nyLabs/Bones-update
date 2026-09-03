package com.sh1nylabs.bonesupdate.common.entities.custom_skeletons;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import net.minecraft.world.level.Level;

// Sub-class of BrokenSkeleton to avoid Broken Wither Skeletons burning under daylight
public class BrokenParched extends BrokenSkeleton {
    public BrokenParched(EntityType<? extends BrokenParched> type, Level level) {
        super(type, level);
    }

    public EntityType<? extends AbstractSkeleton> getSkeletonType() {
        return EntityType.PARCHED;
    }

    // A broken wither skeleton can only be related to a wither skeleton variant
    @Override
    public void setSkeletonType(EntityType<? extends AbstractSkeleton> skeletonType) {
        this.entityData.set(DATA_ID_TYPE_VARIANT, BrokenSkeletonVariants.PARCHED);
    }
}
