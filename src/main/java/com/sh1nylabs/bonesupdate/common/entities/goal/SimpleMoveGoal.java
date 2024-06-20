package com.sh1nylabs.bonesupdate.common.entities.goal;

/* Java class written by sh1nylabs' team. All rights reserved. */


import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

public class SimpleMoveGoal extends Goal {
    private LivingEntity target;
    private final PathfinderMob mob;
    private final float speedModifier;
    private final float radius;

    public SimpleMoveGoal(PathfinderMob mob, float speedModifier, float radius) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.radius = radius;
    }

    public boolean canUse() {
        return mob.getTarget() != null;
    }

    public boolean canContinueToUse() {
        return mob.getTarget() != null && target.distanceToSqr(mob) > radius*radius && !mob.getNavigation().isDone();
    }

    public void start() {
        this.target = mob.getTarget();
        mob.getLookControl().setLookAt(target);
        mob.getNavigation().moveTo(target, speedModifier);
    }

    public void stop() {
        mob.getNavigation().stop();
    }
}
