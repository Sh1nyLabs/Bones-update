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
    private static final int DASH_WARM_UP_TIME = 60; // FIX_VALUE
    private static final double DASH_INTERRUPTION_DISTANCE = 15.0D; // FIX_VALUE
    private static final double DASH_TRIGGER_DISTANCE = 6.0D; // FIX_VALUE
    private int warmUpTime;
    private boolean isWarmingUp;
    private static final Logger LOGGER = LogUtils.getLogger();

    public KnightSkeletonDashesGoal(KnightSkeleton mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(mob,speedModifier,followingTargetEvenIfNotSeen);
        this.isWarmingUp = false;
    }

    @Override
    public boolean canContinueToUse() {
        if (((KnightSkeleton)mob).isBroken()) {
            return false;
        } else {
            return isWarmingUp || super.canContinueToUse();
        }
    }

    public void stop() {
        ((KnightSkeleton)mob).setIsDashing(false);
    }

    private void updateDashAction() {
        if (mob.getTarget() == null || mob.distanceTo(mob.getTarget()) > DASH_INTERRUPTION_DISTANCE) {
            LOGGER.info("dash aborted");
            isWarmingUp = false;
            this.stop();
        } else if (!isWarmingUp && ((KnightSkeleton)mob).canDash() && (mob.distanceTo(mob.getTarget())< DASH_TRIGGER_DISTANCE) && (mob.getLevel().getRandom().nextDouble()<0.5)) {
            LOGGER.info("try to dash");
            ((KnightSkeleton)mob).setIsDashing(true);
            isWarmingUp = true;
            warmUpTime = DASH_WARM_UP_TIME;
            mob.getNavigation().stop();
        }
    }

    @Override
    public void tick() {
        updateDashAction();
        if (isWarmingUp && mob.getTarget()!=null) {
            LOGGER.info("warming up... {}", warmUpTime);
            this.mob.getLookControl().setLookAt(mob.getTarget(), 30.0F, 30.0F);
            this.warmUpTime--;
            if (warmUpTime == 0) {
                // execute dash attack & potentially hurt the player
                this.resetAttackCooldown();
                this.mob.doHurtTarget(mob.getTarget());
                BlockPos position = mob.getTarget().getOnPos();

                // tp behind player
                this.mob.moveTo(position.getX(),position.getY()+1,position.getZ());
                ((KnightSkeleton)mob).resetDashCooldown();
                isWarmingUp = false;
                this.start();
                LOGGER.info("dash should be finished, returns to original melee attack");
            }
        } else {
            super.tick();
        }
    }
}
