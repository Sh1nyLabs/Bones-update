package com.sh1nylabs.bonesupdate.common.entities.goal;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.Grabber;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.function.Predicate;

public class GrabberCelebratesNewItemGoal extends Goal {
    private Grabber grabber;
    private boolean hasCelebrated = false;
    private int counter;
    private Predicate<Grabber> celebrationCondition;

    public GrabberCelebratesNewItemGoal(Grabber grabber, Predicate<Grabber> celebrationCondition) {
        this.grabber = grabber;
        this.celebrationCondition = celebrationCondition;
    }

    @Override
    public boolean canUse() {
        return !hasCelebrated && celebrationCondition.test(grabber);
    }

    public void start() {
        grabber.setCelebratingNewItem(true);
        counter = 20; //Fix_VALUE
    }

    public void tick() {
        counter--;
        if (counter == 0) {
            grabber.setCelebratingNewItem(false);
            hasCelebrated = true;
            this.stop();
        }
    }


}
