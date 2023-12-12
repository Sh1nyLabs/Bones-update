package com.sh1nylabs.bonesupdate.common.entities.goal;

import com.mojang.logging.LogUtils;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.Minion;
import net.minecraft.world.entity.ai.goal.Goal;
import org.slf4j.Logger;


public class MinionFollowsOwnerGoal extends Goal {
    private final Minion minion;
    private int timeToRecalcPath;
    private static final Logger LOGGER = LogUtils.getLogger();

    public MinionFollowsOwnerGoal(Minion minion) {
        this.minion=minion;
    }

    public boolean canUse() {return (this.minion.getOwner()!=null && minion.getLevel().getRandom().nextInt(11)<2);}

    public void start() {
        this.timeToRecalcPath = 0;
    }

    public void tick() {
        if (--this.timeToRecalcPath <= 0) {
            LOGGER.info("updated path navigation to necromancer");
            this.timeToRecalcPath = this.adjustedTickDelay(10);
            this.minion.getNavigation().moveTo(this.minion.getOwner(), 1.0D);
        }
    }
}
