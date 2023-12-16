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
    private static final double DASH_TRIGGER_DISTANCE = 6.0D; // FIX_VALUE
    private boolean isWarmingUp;
    private KnightSkeleton knight;
    private static final Logger LOGGER = LogUtils.getLogger();

    public KnightSkeletonDashesGoal(KnightSkeleton mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(mob,speedModifier,followingTargetEvenIfNotSeen);
        //this.isWarmingUp = false;
        knight = mob;
    }

    @Override
    public boolean canContinueToUse() {
        if (knight.isBroken()) {
            return false;
        } else {
            return knight.isDashing() || super.canContinueToUse();
        }
    }

    public void stop() {
        knight.resetDashCooldown();
    }

    private void updateDashAction() {
        if (knight.getTarget() == null || knight.distanceTo(knight.getTarget()) > DASH_INTERRUPTION_DISTANCE) {
            LOGGER.info("dash aborted");
            //knight.setIsDashing(false);
            this.stop();
        } else if (!knight.isDashing() && knight.canDash() && (knight.distanceTo(knight.getTarget())< DASH_TRIGGER_DISTANCE) && (knight.getLevel().getRandom().nextDouble()<0.5)) {
            LOGGER.info("try to dash");
            ((KnightSkeleton)mob).setIsDashing(true);
            //isWarmingUp = true;
            knight.reInitWarmUpTime();
            mob.getNavigation().stop();
        }
    }

    @Override
    public void tick() {
        updateDashAction();
        if (knight.isDashing() && mob.getTarget()!=null) {
            this.mob.getLookControl().setLookAt(mob.getTarget(), 30.0F, 30.0F);
            knight.tickWarmupTime();
            if (knight.getWarmUpTime() == 0) {
                // execute dash attack & potentially hurt the player
                this.resetAttackCooldown();
                this.mob.doHurtTarget(mob.getTarget());

                // tp behind player
                BlockPos position = mob.getTarget().getOnPos();
                this.mob.moveTo(position.getX(),position.getY()+1,position.getZ());
                knight.resetDashCooldown();
                //isWarmingUp = false;
                this.start();
                LOGGER.info("dash should be finished, returns to original melee attack");
            }
        } else {
            super.tick();
        }
    }
}
