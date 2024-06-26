package com.sh1nylabs.bonesupdate.common.entities.custom_skeletons;

import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.items.AmuletItem;
import com.sh1nylabs.bonesupdate.init.BonesEntities;
import com.sh1nylabs.bonesupdate.init.BonesParticles;
import com.sh1nylabs.bonesupdate.init.BonesSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;
import java.util.UUID;

public class BrokenSkeleton extends AbstractSkeleton {
    private int timeBeforeSkeletonRevives;
    private LivingEntity inheritedKillCredit;
    private boolean friendly = false;
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(BrokenSkeleton.class, EntityDataSerializers.INT);

    public BrokenSkeleton(EntityType<? extends AbstractSkeleton> type, Level level) {
        super(type, level);
    }
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_TYPE_VARIANT, 1);
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Variant", this.entityData.get(DATA_ID_TYPE_VARIANT));
        compoundTag.putInt("TimeToRevive", this.timeBeforeSkeletonRevives);
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.entityData.set(DATA_ID_TYPE_VARIANT, compoundTag.getInt("Variant"));
        this.timeBeforeSkeletonRevives = compoundTag.getInt("TimeToRevive");
    }

    public static AttributeSupplier.Builder getCustomAttributes() {
        return (Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 55.0D));
    }

    @Override
    protected void dropAllDeathLoot(DamageSource damageSource) {
        if (damageSource.getEntity() instanceof Creeper || (damageSource.getEntity() instanceof Player player &&
                (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof AmuletItem ||
                 player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof AmuletItem))) {
            super.dropAllDeathLoot(damageSource);
        }
    }

    /**
     * Function overriden in order to drop skulls even when broken
     * @param damageSource
     * @param lootingLevel
     * @param hurtByPlayer
     */
    @Override
    protected void dropCustomDeathLoot(DamageSource damageSource, int lootingLevel, boolean hurtByPlayer) {
        super.dropCustomDeathLoot(damageSource, lootingLevel, hurtByPlayer);
        Entity entity = damageSource.getEntity();
        if (entity instanceof Creeper creeper) {
            if (creeper.canDropMobsSkull()) {
                creeper.increaseDroppedSkulls();
                if (getSkeletonType() == EntityType.WITHER_SKELETON) {
                    this.spawnAtLocation(Items.WITHER_SKELETON_SKULL);
                } else if (getSkeletonType() != EntityType.STRAY) {
                    this.spawnAtLocation(Items.SKELETON_SKULL);
                }
            }
        }
    }

    public void playRevivingSound() {
        SoundEvent soundevent = BonesSounds.BROKEN_SKELETON_REVIVES.get();
        this.playSound(soundevent, this.getSoundVolume(), this.getVoicePitch());
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide()) {
            if (timeBeforeSkeletonRevives <= 0 && this.isAlive()) {
                ServerLevel svrLevel = (ServerLevel) this.level();
                AbstractSkeleton skeleton = getSkeletonType().create(svrLevel);
                if (skeleton!=null) {
                    skeleton.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                    for(MobEffectInstance mobeffectinstance : this.getActiveEffectsMap().values()) {
                        skeleton.getActiveEffectsMap().put(mobeffectinstance.getEffect(), mobeffectinstance);
                    }
                    if (skeleton instanceof FriendlySkeleton friendlySk) {
                        friendlySk.setFriendly(friendly);
                    }
                    ForgeEventFactory.onFinalizeSpawn(skeleton, svrLevel, svrLevel.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.CONVERSION, null, null);

                    skeleton.setItemInHand(InteractionHand.MAIN_HAND,this.getMainHandItem());
                    skeleton.setItemInHand(InteractionHand.OFF_HAND,this.getOffhandItem());

                    skeleton.setRemainingFireTicks(this.getRemainingFireTicks());

                    net.minecraftforge.event.ForgeEventFactory.onLivingConvert(this, skeleton);
                    svrLevel.addFreshEntityWithPassengers(skeleton);
                    svrLevel.gameEvent(skeleton, GameEvent.ENTITY_PLACE, this.blockPosition());
                    this.discard();
                    svrLevel.sendParticles(BonesParticles.PURPLE_SOUL.get(),
                            skeleton.getX(), skeleton.getY() + 0.5D, skeleton.getZ(),
                            50, 0.0D, 0.1D, 0.0D, 0.20D);
                    this.playRevivingSound();

                }
            } else {
                timeBeforeSkeletonRevives--;}
        }
        super.tick();
    }

    @Override
    public void die(DamageSource damageSource) {
        if (!this.isRemoved() && !this.dead && this.level() instanceof ServerLevel) {
            if (damageSource.getEntity() instanceof Player player) {
                player.awardStat(Stats.ENTITY_KILLED.get(this.getSkeletonType()));
            }
        }
        super.die(damageSource);
    }

    @Override
    public ResourceLocation getDefaultLootTable() {
        return getSkeletonType().getDefaultLootTable();
    }

    @Override
    public LivingEntity getKillCredit() {
        return (inheritedKillCredit != null ? inheritedKillCredit : super.getKillCredit());
    }

    @Override
    public boolean canBeSeenAsEnemy() {
        return false;
    }

    @Override
    public boolean isSunBurnTick() {
        return getSkeletonType() != EntityType.WITHER_SKELETON && super.isSunBurnTick();
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
    } //TODO: update sounds

    /** Overriden so that eye height is modified when the skeleton is broken. */
    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {return 0.3F;}

    public final String getSkeletonTypeString() {
        return getSkeletonType() == null? "none" : getSkeletonType().toString();
    }

    public final EntityType<? extends AbstractSkeleton> getSkeletonType() {
        return switch (this.entityData.get(DATA_ID_TYPE_VARIANT)) { /* case 1 is SKELETON */
            case 2 -> EntityType.STRAY;
            case 3 -> EntityType.WITHER_SKELETON;
            case 4 -> BonesEntities.HAUNTER_SKELETON.get();
            case 5 -> BonesEntities.KNIGHT_SKELETON.get();
            default -> EntityType.SKELETON;
        };
    }

    public final void setSkeletonType(EntityType<? extends AbstractSkeleton> skeletonType) {
        this.entityData.set(DATA_ID_TYPE_VARIANT, switch (skeletonType.toString()) {
            case "entity.minecraft.stray" -> 2;
            case "entity.minecraft.wither_skeleton" -> 3;
            case "entity.bonesupdate.haunter_skeleton" -> 4;
            case "entity.bonesupdate.knight_skeleton" -> 5;
            default -> 1;
        });
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag tag) {
        timeBeforeSkeletonRevives = 1025 + random.nextInt(100);
        if (spawnData instanceof BrokenSkeletonSpawnData skeletonData) { /* Defining which skeleton to create after revival */
            setSkeletonType(skeletonData.skeletonType);
            this.friendly = skeletonData.friendly;
            this.inheritedKillCredit = skeletonData.inheritedKillCredit;
            this.setItemInHand(InteractionHand.MAIN_HAND,skeletonData.mainHandItem);
            this.setItemInHand(InteractionHand.OFF_HAND,skeletonData.offHandItem);

            this.setRemainingFireTicks(skeletonData.remainingFireTicks);
            for(int i = 0; i < skeletonData.listtag.size(); ++i) {
                CompoundTag compoundtag = skeletonData.listtag.getCompound(i);
                MobEffectInstance mobeffectinstance = MobEffectInstance.load(compoundtag);
                if (mobeffectinstance != null) {
                    this.getActiveEffectsMap().put(mobeffectinstance.getEffect(), mobeffectinstance);
                }
            }
        } else {
            if (this.level().dimension() == Level.NETHER) {
                this.entityData.set(DATA_ID_TYPE_VARIANT, 1);
            } else {
                this.entityData.set(DATA_ID_TYPE_VARIANT, 4 + random.nextInt(3));
            }
        }
        return spawnData;
    }

    public static class BrokenSkeletonSpawnData implements SpawnGroupData {
        public EntityType<? extends AbstractSkeleton> skeletonType;
        public ListTag listtag;
        public LivingEntity inheritedKillCredit;
        public ItemStack mainHandItem;
        public ItemStack offHandItem;
        public int remainingFireTicks;
        public boolean friendly;

        public BrokenSkeletonSpawnData(AbstractSkeleton entity) {
            this.skeletonType = (EntityType<? extends AbstractSkeleton>) entity.getType();
            this.listtag = new ListTag();
            this.remainingFireTicks = entity.getRemainingFireTicks();
            this.inheritedKillCredit = entity.getKillCredit();
            this.mainHandItem = entity.getMainHandItem();
            this.offHandItem = entity.getOffhandItem();
            this.friendly = (entity instanceof FriendlySkeleton friendlySk && friendlySk.isFriendly());

            for(MobEffectInstance mobeffectinstance : entity.getActiveEffectsMap().values()) {
                listtag.add(mobeffectinstance.save(new CompoundTag()));
            }
        }
    }
}
