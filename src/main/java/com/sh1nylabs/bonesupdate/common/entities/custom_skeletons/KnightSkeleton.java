package com.sh1nylabs.bonesupdate.common.entities.custom_skeletons;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.entities.goal.KnightSkeletonDashesGoal;
import com.sh1nylabs.bonesupdate.init.BonesParticles;
import com.sh1nylabs.bonesupdate.init.BonesSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;

public class KnightSkeleton extends FriendlySkeleton {
    private static final int DASH_RESET_DURATION = 130;
    private static final int DASH_WARM_UP_TIME = 60; // FIX_VALUE
    private static final float DASH_BONUS_DAMAGE = 10.0F;
    private static final EntityDataAccessor<Boolean> IS_DASHING = SynchedEntityData.defineId(KnightSkeleton.class, EntityDataSerializers.BOOLEAN);
    private int dashCooldown = DASH_RESET_DURATION;
    private int warmUpTime = DASH_WARM_UP_TIME;
    private int particleSpawnDuration = DASH_WARM_UP_TIME - 17;

    public KnightSkeleton(EntityType<? extends AbstractSkeleton> entityType, Level level) {super(entityType, level);}

    public static AttributeSupplier.Builder getCustomAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH,20.0D) //FIX_VALUE
                .add(Attributes.ATTACK_DAMAGE, 6.0D) //FIX_VALUE
                .add(Attributes.MOVEMENT_SPEED, 0.21F) //FIX_VALUE
                .add(Attributes.FOLLOW_RANGE,20.0F); //FIX_VALUE
    }
    @Override
    protected SoundEvent getAmbientSound() {
        return BonesSounds.KNIGHT_SKELETON_AMBIENT.get();
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return BonesSounds.KNIGHT_SKELETON_HURT.get();
    }
    @Override
    protected SoundEvent getDeathSound() {
        return BonesSounds.KNIGHT_SKELETON_DEATH.get();
    }

    @Override
    protected SoundEvent getStepSound() {
        return BonesSounds.KNIGHT_SKELETON_STEP.get();
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_DASHING, false);
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("DashCooldown", this.dashCooldown);
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.dashCooldown = compoundTag.getInt("DashCooldown");
    }

    public void setIsDashing(boolean bool) {
        this.entityData.set(IS_DASHING,bool);
    }
    public boolean isDashing() {return this.entityData.get(IS_DASHING);}

    @Override
    public void registerGoals() {

        super.registerGoals();

        this.goalSelector.addGoal(4, new KnightSkeletonDashesGoal(this,1.3D,false));
        this.goalSelector.addGoal(5, new AvoidEntityGoal<>(this, Wolf.class, 6.0F, 1.0D, 1.2D)); //FIX_VALUE (radius of search / walkSpeedModif / sprintspeedModif)
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F)); //FIX_VALUE (radius of search)
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource rdmSequence, DifficultyInstance difficulty) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag tag) {
        this.populateDefaultEquipmentSlots(level.getRandom(), difficulty);
        return spawnData;
    }
    public boolean canDash() {return this.dashCooldown<0;}

    public void resetDashCooldown() {
        this.dashCooldown = DASH_RESET_DURATION;
        this.setIsDashing(false);
    }

    @Override
    public void tick() {
        if (level().isClientSide()) {
            if (isDashing()) { /* stuff to stop showing particles at appropriate time*/
                if (particleSpawnDuration>=0) {
                    particleSpawnDuration--;
                    spawnWarmUpParticles();
                }
            } else {
                 particleSpawnDuration = DASH_WARM_UP_TIME - 17;
            }
        }
        if (dashCooldown >= 0) {
            this.dashCooldown--;
        }
        super.tick();
    }

    public void spawnWarmUpParticles() { /* Only Client-sided */
        float f1 = (float) (2*Mth.PI*random.nextDouble());
        double d1 = 0.2 + 0.65*random.nextDouble();
        level().addParticle(BonesParticles.PURPLE_BAR.get(),
                this.getX() + d1*Mth.cos(f1),
                this.getY(),
                this.getZ() + d1*Mth.sin(f1),
                0.0D, 0.15D, 0.0D);
    }

    /**
     * Function overriden in order to introduce dash bonus damage.
     * @param entity : entity hurt
     * @return boolean
     */
    @Override
    public boolean doHurtTarget(Entity entity) {
        float hurtAmount = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        if (this.isDashing()) {
            hurtAmount += DASH_BONUS_DAMAGE;
        }
        float f1 = (float)this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        if (entity instanceof LivingEntity) {
            hurtAmount += EnchantmentHelper.getDamageBonus(this.getMainHandItem(), ((LivingEntity)entity).getMobType());
            f1 += (float)EnchantmentHelper.getKnockbackBonus(this);
        }

        int i = EnchantmentHelper.getFireAspect(this);
        if (i > 0) {
            entity.setSecondsOnFire(i * 4);
        }

        boolean flag = entity.hurt(this.damageSources().mobAttack(this), hurtAmount);
        if (flag) {
            if (f1 > 0.0F && entity instanceof LivingEntity) {
                ((LivingEntity)entity).knockback((double)(f1 * 0.5F), (double) Mth.sin(this.getYRot() * ((float)Math.PI / 180F)), (double)(-Mth.cos(this.getYRot() * ((float)Math.PI / 180F))));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
            }

            this.doEnchantDamageEffects(this, entity);
            this.setLastHurtMob(entity);
        }

        return flag;
    }

    public void reInitWarmUpTime(int value) {this.warmUpTime = value;}
    public void reInitWarmUpTime() {reInitWarmUpTime(DASH_WARM_UP_TIME);}
    public void tickWarmupTime() {this.warmUpTime--;}
    public int getWarmUpTime() {return warmUpTime;}
}
