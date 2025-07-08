package com.sh1nylabs.bonesupdate.common.entities.goal;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.common.entities.necromancy.Reaper;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;


public class ReaperAttackGoal extends Goal {
    Reaper reaper;
    int ticksUntilNextAttack = 0;

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
        this.reaper.getMoveControl().setWantedPosition(target.getX(), target.getY(), target.getZ(), 1.0D);
    }

    @Override
    public void stop() {
        ((ReaperMoveControl)reaper.getMoveControl()).waitNextPosition();
        reaper.setTarget(null);
    }

    @Override
    public void tick() {
        LivingEntity target = reaper.getTarget();
        if (target != null) {
            ticksUntilNextAttack--;
            if (reaper.getBoundingBox().inflate(1.8D).intersects(target.getBoundingBox())) {
                if (ticksUntilNextAttack <= 0) {
                    reaper.swing(InteractionHand.MAIN_HAND);
                    reaper.doHurtTarget(target);
                    target.addEffect(new MobEffectInstance(MobEffects.WITHER, 100), reaper);
                    ticksUntilNextAttack =  10 * (int) reaper.getAttributeValue(Attributes.ATTACK_SPEED);
                    ((ReaperMoveControl)reaper.getMoveControl()).waitNextPosition();
                }
            } else {
                this.reaper.getMoveControl().setWantedPosition(target.getX(), target.getY(), target.getZ(), 1.0D);
            }
        }
    }

    public static class ReaperMoveControl extends MoveControl {

        public ReaperMoveControl(Mob mob) {
            super(mob);
        }

        public void tick() {
            if (this.operation == MoveControl.Operation.MOVE_TO) {

                Vec3 vec3;
                if (mob.level().getBlockState(mob.getOnPos()).isAir() && mob.level().getBlockState(mob.getOnPos().below()).isAir()) {
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
                    this.waitNextPosition();
                }
            }
        }

        protected void waitNextPosition() {
            this.operation = MoveControl.Operation.WAIT;
        }

        private boolean canReach(Vec3 vector, int timeStep) {
            AABB aabb = this.mob.getBoundingBox();

            for(int i = 1; i < timeStep; ++i) {
                aabb = aabb.move(vector);
                if (!this.mob.level().noCollision(this.mob, aabb)) {
                    return false;
                }
            }
            return true;
        }
    }
}
