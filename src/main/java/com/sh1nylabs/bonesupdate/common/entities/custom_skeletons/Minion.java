package com.sh1nylabs.bonesupdate.common.entities.custom_skeletons;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.common.entities.goal.MinionFollowsOwnerGoal;
import com.sh1nylabs.bonesupdate.common.entities.necromancy.Necromancer;
import com.sh1nylabs.bonesupdate.common.items.NecroScepterItem;
import com.sh1nylabs.bonesupdate.common.unclassed.CanSummonMinions;
import com.sh1nylabs.bonesupdate.init.BonesItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;

public class Minion extends FriendlySkeleton {
    Necromancer owner; //TODO: right now, the owner can only be a Necromancer. Potentially extend this to Player

    public Minion(EntityType<? extends AbstractSkeleton> type, Level level) {
        super(type, level);
        this.xpReward = 1;
        this.owner = null;
    }

    @Override
    public boolean isSunBurnTick() {
        return false;
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int lootingLevel, boolean hurtByPlayer) {}

    public static AttributeSupplier.Builder getCustomAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH,2.0D) //FIX_VALUE
                .add(Attributes.ATTACK_DAMAGE, 1.5D) //FIX_VALUE
                .add(Attributes.MOVEMENT_SPEED, 0.2F) //FIX_VALUE
                .add(Attributes.ATTACK_KNOCKBACK,0D); //FIX_VALUE
    }

    public void registerGoals() {

        this.goalSelector.addGoal(2, new MeleeAttackGoal(this,1.7D,false));  //FIX_VALUE (speedModifier)
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Wolf.class, 6.0F, 1.0D, 1.2D)); //FIX_VALUE (radius of search / walkSpeedModif / sprintspeedModif)
        this.goalSelector.addGoal(4, new MinionFollowsOwnerGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Necromancer.class, 5.0F)); //FIX_VALUE (radius of search)
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F)); //FIX_VALUE (radius of search)
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

        this.registerSkeletonTargets();

}

    @Override
    protected SoundEvent getStepSound() {
        return SoundEvents.SKELETON_STEP;
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource rdmSequence, DifficultyInstance difficulty) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BonesItems.MINION_SWORD.get()));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnData) {
        this.populateDefaultEquipmentSlots(level.getRandom(), difficulty);
        if (spawnData instanceof MinionData minionData) {
            this.setFriendly(minionData.summoningWay instanceof NecroScepterItem);
            if (minionData.summoningWay instanceof Necromancer necromancer) {
                this.setOwner(necromancer);
                necromancer.addMinionToStock(-1);
            } else {
                this.setOwner(null);
            }
        }
        return spawnData;
    }

    public boolean checkSpawnRules(LevelAccessor level, MobSpawnType spawnType) {
        return spawnType != MobSpawnType.NATURAL;
    }

    public void setOwner(@Nullable Necromancer necromancer) {
        this.owner = necromancer;
    }
    public Necromancer getOwner() {
        return this.owner;
    }

    @Override
    public void die(DamageSource source) {
        try {
            this.owner.addMinionToStock(1);
        } catch (Exception ignored) {}
        super.die(source);
    }

    public static class MinionData implements SpawnGroupData {
        public CanSummonMinions summoningWay;

        public MinionData(CanSummonMinions summoningWay) {
            this.summoningWay = summoningWay;
        }
    }
}
