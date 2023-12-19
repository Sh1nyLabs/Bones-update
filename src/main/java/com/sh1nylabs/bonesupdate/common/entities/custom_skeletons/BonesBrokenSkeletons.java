package com.sh1nylabs.bonesupdate.common.entities.custom_skeletons;

import com.sh1nylabs.bonesupdate.common.entities.necromancy.Necromancer;
import com.sh1nylabs.bonesupdate.common.items.AmuletItem;
import com.mojang.logging.LogUtils;
import com.sh1nylabs.bonesupdate.init.BonesParticles;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;

import static java.lang.Math.max;

/**
 * This mod introduces special skeletons, with particular behaviours. They share some behaviours, such as:
 * - The ability to become 'friendly' (as a dog or a golem) and to defend the player during battle.
 * - The ability to become broken instead of killed. The only way to kill them is, when they are broken,
 *      to 'purify' them using a specific item or to put them in front of fire.
 *      If they are not killed rapidly, they might become mobile skeletons again.
 * Some behaviours may be overriden by dependant entities.
 */
public abstract class BonesBrokenSkeletons extends AbstractSkeleton {
    private static final EntityDataAccessor<Boolean> IS_BROKEN = SynchedEntityData.defineId(BonesBrokenSkeletons.class, EntityDataSerializers.BOOLEAN);
    private boolean clientSideBrokenState = false;
    private static final float HEALTH_WHEN_SKELETON_BREAKS = 15.0F; // FIX_VALUE
    private static final int REVIVING_TIME_WHEN_BROKEN = 250; // FIX_VALUE
    private boolean friendly;
    private int timeBeforeSkeletonRevives;
    private static final Logger LOGGER = LogUtils.getLogger();

    public BonesBrokenSkeletons(EntityType<? extends AbstractSkeleton> entityType, Level level) {
        super(entityType, level);
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Friendly", this.friendly);
        compoundTag.putInt("TimeToRevive", this.timeBeforeSkeletonRevives);
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.friendly = compoundTag.getBoolean("Friendly");
        this.timeBeforeSkeletonRevives = compoundTag.getInt("TimeToRevive");
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_BROKEN, false);
    }

    public boolean isFriendly() {return this.friendly;}
    public void setFriendly(boolean friendly) {this.friendly = friendly;}
    public void becomesFriendly(Level level) { /* Can be called on both sides */
        if (!level.isClientSide()) {
            this.friendly = true;
        } else {
            level.addParticle(ParticleTypes.HEART, this.getRandomX(1.0D), this.getRandomY() + 0.1D, this.getRandomZ(1.0D), this.random.nextGaussian() * 0.02D, this.random.nextGaussian() * 0.02D, this.random.nextGaussian() * 0.02D);
            level.addParticle(ParticleTypes.HEART, this.getRandomX(1.0D), this.getRandomY() + 0.1D, this.getRandomZ(1.0D), this.random.nextGaussian() * 0.02D, this.random.nextGaussian() * 0.02D, this.random.nextGaussian() * 0.02D);
            level.addParticle(ParticleTypes.HEART, this.getRandomX(1.0D), this.getRandomY() + 0.1D, this.getRandomZ(1.0D), this.random.nextGaussian() * 0.02D, this.random.nextGaussian() * 0.02D, this.random.nextGaussian() * 0.02D);
        }
    }

    @Override
    public boolean canAttack(LivingEntity entity) {
        return super.canAttack(entity) && (entity instanceof Player? canAttackPlayer(entity) : canAttackMonsters(entity));
    }
    public boolean canAttackPlayer(LivingEntity entity) {return !isFriendly();}
    public boolean canAttackMonsters(LivingEntity entity) {
        return isFriendly() && !(entity instanceof AbstractSkeleton || entity instanceof Creeper || entity instanceof Necromancer);
    }

    @Override
    public void registerGoals() {

        this.goalSelector.addGoal(2, new RestrictSunGoal(this));
        this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0D));

        this.registerSkeletonTargets();
        }

    protected void registerSkeletonTargets() {
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Monster.class, true,this::canAttackMonsters));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true,this::canAttackPlayer));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true,this::canAttackPlayer));
    }

    public boolean isBroken() {return this.entityData.get(IS_BROKEN);}
    public void setBroken(boolean nextState) {
        this.entityData.set(IS_BROKEN,nextState);
    }

    /** Overriden so that hitboxes are modified when the skeleton is broken. */
    @Override
    public EntityDimensions getDimensions(Pose pose) {
        if (this.isBroken()) {
            return super.getDimensions(pose).scale(1.1F,0.3F);
        } else {
            return super.getDimensions(pose);
        }
    }
    /** Overriden so that eye height is modified when the skeleton is broken. */
    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
        float f = super.getStandingEyeHeight(pose, entityDimensions);
        return this.isBroken() ? 0.3F : f;
    }

    /**
     * When broken, only fire can damage the monster (sun, lava, fire aspect, flame, flint & steel...).
     * @param damageSource : to check if it is fire or not
     * @return boolean
     */
    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return (this.isBroken() && !damageSource.is(DamageTypeTags.IS_FIRE)) || super.isInvulnerableTo(damageSource);
    }

    /**
     * Update the damage taken when it is switching from state "mobile" to state "broken".
     * @param amount : damage amount
     * @param damageSource : damage source
     * @return new damage value
     */
    public float updateDamageIfBecomesBroken(float amount, DamageSource damageSource) {
        if (!isBroken() && (getHealth()-amount < HEALTH_WHEN_SKELETON_BREAKS)) { //broke the skeleton
            this.setBroken(true);
            if (damageSource.getEntity() instanceof Mob mob) {
                mob.setTarget(null);
            }
            this.timeBeforeSkeletonRevives = REVIVING_TIME_WHEN_BROKEN + random.nextInt(40);
            level.levelEvent(2009,blockPosition(),0);
            return max(getHealth()-HEALTH_WHEN_SKELETON_BREAKS,0.0F);
        }
        return amount;
    }

    /**
     * To get the loot of the skeletons, you need to 'purify' them using an Amulet.
     * @param damageSource : to check what are the items in player's hands
     */
    protected void dropAllDeathLoot(DamageSource damageSource) {
        if (damageSource.getEntity() instanceof Player player) {
            if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof AmuletItem ||
                    player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof AmuletItem) {
                super.dropAllDeathLoot(damageSource);
            }
        }
    }

    /**
     * When he revives, the skeleton instantly recovers all his health.
     */
    public void brokenSkeletonRevives() {
        this.setBroken(false);
        this.setHealth(this.getMaxHealth());
        ((ServerLevel) level).sendParticles(BonesParticles.PURPLE_SOUL.get(),
                this.getX(), this.getY() + 0.5D, this.getZ(),
                50, 0.0D, 0.1D, 0.0D, 0.20D);
        LOGGER.info("should be revived");
    }

    /**
     * Override to not allow the skeleton to move when broken
     * @return boolean
     */
    @Override
    public boolean isImmobile() {
        return isBroken() || super.isImmobile();
    }

    /**
     * tick Overriden to update the broken state (true / false)
     */
    @Override
    public void tick() {
        super.tick();
        if (isBroken() && !this.getLevel().isClientSide()) {
            if (timeBeforeSkeletonRevives <= 0 && this.isAlive()) {
                this.brokenSkeletonRevives();
            } else {
                timeBeforeSkeletonRevives--;}
        }
        if (this.getLevel().isClientSide() && (clientSideBrokenState != isBroken())) { /* Update the bounding box of the entity */
            clientSideBrokenState = isBroken();
            this.refreshDimensions();
        }
    }
}
