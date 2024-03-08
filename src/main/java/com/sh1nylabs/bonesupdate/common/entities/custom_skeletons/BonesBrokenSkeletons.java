package com.sh1nylabs.bonesupdate.common.entities.custom_skeletons;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.common.entities.necromancy.Necromancer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;


/**
 * This mod introduces special skeletons, with particular behaviours. They share some behaviours, such as:
 * - The ability to become 'friendly' (as a dog or a golem) and to defend the player during battle.
  * Some behaviours may be overriden by dependant entities.
 */
public abstract class BonesBrokenSkeletons extends AbstractSkeleton {
    private boolean friendly;

    public BonesBrokenSkeletons(EntityType<? extends AbstractSkeleton> entityType, Level level) {
        super(entityType, level);
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Friendly", this.friendly);
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.friendly = compoundTag.getBoolean("Friendly");
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
        return super.canAttack(entity) && isFriendly() != (entity instanceof Player || entity instanceof IronGolem);
    }
    public boolean canAttackPlayer(LivingEntity entity) {return !isFriendly();}
    public boolean canAttackMonsters(LivingEntity entity) {
        return isFriendly() && !(entity instanceof AbstractSkeleton || entity instanceof Creeper || entity instanceof Necromancer);
    }

    @Override
    public void registerGoals() {

        this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0D));

        this.registerSkeletonTargets();
        }

    protected void registerSkeletonTargets() {
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Monster.class, true,this::canAttackMonsters));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true,this::canAttackPlayer));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true,this::canAttackPlayer));
    }


}
