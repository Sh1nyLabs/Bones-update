package com.sh1nylabs.bonesupdate.common.entities.necromancy;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.common.entities.goal.NecromancerApproachAGraveGoal;
import com.sh1nylabs.bonesupdate.common.entities.goal.NecromancerPlacesAGraveGoal;
import com.sh1nylabs.bonesupdate.common.unclassed.CanSummonMinions;
import com.sh1nylabs.bonesupdate.common.blocks.GraveBlockEntity;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.Minion;
import com.sh1nylabs.bonesupdate.init.BonesItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;

public class Necromancer extends AbstractIllager {
    private static final EntityDataAccessor<Byte> DATA_NECRO_SPELL_CASTING_ID = SynchedEntityData.defineId(Necromancer.class, EntityDataSerializers.BYTE);
    private static final int CAST_ANIMATION_TIME = 60; // FIX_VALUE
    private static final int TIME_BETWEEN_TWO_CASTS_MIN = 500; // FIX_VALUE
    private static final int TIME_BETWEEN_TWO_CASTS_MAX = 800; // FIX_VALUE
    private static final int MINION_STOCK_ON_SPAWN = 15; // FIX_VALUE
    private static final int GRAVE_STOCK_ON_SPAWN = 6; // FIX_VALUE
    private int timeBeforeNextCast = 100; // FIX_VALUE
    private int minionStock = MINION_STOCK_ON_SPAWN;
    public int graveStock = GRAVE_STOCK_ON_SPAWN;
    public BlockPos gravePosition = BlockPos.ZERO;

    public Necromancer(EntityType<? extends AbstractIllager> entityType, Level level) {
        super(entityType, level);
    }

    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.timeBeforeNextCast = tag.getInt("CastTicks");
        this.minionStock = tag.getInt("Stock");
        this.graveStock = tag.getInt("Grave_stock");
        this.gravePosition = NbtUtils.readBlockPos(tag, "GravePos").get();
    }

    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("CastTicks", this.timeBeforeNextCast);
        tag.putInt("Stock", this.minionStock);
        tag.putInt("Grave_stock", this.graveStock);
        tag.put("GravePos", NbtUtils.writeBlockPos(this.gravePosition));
    }

    protected void defineSynchedData(SynchedEntityData.Builder syncBuilder) {
        super.defineSynchedData(syncBuilder);
        syncBuilder.define(DATA_NECRO_SPELL_CASTING_ID, (byte)0);
    }

    public static AttributeSupplier.Builder getCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH,25.0D) // FIX_VALUE
                .add(Attributes.MOVEMENT_SPEED, 0.4F); // FIX_VALUE
    }

    @Override
    public void applyRaidBuffs(int waveNb, boolean bool) {}

    public boolean canIncreaseItsArmy() {
        if (timeBeforeNextCast <= 0) {
            return(level().getNearbyEntities(Player.class, TargetingConditions.forNonCombat(),this,getBoundingBox().inflate(20.0D, 8.0D, 20.0D)).isEmpty()?
                    this.minionStock > (int) (3.0*MINION_STOCK_ON_SPAWN/4.0):
                    this.minionStock > 0);
        }
        return false;
    }

    public void addMinionToStock(int quantity) {
        this.minionStock = this.minionStock + quantity;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnData) {
        this.minionStock = MINION_STOCK_ON_SPAWN; // FIX_VALUE
        this.graveStock = GRAVE_STOCK_ON_SPAWN; // FIX_VALUE
        this.populateDefaultEquipmentSlots(level.getRandom(), difficulty);
        return spawnData;
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource rdmSequence, DifficultyInstance difficulty) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BonesItems.NECRO_SCEPTER.get()));
    }

    @Override
    protected float getEquipmentDropChance(EquipmentSlot slot) {
        return 0.0F;
    }

    @Override
    public void tick() {

        if (timeBeforeNextCast != 0) {
            timeBeforeNextCast--;
        }

        if (level().isClientSide() && this.isCastingSpell()) { // Animation when the necromancer moves its scepter
            float f = this.yBodyRot * ((float)Math.PI / 180F) + Mth.cos((float)this.tickCount * 0.6662F) * 0.15F;
            level().addParticle(ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT,0.3F, 0.35F, 0.65F),this.getX() -0.5D * (double) (Mth.cos(f))+0.8D*Mth.sin(0.4F*tickCount),this.getY()+2.5D,this.getZ() - 0.8D * (double) (Mth.sin(f)) + 0.8D*Mth.cos(0.4F*tickCount),0.0D,0.0D,0.0D);
        }
        super.tick();

    }

    @Override
    public SoundEvent getCelebrateSound() {
        return SoundEvents.EVOKER_CELEBRATE;
    }

    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new NecromancerSummons(this));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 8.0F, 0.6D, 0.8D));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, IronGolem.class, 5.0F, 0.6D, 0.8D));
        this.goalSelector.addGoal(2, new NecromancerApproachAGraveGoal(this,0.8D,20,3));
        this.goalSelector.addGoal(2, new NecromancerPlacesAGraveGoal(this,0.8D,10,4));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6D));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));

        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
         }

    public boolean isCastingSpell() {
        return this.entityData.get(DATA_NECRO_SPELL_CASTING_ID) > 0;
    }

    private class NecromancerSummons extends Goal implements CanSummonMinions {
        private final Necromancer necromancer;
        private int warmUpDelay;
        private boolean summonWithAGrave = false;
        public NecromancerSummons(Necromancer mob) {
            super();
            this.necromancer = mob;
            this.warmUpDelay = CAST_ANIMATION_TIME;
        }

        public void delayNextSummon(RandomSource rdmSequence) {
            necromancer.timeBeforeNextCast = TIME_BETWEEN_TWO_CASTS_MIN + rdmSequence.nextInt(TIME_BETWEEN_TWO_CASTS_MAX-TIME_BETWEEN_TWO_CASTS_MIN);
        }

        @Override
        public boolean canUse() {
            if (necromancer.level().getBlockEntity(gravePosition) instanceof GraveBlockEntity grave && grave.necromancerTriggerSummon(CAST_ANIMATION_TIME)) {
                summonWithAGrave = true;
                return true;
            }
            return necromancer.canIncreaseItsArmy();
        }

        @Override
        public boolean canContinueToUse() {
            return warmUpDelay>=0;
        }

        @Override
        public void start() {
            this.warmUpDelay = CAST_ANIMATION_TIME;
            this.necromancer.entityData.set(DATA_NECRO_SPELL_CASTING_ID, (byte) 1);
            this.necromancer.navigation.stop();
        }

        @Override
        public void stop() {
            this.necromancer.entityData.set(DATA_NECRO_SPELL_CASTING_ID, (byte) 0);
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            if (level() instanceof ServerLevel) {
                this.warmUpDelay--;
                if (this.warmUpDelay == 0) {
                    RandomSource rdmSource = level().getRandom();
                    if (!summonWithAGrave) {
                        int rdmQuantity = Math.min(necromancer.minionStock, 2+rdmSource.nextInt(4)); // FIX_VALUE
                        this.summonMinion((ServerLevel) level(), rdmSource,
                                rdmQuantity,
                                necromancer.getOnPos().above(), MobSpawnType.MOB_SUMMONED, new Minion.MinionData(this));
                    }
                    summonWithAGrave = false;
                }
            }
        }
    }

}
