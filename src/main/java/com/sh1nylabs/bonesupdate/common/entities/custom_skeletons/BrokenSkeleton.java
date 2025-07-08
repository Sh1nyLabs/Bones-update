package com.sh1nylabs.bonesupdate.common.entities.custom_skeletons;

import com.sh1nylabs.bonesupdate.common.items.AmuletItem;
import com.sh1nylabs.bonesupdate.registerer.BonesRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Bogged;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootTable;

import javax.annotation.Nullable;

public class BrokenSkeleton extends AbstractSkeleton {
    private int timeBeforeSkeletonRevives;
    private LivingEntity inheritedKillCredit;
    private boolean friendly = false;
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(BrokenSkeleton.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> DATA_BOGGED_SHEARED = SynchedEntityData.defineId(BrokenSkeleton.class, EntityDataSerializers.BOOLEAN);

    public BrokenSkeleton(EntityType<? extends AbstractSkeleton> type, Level level) {
        super(type, level);
    }

    protected void defineSynchedData(SynchedEntityData.Builder syncBuilder) {
        super.defineSynchedData(syncBuilder);
        syncBuilder.define(DATA_ID_TYPE_VARIANT, 1);
        syncBuilder.define(DATA_BOGGED_SHEARED, false);
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Variant", this.entityData.get(DATA_ID_TYPE_VARIANT));
        compoundTag.putBoolean("sheared", this.boggedIsSheared());
        compoundTag.putInt("TimeToRevive", this.timeBeforeSkeletonRevives);
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.entityData.set(DATA_ID_TYPE_VARIANT, compoundTag.getInt("Variant"));
        this.entityData.set(DATA_BOGGED_SHEARED, compoundTag.getBoolean("sheared"));
        this.timeBeforeSkeletonRevives = compoundTag.getInt("TimeToRevive");
    }

    public static AttributeSupplier.Builder getCustomAttributes() {
        return (Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 55.0D));
    }

    public boolean boggedIsSheared() {
        return this.entityData.get(DATA_BOGGED_SHEARED);
    }

    @Override
    protected void dropAllDeathLoot(ServerLevel level, DamageSource damageSource) {
        boolean playerGotAmulet = (damageSource.getEntity() instanceof Player player &&
                (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof AmuletItem ||
                        player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof AmuletItem));
        if (damageSource.getEntity() instanceof Creeper || playerGotAmulet) {
            super.dropAllDeathLoot(level, damageSource);
        }
        if (playerGotAmulet) {
            this.spawnAtLocation(BonesRegistry.SKELETON_SOUL.item());
        }
    }

