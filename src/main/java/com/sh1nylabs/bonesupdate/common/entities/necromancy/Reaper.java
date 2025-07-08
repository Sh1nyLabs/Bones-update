package com.sh1nylabs.bonesupdate.common.entities.necromancy;

/* Java class written by sh1nylabs' team. All rights reserved. */


import com.sh1nylabs.bonesupdate.common.entities.goal.ReaperAttackGoal;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class Reaper extends FlyingMob implements Enemy {
    public Reaper(EntityType<? extends FlyingMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new ReaperAttackGoal.ReaperMoveControl(this);
    }

    public static AttributeSupplier.Builder getCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH,28.0D) // FIX_VALUE
                .add(Attributes.MOVEMENT_SPEED, 0.2D) // FIX_VALUE
                .add(Attributes.ATTACK_DAMAGE, 8.0D) // FIX_VALUE
                .add(Attributes.ATTACK_SPEED, 1.7D); // FIX_VALUE, as ATTACK_SPEED * 10 ticks
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
