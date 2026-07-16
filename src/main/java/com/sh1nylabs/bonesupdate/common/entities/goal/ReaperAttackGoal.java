package com.sh1nylabs.bonesupdate.common.entities.goal;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.entities.necromancy.Reaper;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
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
        this.reaper.getNavigation().moveTo(target.getX(), target.getY(), target.getZ(), 1.0D);
    }

    @Override
    public void stop() {
        reaper.setTarget(null);
        reaper.getNavigation().stop();
    }

    @Override
    public void tick() {
        LivingEntity target = reaper.getTarget();
        if (target != null) {
            ticksUntilNextAttack--;
            if (reaper.getBoundingBox().inflate(1.8D).intersects(target.getBoundingBox())) {
                if (ticksUntilNextAttack <= 0) {
                    reaper.swing(InteractionHand.MAIN_HAND);
                    reaper.doHurtTarget(getServerLevel(target), target);
                    target.addEffect(new MobEffectInstance(MobEffects.WITHER, 100), reaper);
                    ticksUntilNextAttack =  10 * (int) reaper.getAttributeValue(Attributes.ATTACK_SPEED);

                }
            } else {
                this.reaper.getNavigation().moveTo(target.getX(), target.getY(), target.getZ(), 1.0D);
            }
        }
    }
}
