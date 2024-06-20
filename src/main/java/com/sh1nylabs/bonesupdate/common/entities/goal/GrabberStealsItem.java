package com.sh1nylabs.bonesupdate.common.entities.goal;

/* Java class written by sh1nylabs' team. All rights reserved. */


import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.Grabber;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;

public class GrabberStealsItem extends Goal {

    private final Grabber grabber;
    private int tickTimer;

    public GrabberStealsItem(Grabber grabber) {
        this.grabber = grabber;
    }

    @Override
    public boolean canUse() {
        if (grabber.getTarget() == null) {
            return false;
        } else {
            return (grabber.getTarget().distanceToSqr(grabber) < 3.0D);
        }
    }

    @Override
    public void start() {
        tickTimer = 10;
    }


    public void tick() {
        tickTimer --;
        if (tickTimer == 0) {
            InteractionHand hand;
            if (grabber.wantsItem(grabber.getTarget().getMainHandItem().getItem())) {
                hand = InteractionHand.MAIN_HAND;
            } else if (grabber.wantsItem(grabber.getTarget().getOffhandItem().getItem())) {
                hand = InteractionHand.OFF_HAND;
            } else {
                stop();
                return ;
            }
            grabber.setItemInHand(InteractionHand.OFF_HAND,grabber.getTarget().getItemInHand(hand).copy());
            grabber.getTarget().setItemInHand(hand,ItemStack.EMPTY);
        }
    }

}
