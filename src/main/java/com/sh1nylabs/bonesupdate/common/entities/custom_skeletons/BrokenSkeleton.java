package com.sh1nylabs.bonesupdate.common.entities.custom_skeletons;

import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.items.AmuletItem;
import com.sh1nylabs.bonesupdate.init.BonesParticles;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;

public class BrokenSkeleton extends AbstractSkeleton {
    private int timeBeforeSkeletonRevives; //TODO: What about skeleton head?
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(BrokenSkeleton.class, EntityDataSerializers.INT);

    public BrokenSkeleton(EntityType<? extends AbstractSkeleton> type, Level level) {
        super(type, level);
    }
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_TYPE_VARIANT, 0);
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Variant", this.entityData.get(DATA_ID_TYPE_VARIANT));
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.entityData.set(DATA_ID_TYPE_VARIANT, compoundTag.getInt("Variant"));
    }

    public static AttributeSupplier.Builder getCustomAttributes() {
        return (Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 55.0D));
    }

    public SkeletonVariants getVariant() {return SkeletonVariants.getVariantFromValue(this.entityData.get(DATA_ID_TYPE_VARIANT));}
    public void setVariant(SkeletonVariants variant) {
        this.entityData.set(DATA_ID_TYPE_VARIANT, variant.value);
    }

    protected void dropAllDeathLoot(DamageSource damageSource) {
        if (damageSource.getEntity() instanceof Player player) {
            if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof AmuletItem ||
                    player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof AmuletItem) {
                super.dropAllDeathLoot(damageSource);
            }
        }
    }
    public ResourceLocation getDefaultLootTable() {
        BonesUpdate.LOGGER.info("loot table called");
        return getVariant().entityType.getDefaultLootTable();
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag tag) {
        timeBeforeSkeletonRevives = 1050 + random.nextInt(100);
        if (spawnData instanceof BrokenSkeletonSpawnData skeletonData) { /* Defining which skeleton to create after revival */
            this.setVariant(skeletonData.variant);
        } else {
            this.setVariant(SkeletonVariants.NONE);
        }
        return spawnData;
    }

    @Override
    public void tick() {
        if (!this.getLevel().isClientSide()) {
            if (timeBeforeSkeletonRevives <= 0 && this.isAlive()) {
                if (getVariant().entityType == null) {
                    this.kill();
                } else {
                    ServerLevel svrLevel = (ServerLevel) level;
                    BonesUpdate.LOGGER.info("creating skeleton");
                    AbstractSkeleton skeleton = getVariant().entityType.create(svrLevel);
                    if (skeleton!=null) {
                        skeleton.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                        ForgeEventFactory.onFinalizeSpawn(skeleton, svrLevel, svrLevel.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.CONVERSION, null, null);

                        net.minecraftforge.event.ForgeEventFactory.onLivingConvert(this, skeleton);
                        svrLevel.addFreshEntityWithPassengers(skeleton);
                        svrLevel.gameEvent(skeleton, GameEvent.ENTITY_PLACE, this.blockPosition());
                        this.discard();
                        svrLevel.sendParticles(BonesParticles.PURPLE_SOUL.get(),
                                skeleton.getX(), skeleton.getY() + 0.5D, skeleton.getZ(),
                                50, 0.0D, 0.1D, 0.0D, 0.20D);
                    }
                }
            } else {
                timeBeforeSkeletonRevives--;}
        }
        super.tick();
    }

    @Override
    public boolean canBeSeenAsEnemy() {
        return false;
    }

    @Override
    public boolean isSunBurnTick() {
        return getVariant().entityType != EntityType.WITHER_SKELETON;
    }

    @Override
    public boolean isImmobile() {
        return true;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return (!damageSource.is(DamageTypeTags.IS_FIRE)  && !damageSource.is(DamageTypeTags.IS_EXPLOSION)) || super.isInvulnerableTo(damageSource);
    }

    @Override
    protected SoundEvent getStepSound() {
        return SoundEvents.SKELETON_STEP;
    }

    public static class BrokenSkeletonSpawnData implements SpawnGroupData {
        public SkeletonVariants variant;
        public BrokenSkeletonSpawnData(EntityType<?> entityType) {
            this.variant = SkeletonVariants.getVariantFromEntity(entityType);
        }
    }

    /**
     * Enum to fill when new skeletons are added to the game.
     */
    public enum SkeletonVariants {
        NONE(0, null),
        SKELETON(1, EntityType.SKELETON),
        STRAY(2, EntityType.STRAY),
        WITHER_SKELETON(3, EntityType.WITHER_SKELETON);
        public final int value;
        public final EntityType<? extends AbstractSkeleton> entityType;

        SkeletonVariants(int value, EntityType<? extends AbstractSkeleton> entityType) {
            this.value = value;
            this.entityType = entityType;
        }

        public static SkeletonVariants getVariantFromValue(int value) {
            return switch (value) {
                case 1 -> SKELETON;
                case 2 -> STRAY;
                case 3 -> WITHER_SKELETON;
                default -> NONE;
            };
        }

        public static SkeletonVariants getVariantFromEntity(EntityType<?> entityType) {
            if (entityType == EntityType.SKELETON) {
                return (SKELETON);
            } else if (entityType == EntityType.STRAY) {
                return (STRAY);
            } else if (entityType == EntityType.WITHER_SKELETON) {
                return (WITHER_SKELETON);
            } else {
                return (NONE);
            }
        }
    }
}
