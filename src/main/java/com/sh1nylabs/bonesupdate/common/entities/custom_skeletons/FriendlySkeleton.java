package com.sh1nylabs.bonesupdate.common.entities.custom_skeletons;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.common.entities.necromancy.Necromancer;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;


/**
 * This mod introduces special skeletons, with particular behaviours. They share some behaviours, such as:
 * - The ability to become 'friendly' (as a dog or a golem) and to defend the player during battle.
  * Some behaviours may be overriden by dependant entities.
 */
public abstract class FriendlySkeleton extends AbstractSkeleton {
    private boolean friendly;

    public FriendlySkeleton(EntityType<? extends AbstractSkeleton> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void addAdditionalSaveData(ValueOutput valueOutput) {
        super.addAdditionalSaveData(valueOutput);
        valueOutput.putBoolean("Friendly", this.friendly);
    }

    @Override
    public void readAdditionalSaveData(ValueInput valueInput) {
        super.readAdditionalSaveData(valueInput);
        this.friendly = valueInput.getBooleanOr("Friendly", false);
    }

    public boolean isFriendly() {return this.friendly;}
    public void setFriendly(boolean friendly) {this.friendly = friendly;}

    @Override
    public boolean canAttack(LivingEntity entity) {
        return super.canAttack(entity) && canAttackEntity(entity);
    }

    /**
     *
     * @param entity the entity to attack
     * @return  friendly = true -> entity = Monster (but not AbstractSkeleton, Creeper nor Necromancer)
     *          friendly = false -> entity = Player or IronGolem
     *
     */
    public boolean canAttackEntity(LivingEntity entity, ServerLevel level) {return canAttackEntity(entity);}

    public boolean canAttackEntity(LivingEntity entity) {
        return isFriendly() ?
                entity instanceof Monster && !(entity instanceof AbstractSkeleton || entity instanceof Creeper || entity instanceof Necromancer)
                : entity instanceof Player || entity instanceof IronGolem;
    }

    @Override
    public void registerGoals() {

        this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0D));

        this.registerSkeletonTargets();
        }

    protected void registerSkeletonTargets() {
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Monster.class, true, this::canAttackEntity));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true, this::canAttackEntity));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true, this::canAttackEntity));
    }


}
