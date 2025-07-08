package com.sh1nylabs.bonesupdate.common.entities.goal;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.Minion;
import net.minecraft.world.entity.ai.goal.Goal;


public class MinionFollowsOwnerGoal extends Goal {
    private final Minion minion;
    private int timeToRecalcPath;

    public MinionFollowsOwnerGoal(Minion minion) {
        this.minion = minion;
    }

    public boolean canUse() {return (this.minion.getOwner() != null && (minion.getOwner().hasActiveRaid() || minion.level().getRandom().nextInt(70) < 2));}

    public boolean canContinueToUse() {
        return minion.getOwner()!=null && minion.distanceTo(minion.getOwner()) > 3;
    }

    public void start() {
        this.timeToRecalcPath = 0;
    }
    public void stop() {
        minion.getNavigation().stop();
    }

    public void tick() {
        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = this.adjustedTickDelay(10);
            this.minion.getNavigation().moveTo(this.minion.getOwner(), 1.3D);
        }
    }
}
