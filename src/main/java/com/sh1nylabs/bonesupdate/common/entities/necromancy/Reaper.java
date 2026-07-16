package com.sh1nylabs.bonesupdate.common.entities.necromancy;

/* Java class written by sh1nylabs' team. All rights reserved. */


import com.sh1nylabs.bonesupdate.common.entities.goal.ReaperAttackGoal;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class Reaper extends PathfinderMob implements Enemy {
    public static double REAPER_FLYING_SPEED = 0.06;
    public Reaper(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 10, true);
    }

    public static AttributeSupplier.Builder getCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH,28.0D) // FIX_VALUE
                .add(Attributes.MOVEMENT_SPEED, REAPER_FLYING_SPEED) // FIX_VALUE
                .add(Attributes.ATTACK_DAMAGE, 8.0D) // FIX_VALUE
                .add(Attributes.ATTACK_SPEED, 1.7D) // FIX_VALUE, as ATTACK_SPEED * 10 ticks
                .add(Attributes.FLYING_SPEED, REAPER_FLYING_SPEED);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, level);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setRequiredPathLength(48.0F);
        return flyingpathnavigation;
    }

    @Override
    public void travel(Vec3 vec3) {
        this.travelFlying(vec3, this.getSpeed());
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(3, new ReaperAttackGoal(this));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }


    @Override
    public void aiStep() {
        super.aiStep();
        this.updateSwingTime();
    }


}
