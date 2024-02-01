package com.sh1nylabs.bonesupdate.common.entities.goal;

import com.sh1nylabs.bonesupdate.common.entities.necromancy.Reaper;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;


public class ReaperAttackGoal extends Goal {
    Reaper reaper;
    int ticksUntilNextAttack;

    public ReaperAttackGoal(Reaper reaper) {
        this.reaper = reaper;}
    @Override
    public boolean canUse() {
        return reaper.getTarget() != null;
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity livingentity = reaper.getTarget();
        if (livingentity == null) {
            return false;
        } else if (!livingentity.isAlive()) {
            return false;
        } else if (livingentity instanceof Player player && (player.isSpectator() || player.isCreative())) {
                return false;
        } else {
            return (reaper.distanceTo(livingentity) < 40.0D);
        }
    }

    @Override
    public void start() {
        LivingEntity target = reaper.getTarget();
        ticksUntilNextAttack = 30;
        this.reaper.getMoveControl().setWantedPosition(target.getX(), target.getY(), target.getZ(), 1.0D);
    }

    @Override
    public void stop() {
        reaper.setTarget(null);
    }

    @Override
    public void tick() {
        LivingEntity target = reaper.getTarget();
        if (target != null) {
            ticksUntilNextAttack--;
            if (reaper.getBoundingBox().inflate(1.5D).intersects(target.getBoundingBox())) {
                if (ticksUntilNextAttack <= 0) {
                    reaper.doHurtTarget(target);
                    ticksUntilNextAttack = 12;
                }
            } else {
                this.reaper.getMoveControl().setWantedPosition(target.getX(), target.getY(), target.getZ(), 1.0D);
            }
        }
    }

    public static class ReaperMoveControl extends MoveControl {
        private int floatDuration;

        public ReaperMoveControl(Mob mob) {
            super(mob);
        }

        public void tick() {
            if (this.operation == MoveControl.Operation.MOVE_TO) {

                Vec3 vec3;
                if (mob.getLevel().getBlockState(mob.getOnPos()).isAir() && mob.getLevel().getBlockState(mob.getOnPos().below()).isAir()) {
                    vec3 = new Vec3(this.wantedX - this.mob.getX(),
                                    this.wantedY - this.mob.getY(),
                                    this.wantedZ - this.mob.getZ());
                } else {
                    vec3 = new Vec3((this.wantedX - this.mob.getX()) * 0.1D,
                                    this.wantedY - this.mob.getY() + 1.0D,
                                    (this.wantedZ - this.mob.getZ()) * 0.1D);
                }
                double d0 = vec3.length();
                vec3 = vec3.normalize();
                if (this.canReach(vec3, Mth.ceil(d0))) {
                    this.mob.setDeltaMovement(this.mob.getDeltaMovement().add(vec3.scale(0.01D)));
                    float f9 = (float)(Mth.atan2(this.wantedZ - this.mob.getZ(), this.wantedX - this.mob.getX()) * (double)(180F / (float)Math.PI)) - 90.0F;
                    this.mob.setYRot(this.rotlerp(this.mob.getYRot(), f9, 90.0F));
                } else {
                    this.operation = MoveControl.Operation.WAIT;
                }
            }
        }

        private boolean canReach(Vec3 p_32771_, int p_32772_) {
            AABB aabb = this.mob.getBoundingBox();

            for(int i = 1; i < p_32772_; ++i) {
                aabb = aabb.move(p_32771_);
                if (!this.mob.level.noCollision(this.mob, aabb)) {
                    return false;
                }
            }

            return true;
        }
    }
}
