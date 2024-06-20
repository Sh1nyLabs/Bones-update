package com.sh1nylabs.bonesupdate.common.entities.goal;

/* Java class written by sh1nylabs' team. All rights reserved. */


import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.Grabber;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.EnumSet;

public class NearestStealableTargetGoal extends TargetGoal {

    private Player target;
    private final TargetingConditions targetConditions;

    private final int randomInterval;
    private int unseenTicks;
    protected int unseenMemoryTicks = 60;

    public NearestStealableTargetGoal(Grabber grabber) {
        super(grabber, true, false);
        this.randomInterval = reducedTickDelay(20);
        this.target = null;
        this.setFlags(EnumSet.of(Goal.Flag.TARGET));
        this.targetConditions = TargetingConditions.forNonCombat().range(20.0D).selector((player)->{
            return grabber.wantsItem(player.getMainHandItem().getItem()) || grabber.wantsItem(player.getOffhandItem().getItem());});
    }

    @Override
    public boolean canUse() {
        if (this.mob.getItemInHand(InteractionHand.OFF_HAND) != ItemStack.EMPTY) {
            return false;
        }
        if (this.randomInterval > 0 && this.mob.getRandom().nextInt(this.randomInterval) != 0) {
            return false;
        } else {
            this.target = this.mob.level.getNearestPlayer(this.targetConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
            if (target != null) {
            }
            return target != null;
        }
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity livingentity = mob.getTarget();
        if (livingentity == null || !livingentity.isAlive() || !(targetConditions.test(mob, livingentity))) {
            this.target = null;
            return false;
        }
        if (this.mob.getSensing().hasLineOfSight(livingentity)) {
            this.unseenTicks = 0;
        } else {return ++this.unseenTicks <= reducedTickDelay(this.unseenMemoryTicks);}
        return true;
    }

    @Override
    public void start() {
        super.start();
        mob.setTarget(this.target);
    }
}
