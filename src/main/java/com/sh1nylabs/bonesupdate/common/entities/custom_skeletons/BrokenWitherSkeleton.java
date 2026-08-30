package com.sh1nylabs.bonesupdate.common.entities.custom_skeletons;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;

// Sub-class of BrokenSkeleton to avoid Broken Wither Skeletons burning under daylight
public class BrokenWitherSkeleton extends BrokenSkeleton {
    public BrokenWitherSkeleton(EntityType<? extends AbstractSkeleton> type, Level level) {
        super(type, level);
    }

    public EntityType<? extends AbstractSkeleton> getSkeletonType() {
        return EntityType.WITHER_SKELETON;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnType, @Nullable SpawnGroupData spawnData) {
        this.entityData.set(DATA_ID_TYPE_VARIANT, 1);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnData);
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource damageSource, boolean hurtByPlayer) {
        super.dropCustomDeathLoot(level, damageSource, hurtByPlayer);
        this.spawnAtLocation(level, Items.WITHER_SKELETON_SKULL);
    }
}
