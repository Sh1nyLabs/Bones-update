package com.sh1nylabs.bonesupdate.common.entities.goal;

/* Java class written by sh1nylabs' team. All rights reserved. */


import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.KnightSkeleton;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

/**
 * A dash action that can be done by a knight skeleton.
 */
public class KnightSkeletonDashesGoal extends MeleeAttackGoal {
    private static final double DASH_INTERRUPTION_DISTANCE = 15.0D; // FIX_VALUE
    private static final double DASH_TRIGGER_DISTANCE = 7.0D; // FIX_VALUE
    private final KnightSkeleton knight; /** we can use the parameter LivingEntity mob, but we prefer not to use cast everywhere */

    public KnightSkeletonDashesGoal(KnightSkeleton knight, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(knight,speedModifier,followingTargetEvenIfNotSeen);
        this.knight = knight;
    }

    @Override
    public boolean canContinueToUse() {
        return knight.isDashing() || super.canContinueToUse();
    }

    /** When the knight dashes, a counter is decremented. At 0, it should launch its attack.
     * During dash, it also checks if it can keep warming up. Otherwise, it stops its dash.
     * It begins to warm up when:
     *  - The attacked entity is near enough (< DASH_TRIGGER_DISTANCE),
     *  - The knight is ready to dash (dash cooldown ended, not broken).
     * It stops warming up when:
     *  - The attacked entity is too far away (> DASH_INTERRUPTION_DISTANCE),
     *  - The attacked entity is too near (< 1.5D),
     *  - The attacked entity is forgotten.
     */
    @Override
    public void tick() {
        if (knight.isDashing()) {
            if (knight.getTarget() == null || knight.distanceTo(knight.getTarget()) > DASH_INTERRUPTION_DISTANCE || knight.distanceTo(knight.getTarget()) < 1.5D) {
                knight.resetDashCooldown();
            } else { /* dash conditions are still met */
                this.mob.getLookControl().setLookAt(mob.getTarget(), 30.0F, 30.0F);
                knight.tickWarmupTime();

                if (knight.getWarmUpTime() == 0) { // warm up time ended
                    this.resetAttackCooldown();
                    this.knight.doHurtTarget(knight.getTarget());

                    BlockPos position = knight.getTarget().getOnPos(); // tp behind player
                    this.knight.moveTo(position.getX(),position.getY()+1,position.getZ());
                    knight.resetDashCooldown();
                    this.start(); // to recompute a path navigation
                }
            }
        } else { // knight not currently dashing

            super.tick();

            if (knight.canDash() && (knight.getTarget()!=null) && (knight.distanceTo(knight.getTarget()) < DASH_TRIGGER_DISTANCE) && (knight.distanceTo(knight.getTarget()) > 1.5D) && (knight.getLevel().getRandom().nextDouble()<0.5)) { // prepare dash procedure
                knight.setIsDashing(true);
                knight.reInitWarmUpTime();
                knight.getNavigation().stop();
            }
        }
    }
}
