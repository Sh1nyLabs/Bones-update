package com.sh1nylabs.bonesupdate.common.entities.goal;

/* Java class written by sh1nylabs' team. All rights reserved. */


import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.Grabber;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.EnumSet;
import java.util.function.Predicate;

public class NearestStealableTargetGoal extends TargetGoal {

    private Player target;
    private final TargetingConditions targetConditions;

    private final int randomInterval;
    private int unseenTicks;
    protected int unseenMemoryTicks = 60;
    private final Predicate<Player> grabberWantsItem = player -> this.mob instanceof Grabber grabber &&
            (grabber.wantsItem(player.getMainHandItem().getItem()) || grabber.wantsItem(player.getOffhandItem().getItem()));

    public NearestStealableTargetGoal(Grabber grabber) {
        super(grabber, true, false);
        this.randomInterval = reducedTickDelay(20);
        this.target = null;
        this.setFlags(EnumSet.of(Goal.Flag.TARGET));
        this.targetConditions = TargetingConditions.forNonCombat().range(20.0D);
    }

    @Override
    public boolean canUse() {
        if (this.mob.getItemInHand(InteractionHand.OFF_HAND) != ItemStack.EMPTY) {
            return false;
        }
        if (this.randomInterval > 0 && this.mob.getRandom().nextInt(this.randomInterval) != 0) {
            return false;
        } else {
            this.target = getServerLevel(this.mob).getNearestEntity(this.mob
                            .level()
                            .getEntitiesOfClass(Player.class, this.mob.getBoundingBox().inflate(20.0D, 3.0D, 20.0D),
                                    player -> grabberWantsItem.test((Player) player) && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(player)),
                            this.targetConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
            if (target != null) {
            }
            return target != null;
        }
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity livingentity = mob.getTarget();
        if (livingentity == null || !livingentity.isAlive() || !(grabberWantsItem.test((Player) livingentity))) {
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