    /**
     * Function overriden in order to drop skulls even when broken
     * @param damageSource
     * @param hurtByPlayer
     */
    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource damageSource, boolean hurtByPlayer) {
        super.dropCustomDeathLoot(level, damageSource, hurtByPlayer);
        if (getSkeletonType() == EntityType.WITHER_SKELETON) {
            this.spawnAtLocation(Items.WITHER_SKELETON_SKULL);
        } else if (getSkeletonType() != EntityType.BOGGED){
            this.spawnAtLocation(Items.SKELETON_SKULL);
        }
    }

    public void playRevivingSound() {
        SoundEvent soundevent = BonesRegistry.BROKEN_SKELETON_REVIVES.get();
        this.playSound(soundevent, this.getSoundVolume(), this.getVoicePitch());
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide()) {
            if (timeBeforeSkeletonRevives <= 0 && this.isAlive()) {
                ServerLevel svrLevel = (ServerLevel) this.level();
                AbstractSkeleton skeleton = getSkeletonType().create(svrLevel);
                if (skeleton!=null) {
                    skeleton.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                    for(MobEffectInstance mobeffectinstance : this.getActiveEffectsMap().values()) {
                        skeleton.getActiveEffectsMap().put(mobeffectinstance.getEffect(), mobeffectinstance);
                    }
                    if (skeleton instanceof FriendlySkeleton friendlySk) {
                        friendlySk.setFriendly(friendly);
                    }
                    if (skeleton instanceof Bogged bogged) {
                        bogged.setSheared(boggedIsSheared());
                    }
                    net.minecraftforge.event.ForgeEventFactory.onFinalizeSpawn(skeleton, svrLevel, svrLevel.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.CONVERSION, null);
                    if (this.getMainHandItem() != ItemStack.EMPTY) {
                        skeleton.setItemInHand(InteractionHand.MAIN_HAND,this.getMainHandItem());
                    }
                    if (this.getOffhandItem() != ItemStack.EMPTY) {
                        skeleton.setItemInHand(InteractionHand.OFF_HAND,this.getOffhandItem());
                    }

                    skeleton.setRemainingFireTicks(this.getRemainingFireTicks());

                    net.minecraftforge.event.ForgeEventFactory.onLivingConvert(this, skeleton);
                    svrLevel.addFreshEntityWithPassengers(skeleton);
                    svrLevel.gameEvent(skeleton, GameEvent.ENTITY_PLACE, this.blockPosition());
                    this.discard();
                    svrLevel.sendParticles(BonesRegistry.PURPLE_SOUL.particle(),
                            skeleton.getX(), skeleton.getY() + 0.5D, skeleton.getZ(),
                            50, 0.0D, 0.1D, 0.0D, 0.20D);
                    this.playRevivingSound();

                }
            } else {
                timeBeforeSkeletonRevives--;}
        }
        super.tick();
    }

    @Override
    public void die(DamageSource damageSource) {
        if (!this.isRemoved() && !this.dead && this.level() instanceof ServerLevel) {
            if (damageSource.getEntity() instanceof Player player) {
                player.awardStat(Stats.ENTITY_KILLED.get(this.getSkeletonType()));
            }
        }
        super.die(damageSource);
    }

    @Override
    public ResourceKey<LootTable> getDefaultLootTable() {
        return getSkeletonType().getDefaultLootTable();
    }

    @Override
    public LivingEntity getKillCredit() {
        return (inheritedKillCredit != null ? inheritedKillCredit : super.getKillCredit());
    }

    @Override
    public boolean canBeSeenAsEnemy() {
        return false;
    }

    @Override
    public boolean isSunBurnTick() {
        return getSkeletonType() != EntityType.WITHER_SKELETON && super.isSunBurnTick();
    }

    @Override
    public boolean isImmobile() {
        return true;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return (!damageSource.is(DamageTypeTags.IS_FIRE)  && !damageSource.is(DamageTypeTags.IS_EXPLOSION)) || super.isInvulnerableTo(damageSource);
    }

    @Override
    protected SoundEvent getStepSound() {
        return SoundEvents.SKELETON_STEP;
    }

    public final String getSkeletonTypeString() {
        return getSkeletonType() == null ? "none" : getSkeletonType().toString();
    }

    public final EntityType<? extends AbstractSkeleton> getSkeletonType() {
        return switch (this.entityData.get(DATA_ID_TYPE_VARIANT)) { /* case 1 is SKELETON */
            case 2 -> EntityType.STRAY;
            case 3 -> EntityType.WITHER_SKELETON;
            case 4 -> BonesRegistry.HAUNTER_SKELETON.type();
            case 5 -> BonesRegistry.KNIGHT_SKELETON.type();
            case 6 -> EntityType.BOGGED;
            default -> EntityType.SKELETON;
        };
    }

    public final void setSkeletonType(EntityType<? extends AbstractSkeleton> skeletonType) {
        this.entityData.set(DATA_ID_TYPE_VARIANT, switch (skeletonType.toString()) {
            case "entity.minecraft.stray" -> 2;
            case "entity.minecraft.wither_skeleton" -> 3;
            case "entity.bonesupdate.haunter_skeleton" -> 4;
            case "entity.bonesupdate.knight_skeleton" -> 5;
            case "entity.minecraft.bogged" -> 6;
            default -> 1;
        });
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnData) {
        timeBeforeSkeletonRevives = 905 + random.nextInt(200);
        if (spawnData instanceof BrokenSkeletonSpawnData skeletonData) { /* Defining which skeleton to create after revival */
            setSkeletonType(skeletonData.skeletonType);
            this.friendly = skeletonData.friendly;
            this.entityData.set(DATA_BOGGED_SHEARED, skeletonData.bogged_sheared);
            this.inheritedKillCredit = skeletonData.inheritedKillCredit;
            this.setItemInHand(InteractionHand.MAIN_HAND,skeletonData.mainHandItem);
            this.setItemInHand(InteractionHand.OFF_HAND,skeletonData.offHandItem);

            this.setRemainingFireTicks(skeletonData.remainingFireTicks);
            for(int i = 0; i < skeletonData.listtag.size(); ++i) {
                CompoundTag compoundtag = skeletonData.listtag.getCompound(i);
                MobEffectInstance mobeffectinstance = MobEffectInstance.load(compoundtag);
                if (mobeffectinstance != null) {
                    this.getActiveEffectsMap().put(mobeffectinstance.getEffect(), mobeffectinstance);
                }
            }
        } else {
            if (this.level().dimension() == Level.NETHER) {
                this.entityData.set(DATA_ID_TYPE_VARIANT, 1);
            } else {
                this.entityData.set(DATA_ID_TYPE_VARIANT, 4 + random.nextInt(4));
                if (getSkeletonType()==BonesRegistry.HAUNTER_SKELETON.type()) {
                    this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BonesRegistry.HAUNTER_SPEAR.item()));
                }
            }
        }
        return spawnData;
    }

    public static class BrokenSkeletonSpawnData implements SpawnGroupData {
        public EntityType<? extends AbstractSkeleton> skeletonType;
        public ListTag listtag;
        public LivingEntity inheritedKillCredit;
        public ItemStack mainHandItem;
        public ItemStack offHandItem;
        public int remainingFireTicks;
        public boolean friendly;
        public boolean bogged_sheared = false;

        public BrokenSkeletonSpawnData(AbstractSkeleton entity) {
            this.skeletonType = (EntityType<? extends AbstractSkeleton>) entity.getType();
            this.listtag = new ListTag();
            this.remainingFireTicks = entity.getRemainingFireTicks();
            this.inheritedKillCredit = entity.getKillCredit();
            this.mainHandItem = entity.getMainHandItem();
            this.offHandItem = entity.getOffhandItem();
            this.friendly = (entity instanceof FriendlySkeleton friendlySk && friendlySk.isFriendly());
            this.bogged_sheared = (entity instanceof Bogged bogged && bogged.isSheared());

            for(MobEffectInstance mobeffectinstance : entity.getActiveEffectsMap().values()) {
                listtag.add(mobeffectinstance.save());
            }
        }
    }
}
