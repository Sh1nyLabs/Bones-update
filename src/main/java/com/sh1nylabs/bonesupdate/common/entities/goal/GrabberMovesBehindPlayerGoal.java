package com.sh1nylabs.bonesupdate.common.entities.goal;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.BUConfig;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.Grabber;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.function.Predicate;

public class GrabberMovesBehindPlayerGoal extends AvoidEntityGoal<Player> {

    public GrabberMovesBehindPlayerGoal(Grabber grabber, float maxDist, double walkModifier, double sprintModifier, Predicate<LivingEntity> predicate) {
        super(grabber, Player.class, maxDist, walkModifier, sprintModifier, predicate);
    }

    @Override
    public boolean canUse() {
        this.toAvoid = this.mob
                .level()
                .getNearestEntity(
                        this.mob
                                .level()
                                .getEntitiesOfClass(this.avoidClass, this.mob.getBoundingBox().inflate(this.maxDist, 3.0, this.maxDist), player -> ((Grabber) mob).playerCanSeeMe(player)),
                        TargetingConditions.forCombat().range(this.maxDist).selector(EntitySelector.NO_CREATIVE_OR_SPECTATOR::test),
                        this.mob,
                        this.mob.getX(),
                        this.mob.getY(),
                        this.mob.getZ()
                );
        if (this.toAvoid == null) {
            return false;
        } else {
            return this.calculateNavDestination(this.toAvoid.getViewVector(1.0F).normalize(), this.mob.position(), this.toAvoid.position());
        }
    }

    private boolean calculateNavDestination(Vec3 vecView, Vec3 mobPosition, Vec3 playerPosition) {
        Vec3 vecDist = mobPosition.subtract(playerPosition);
        double projLength = vecDist.x*vecView.x + vecDist.z*vecView.z;

        double multFactor = 1.2D * Math.abs(projLength * Math.tan(BUConfig.grabber_ceil_heading_angle));
        Vec3 directionVector = (new Vec3(vecDist.x - vecView.x * projLength, 0, vecDist.z - vecView.z * projLength)).multiply(multFactor - 1, 1, multFactor - 1);

        BlockPos blockpos = RandomPos.generateRandomPosTowardDirection(mob, 4, mob.getRandom(), BlockPos.containing(directionVector));
        if (blockpos == null) {
            return false;
        } else if (playerPosition.distanceToSqr(blockpos.getX(), blockpos.getY(), blockpos.getZ()) < playerPosition.distanceToSqr(mobPosition)) {
            return false;
        } else {
            this.path = this.pathNav.createPath(blockpos.getX(), blockpos.getY(), blockpos.getZ(), 0);
            return this.path != null;
        }
    }

    @Override
    public boolean canContinueToUse() {
        return !this.pathNav.isDone() && ((Grabber) this.mob).playerCanSeeMe(this.toAvoid);
    }
}
