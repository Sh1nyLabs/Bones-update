package com.sh1nylabs.bonesupdate.common.entities.goal;

import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.KnightSkeleton;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import org.slf4j.Logger;

/**
 * A dash action that can be done by a knight skeleton.
 */
public class KnightSkeletonDashesGoal extends MeleeAttackGoal {
    private static final double DASH_INTERRUPTION_DISTANCE = 15.0D; // FIX_VALUE
    private static final double DASH_TRIGGER_DISTANCE = 7.0D; // FIX_VALUE
    private KnightSkeleton knight; /** we can use LivingEntity mob, but we prefer not to use cast everywhere */
    private static final Logger LOGGER = LogUtils.getLogger();

    public KnightSkeletonDashesGoal(KnightSkeleton knight, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(knight,speedModifier,followingTargetEvenIfNotSeen);
        //this.isWarmingUp = false;
        this.knight = knight;
    }

    @Override
    public boolean canContinueToUse() {
        if (knight.isBroken()) {
            return false;
        } else {
            return knight.isDashing() || super.canContinueToUse();
        }
    }

    /** When he dashes, a counter is decremented. At 0, it should launch its attack.
     * During dash, it also checks if it can keep going. Otherwise, it stops its dash.
     *
     */
    @Override
    public void tick() {
        if (knight.isDashing()) {
            if (knight.getTarget() == null || knight.distanceTo(knight.getTarget()) > DASH_INTERRUPTION_DISTANCE) {
                LOGGER.info("dash aborted");
                knight.resetDashCooldown();
            } else { // dash conditions are still met
                this.mob.getLookControl().setLookAt(mob.getTarget(), 30.0F, 30.0F);
                knight.tickWarmupTime();

                if (knight.getWarmUpTime() == 0) { // warm up time ended
                    this.resetAttackCooldown();
                    this.knight.doHurtTarget(knight.getTarget());

                    BlockPos position = knight.getTarget().getOnPos(); // tp behind player
                    this.knight.moveTo(position.getX(),position.getY()+1,position.getZ());
                    knight.resetDashCooldown();
                    this.start(); // to recompute a path navigation
                    LOGGER.info("dash should be finished, returns to original melee attack");
                }
            }
        } else { // knight not currently dashing

            super.tick();

            if (knight.canDash() && (knight.getTarget()!=null) && (knight.distanceTo(knight.getTarget())< DASH_TRIGGER_DISTANCE) && (knight.getLevel().getRandom().nextDouble()<0.5)) { // prepare dash procedure
                LOGGER.info("try to dash");
                knight.setIsDashing(true);
                knight.reInitWarmUpTime();
                knight.getNavigation().stop();
            }
        }
    }
}
