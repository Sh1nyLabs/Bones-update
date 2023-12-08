package com.sh1nylabs.bonesupdate.common.entities.goal;

import com.sh1nylabs.bonesupdate.common.blocks.GraveBlockEntity;
import com.sh1nylabs.bonesupdate.common.entities.necromancy.Necromancer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;

public class NecromancerApproachAGraveGoal extends MoveToBlockGoal {

    public NecromancerApproachAGraveGoal(Necromancer mob, double speedModif, int searchRange, int vertSearchRange) {
        super(mob, speedModif, searchRange, vertSearchRange);
    }

    @Override
    public double acceptedDistance() {
        return 2.0D;
    }

    public boolean canContinueToUse() {
        return !isReachedTarget() && super.canContinueToUse();
    }

    @Override
    protected boolean isValidTarget(LevelReader level, BlockPos blockPos) {
        return (level.getBlockEntity(blockPos) instanceof GraveBlockEntity grave && grave.canSummonMinion());
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isReachedTarget()) { // the target might have been reached but can have been destroyed.
            mob.getNavigation().stop();
            if (mob.getLevel().getBlockEntity(this.blockPos) instanceof GraveBlockEntity) {
                ((Necromancer)mob).gravePosition = blockPos;
            }
        }
    }
}