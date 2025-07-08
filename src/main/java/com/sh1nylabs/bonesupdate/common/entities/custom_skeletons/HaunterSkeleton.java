package com.sh1nylabs.bonesupdate.common.entities.custom_skeletons;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.common.entities.goal.ProtectKnightGoal;
import com.sh1nylabs.bonesupdate.registerer.BonesRegistry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HaunterSkeleton extends FriendlySkeleton {
    public HaunterSkeleton(EntityType<? extends AbstractSkeleton> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder getCustomAttributes() {
        return (Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ATTACK_DAMAGE,5.0D)
                .add(Attributes.FOLLOW_RANGE, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.21F)
                .add(Attributes.ATTACK_KNOCKBACK, 4.0D));
    }

    @Override
    public void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(4, new ProtectKnightGoal(this,1.2D));

        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.1D,false));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource rdmSequence, DifficultyInstance difficulty) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BonesRegistry.HAUNTER_SPEAR.item()));
    }

    @Override
    protected float getEquipmentDropChance(EquipmentSlot slot) {
        return 0.0F;
    }

    @Override
    protected SoundEvent getStepSound() {
        return BonesRegistry.HAUNTER_SKELETON.step();
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return BonesRegistry.HAUNTER_SKELETON.hurt();
    }
    @Override
    protected SoundEvent getDeathSound() {
        return BonesRegistry.HAUNTER_SKELETON.death();
    }
    @Override
    protected SoundEvent getAmbientSound() {
        return BonesRegistry.HAUNTER_SKELETON.ambient();
    }





}
