package com.sh1nylabs.bonesupdate.common.entities.goal;

import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.KnightSkeleton;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class ProtectKnightGoal extends Goal {
    Mob mob;
    KnightSkeleton knight;
    private final double speedModifier;

    public ProtectKnightGoal(Mob mob, double speedModifier) {
        this.mob = mob;
        this.speedModifier = speedModifier;
    }
    @Override
    public boolean canUse() {
        RandomSource random = mob.getLevel().getRandom();
        if ((knight == null || mob.distanceTo(knight) > 15.0D) && random.nextInt(50) == 0){ /* Used to dwindle the number of area checks */
            AABB aabb = mob.getBoundingBox().inflate(10.0D,6.0D,10.0D);
            List<KnightSkeleton> list = mob.getLevel().getNearbyEntities(KnightSkeleton.class, TargetingConditions.forNonCombat().range(10.0D), mob, aabb).stream().filter((knight)->knight.canDash()).toList();
            if (!list.isEmpty()) {
                knight = list.get(0);
            }
        }
        return (knight != null && (knight.isDashing() || random.nextInt(7) == 0)); /* Goal used randomly (1 of 7) or when the knight dashes */
    }

    public boolean canContinueToUse() {
        return knight != null && mob.distanceTo(knight) < 1.5D; /* Stops the goal when the haunter is near to the knight */
    }

    public void start() {
        mob.getNavigation().moveTo(knight,speedModifier);
    }

    public void stop() {
        mob.getNavigation().stop();
    }
}
